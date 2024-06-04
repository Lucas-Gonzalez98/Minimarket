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

}
