package entidades;

import java.sql.Date;
import java.util.ArrayList;


public class Venta {
    private int id;
    private ArrayList<Producto> productos = new ArrayList<Producto>();
    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private ArrayList<Empleado> empleados = new ArrayList<Empleado>();
    private int cantidad;
    private Date fecha;
    private double total;

    public Venta(int id, ArrayList<Producto> productos, ArrayList<Cliente> clientes, ArrayList<Empleado> empleados, int cantidad, Date fecha, double total) {
        this.id = id;
        this.productos = productos;
        this.clientes = clientes;
        this.empleados = empleados;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
    }
    public Venta() {
    }
    public Venta(double total){
        this.total = total;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
    public void addProductos(Producto producto){
        this.productos.add(producto);
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", productos=" + productos +
                ", clientes=" + clientes +
                ", empleados=" + empleados +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                ", total=" + total +
                '}';
    }
}

