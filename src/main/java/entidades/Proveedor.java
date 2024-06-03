package entidades;

import java.util.Date;

public class Proveedor {
    private int id;
    private String nombre;
    private String direccion;
    private Date fechaDePago;
    private double deuda;

    public Proveedor(String nombre, String direccion, Date fechaDePago, double deuda) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaDePago = fechaDePago;
        this.deuda = deuda;
    }
    public Proveedor() {
    }

    public Date getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
