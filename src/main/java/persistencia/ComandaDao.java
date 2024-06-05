package persistencia;

import entidades.*;
import entidades.Comanda;
import modelos.ClienteModelo;
import modelos.ComandaModelo;
import modelos.EmpleadoModelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ComandaDao extends DAO {
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
            String query = "SELECT nombre, cantidad FROM Comanda WHERE cantidad = (SELECT MAX(cantidad) FROM Comanda)";
            Connection connection = conectarBase();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                platoMasPedido = resultSet.getString("nombre");
                cantidad = resultSet.getInt("cantidad");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("El plato más pedido es: " + platoMasPedido + " (" + cantidad + " pedidos)");
        //return "hola";
        //return platoMasPedido + " (" + maxConteo + " pedidos)";
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



}

