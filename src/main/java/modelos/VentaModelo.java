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

    public void ingresarVenta(int idProducto, String apellidoCliente, String nombreCliente, String nombreEmpleado, int cantidad, Date fecha) throws Exception {
        ventaDao.ingresarVenta(idProducto, apellidoCliente, nombreCliente, nombreEmpleado, cantidad, fecha);
    }


}
