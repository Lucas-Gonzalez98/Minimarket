package persistencia;

import entidades.Cliente;
import modelos.ClienteModelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ClienteDao extends DAO{
    private static ClienteDao instance;
    private ClienteModelo clienteModelo;

    private ClienteDao() {}

    public static ClienteDao getInstance() {
        if (instance == null) {
            instance = new ClienteDao();
        }
        return instance;
    }

    public void setClienteModelo(ClienteModelo clienteModelo) {
        this.clienteModelo = clienteModelo;
    }

    public void insertarDatosClientes(Cliente cliente) throws Exception {
        try {
            String IngresarDatosClientes = "INSERT INTO Cliente (nombre, apellido, direccion) VALUES (?, ?, ?)";
            Connection connection = conectarBase();
            PreparedStatement preparedStatement = connection.prepareStatement(IngresarDatosClientes);
            Random random = new Random();
            String[] NOMBRES = {"Juan", "María", "Carlos", "Ana", "Luis", "Laura", "José", "Marta", "Pedro", "Lucía", "Miguel", "Sofía", "Jorge", "Elena", "Ricardo", "Patricia", "Raúl", "Clara", "Fernando", "Valeria"};
            String[] APELLIDOS = {"González", "Rodríguez", "López", "Martínez", "García", "Fernández", "Pérez", "Sánchez", "Ramírez", "Torres", "Vargas", "Morales", "Díaz", "Ortiz", "Cruz", "Reyes", "Flores", "Jiménez", "Castro", "Gutiérrez"};

            for (int i = 0; i < 15; i++) {
                String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
                String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
                String direccion = "Calle " + (random.nextInt(4000) + 1) + ", Mendoza, Argentina";

                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, apellido);
                preparedStatement.setString(3, direccion);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public void ingresarCliente(Cliente cliente) throws Exception {
        String sql = "INSERT INTO Cliente(nombre,apellido,direccion) VALUES('" + cliente.getNombre() + "', '" + cliente.getApellido() + "', '" + cliente.getDireccion() + "')";
        insertarModificarEliminar(sql);
    }

    public void modificarClienteDireccion(Cliente cliente) throws Exception {
        String sql = "UPDATE Cliente SET direccion = '" + cliente.getDireccion() + "' WHERE id = '" + cliente.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void modificarClienteNombre(Cliente cliente) throws Exception {
        String sql = "UPDATE Cliente SET nombre = '" + cliente.getNombre() + "' WHERE id = '" + cliente.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void modificarClienteApellido(Cliente cliente) throws Exception {
        String sql = "UPDATE Cliente SET apellido = '" + cliente.getApellido() + "' WHERE id = '" + cliente.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void eliminarCliente(Cliente cliente) throws Exception {
        String sql = "DELETE FROM Cliente WHERE id ='" + cliente.getId() + "' ";
        insertarModificarEliminar(sql);
    }

    public Cliente buscarClientePorId(int id) throws Exception {
        String sql = "SELECT * FROM Cliente WHERE id = '" + id + "'";
        consultarBase(sql);
        while (resultado.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(id);
            cliente.setNombre(resultado.getString(2));
            return cliente;
        }
        return null;
    }

    public Cliente buscarClientePorNombre(String nombre) throws Exception {
        String sql = "SELECT * FROM Cliente WHERE nombre = '" + nombre + "'";
        consultarBase(sql);
        while (resultado.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(resultado.getInt(1));
            cliente.setNombre(resultado.getString(2));
            return cliente;
        }
        return null;
    }

    public ArrayList<Cliente> buscarClientes() throws Exception {
        String sql = "SELECT * FROM Cliente";
        consultarBase(sql);
        ArrayList<Cliente> clientes = new ArrayList();
        while (resultado.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(resultado.getInt(1));
            cliente.setNombre(resultado.getString(2));
            cliente.setApellido(resultado.getString(3));
            cliente.setDireccion(resultado.getString(4));
            clientes.add(cliente);
        }
        if (clientes.size() == 0) {
            return null;
        }
        return clientes;
    }
}
