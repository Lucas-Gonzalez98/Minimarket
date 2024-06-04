package org.example;

import entidades.Cliente;
import modelos.ClienteModelo;
import persistencia.ClienteDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    static Statement stmt = null;
    static Connection conn = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        Metodos mt = Metodos.getInstance();
        mt.crearTablas();
        //mt.insertarDatos();

        do {
            //prueba git
            System.out.println("");
            System.out.println("Menú Principal");
            System.out.println("1. Vender/Cobrar un producto"); // LISTO FALTA LOGGS
            System.out.println("2. Ingreso de mercadería"); //LISTO FALTA LOGGS
            System.out.println("3. Pago a proveedor"); //LISTO FALTA LOGGS
            System.out.println("4. Consulta de ventas"); // LISTO
            System.out.println("5. Balance (mostrar ganancias y pérdidas)");
            System.out.println("6. Solicitar una comanda a la cocina"); //LOGGS
            System.out.println("7. Pagar cuenta"); //LOGGS
            System.out.println("8. Información estadística de platos más pedidos");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();

            try {
                switch (opcion) {
                    case 1:
                        mt.venderCobrarProducto();
                        break;
                    case 2:
                        mt.ingresoMercaderia();
                        break;
                    case 3:
                        mt.pagoProveedor();
                        break;
                    case 4:
                        mt.consultaVentas(scanner);
                        break;
                    case 5:
                        mt.balance();
                        break;
                    case 6:
                        mt.solicitarComanda();
                        break;
                    case 7:
                        mt.pagarCuenta();
                        break;
                    case 8:
                        mt.informacionEstadistica();
                        break;
                    case 9:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente de nuevo.");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException var16) {
                }

                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException var15) {
                    var15.printStackTrace();
                }

            }
            System.out.println();
        } while (opcion != 9);
    }
}