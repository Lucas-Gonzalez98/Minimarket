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
        String[] NOMBRES = {"Leche", "Pan", "Queso", "Manteca", "Yogurt", "Cereal", "Jugo de Naranja", "Café", "Azúcar", "Arroz", "Pasta", "Aceite", "Pollo", "Carne", "Pescado", "Huevos", "Pizza", "Hamburguesa", "Sushi", "Tacos"};
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

    public void ingresarMercaderia(String nombreProducto, int cantidad) throws SQLException {
        String sql = "UPDATE Producto SET stock = stock + ? WHERE nombre = ?";
        try (Connection connection = conectarBase();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cantidad);
            preparedStatement.setString(2, nombreProducto);
            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Stock del producto \"" + nombreProducto + "\" actualizado correctamente.");
            } else {
                System.out.println("No se encontró el producto con nombre: " + nombreProducto);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    public void modificarProductoPrecio(Producto producto) throws Exception {
        String sql = "UPDATE Producto SET precio = '" + producto.getPrecio() + "' WHERE id = '" + producto.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void modificarProductoStock(Producto producto) throws Exception {
        String sql = "UPDATE Producto SET stock = '" + producto.getStock() + "' WHERE id = '" + producto.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void eliminarProductoNombre(Producto producto) throws Exception {
        String sql = "DELETE FROM Producto WHERE nombre ='" + producto.getNombre() + "' ";
        insertarModificarEliminar(sql);
    }

    public void eliminarProductoId(Producto producto) throws Exception {
        String sql = "DELETE FROM Producto WHERE id ='" + producto.getId() + "' ";
        insertarModificarEliminar(sql);
    }

    public Producto buscarProductoPorId(int id) throws Exception {
        String sql = "SELECT * FROM Producto WHERE id = '" + id + "'";
        consultarBase(sql);
        while (resultado.next()) {
            Producto producto = new Producto();
            producto.setId(id);
            producto.setNombre(resultado.getString(2));
            producto.setPrecio(resultado.getInt(3));
            producto.setStock(resultado.getInt(4));
            return producto;
        }
        return null;
    }

    public Producto buscarProductoPorNombre(String nombre) throws Exception {
        String sql = "SELECT * FROM Producto WHERE nombre = '" + nombre + "'";
        consultarBase(sql);
        while (resultado.next()) {
            Producto producto = new Producto();
            producto.setId(resultado.getInt(1));
            producto.setNombre(resultado.getString(2));
            producto.setPrecio(resultado.getInt(3));
            producto.setStock(resultado.getInt(4));
            return producto;
        }
        return null;
    }

    public ArrayList<Producto> buscarProductos() throws Exception {
        String sql = "SELECT * FROM Producto";
        consultarBase(sql);
        ArrayList<Producto> productos = new ArrayList();
        while (resultado.next()) {
            Producto producto = new Producto();
            producto.setId(resultado.getInt(1));
            producto.setNombre(resultado.getString(2));
            producto.setPrecio(resultado.getInt(3));
            producto.setStock(resultado.getInt(4));
            productos.add(producto);
        }
        if (productos.size() == 0) {
            return null;
        }
        return productos;
    }
}
