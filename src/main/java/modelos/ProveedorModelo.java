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
    public void ingresarProveedor(Proveedor proveedor) throws Exception {
        proveedorDao.ingresarProveedor(proveedor);
    }

    public void modificarProveedor(Proveedor proveedor) throws Exception {
        proveedorDao.modificarProveedor(proveedor);
    }

    public void eliminarProveedorId(Proveedor proveedor) throws Exception {
        proveedorDao.eliminarProveedorId(proveedor);
    }

    public void eliminarProveedorNombre(Proveedor proveedor) throws Exception {
        proveedorDao.eliminarProveedorNombre(proveedor);
    }

    public Proveedor buscarProveedorPorId(int id) throws Exception {
        return proveedorDao.buscarProveedorPorId(id);
    }

    public Proveedor buscarProveedorPorNombre(String nombre) throws Exception {
        return proveedorDao.buscarProveedorPorNombre(nombre);
    }

    public ArrayList<Proveedor> buscarProveedores() throws Exception {
        return proveedorDao.buscarProveedores();
    }
}
