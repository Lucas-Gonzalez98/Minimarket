package modelos;

import entidades.Comanda;
import persistencia.ComandaDao;

import java.util.ArrayList;

public class ComandaModelo {
    private static ComandaModelo instance;
    private ComandaDao comandaDao;

    private ComandaModelo() {
        this.comandaDao = ComandaDao.getInstance();
        this.comandaDao.setComandaModelo(this);
    }

    public static ComandaModelo getInstance() {
        if (instance == null) {
            instance = new ComandaModelo();
        }
        return instance;
    }

    public void insertarDatosComanda(Comanda comanda) throws Exception{
        comandaDao.insertarDatosComandas(comanda);
    }
    public void ingresarComanda(Comanda comanda) throws Exception {
        comandaDao.ingresarComanda(comanda);
    }

    public void modificarComandaTotal(Comanda comanda) throws Exception {
        comandaDao.modificarComandaTotal(comanda);
    }

    public void eliminarComanda(Comanda comanda) throws Exception {
        comandaDao.eliminarComanda(comanda);
    }

    public Comanda buscarComandaPorId(int id) throws Exception {
        return comandaDao.buscarComandaPorId(id);
    }

    public ArrayList<Comanda> buscarComandas() throws Exception {
        return comandaDao.buscarComandas();
    }
}
