package entidades;

import java.time.LocalDate;
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
    private int idCliente;
    private int idEmpleado;
    private LocalDate fechaActual;

    public LocalDate getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(LocalDate fechaActual) {
        this.fechaActual = fechaActual;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Comanda(int id, ArrayList<Cliente> clientes, ArrayList<Empleado> empleados, String nombreComanda, Date fecha, int cantidad, double precioComanda) {
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
    public Comanda(int cantidad, double precioComanda){
        this.cantidad = cantidad;
        this.precioComanda = precioComanda;
    }
    public Comanda (double precioComanda){
        this.precioComanda = precioComanda;
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
