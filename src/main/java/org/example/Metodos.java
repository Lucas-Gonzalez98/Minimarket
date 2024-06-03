package org.example;

import entidades.*;
import modelos.*;
import persistencia.DAO;
import persistencia.ProductoDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;

public class Metodos extends DAO {
    private ClienteModelo clienteModelo;
    private EmpleadoModelo empleadoModelo;
    private ProductoModelo productoModelo;
    private ProveedorModelo proveedorModelo;
    private ComandaModelo comandaModelo;
    private DetalleComandaModelo detalleComandaModelo;
    private VentaModelo ventaModelo;

    private ArrayList<Double> pagos = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // Constructor privado para evitar instanciación
    private Metodos() {
        clienteModelo = ClienteModelo.getInstance();
        empleadoModelo = EmpleadoModelo.getInstance();
        productoModelo = ProductoModelo.getInstance();
        proveedorModelo = ProveedorModelo.getInstance();
        comandaModelo = ComandaModelo.getInstance();
        detalleComandaModelo = DetalleComandaModelo.getInstance();
        ventaModelo = VentaModelo.getInstance();
    }

    // Implementación del patrón Singleton
    private static Metodos instance;

    public static Metodos getInstance() {
        if (instance == null) {
            instance = new Metodos();
        }
        return instance;
    }
    public void crearTablas() {
        try {
            System.out.println("Creando tablas...");
            String crearTablaProducto = "CREATE TABLE IF NOT EXISTS Producto ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "precio DECIMAL(10, 2) NOT NULL, "
                    + "stock INT NOT NULL)";
            String crearTablaCliente = "CREATE TABLE IF NOT EXISTS Cliente (" //LISTO
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "apellido VARCHAR(100) NOT NULL, "
                    + "direccion VARCHAR(200) DEFAULT NULL)";
            String crearTablaEmpleado = "CREATE TABLE IF NOT EXISTS Empleado (" //LISTO
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "apellido VARCHAR(100) NOT NULL, "
                    + "cargo VARCHAR(50) NOT NULL, "
                    + "salario DECIMAL(10, 2) NOT NULL)";
            String crearTablaComanda = "CREATE TABLE IF NOT EXISTS Comanda ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "cliente_id INT DEFAULT NULL, "
                    + "empleado_id INT NOT NULL, "
                    + "fecha DATE NOT NULL, "
                    + "total DECIMAL(10, 2) NOT NULL, "
                    + "FOREIGN KEY (cliente_id) REFERENCES Cliente(id), "
                    + "FOREIGN KEY (empleado_id) REFERENCES Empleado(id))";
            String crearTablaDetalleComanda = "CREATE TABLE IF NOT EXISTS DetalleComanda ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "comanda_id INT NOT NULL, "
                    + "producto_id INT NOT NULL, "
                    + "cantidad INT NOT NULL, "
                    + "precio DECIMAL(10, 2) NOT NULL, "
                    + "FOREIGN KEY (comanda_id) REFERENCES Comanda(id), "
                    + "FOREIGN KEY (producto_id) REFERENCES Producto(id))";
            String crearTablaProveedor = "CREATE TABLE IF NOT EXISTS Proveedor ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "direccion VARCHAR(200) DEFAULT NULL, "
                    + "fecha DATE NOT NULL, "
                    + "deuda DECIMAL(10, 2) NOT NULL)";
            String crearTablaVenta = "CREATE TABLE IF NOT EXISTS Venta ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "producto_id INT NOT NULL, "
                    + "cliente_id INT DEFAULT NULL, "
                    + "empleado_id INT NOT NULL, "
                    + "cantidad INT NOT NULL, "
                    + "fecha DATE NOT NULL, "
                    + "total DECIMAL(10, 2) NOT NULL, "
                    + "FOREIGN KEY (producto_id) REFERENCES Producto(id), "
                    + "FOREIGN KEY (cliente_id) REFERENCES Cliente(id), "
                    + "FOREIGN KEY (empleado_id) REFERENCES Empleado(id))";

            insertarModificarEliminar(crearTablaProducto);
            insertarModificarEliminar(crearTablaCliente);
            insertarModificarEliminar(crearTablaEmpleado);
            insertarModificarEliminar(crearTablaComanda);
            insertarModificarEliminar(crearTablaDetalleComanda);
            insertarModificarEliminar(crearTablaProveedor);
            insertarModificarEliminar(crearTablaVenta);
            System.out.println("Tablas creadas o ya existentes.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertarDatos(){
        System.out.println("ingresando datos");
        insertarDatosCliente();
        insertarDatosEmpleado();
        insertarDatosProducto();
        insertarDatosComanda();
        insertarDatosDetalleComanda();
        insertarDatosProveedor();
        insertarDatosVenta();
        System.out.println("Datos generados Exitosamente");
    }
    public void insertarDatosCliente() {
        try {
            Cliente cliente = new Cliente();
            clienteModelo.insertarDatosClientes(cliente);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void insertarDatosEmpleado() {
        try {
            Empleado empleado = new Empleado();
            empleadoModelo.insertarDatosEmpleados(empleado);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertarDatosProveedor() {
        try {
            Proveedor proveedor = new Proveedor();
            proveedorModelo.insertarProveedores(proveedor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertarDatosProducto() {
        try {
            Producto producto = new Producto();
            productoModelo.insertarProductos(producto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertarDatosComanda() {
        try {
            Comanda comanda = new Comanda();
            comandaModelo.insertarDatosComanda(comanda);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertarDatosDetalleComanda() {
        try {
            DetalleComanda detalleComanda = new DetalleComanda();
            detalleComandaModelo.insertarDatosDetallesComanda(detalleComanda);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertarDatosVenta() {
        try {
            Venta venta = new Venta();
            ventaModelo.insertarDatosVentas(venta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void venderCobrarProducto() throws Exception {
        System.out.println("Ingrese el nombre del producto:");
        String nombreProducto = scanner.nextLine();
        System.out.println("Ingrese el nombre del cliente:");
        String nombreCliente = scanner.nextLine();
        System.out.println("Ingrese el nombre del empleado:");
        String nombreEmpleado = scanner.nextLine();
        System.out.println("Ingrese la cantidad:");
        int cantidad = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese la fecha (yyyy-mm-dd):");
        String fechaStr = scanner.nextLine();
        Date fecha = Date.valueOf(fechaStr);
        try {
            ventaModelo.ingresarVenta(nombreProducto, nombreCliente, nombreEmpleado, cantidad, fecha);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void pagoProveedor() {
        System.out.println("Ha seleccionado pago a proveedor.");
        System.out.println("Ingrese el nombre del proveedor:");
        String nombreProveedor = scanner.nextLine();
        System.out.println("Ingrese la cantidad a pagar:");
        double cantidadPago = scanner.nextDouble();
        scanner.nextLine();
        try {
            proveedorModelo.pagarProveedor(nombreProveedor, cantidadPago);
            pagos.add(cantidadPago);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void ingresoMercaderia() {
        System.out.println("Ingrese el nombre del producto:");
        String nombreProducto = scanner.nextLine();
        System.out.println("Ingrese la cantidad de mercadería a ingresar:");
        int cantidad = scanner.nextInt();
        scanner.nextLine();
        try {
            productoModelo.ingresarMercaderia(nombreProducto, cantidad);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void consultaVentas(Scanner scanner) {
        int segundaOpcion;
        do {
            System.out.println("Consulta de Ventas");
            System.out.println("1. Diaria");
            System.out.println("2. Mensual");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            segundaOpcion = scanner.nextInt();

            switch (segundaOpcion) {
                case 1:
                    consultaVentasDiaria();
                    break;
                case 2:
                    consultaVentasMensual();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
            System.out.println();
        } while (segundaOpcion != 3);
    }
    public void consultaVentasMensual() {
        System.out.println("Ha seleccionado consulta de ventas mensual.");
        //codigo
    }
    public void consultaVentasDiaria() {
        System.out.println("Ha seleccionado consulta de ventas diaria.");
        //codigo
    }
    public void balance() {
        System.out.println("Ha seleccionado balance (mostrar ganancias y pérdidas).");
        //codigo
    }
    public void solicitarComanda() {
        System.out.println("Ha seleccionado solicitar una comanda a la cocina.");
        //codigo
    }
    public void pagarCuenta() {
        System.out.println("Ha seleccionado pagar cuenta.");
        //codigo
    }
    public void informacionEstadistica() {
        System.out.println("Ha seleccionado información estadística de platos más pedidos.");
        //codigo
    }
}
