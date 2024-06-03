package entidades;

import java.time.LocalDate;

public class Venta {
    private int id;
    private Producto producto;
    private Cliente cliente;
    private Empleado empleado;
    private int cantidad;
    private LocalDate fecha;
    private double total;

    public Venta(Producto producto, Cliente cliente, Empleado empleado, int cantidad, LocalDate fecha, double total) {
        this.producto = producto;
        this.cliente = cliente;
        this.empleado = empleado;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
    }

    public Venta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
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
                ", producto=" + producto +
                ", cliente=" + cliente +
                ", empleado=" + empleado +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                ", total=" + total +
                '}';
    }
}
