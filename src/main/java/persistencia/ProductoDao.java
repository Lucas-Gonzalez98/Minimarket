package persistencia;

import entidades.Producto;
import modelos.ProductoModelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ProductoDao extends DAO{
    private static ProductoDao instance;
    private ProductoModelo productoModelo;

    private ProductoDao() {}

    public static ProductoDao getInstance() {
        if (instance == null) {
            instance = new ProductoDao();
        }
        return instance;
    }

    public void setProductoModelo(ProductoModelo productoModelo) {
        this.productoModelo = productoModelo;
    }

    public ProductoDao(ProductoModelo productoModelo) {
        this.productoModelo = productoModelo;
    }
    public void insertarProductos(Producto producto) throws Exception {
        try {
        String ingresarDatosProductos = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)";
        Connection connection = conectarBase();
        PreparedStatement preparedStatement = connection.prepareStatement(ingresarDatosProductos);
        Random random = new Random();
        String[] NOMBRES = {"Leche", "Pan", "Queso", "Manteca", "Yogurt", "Cereal", "Jugo de Naranja", "Café", "Azúcar", "Arroz", "Pasta", "Aceite", "Pollo", "Carne", "Pescado", "Huevos", "Chocolate", "Galletas dulces", "Caramelos", "Chicles"};
            for (int i = 0; i < 20; i++) {
                String nombre = NOMBRES[i];
                double precio = i < 16 ? 2000 + random.nextDouble() * 2000 : 4000 + random.nextDouble() * 4000;
                int stock = 20 + random.nextInt(31);

                preparedStatement.setString(1, nombre);
                preparedStatement.setDouble(2, precio);
                preparedStatement.setInt(3, stock);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void ingresarMercaderia(int id, int cantidad) throws SQLException {
        String cambiarStock = "UPDATE Producto SET stock = stock + ? WHERE id = ?";
        try (Connection connection = conectarBase();
             PreparedStatement preparedStatement = connection.prepareStatement(cambiarStock)) {
            preparedStatement.setInt(1, cantidad);
            preparedStatement.setInt(2, id);
            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Stock actualizado correctamente.");
            } else {
                System.out.println("No se encontró el ID: " + id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void listarProductos() {
        String listaProductos = "SELECT id, nombre FROM Producto";

        try (Connection coneccion = conectarBase();
             PreparedStatement preparedStatement = coneccion.prepareStatement(listaProductos);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                System.out.println("[" + id + "] - " + nombre);
            }
        } catch (Exception e) {
            System.out.println("Error al listar los productos: " + e.getMessage());
        }
    }

    public Producto buscarProductoPorId(int idProducto) {
        String sql = "SELECT * FROM Producto WHERE id='"+idProducto+"'";

        try{
            conexion = conectarBase();
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            Producto producto = null;

            while (resultado.next()){
                producto = new Producto();
                producto.setId(resultado.getInt(1));
                producto.setNombre(resultado.getString(2));
                producto.setPrecio(resultado.getDouble(3));
                producto.setStock(resultado.getInt(4));
            }
            return producto;

        } catch (Exception e){
            System.out.println("Error al buscar producto por Id. " + e.getMessage());
            return null;
        }
    }
    public Producto buscarProductoPorIdNombre(int idProducto) {
        String sql = "SELECT * FROM Producto WHERE id='"+idProducto+"'";

        try{
            conexion = conectarBase();
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
            Producto producto = null;

            while (resultado.next()){
                producto = new Producto();
                producto.setId(resultado.getInt(1));
                producto.setNombre(resultado.getString(2));
                producto.setPrecio(resultado.getDouble(3));
                producto.setStock(resultado.getInt(4));
            }
            return producto;

        } catch (Exception e){
            System.out.println("Error al buscar producto por Id. " + e.getMessage());
            return null;
        }
    }
}
