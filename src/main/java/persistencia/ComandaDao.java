package persistencia;

import entidades.*;
import entidades.Comanda;
import modelos.ClienteModelo;
import modelos.ComandaModelo;
import modelos.EmpleadoModelo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ComandaDao extends DAO {
    private static final Logger platoMasPedidoLogger = LogManager.getLogger("PlatoMasPedidoLogger");
    private static ComandaDao instance;
    private ComandaModelo comandaModelo;
    private ClienteModelo clienteModelo;
    private EmpleadoModelo empleadoModelo;

    private ComandaDao() {
    }

    public static ComandaDao getInstance() {
        if (instance == null) {
            instance = new ComandaDao();
        }
        return instance;
    }

    public void setComandaModelo(ComandaModelo comandaModelo) {
        this.comandaModelo = comandaModelo;
    }

    public void insertarDatosComandas(Comanda comanda) throws Exception {
        try {
            String ingresarDatosComandas = "INSERT INTO Comanda (cliente_id, empleado_id, nombre, fecha, cantidad, precioComanda) VALUES (?, ?, ?, ?, ?, ?)";
            Connection connection = conectarBase();
            PreparedStatement preparedStatement = connection.prepareStatement(ingresarDatosComandas);
            Random random = new Random();

            String[] NOMBRES = {"Milanesas con papas fritas", "Hamburguesa con papas", "Pancho con papas", "Pizza"};
            Double[] PRECIOS = {10000.00, 8000.00, 6500.00, 7000.00};
            for (int i = 0; i < 13; i++) {
                int clienteId = random.nextInt(15) + 1;
                int empleadoId = random.nextInt(4) + 1;
                Date fecha = Date.valueOf("2024-05-" + (random.nextInt(31) + 1));
                int cantidad = random.nextInt(5) + 1;
                double precio = PRECIOS[random.nextInt(PRECIOS.length)];
                String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
                double total = precio * cantidad;

                preparedStatement.setInt(1, clienteId);
                preparedStatement.setInt(2, empleadoId);
                preparedStatement.setString(3, nombre);
                preparedStatement.setDate(4, fecha);
                preparedStatement.setInt(5, cantidad);
                preparedStatement.setDouble(6, total);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public ArrayList<Comanda> obtenerComandas() throws Exception {
        String comandaVentas = "SELECT cantidad, precioComanda FROM Comanda";
        ArrayList<Comanda> comandas = new ArrayList<>();
        try (Connection conexion = conectarBase();
             PreparedStatement preparedStatement = conexion.prepareStatement(comandaVentas);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int cantidad = resultSet.getInt("cantidad");
                double precioComanda = resultSet.getDouble("precioComanda");
                comandas.add(new Comanda(cantidad, precioComanda));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return comandas;
    }
    public void platoMasPedido(){
        String platoMasPedido = "";
        int cantidad = 0;

        try {
            String query = "SELECT nombre, SUM(cantidad) AS total_cantidad " +
                    "FROM Comanda " +
                    "GROUP BY nombre " +
                    "ORDER BY total_cantidad DESC " +
                    "LIMIT 1";
            Connection connection = conectarBase();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                platoMasPedido = resultSet.getString("nombre");
                cantidad = resultSet.getInt("total_cantidad");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        platoMasPedidoLogger.log(Level.getLevel("PLATO MAS PEDIDO"), "El plato más pedido fué: " + platoMasPedido + " (cantidad: " + cantidad + ")");
    }

    public void ingresarComanda(Comanda comanda){
        String sql = "INSERT INTO Comanda (cliente_id, empleado_id, nombre, fecha, cantidad, precioComanda) " +
                "VALUES ('"+comanda.getIdCliente()+"', '"+comanda.getIdEmpleado()+"', '"+comanda.getNombreComanda()
                +"', '"+comanda.getFechaActual()+"', '"+comanda.getCantidad()+"', '"+comanda.getPrecioComanda()+"')";

        try{
            insertarModificarEliminar(sql);
        }catch (Exception e){
            System.out.println("Error al ingresar comanda. " + e.getMessage());
        }
    }
    public void pagarCuenta() {
        System.out.println("Ingrese el nombre del Empleado a cargo de la comanda");
        Scanner sc = new Scanner(System.in);
        String nombreEmpleado = sc.next();
        System.out.println("Ingrese el apellido del Empleado a cargo de la comanda");
        String apellidoEmpleado = sc.next();
        System.out.println("Ingrese el nombre del Cliente a cargo de la comanda");
        String nombreCliente = sc.next();
        System.out.println("Ingrese el apellido del Cliente a cargo de la comanda");
        String apellidoCliente = sc.next();

        Connection connection = null;
        PreparedStatement preparedStatementBuscarEmpleado = null;
        PreparedStatement preparedStatementBuscarCliente = null;
        PreparedStatement preparedStatementBuscarComanda = null;
        ResultSet resultSetEmpleado = null;
        ResultSet resultSetCliente = null;
        ResultSet resultSetComanda = null;

        try {
            connection = conectarBase();

            // Consulta para obtener las comandas del empleado
            String seleccionarEmpleado = "SELECT * FROM Empleado WHERE nombre = ? AND apellido = ?";
            preparedStatementBuscarEmpleado = connection.prepareStatement(seleccionarEmpleado);
            preparedStatementBuscarEmpleado.setString(1, nombreEmpleado);
            preparedStatementBuscarEmpleado.setString(2, apellidoEmpleado);
            resultSetEmpleado = preparedStatementBuscarEmpleado.executeQuery();
            if (!resultSetEmpleado.next()) {
                System.out.println("No se encontró el Empleado con nombre: " + nombreEmpleado);
                return;
            }
            int idEmpleado = resultSetEmpleado.getInt("id");
            System.out.println("ID del Empleado: " + idEmpleado); // Depuración

            String seleccionarCliente = "SELECT * FROM Cliente WHERE nombre = ? AND apellido = ?";
            preparedStatementBuscarCliente = connection.prepareStatement(seleccionarCliente);
            preparedStatementBuscarCliente.setString(1, nombreCliente);
            preparedStatementBuscarCliente.setString(2, apellidoCliente);
            resultSetCliente = preparedStatementBuscarCliente.executeQuery();
            if (!resultSetCliente.next()) {
                System.out.println("No se encontró el Cliente con nombre: " + nombreCliente);
                return;
            }
            int idCliente = resultSetCliente.getInt("id");
            System.out.println("ID del Cliente: " + idCliente); // Depuración

            String seleccionarComanda = "SELECT * FROM Comanda WHERE empleado_id = ? AND cliente_id = ?";
            preparedStatementBuscarComanda = connection.prepareStatement(seleccionarComanda);
            preparedStatementBuscarComanda.setInt(1, idEmpleado);
            preparedStatementBuscarComanda.setInt(2, idCliente);
            resultSetComanda = preparedStatementBuscarComanda.executeQuery();

            System.out.println("Platos a cargo del empleado: " + nombreEmpleado + " " + apellidoEmpleado);
            boolean comandaEncontrada = false;
            while (resultSetComanda.next()) {
                comandaEncontrada = true;
                int id = resultSetComanda.getInt("id");
                int clienteId = resultSetComanda.getInt("cliente_id");
                String nombre = resultSetComanda.getString("nombre");
                String fecha = resultSetComanda.getString("fecha");
                int cantidad = resultSetComanda.getInt("cantidad");
                double precioComanda = resultSetComanda.getDouble("precioComanda");
                // Mostrar detalles de las comandas
                System.out.println("ID: " + id + ", Cliente ID: " + clienteId + ", Nombre: " + nombre + ", Fecha: " + fecha + ", Cantidad: " + cantidad + ", Total: " + precioComanda);
                // .log(Level.getLevel("CUENTA"), "Se pago la cuenta del cliente: " + nombreCliente + " " + apellidoCliente + " el total es: " + precioComanda);

            }
            if (!comandaEncontrada) {
                System.out.println("No se encontraron comandas para el empleado y cliente especificados.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

