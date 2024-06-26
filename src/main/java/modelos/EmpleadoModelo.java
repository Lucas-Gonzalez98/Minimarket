package modelos;

import entidades.Empleado;
import persistencia.ClienteDao;
import persistencia.EmpleadoDao;
import java.util.ArrayList;

public class EmpleadoModelo {
    private static EmpleadoModelo instance;
    private EmpleadoDao empleadoDao;

    private EmpleadoModelo() {
        this.empleadoDao = EmpleadoDao.getInstance();
        this.empleadoDao.setEmpleadoModelo(this);
    }

    public static EmpleadoModelo getInstance() {
        if (instance == null) {
            instance = new EmpleadoModelo();
        }
        return instance;
    }

    public void insertarDatosEmpleados(Empleado empleado) throws Exception {
        empleadoDao.insertarDatosEmpleados(empleado);
    }
    public void listarEmpleados(){
        empleadoDao.listarEmpleados();
    }
}
