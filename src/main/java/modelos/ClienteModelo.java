package modelos;

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
    public void ingresarCliente(Cliente cliente) throws Exception {
        clienteDao.ingresarCliente(cliente);
    }

    public void modificarClienteDireccion(Cliente cliente) throws Exception {
        clienteDao.modificarClienteDireccion(cliente);
    }

    public void modificarClienteNombre(Cliente cliente) throws Exception {
        clienteDao.modificarClienteNombre(cliente);
    }

    public void modificarClienteApellido(Cliente cliente) throws Exception {
        clienteDao.modificarClienteApellido(cliente);
    }

    public void eliminarCliente(Cliente cliente) throws Exception {
        clienteDao.eliminarCliente(cliente);
    }

    public Cliente buscarClientePorId(int id) throws Exception {
        return clienteDao.buscarClientePorId(id);
    }

    public Cliente buscarClientePorNombre(String nombre) throws Exception {
        return clienteDao.buscarClientePorNombre(nombre);
    }

    public ArrayList<Cliente> buscarClientes() throws Exception {
        return clienteDao.buscarClientes();
    }
}
