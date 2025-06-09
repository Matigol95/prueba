import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();
        Scanner scanner = new Scanner(System.in);
        
        int opcion;
        do {
            mostrarMenuPrincipal();
            
            try {
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (opcion) {
                        case 1:
                            banco.registrarCliente(scanner);
                            break;
                        case 2:
                            banco.autenticarCliente(scanner);
                            if (banco.estaAutenticado()) {
                                banco.operarConCliente(scanner);
                            }
                            break;
                        case 3:
                            if (banco.estaAutenticado()) {
                                banco.operarConCliente(scanner);
                            } else {
                                System.out.println("Error: Debe autenticarse primero (opción 2)");
                            }
                            break;
                        case 4:
                            banco.cerrarSesion();
                            System.out.println("Sesión cerrada correctamente");
                            break;
                        case 5:
                            System.out.println("Saliendo del sistema...");
                            break;
                        default:
                            System.out.println("Opción no válida. Debe ser entre 1 y 5");
                    }
                } else {
                    System.out.println("Error: Debe ingresar un número (1-5)");
                    scanner.next();
                    opcion = 0;
                }
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                scanner.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
        
        scanner.close();
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL BANK BOSTON ===");
        System.out.println("1. Registrar nuevo cliente");
        System.out.println("2. Autenticar cliente y operar");
        System.out.println("3. Operaciones bancarias (si está autenticado)");
        System.out.println("4. Cerrar sesión");
        System.out.println("5. Salir");
    }
}