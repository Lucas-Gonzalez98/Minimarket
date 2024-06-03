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

public class ComandaDao extends DAO{
    private static ComandaDao instance;
    private ComandaModelo comandaModelo;
    private ClienteModelo clienteModelo;
    private EmpleadoModelo empleadoModelo;
    private ComandaDao() {}
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
        try{
        String ingresarDatosComandas = "INSERT INTO Comanda (cliente_id, empleado_id, fecha, total) VALUES (?, ?, ?, ?)";
        Connection connection = conectarBase();
        PreparedStatement preparedStatement = connection.prepareStatement(ingresarDatosComandas);
        Random random = new Random();

            for (int i = 0; i < 4; i++) {
                int clienteId = random.nextInt(10) + 1; // Asumiendo que hay 10 clientes
                int empleadoId = random.nextInt(4) + 1; // Asumiendo que hay 4 empleados
                Date fecha = Date.valueOf("2024-05-" + (random.nextInt(31) + 1));
                double total = (random.nextInt(8001) + 4000) + random.nextDouble(); // Entre 4000 y 12000

                preparedStatement.setInt(1, clienteId);
                preparedStatement.setInt(2, empleadoId);
                preparedStatement.setDate(3, fecha);
                preparedStatement.setDouble(4, total);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void ingresarComanda(Comanda comanda) throws Exception {
        String sql = "INSERT INTO Comanda(cliente_id,empleado_id,fecha,total) VALUES('" + comanda.getCliente() + "', '" + comanda.getEmpleado() + "', '" + comanda.getFecha() + "', '" + comanda.getTotal() + "')";
        insertarModificarEliminar(sql);
    }

    public void modificarComandaTotal(Comanda comanda) throws Exception {
        String id;
        if (comanda.getCliente() != null){
            id = " = " +String.valueOf(comanda.getCliente().getId());
        } else {
            id = "= null";
        }
        String sql = "UPDATE Comanda SET total = '" + comanda.getTotal() + "' WHERE id = '" + comanda.getId() + "'";
    }

    public void eliminarComanda(Comanda comanda) throws Exception {
        String sql = "DELETE FROM Comanda WHERE id ='" + comanda.getId() + "' ";
        insertarModificarEliminar(sql);
    }

    public Comanda buscarComandaPorId(int id) throws Exception {
        String sql = "SELECT * FROM Comanda WHERE id = '" + id + "'";
        try{
            consultarBase(sql);
            Comanda comanda = null;
            while (resultado.next()) {
                comanda = new Comanda();
                comanda.setId(resultado.getInt(1));
                int idCliente = resultado.getInt(2);
                Cliente cliente = clienteModelo.buscarClientePorId(idCliente);
                comanda.setCliente(cliente);
                int idEmpleado = resultado.getInt(3);
                Empleado empleado = empleadoModelo.buscarEmpleadoPorId(idEmpleado);
                comanda.setEmpleado(empleado);
                comanda.setFecha(resultado.getDate("fecha"));
                comanda.setTotal(resultado.getInt(5));
            }
            desconectarBase();
            return comanda;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Comanda> buscarComandas() throws Exception {
        String sql = "SELECT * FROM Comanda";
        consultarBase(sql);
        ArrayList<Comanda> comandas = new ArrayList();
        try {
            while (resultado.next()) {
                Comanda comanda = new Comanda();
                comanda.setId(resultado.getInt(1));
                comanda.setCliente(clienteModelo.buscarClientePorId(resultado.getInt(2)));
                comanda.setEmpleado(empleadoModelo.buscarEmpleadoPorId(resultado.getInt(3)));
                comanda.setFecha(resultado.getDate("fecha"));
                comanda.setTotal(resultado.getInt(5));
                comandas.add(comanda);
            }
            desconectarBase();
            if (comandas.isEmpty()) {
                return null;
            }
            return comandas;
        } catch (Exception e) {
            throw e;
        }
    }
}
