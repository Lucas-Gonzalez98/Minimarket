package persistencia;

import entidades.*;
import modelos.ComandaModelo;
import modelos.ProductoModelo;
import modelos.DetalleComandaModelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DetalleComandaDao extends DAO{
    private static DetalleComandaDao instance;
    private DetalleComandaModelo detalleComandaModelo;
    private ComandaModelo comandaModelo;
    private ProductoModelo productoModelo;
    private DetalleComandaDao() {}

    public static DetalleComandaDao getInstance() {
        if (instance == null) {
            instance = new DetalleComandaDao();
        }
        return instance;
    }
    public void insertarDatosDetallesComanda(DetalleComanda detalleComanda) throws Exception {
        try{
        String ingresarDatosComanda = "INSERT INTO DetalleComanda (comanda_id, producto_id, cantidad, precio) VALUES (?, ?, ?, ?)";
        Connection connection = conectarBase();
        PreparedStatement preparedStatement = connection.prepareStatement(ingresarDatosComanda);
        Random random = new Random();

            for (int i = 0; i < 4; i++) {
                int comandaId = random.nextInt(4) + 1;
                int productoId = random.nextInt(20) + 1;
                int cantidadProducto = random.nextInt(2) + 1;
                double precio = ((random.nextInt(4001) + 4000) + random.nextDouble()) * cantidadProducto;

                preparedStatement.setInt(1, comandaId);
                preparedStatement.setInt(2, productoId);
                preparedStatement.setInt(3, cantidadProducto);
                preparedStatement.setDouble(4, precio);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void ingresarDetalleComanda(DetalleComanda detalleComanda) throws Exception {
        String sql = "INSERT INTO DetalleComanda(cantidad,precio) VALUES('" + "', '" + detalleComanda.getCantidad() + "', '" + detalleComanda.getPrecio() + "')";
        insertarModificarEliminar(sql);
    }

    public void modificarDetalleComandaCantidad(DetalleComanda detalleComanda) throws Exception {
        String sql = "UPDATE DetalleComanda SET cantidad = '" + detalleComanda.getCantidad() + "' WHERE id = '" + detalleComanda.getId() + "'";
        insertarModificarEliminar(sql);
    }

    public void eliminarDetalleComanda(DetalleComanda detalleComanda) throws Exception {
        String sql = "DELETE FROM DetalleComanda WHERE id ='" + detalleComanda.getId() + "' ";
        insertarModificarEliminar(sql);
    }

    public DetalleComanda buscarDetalleComandaPorId(int id) throws Exception {
        String sql = "SELECT * FROM DetalleComanda WHERE id = '" + id + "'";
        try{
            consultarBase(sql);
            DetalleComanda detalleComanda = null;
            while (resultado.next()) {
                detalleComanda = new DetalleComanda();
                detalleComanda.setId(resultado.getInt(1));
                int idComanda = resultado.getInt(2);
                Comanda comanda = comandaModelo.buscarComandaPorId(idComanda);
                detalleComanda.setComanda(comanda);
                int idProducto = resultado.getInt(3);
                Producto producto = productoModelo.buscarProductoPorId(idProducto);
                detalleComanda.setProducto(producto);
                detalleComanda.setCantidad(resultado.getInt(4));
                detalleComanda.setPrecio(resultado.getInt(5));
            }
            desconectarBase();
            return detalleComanda;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<DetalleComanda> buscarDetallesComandas() throws Exception {
        String sql = "SELECT * FROM DetalleComanda";
        consultarBase(sql);
        ArrayList<DetalleComanda> detallesComandas = new ArrayList();
        try {
            while (resultado.next()) {
                DetalleComanda detalleComanda = new DetalleComanda();
                detalleComanda.setId(resultado.getInt(1));
                detalleComanda.setComanda(comandaModelo.buscarComandaPorId(resultado.getInt(2)));
                detalleComanda.setProducto(productoModelo.buscarProductoPorId(resultado.getInt(3)));
                detalleComanda.setCantidad(resultado.getInt(4));
                detalleComanda.setPrecio(resultado.getInt(5));
                detallesComandas.add(detalleComanda);
            }
            desconectarBase();
            if (detallesComandas.isEmpty()) {
                return null;
            }
            return detallesComandas;
        } catch (Exception e) {
            throw e;
        }
    }
}
