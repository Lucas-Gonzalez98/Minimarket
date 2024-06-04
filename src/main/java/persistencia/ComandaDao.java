package persistencia;

import entidades.Comanda;
import entidades.Cliente;
import entidades.Empleado;
import modelos.ClienteModelo;
import modelos.ComandaModelo;
import modelos.EmpleadoModelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ComandaDao extends DAO {
    private static ComandaDao instance;
    private ComandaModelo comandaModelo;
    private ClienteModelo clienteModelo;
    private EmpleadoModelo empleadoModelo;

    private ComandaDao() {
    }

    public static ComandaDao getInstance() {
        if (instance == null) {
            instance = new ComandaDao();
        }
        return instance;
    }

    public void setComandaModelo(ComandaModelo comandaModelo) {
        this.comandaModelo = comandaModelo;
    }

    public void insertarDatosComandas(Comanda comanda) throws Exception {
        try {
            String ingresarDatosComandas = "INSERT INTO Comanda (cliente_id, empleado_id, nombre, fecha, cantidad, total) VALUES (?, ?, ?, ?, ?, ?)";
            Connection connection = conectarBase();
            PreparedStatement preparedStatement = connection.prepareStatement(ingresarDatosComandas);
            Random random = new Random();

            String[] NOMBRES = {"Milanesas con papas fritas", "Hamburguesa con papas", "Pancho con papas", "Pizza"};
            for (int i = 0; i < 4; i++) {
                int clienteId = random.nextInt(15) + 1;
                int empleadoId = random.nextInt(4) + 1;
                Date fecha = Date.valueOf("2024-05-" + (random.nextInt(31) + 1));
                int cantidad = random.nextInt(5) + 1;
                double precio = (random.nextInt(9001) + 3000) + random.nextDouble();
                String nombre = NOMBRES[i];
                double total = precio * cantidad;

                preparedStatement.setInt(1, clienteId);
                preparedStatement.setInt(2, empleadoId);
                preparedStatement.setString(3, nombre);
                preparedStatement.setDate(4, fecha);
                preparedStatement.setInt(5, cantidad);
                preparedStatement.setDouble(6, total);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

