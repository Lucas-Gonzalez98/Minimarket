package persistencia;

import entidades.Cliente;
import entidades.Empleado;
import entidades.Producto;
import entidades.Venta;
import modelos.ClienteModelo;
import modelos.EmpleadoModelo;
import modelos.ProductoModelo;
import modelos.VentaModelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class VentaDao extends DAO{
    private static VentaDao instance;
    private VentaModelo ventaModelo;
    private final ProductoModelo productoModelo = new ProductoModelo();
    private ClienteModelo clienteModelo;
    private EmpleadoModelo empleadoModelo;

    private VentaDao() {}

    public static VentaDao getInstance() {
        if (instance == null) {
            instance = new VentaDao();
        }
        return instance;
    }

    public void setVentaModelo(VentaModelo ventaModelo) {
        this.ventaModelo = ventaModelo;
    }
    public void insertarDatosVentas(Venta venta) throws Exception {
        try{
            String sql = "INSERT INTO Venta (producto_id, cliente_id, empleado_id, cantidad, fecha, total) VALUES (?, ?, ?, ?, ?, ?)";
            Connection connection = conectarBase();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            Random random = new Random();
            ArrayList<String> nombresProductos = obtenerNombresProductos(connection);

            for (int i = 0; i < 50; i++) {
                String nombreProducto = nombresProductos.get(random.nextInt(nombresProductos.size()));
                int productoId =  obtenerIdProductoPorNombre(nombreProducto, connection);
                int clienteId = random.nextInt(15) + 1;
                int empleadoId = random.nextInt(4) + 1;
                int cantidadVenta = random.nextInt(5) + 1;
                Date fechaVenta = Date.valueOf("2024-05-" + (random.nextInt(31) + 1));

                double precioProducto = obtenerPrecioProductoPorId(productoId, connection);
                double totalVenta = precioProducto * cantidadVenta;

                preparedStatement.setInt(1, productoId);
                preparedStatement.setInt(2, clienteId);
                preparedStatement.setInt(3, empleadoId);
                preparedStatement.setInt(4, cantidadVenta);
                preparedStatement.setDate(5, fechaVenta);
                preparedStatement.setDouble(6, totalVenta);
                preparedStatement.addBatch();

            }

            preparedStatement.executeBatch();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private int obtenerIdProductoPorNombre(String nombreProducto, Connection connection) throws SQLException {
        String sql = "SELECT id FROM Producto WHERE nombre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombreProducto);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        }
        throw new SQLException("Producto no encontrado");
    }
    private double obtenerPrecioProductoPorId(int productoId, Connection connection) throws SQLException {
        String sql = "SELECT precio FROM Producto WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("precio");
            }
        }
        throw new SQLException("Producto no encontrado");
    }
    private ArrayList<String> obtenerNombresProductos(Connection connection) throws SQLException {
        String sql = "SELECT nombre FROM Producto";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            ArrayList<String> nombresProductos = new ArrayList<>();
            while (resultSet.next()) {
                nombresProductos.add(resultSet.getString("nombre"));
            }
            return nombresProductos;
        }
    }

    public void ingresarVenta(int idProducto, String apellidoCliente, String nombreCliente, String nombreEmpleado, int cantidad, Date fecha) throws Exception {
        String buscarProducto = "SELECT id, nombre, precio, stock FROM Producto WHERE id = ?";
        String buscarCliente = "SELECT id FROM Cliente WHERE nombre = ? AND apellido = ?";
        String buscarEmpleado = "SELECT id, apellido, cargo FROM Empleado WHERE nombre = ?";
        String actualizarStockProducto = "UPDATE Producto SET stock = stock - ? WHERE id = ?";
        String insertarCliente = "INSERT INTO Cliente (nombre, apellido, direccion) VALUES (?, ?, ?)";
        String insertarVenta = "INSERT INTO Venta (producto_id, cliente_id, empleado_id, cantidad, fecha, total) VALUES (?, ?, ?, ?, ?, ?)";
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = conectarBase();
             PreparedStatement preparedStatementBuscarProducto = connection.prepareStatement(buscarProducto);
             PreparedStatement preparedStatementBuscarCliente = connection.prepareStatement(buscarCliente);
             PreparedStatement preparedStatementBuscarEmpleado = connection.prepareStatement(buscarEmpleado);
             PreparedStatement preparedStatementActualizarStock = connection.prepareStatement(actualizarStockProducto);
             PreparedStatement preparedStatementInsertarCliente = connection.prepareStatement(insertarCliente, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementInsertarVenta = connection.prepareStatement(insertarVenta)) {

            // Buscar producto
            preparedStatementBuscarProducto.setInt(1, idProducto);
            ResultSet resultSetProducto = preparedStatementBuscarProducto.executeQuery();
            if (!resultSetProducto.next()) {
                System.out.println("No se encontró el producto con ID: " + idProducto);
                return;
            }
            int stockProducto = resultSetProducto.getInt("stock");
            String nombreProducto = resultSetProducto.getString("nombre");

            if (cantidad > stockProducto) {
                System.out.println("No hay suficiente stock del producto con ID: " + idProducto);
                return;
            }

            // Buscar cliente
            preparedStatementBuscarCliente.setString(1, nombreCliente);
            preparedStatementBuscarCliente.setString(2, apellidoCliente);
            ResultSet resultSetCliente = preparedStatementBuscarCliente.executeQuery();
            int clienteId;
            if (!resultSetCliente.next()) {
                // Si el cliente no existe, lo insertamos en la base de datos
                System.out.println("El cliente no existe en la base de datos. Ingresando nuevo cliente...");
                System.out.println("Ingrese la dirección del cliente:");
                String direccionCliente = scanner.nextLine();
                clienteId = insertarNuevoCliente(nombreCliente, apellidoCliente, direccionCliente, preparedStatementInsertarCliente);
            } else {
                clienteId = resultSetCliente.getInt("id");
            }

            // Buscar empleado
            preparedStatementBuscarEmpleado.setString(1, nombreEmpleado);
            ResultSet resultSetEmpleado = preparedStatementBuscarEmpleado.executeQuery();
            if (!resultSetEmpleado.next()) {
                System.out.println("No se encontró el empleado con nombre: " + nombreEmpleado);
                return;
            }
            int empleadoId = resultSetEmpleado.getInt("id");
            String apellidoEmpleado = resultSetEmpleado.getString("apellido");
            String cargoEmpleado = resultSetEmpleado.getString("cargo");

            // Calcular el precio total de la venta
            double precioProducto = obtenerPrecioProductoPorId(idProducto, connection);
            double totalVenta = cantidad * precioProducto;

            // Actualizar stock del producto
            preparedStatementActualizarStock.setInt(1, cantidad);
            preparedStatementActualizarStock.setInt(2, idProducto);
            preparedStatementActualizarStock.executeUpdate();

            // Insertar la venta
            preparedStatementInsertarVenta.setInt(1, idProducto);
            preparedStatementInsertarVenta.setInt(2, clienteId);
            preparedStatementInsertarVenta.setInt(3, empleadoId);
            preparedStatementInsertarVenta.setInt(4, cantidad);
            preparedStatementInsertarVenta.setDate(5, fecha);
            preparedStatementInsertarVenta.setDouble(6, totalVenta);
            preparedStatementInsertarVenta.executeUpdate();

            System.out.println("Venta generada exitosamente:");
            System.out.println("Empleado: " + nombreEmpleado + " " + apellidoEmpleado);
            System.out.println("Cargo del empleado: " + cargoEmpleado);
            System.out.println("Cliente: " + nombreCliente + " " + apellidoCliente);
            System.out.println("Fecha: " + fecha);
            System.out.println("Producto ID: " + idProducto + " Nombre Producto: " + nombreProducto + " Precio Producto: " + precioProducto);
            System.out.println("Cantidad: " + cantidad);
            System.out.println("Total: " + totalVenta);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private int insertarNuevoCliente(String nombreCliente, String apellidoCliente, String direccionCliente, PreparedStatement preparedStatementInsertarCliente) throws SQLException {
        preparedStatementInsertarCliente.setString(1, nombreCliente);
        preparedStatementInsertarCliente.setString(2, apellidoCliente);
        preparedStatementInsertarCliente.setString(3, direccionCliente);
        preparedStatementInsertarCliente.executeUpdate();

        ResultSet rs = preparedStatementInsertarCliente.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        throw new SQLException("Error al insertar cliente");
    }

    public void buscarVentaDiaria(Date fecha) throws SQLException {
        String sql = "SELECT * FROM Venta WHERE fecha = ?";

        try {
            conexion = conectarBase();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setDate(1, new java.sql.Date(fecha.getTime())); // Establecer el parámetro de fecha
            resultado = sentencia.executeQuery();

            int totalVentas = 0;
            while (resultado.next()) {
                int productoId = resultado.getInt(2);
                int cantidad = resultado.getInt(5);
                totalVentas += cantidad;
                Producto producto = productoModelo.buscarProductoPorId(productoId);
                System.out.println("[" + producto.getNombre() + "] - Cantidad vendida:" + cantidad);
            }

            if (totalVentas == 0) {
                System.out.println("No hubo ventas en la fecha seleccionada.");
            } else {
                System.out.println("TOTAL DE VENTAS = " + totalVentas);
            }

        } catch (Exception e) {
            System.out.println("Error al buscar venta." + e.getMessage());
        }

    }

    public void buscarVentaMensual(String mes){
        String sql = "SELECT * FROM Venta WHERE MONTH(FECHA) = '"+mes+"'";

        try{
            conexion = conectarBase();
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);

            int totalVentas = 0;
            while (resultado.next()) {
                int productoId = resultado.getInt(2);
                int cantidad = resultado.getInt(5);
                totalVentas += cantidad;
                Producto producto = productoModelo.buscarProductoPorId(productoId);
                System.out.println("[" + producto.getNombre() + "] - Cantidad vendida:" + cantidad);
            }

            if (totalVentas == 0) {
                System.out.println("No hubo ventas en el mes seleccionado.");
            } else {
                System.out.println("TOTAL DE VENTAS = " + totalVentas);
            }

        }catch (Exception e){
            System.out.println("Error al buscar venta mensual. " + e.getMessage());
        }
    }
    public ArrayList<Venta> obtenerVentas() throws Exception {
        String ventaTotal = "SELECT total FROM Venta";
        ArrayList<Venta> ventas = new ArrayList<>();
        try (Connection conexion = conectarBase();
             PreparedStatement preparedStatement = conexion.prepareStatement(ventaTotal);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                double total = resultSet.getDouble("total");
                ventas.add(new Venta(total));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ventas;
    }
}
