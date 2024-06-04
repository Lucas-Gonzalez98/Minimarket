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
    public void ingresarMercaderia(int id, int cantidad) throws Exception {
        productoDao.ingresarMercaderia(id, cantidad);
    }
    public void listarProductos(){
        productoDao.listarProductos();
    }

}
