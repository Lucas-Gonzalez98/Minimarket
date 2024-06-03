package modelos;

import entidades.Venta;
import persistencia.VentaDao;

import java.sql.Date;
import java.util.ArrayList;

public class VentaModelo {
    private static VentaModelo instance;
    private VentaDao ventaDao;

    private VentaModelo() {
        this.ventaDao = VentaDao.getInstance();
    }

    public static VentaModelo getInstance() {
        if (instance == null) {
            instance = new VentaModelo();
        }
        return instance;
    }

    public void insertarDatosVentas(Venta venta) throws Exception {
        ventaDao.insertarDatosVentas(venta);
    }

    public void ingresarVenta(String nombreProducto, String nombreCliente, String nombreEmpleado, int cantidad, Date fecha) throws Exception {
        ventaDao.venderProducto(nombreProducto, nombreCliente, nombreEmpleado, cantidad, fecha);
    }

    public void modificarVentaCantidad(Venta venta) throws Exception {
        ventaDao.modificarVentaCantidad(venta);
    }

    public void eliminarVenta(Venta venta) throws Exception {
        ventaDao.eliminarVenta(venta);
    }

    public Venta buscarVentaPorId(int id) throws Exception {
        return ventaDao.buscarVentaPorId(id);
    }

    public ArrayList<Venta> ventas() throws Exception {
        return ventaDao.buscarVentas();
    }
}
