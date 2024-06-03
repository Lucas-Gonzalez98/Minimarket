package modelos;

import entidades.Producto;
import persistencia.ProductoDao;
import java.util.ArrayList;

public class ProductoModelo {
    private static ProductoModelo instance;
    private ProductoDao productoDao;

    private ProductoModelo() {
        this.productoDao = ProductoDao.getInstance();
    }

    public static ProductoModelo getInstance() {
        if (instance == null) {
            instance = new ProductoModelo();
        }
        return instance;
    }

    public void insertarProductos(Producto producto) throws Exception {
        productoDao.insertarProductos(producto);
    }
    public void ingresarMercaderia(String nombreProducto, int cantidad) throws Exception {
        productoDao.ingresarMercaderia(nombreProducto, cantidad);
    }

    public void modificarProductoPrecio(Producto producto) throws Exception {
        productoDao.modificarProductoPrecio(producto);
    }

    public void modificarProductoStock(Producto producto) throws Exception {
        productoDao.modificarProductoStock(producto);
    }

    public void eliminarProductoId(Producto producto) throws Exception {
        productoDao.eliminarProductoId(producto);
    }

    public void eliminarProductoNombre(Producto producto) throws Exception {
        productoDao.eliminarProductoNombre(producto);
    }

    public Producto buscarProductoPorId(int id) throws Exception {
        return productoDao.buscarProductoPorId(id);
    }

    public Producto buscarProductoPorNombre(String nombre) throws Exception {
        return productoDao.buscarProductoPorNombre(nombre);
    }

    public ArrayList<Producto> buscarProductos() throws Exception {
        return productoDao.buscarProductos();
    }
}
