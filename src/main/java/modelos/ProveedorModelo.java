package modelos;

import entidades.Proveedor;
import persistencia.ProveedorDao;
import java.util.ArrayList;

public class ProveedorModelo {
    private static ProveedorModelo instance;
    private ProveedorDao proveedorDao;

    private ProveedorModelo() {
        this.proveedorDao = ProveedorDao.getInstance();
        this.proveedorDao.setProveedorModelo(this);
    }

    public static ProveedorModelo getInstance() {
        if (instance == null) {
            instance = new ProveedorModelo();
        }
        return instance;
    }

    public void insertarProveedores(Proveedor proveedor) throws Exception {
        proveedorDao.insertarProveedores(proveedor);
    }

    public void pagarProveedor(String nombreProveedor, double cantidadPago) throws Exception{
        proveedorDao.pagarProveedor(nombreProveedor, cantidadPago);
    }
    public void listarProveedores(){
        proveedorDao.listarProveedores();
    }
}
