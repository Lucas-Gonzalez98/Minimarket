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

public class VentaDao extends DAO{
    private static VentaDao instance;
    private VentaModelo ventaModelo;
    private ProductoModelo productoModelo;
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
                int productoId = random.nextInt(20) + 1; // Asumiendo que hay 20 productos
                int clienteId = random.nextInt(10) + 1; // Asumiendo que hay 10 clientes
                int empleadoId = random.nextInt(4) + 1; // Asumiendo que hay 4 empleados
                int cantidadVenta = random.nextInt(5) + 1; // Cantidad entre 1 y 5
                Date fechaVenta = Date.valueOf("2024-05-" + (random.nextInt(31) + 1)); // Entre 1 y 31 de mayo de 2024

                double precioProducto = obtenerPrecioProductoPorNombre(nombreProducto, connection);
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

    private double obtenerPrecioProductoPorNombre(String nombreProducto, Connection connection) throws SQLException {
        String sql = "SELECT precio FROM Producto WHERE nombre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombreProducto);
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

    public void venderProducto(String nombreProducto, String nombreCliente, String nombreEmpleado, int cantidad, Date fecha) throws Exception {
        String buscarProducto = "SELECT id, precio, stock FROM Producto WHERE nombre = ?";
        String buscarCliente = "SELECT id FROM Cliente WHERE nombre = ?";
        String buscarEmpleado = "SELECT id, apellido, cargo FROM Empleado WHERE nombre = ? AND (cargo = 'Vendedor' OR cargo = 'Mesero')";
        String actualizarStockProducto = "UPDATE Producto SET stock = stock - ? WHERE id = ?";
        String insertarVenta = "INSERT INTO Venta (producto_id, cliente_id, empleado_id, cantidad, fecha, total) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection coneccion = conectarBase();
             PreparedStatement preparedStatementBuscarProducto = coneccion.prepareStatement(buscarProducto);
             PreparedStatement preparedStatementBuscarCliente = coneccion.prepareStatement(buscarCliente);
             PreparedStatement preparedStatementBuscarEmpleado = coneccion.prepareStatement(buscarEmpleado);
             PreparedStatement preparedStatementActualizarStock = coneccion.prepareStatement(actualizarStockProducto);
             PreparedStatement preparedStatementInsertarVenta = coneccion.prepareStatement(insertarVenta)) {

            preparedStatementBuscarProducto.setString(1, nombreProducto);
            ResultSet resultSetProducto = preparedStatementBuscarProducto.executeQuery();
            if (!resultSetProducto.next()) {
                System.out.println("No se encontró el producto con nombre: " + nombreProducto);
                return;
            }
            int productoId = resultSetProducto.getInt("id");
            double precioProducto = resultSetProducto.getDouble("precio");
            int stockProducto = resultSetProducto.getInt("stock");

            if (cantidad > stockProducto) {
                System.out.println("No hay suficiente stock del producto \"" + nombreProducto + "\".");
                return;
            }

            preparedStatementBuscarCliente.setString(1, nombreCliente);
            ResultSet resultSetCliente = preparedStatementBuscarCliente.executeQuery();
            if (!resultSetCliente.next()) {
                System.out.println("No se encontró el cliente con nombre: " + nombreCliente);
                return;
            }
            int clienteId = resultSetCliente.getInt("id");

            preparedStatementBuscarEmpleado.setString(1, nombreEmpleado);
            ResultSet resultSetEmpleado = preparedStatementBuscarEmpleado.executeQuery();
            if (!resultSetEmpleado.next()) {
                System.out.println("No se encontró el empleado con nombre: " + nombreEmpleado + " y con cargo Vendedor o Mesero.");
                return;
            }
            int empleadoId = resultSetEmpleado.getInt("id");
            String apellidoEmpleado = resultSetEmpleado.getString("apellido");
            String cargoEmpleado = resultSetEmpleado.getString("cargo");

            double totalVenta = cantidad * precioProducto;

            preparedStatementActualizarStock.setInt(1, cantidad);
            preparedStatementActualizarStock.setInt(2, productoId);
            preparedStatementActualizarStock.executeUpdate();

            preparedStatementInsertarVenta.setInt(1, productoId);
            preparedStatementInsertarVenta.setInt(2, clienteId);
            preparedStatementInsertarVenta.setInt(3, empleadoId);
            preparedStatementInsertarVenta.setInt(4, cantidad);
            preparedStatementInsertarVenta.setDate(5, fecha);
            preparedStatementInsertarVenta.setDouble(6, totalVenta);
            preparedStatementInsertarVenta.executeUpdate();

            System.out.println("Venta generada exitosamente:");
            System.out.println("Empleado: " + nombreEmpleado + " " + apellidoEmpleado);
            System.out.println("Cargo del empleado: " + cargoEmpleado);
            System.out.println("Cliente: " + nombreCliente);
            System.out.println("Producto: " + nombreProducto + " - Precio: " + precioProducto);
            System.out.println("Fecha: " + fecha);
            System.out.println("Total: " + totalVenta);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ingresarVenta(Venta venta) throws Exception {
        String sql = "INSERT INTO Venta(cantidad,fecha,total) VALUES('" + venta.getCantidad() + "', '" + venta.getFecha() + "', '" + venta.getTotal() + "')";
        insertarModificarEliminar(sql);
    }

    public void modificarVentaCantidad(Venta venta) throws Exception {
        String sql = "UPDATE Venta SET cantidad = '" + venta.getCantidad() + "' WHERE id = '" + venta.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void eliminarVenta(Venta venta) throws Exception {
        String sql = "DELETE FROM Venta WHERE id ='" + venta.getId() + "' ";
        insertarModificarEliminar(sql);
    }

    public Venta buscarVentaPorId(int id) throws Exception {
        String sql = "SELECT * FROM Venta WHERE id = '" + id + "'";
        try{
            consultarBase(sql);
            Venta venta = null;
            while (resultado.next()) {
                venta = new Venta();
                venta.setId(resultado.getInt(1));
                int idProducto = resultado.getInt(2);
                Producto producto = productoModelo.buscarProductoPorId(idProducto);
                venta.setProducto(producto);
                int idCliente = resultado.getInt(3);
                Cliente cliente = clienteModelo.buscarClientePorId(idCliente);
                venta.setCliente(cliente);
                int idEmpleado = resultado.getInt(4);
                Empleado empleado = empleadoModelo.buscarEmpleadoPorId(idEmpleado);
                venta.setEmpleado(empleado);
                venta.setCantidad(resultado.getInt(5));
                venta.setFecha(resultado.getDate(6).toLocalDate());
                venta.setTotal(resultado.getInt(7));
            }
            desconectarBase();
            return venta;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Venta> buscarVentas() throws Exception {
        String sql = "SELECT * FROM Venta";
        consultarBase(sql);
        ArrayList<Venta> ventas = new ArrayList();
        try {
            while (resultado.next()) {
                Venta venta = new Venta();
                venta.setId(resultado.getInt(1));
                venta.setProducto(productoModelo.buscarProductoPorId(2));
                venta.setCliente(clienteModelo.buscarClientePorId(3));
                venta.setEmpleado(empleadoModelo.buscarEmpleadoPorId(4));
                venta.setCantidad(resultado.getInt(5));
                venta.setFecha(resultado.getDate(6).toLocalDate());
                venta.setTotal(resultado.getInt(7));
                ventas.add(venta);
            }
            desconectarBase();
            if (ventas.isEmpty()) {
                return null;
            }
            return ventas;
        } catch (Exception e) {
            throw e;
        }
    }
}
