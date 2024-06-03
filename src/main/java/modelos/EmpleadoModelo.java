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
    public void ingresarEmpleado(Empleado empleado) throws Exception {
        empleadoDao.ingresarEmpleado(empleado);
    }

    public void modificarEmpleadoCargo(Empleado empleado) throws Exception {
        empleadoDao.modificarEmpleadoCargo(empleado);
    }

    public void modificarEmpleadoSalario(Empleado empleado) throws Exception {
        empleadoDao.modificarEmpleadoSalario(empleado);
    }

    public void eliminarEmpleadoId(Empleado empleado) throws Exception {
        empleadoDao.eliminarEmpleadoId(empleado);
    }

    public Empleado buscarEmpleadoPorId(int id) throws Exception {
        return empleadoDao.buscarEmpleadoPorId(id);
    }

    public ArrayList<Empleado> buscarEmpleados() throws Exception {
        return empleadoDao.buscarEmpleados();
    }
}
