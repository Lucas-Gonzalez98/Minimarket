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

    public void listarClientes(){
        String listaEmpleados = "SELECT id, nombre, apellido FROM Cliente";

        try (Connection coneccion = conectarBase();
             PreparedStatement preparedStatement = coneccion.prepareStatement(listaEmpleados);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                System.out.println("[" + id + "] - " + nombre + " " + apellido);
            }
        } catch (Exception e) {
            System.out.println("Error al listar los clientes: " + e.getMessage());
        }
    }
}
