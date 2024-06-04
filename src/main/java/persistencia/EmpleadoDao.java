package persistencia;

import entidades.Empleado;
import modelos.EmpleadoModelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class EmpleadoDao extends DAO{
    private static EmpleadoDao instance;
    private EmpleadoModelo empleadoModelo;

    private EmpleadoDao() {}

    public static EmpleadoDao getInstance() {
        if (instance == null) {
            instance = new EmpleadoDao();
        }
        return instance;
    }

    public void setEmpleadoModelo(EmpleadoModelo empleadoModelo) {
        this.empleadoModelo = empleadoModelo;
    }
    public void insertarDatosEmpleados(Empleado empleado) throws Exception {
        try {
        String sql = "INSERT INTO Empleado (nombre, apellido, cargo, salario) VALUES (?, ?, ?, ?)";
        Connection connection = conectarBase();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        Random random = new Random();

        String[] nombres = {"Juan", "María", "Carlos", "Ana"};
        String[] apellidos = {"González", "Rodríguez", "López", "Martínez"};
        String[] cargos = {"Vendedor/Mesero", "Cocinero"};

            for (int i = 0; i < 4; i++) {
                String nombre = nombres[i];
                String apellido = apellidos[i];
                String cargo = i < 2 ? cargos[0] : cargos[1];
                double salario = 150000 + (random.nextDouble() * 70000);

                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, apellido);
                preparedStatement.setString(3, cargo);
                preparedStatement.setDouble(4, salario);
                preparedStatement.addBatch();

            }
            preparedStatement.executeBatch();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void ingresarEmpleado(Empleado empleado) throws Exception {
        String sql = "INSERT INTO Empleado(nombre,apellido,cargo,salario) VALUES('" + empleado.getNombre() + "', '" + empleado.getApellido() + "', '" + empleado.getCargo() + "', '" + empleado.getSalario() + "')";
        insertarModificarEliminar(sql);
    }

    public void modificarEmpleadoCargo(Empleado empleado) throws Exception {
        String sql = "UPDATE Empleado SET cargo = '" + empleado.getCargo() + "' WHERE id = '" + empleado.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void modificarEmpleadoSalario(Empleado empleado) throws Exception {
        String sql = "UPDATE Empleado SET salario = '" + empleado.getSalario() + "' WHERE id = '" + empleado.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void eliminarEmpleadoId(Empleado empleado) throws Exception {
        String sql = "DELETE FROM Empleado WHERE id ='" + empleado.getId() + "' ";
        insertarModificarEliminar(sql);
    }

    public Empleado buscarEmpleadoPorId(int id) throws Exception {
        String sql = "SELECT * FROM Empleado WHERE id = '" + id + "'";
        consultarBase(sql);
        while (resultado.next()) {
            Empleado empleado = new Empleado();
            empleado.setId(id);
            empleado.setNombre(resultado.getString(2));
            empleado.setApellido(resultado.getString(3));
            empleado.setCargo(resultado.getString(4));
            empleado.setSalario(resultado.getInt(5));
            return empleado;
        }
        return null;
    }

    public ArrayList<Empleado> buscarEmpleados() throws Exception {
        String sql = "SELECT * FROM Empleado";
        consultarBase(sql);
        ArrayList<Empleado> empleados = new ArrayList();
        while (resultado.next()) {
            Empleado empleado = new Empleado();
            empleado.setId(resultado.getInt(1));
            empleado.setNombre(resultado.getString(2));
            empleado.setApellido(resultado.getString(3));
            empleado.setCargo(resultado.getString(4));
            empleado.setSalario(resultado.getInt(5));
            empleados.add(empleado);
        }
        if (empleados.size() == 0) {
            return null;
        }
        return empleados;
    }

    public void listarEmpleados(){
        String listaEmpleados = "SELECT id, nombre, apellido FROM Empleado";

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
            System.out.println("Error al listar los empleados: " + e.getMessage());
        }
    }
}
