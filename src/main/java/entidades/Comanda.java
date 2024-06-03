package entidades;

import java.util.ArrayList;
import java.sql.Date;
public class Comanda {
    private int id;
    private Cliente cliente;
    private Empleado empleado;
    private Date fecha;
    private double total;
    private ArrayList<DetalleComanda> detalles = new ArrayList<DetalleComanda>();

    public Comanda(Cliente cliente, Empleado empleado, Date fecha, double total, ArrayList<DetalleComanda> detalles) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.fecha = fecha;
        this.total = total;
        this.detalles = detalles;
    }

    public Comanda() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getFecha() {return fecha;}

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<DetalleComanda> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetalleComanda> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", empleado=" + empleado +
                ", fecha=" + fecha +
                ", total=" + total +
                ", detalles=" + detalles +
                '}';
    }
}
