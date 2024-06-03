package persistencia;

import java.sql.*;

public abstract class DAO {
    protected Connection conexion = null;
    protected Statement sentencia = null;
    protected ResultSet resultado = null;

    static final String JDBC_DRIVER = "org.h2.Driver";

    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    protected Connection conectarBase() throws Exception {
        try {
            Class.forName(JDBC_DRIVER);
            conexion = DriverManager.getConnection(DB_URL,JDBC_USER, JDBC_PASSWORD);
        } catch (Exception e) {
            throw e;
        }
        return conexion;
    }

    protected int insertarModificarEliminar(String sql) throws Exception {
        try {
            conectarBase();
            sentencia = conexion.createStatement();
            sentencia.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet result = sentencia.getGeneratedKeys();
            if (result.next()) {
                int idDevuelto = result.getInt(1);
                return idDevuelto;
            }
        } catch (Exception e) {
            conexion.rollback();
            throw e;
        } finally {
            desconectarBase();
        }
        return 0;
    }

    protected void consultarBase(String sql) throws Exception {
        try {
            conectarBase();
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    protected void desconectarBase() throws Exception {
        try {
            if (resultado != null) {
                resultado.close();
            }
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
