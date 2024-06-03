package persistencia;

import entidades.Producto;
import entidades.Proveedor;
import modelos.ProveedorModelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ProveedorDao extends DAO {
    private static ProveedorDao instance;
    private ProveedorModelo proveedorModelo;

    private ProveedorDao() {}

    public static ProveedorDao getInstance() {
        if (instance == null) {
            instance = new ProveedorDao();
        }
        return instance;
    }

    public void setProveedorModelo(ProveedorModelo proveedorModelo) {
        this.proveedorModelo = proveedorModelo;
    }
    public void insertarProveedores(Proveedor proveedor) throws Exception {
        try {
        String ingresarDatosProveedores = "INSERT INTO Proveedor (nombre, direccion, fecha, deuda) VALUES (?, ?, ?, ?)";
        Connection connection = conectarBase();
        PreparedStatement preparedStatement = connection.prepareStatement(ingresarDatosProveedores);
        Random random = new Random();
        String[] NOMBRES = {"Distribuidora A", "Comercializadora C", "Almacenes D"};

            for (int i = 0; i < 3; i++) {
                String nombre = NOMBRES[i];
                String direccion = "Calle " + (random.nextInt(4000) + 1) + ", Mendoza, Argentina";
                Date fechaDePago = Date.valueOf("2024-05-" + (random.nextInt(31) + 1));
                double deuda = 150000 + (random.nextDouble() * 150000);

                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, direccion);
                preparedStatement.setDate(3, fechaDePago);
                preparedStatement.setDouble(4, deuda);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void pagarProveedor(String nombreProveedor, double cantidadPago) throws SQLException {
        String seleccionProveedor = "SELECT deuda FROM Proveedor WHERE nombre = ?";
        String cambiarDeuda = "UPDATE Proveedor SET deuda = deuda - ? WHERE nombre = ?";

        try (Connection coneccion = conectarBase();
             PreparedStatement preparedStatementSelect = coneccion.prepareStatement(seleccionProveedor);
             PreparedStatement preparedStatementUpdate = coneccion.prepareStatement(cambiarDeuda)) {

            preparedStatementSelect.setString(1, nombreProveedor);
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            if (resultSet.next()) {
                double deudaActual = resultSet.getDouble("deuda");

                System.out.println("Deuda actual del proveedor \"" + nombreProveedor + "\": " + deudaActual);
                if (cantidadPago > deudaActual) {
                    System.out.println("El monto a pagar excede la deuda actual. Se pagará el monto restante.");
                    cantidadPago = deudaActual;
                }
                preparedStatementUpdate.setDouble(1, cantidadPago);
                preparedStatementUpdate.setString(2, nombreProveedor);
                int rowsAffected = preparedStatementUpdate.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Pago realizado correctamente. Nueva deuda: " + (deudaActual - cantidadPago));
                } else {
                    System.out.println("No se encontró el proveedor con nombre: " + nombreProveedor);
                }
            } else {
                System.out.println("No se encontró el proveedor con nombre: " + nombreProveedor);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void ingresarProveedor(Proveedor proveedor) throws Exception {
        String sql = "INSERT INTO Proveedor(nombre,direccion) VALUES('" + proveedor.getNombre() + "', '" + proveedor.getDireccion() + "', '" + proveedor.getDeuda() + "')";
        insertarModificarEliminar(sql);
    }

    public void modificarProveedor(Proveedor proveedor) throws Exception {
        String sql = "UPDATE Proveedor SET nombre = '" + proveedor.getNombre() + "' WHERE id = '" + proveedor.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void eliminarProveedorNombre(Proveedor proveedor) throws Exception {
        String sql = "DELETE FROM Proveedor WHERE nombre ='" + proveedor.getNombre() + "' ";
        insertarModificarEliminar(sql);
    }

    public void eliminarProveedorId(Proveedor proveedor) throws Exception {
        String sql = "DELETE FROM Proveedor WHERE id ='" + proveedor.getId() + "' ";
        insertarModificarEliminar(sql);
    }

    public Proveedor buscarProveedorPorId(int id) throws Exception {
        String sql = "SELECT * FROM Proveedor WHERE id = '" + id + "'";
        consultarBase(sql);
        while (resultado.next()) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(id);
            proveedor.setNombre(resultado.getString(2));
            proveedor.setDireccion(resultado.getString(3));
            return proveedor;
        }
        return null;
    }

    public Proveedor buscarProveedorPorNombre(String nombre) throws Exception {
        String sql = "SELECT * FROM Proveedor WHERE nombre = '" + nombre + "'";
        consultarBase(sql);
        while (resultado.next()) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(resultado.getInt(1));
            proveedor.setNombre(resultado.getString(2));
            proveedor.setDireccion(resultado.getString(3));
            return proveedor;
        }
        return null;
    }

    public ArrayList<Proveedor> buscarProveedores() throws Exception {
        String sql = "SELECT * FROM Proveedor";
        consultarBase(sql);
        ArrayList<Proveedor> proveedores = new ArrayList();
        while (resultado.next()) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(resultado.getInt(1));
            proveedor.setNombre(resultado.getString(2));
            proveedor.setDireccion(resultado.getString(3));
            proveedores.add(proveedor);
        }
        if (proveedores.size() == 0) {
            return null;
        }
        return proveedores;
    }
}
