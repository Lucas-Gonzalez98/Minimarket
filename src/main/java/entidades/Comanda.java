package entidades;

import java.util.ArrayList;
import java.sql.Date;
public class Comanda {
    private int id;
    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private ArrayList<Empleado> empleados = new ArrayList<Empleado>();
    private String nombreComanda;
    private Date fecha;
    private int cantidad;
    private double precioComanda;

    public Comanda(int id, ArrayList<Cliente> clientes, ArrayList<Empleado> empleados,String nombreComanda, Date fecha, int cantidad, double precioComanda) {
        this.id = id;
        this.clientes = clientes;
        this.nombreComanda = nombreComanda;
        this.empleados = empleados;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.precioComanda = precioComanda;
    }
    public Comanda() {
    }

    public String getNombreComanda() {
        return nombreComanda;
    }

    public void setNombreComanda(String nombreComanda) {
        this.nombreComanda = nombreComanda;
    }

    public double getPrecioComanda() {
        return precioComanda;
    }

    public void setPrecioComanda(double precioComanda) {
        this.precioComanda = precioComanda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
    public void addClientes(Cliente cliente){
        this.clientes.add(cliente);
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }
    public void addEmpleados(Empleado empleado){
        this.empleados.add(empleado);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", cliente=" + clientes +
                ", empleado=" + empleados +
                ", fecha=" + fecha +
                ", precio Comanda=" + precioComanda +
                '}';
    }
}
