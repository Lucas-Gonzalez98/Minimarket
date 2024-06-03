package modelos;

import entidades.DetalleComanda;
import persistencia.DetalleComandaDao;

import java.util.ArrayList;

public class DetalleComandaModelo {
    private static DetalleComandaModelo instance;
    private DetalleComandaDao detalleComandaDao;

    private DetalleComandaModelo() {
        this.detalleComandaDao = DetalleComandaDao.getInstance();
    }

    public static DetalleComandaModelo getInstance() {
        if (instance == null) {
            instance = new DetalleComandaModelo();
        }
        return instance;
    }
    public void insertarDatosDetallesComanda(DetalleComanda detalleComanda) throws Exception {
        detalleComandaDao.insertarDatosDetallesComanda(detalleComanda);
    }
    public void ingresarDetalleComanda(DetalleComanda detalleComanda) throws Exception {
        detalleComandaDao.ingresarDetalleComanda(detalleComanda);
    }

    public void modificarDetalleComandaCantidad(DetalleComanda detalleComanda) throws Exception {
        detalleComandaDao.modificarDetalleComandaCantidad(detalleComanda);
    }

    public void eliminarDetalleComanda(DetalleComanda detalleComanda) throws Exception {
        detalleComandaDao.eliminarDetalleComanda(detalleComanda);
    }

    public DetalleComanda buscarComandaPorId(int id) throws Exception {
        return detalleComandaDao.buscarDetalleComandaPorId(id);
    }

    public ArrayList<DetalleComanda> buscarDetallesComandas() throws Exception {
        return detalleComandaDao.buscarDetallesComandas();
    }
}
