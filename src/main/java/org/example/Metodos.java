package org.example;

import entidades.*;
import modelos.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistencia.DAO;
import persistencia.ProductoDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;

public class Metodos extends DAO {
    private static final Logger ingresoVentaLogger = LogManager.getLogger("IngresoVentaLogger");
    private static final Logger ingresoMercaderiaLogger = LogManager.getLogger("IngresoMercaderiaLogger");
    private static final Logger proveedorLogger = LogManager.getLogger("ProveedorLogger");
    private static final Logger comandaLogger = LogManager.getLogger("ComandaLogger");
    private static final Logger cuentaLogger = LogManager.getLogger("CuentaLogger");
    private ClienteModelo clienteModelo;
    private EmpleadoModelo empleadoModelo;
    private ProductoModelo productoModelo;
    private ProveedorModelo proveedorModelo;
    private ComandaModelo comandaModelo;
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
    public void ingresoDePagos() {
        pagos.add(50000.00);
        pagos.add(90000.00);
        pagos.add(120000.00);
        pagos.add(70000.00);
    }

    public void crearTablas() {
        try {
            System.out.println("Creando tablas...");
            String crearTablaProducto = "CREATE TABLE IF NOT EXISTS Producto ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "precio DECIMAL(10, 2) NOT NULL, "
                    + "stock INT NOT NULL)";
            String crearTablaCliente = "CREATE TABLE IF NOT EXISTS Cliente ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "apellido VARCHAR(100) NOT NULL, "
                    + "direccion VARCHAR(200) DEFAULT NULL)";
            String crearTablaEmpleado = "CREATE TABLE IF NOT EXISTS Empleado ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "apellido VARCHAR(100) NOT NULL, "
                    + "cargo VARCHAR(50) NOT NULL, "
                    + "salario DECIMAL(10, 2) NOT NULL)";
            String crearTablaComanda = "CREATE TABLE IF NOT EXISTS Comanda ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "cliente_id INT, "
                    + "empleado_id INT, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "fecha DATE NOT NULL, "
                    + "cantidad INT NOT NULL, "
                    + "precioComanda DECIMAL(10, 2) NOT NULL, "
                    + "FOREIGN KEY (cliente_id) REFERENCES Cliente(id), "
                    + "FOREIGN KEY (empleado_id) REFERENCES Empleado(id))";
            String crearTablaProveedor = "CREATE TABLE IF NOT EXISTS Proveedor ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "direccion VARCHAR(200) DEFAULT NULL, "
                    + "fechaDePago DATE NOT NULL, "
                    + "deuda DECIMAL(10, 2) NOT NULL)";
            String crearTablaVenta = "CREATE TABLE IF NOT EXISTS Venta ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "producto_id INT, "
                    + "cliente_id INT, "
                    + "empleado_id INT, "
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
    public void insertarDatosVenta() {
        try {
            Venta venta = new Venta();
            ventaModelo.insertarDatosVentas(venta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void venderCobrarProducto(){
        try{
            System.out.println("Lista de productos:");
            productoModelo.listarProductos();
            System.out.println();
            System.out.println("Ingrese el ID del producto:");
            int idProducto = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Lista de empleados del local:");
            empleadoModelo.listarEmpleados();
            System.out.println("Ingrese el nombre del empleado:");
            String nombreEmpleado = scanner.nextLine();
            System.out.println("Ingrese la cantidad:");
            int cantidad = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Ingrese la fecha (yyyy-mm-dd):");
            String fechaStr = scanner.nextLine();
            Date fecha = Date.valueOf(fechaStr);
            System.out.println("Ingrese el nombre del cliente:");
            String nombreCliente = scanner.nextLine();
            System.out.println("Ingrese el apellido del cliente:");
            String apellidoCliente = scanner.nextLine();
            Producto producto = productoModelo.buscarProductoPorId(idProducto);
            ventaModelo.ingresarVenta(idProducto, apellidoCliente, nombreCliente, nombreEmpleado, cantidad, fecha);
            ingresoVentaLogger.log(Level.getLevel("VENTA"),"Se ingreso una venta, Vendedor: " + nombreEmpleado + " Cliente: " + nombreCliente + " " + apellidoCliente + " Producto: " + producto.getNombre() + " Cantidad: " + cantidad + " Precio x Unidad: " + producto.getPrecio());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void pagoProveedor() {
        System.out.println("Ha seleccionado pago a proveedor.");
        System.out.println("Lista de productos:");
        proveedorModelo.listarProveedores();
        System.out.println();
        System.out.println("Ingrese el nombre del proveedor como sale en la lista de arriba:");
        String nombreProveedor = scanner.nextLine();
        System.out.println("Ingrese la cantidad a pagar:");
        double cantidadPago = scanner.nextDouble();
        scanner.nextLine();
        try {
            proveedorModelo.pagarProveedor(nombreProveedor, cantidadPago);
            pagos.add(cantidadPago);
            proveedorLogger.log(Level.getLevel("PROVEEDOR"),"Se pago al proveedor: " + nombreProveedor + " la cantidad de: " + cantidadPago);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void ingresoMercaderia() {
        System.out.println("Lista de productos:");
        productoModelo.listarProductos();
        System.out.println();
        System.out.println("Ingrese el ID del producto:");
        int idProducto = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese la cantidad de mercadería a ingresar:");
        int cantidad = scanner.nextInt();
        scanner.nextLine();
        try {
            productoModelo.ingresarMercaderia(idProducto, cantidad);
            Producto producto = productoModelo.buscarProductoPorId(idProducto);
            ingresoMercaderiaLogger.log(Level.getLevel("MERCADERIA"), "Se ingreso la mercaderia de: " + producto.getNombre() + " por la cantidad de: " + cantidad + " STOCK ACTUALIZADO EN: " + producto.getStock());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void consultaVentas(Scanner scanner) throws Exception {
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
    public void consultaVentasMensual() throws Exception {
        System.out.println("Ha seleccionado consulta de ventas mensual.");
        System.out.println("Ingrese el mes deseado: ");
        String mes = scanner.nextLine();

        ventaModelo.buscarVentaMensual(mes);
    }
    public void consultaVentasDiaria() throws Exception {
        System.out.println("Ingrese la fecha de consulta (yyyy-mm-dd):");
        String fechaStr = scanner.nextLine();
        Date fecha = Date.valueOf(fechaStr);
        ventaModelo.buscarVentaDiaria(fecha);
    }
    public void balance() {
        try {
            System.out.println("Ha seleccionado balance (mostrar ganancias y pérdidas).");
            ArrayList<Venta> ventas = ventaModelo.obtenerVentas();
            ArrayList<Comanda> comandas = comandaModelo.obtenerComandas();
            double totalPagosDeuda = 0;
            double totalVentas = 0;
            double totalComandas = 0;
            for (Double pago : pagos){
                totalPagosDeuda += pago;
            }
            for (Venta venta : ventas) {
                totalVentas += venta.getTotal();
            }
            for (Comanda comanda : comandas) {
                totalComandas += comanda.getCantidad() * comanda.getPrecioComanda();
            }
            double balanceFinal = totalComandas + totalVentas - totalPagosDeuda;

            System.out.println("===== Balance =====");
            System.out.println("Ingresos:");
            System.out.println("Total de Ventas: $" + totalVentas);
            System.out.println("Total de Comandas: $" + totalComandas);
            System.out.println("-------------------");
            System.out.println("Egresos:");
            System.out.println("Total de Pagos de Deuda: $" + totalPagosDeuda);
            System.out.println("-------------------");
            System.out.println("Balance Final: $" + balanceFinal);
        }catch (Exception e){
            System.out.println("Error al calcular el balance: " + e.getMessage());
        }
    }

    public void solicitarComanda() throws Exception {
        Comanda comanda = new Comanda();

        int opcionElegida;
        do {
            System.out.println("Seleccione la comanda: ");
            System.out.println("1-Hamburguesa con papas fritas | $7500");
            System.out.println("2-Pizza muzzarela | $7000");
            System.out.println("3-Lomo con papas fritas | $12000");
            System.out.println("4-Milanesa napolitana con puré | $10500");

            opcionElegida = scanner.nextInt();

            if(opcionElegida<1 || opcionElegida>4){
                System.out.println("Opción inválida, intente nuevamente.");
            }
        } while (opcionElegida<1 || opcionElegida>4);

        System.out.println("Correcto, ahora ingrese la cantidad deseada: ");
        int cantidadElegida = scanner.nextInt();
        comanda.setCantidad(cantidadElegida);

        switch (opcionElegida){
            case 1:
                comanda.setPrecioComanda(7500);
                comanda.setNombreComanda("Hamburguesa con papas fritas");
                break;
            case 2:
                comanda.setPrecioComanda(7000);
                comanda.setNombreComanda("Pizza muzzarela");
                break;
            case 3:
                comanda.setPrecioComanda(12000);
                comanda.setNombreComanda("Lomo con papas fritas");
                break;
            case 4:
                comanda.setPrecioComanda(10500);
                comanda.setNombreComanda("Milanesa napoiltana con puré");
                break;
        }

        System.out.println("Correcto, ahora seleccione el id del empleado que atiende la comanda: ");
        empleadoModelo.listarEmpleados();
        int idEmpleado = scanner.nextInt();
        comanda.setIdEmpleado(idEmpleado);
        System.out.println();

        System.out.println("Seleccione el id del cliente que solicita la comanda: ");
        clienteModelo.listarClientes();
        int idCliente = scanner.nextInt();
        comanda.setIdCliente(idCliente);
        System.out.println();

        LocalDate fechaActual = LocalDate.now();
        comanda.setFechaActual(fechaActual);

        comandaModelo.solicitarComanda(comanda);
        System.out.println("Comanda solicitada correctamente.");
        Cliente cliente = clienteModelo.buscarClienteID(idCliente);
        comandaLogger.log(Level.getLevel("COMANDA"), "Se solicita la comanda: " + comanda.getNombreComanda() + " por la cantidad de " + comanda.getCantidad() + " al cliente: " + cliente.getNombre()+ " " + cliente.getApellido());
    }
    public void pagarCuenta() {
        System.out.println("Ha seleccionado pagar cuenta.");
        comandaModelo.pagarCuenta();
    }
    public void informacionEstadistica() {
        System.out.println("Ha seleccionado información estadística de platos más pedidos.");
        comandaModelo.platoMasPedido();
    }
}
