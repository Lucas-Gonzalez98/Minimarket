package modelos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import entidades.Cliente;
import persistencia.ClienteDao;

public class ClienteModelo {
    private static ClienteModelo instance;
    private ClienteDao clienteDao;

    private ClienteModelo() {
        this.clienteDao = ClienteDao.getInstance();
        this.clienteDao.setClienteModelo(this);
    }

    public static ClienteModelo getInstance() {
        if (instance == null) {
            instance = new ClienteModelo();
        }
        return instance;
    }
    public void insertarDatosClientes(Cliente cliente) throws Exception{
        clienteDao.insertarDatosClientes(cliente);
    }

    public void listarClientes() throws Exception{
        clienteDao.listarClientes();
    }
    public Cliente buscarClienteID(int idCliente) throws Exception{
        return clienteDao.buscarClientePorID(idCliente);
    }
}
