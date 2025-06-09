import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Banco {
    private List<Cliente> clientes;
    private Map<String, Cliente> clientesPorRut;
    private Map<String, Cliente> clientesPorTelefono;
    private Map<Integer, Cliente> clientesPorCuenta;
    private Cliente clienteAutenticado;
    
    public Banco() {
        this.clientes = new ArrayList<>();
        this.clientesPorRut = new HashMap<>();
        this.clientesPorTelefono = new HashMap<>();
        this.clientesPorCuenta = new HashMap<>();
    }
    
    public int obtenerEnteroPositivo(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                if (scanner.hasNextInt()) {
                    int valor = scanner.nextInt();
                    scanner.nextLine(); 
                    if (valor <= 0) {
                        System.out.println("Error: Debe ingresar un número positivo");
                        continue;
                    }
                    return valor;
                } else {
                    System.out.println("Error: Debe ingresar un número válido");
                    scanner.next(); 
                }
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
    
    public int obtenerOpcionMenu(Scanner scanner, int min, int max) {
        while (true) {
            try {
                System.out.print("Seleccione una opción (" + min + "-" + max + "): ");
                if (scanner.hasNextInt()) {
                    int opcion = scanner.nextInt();
                    scanner.nextLine(); 
                    if (opcion >= min && opcion <= max) {
                        return opcion;
                    } else {
                        System.out.println("Error: Opción debe estar entre " + min + " y " + max);
                    }
                } else {
                    System.out.println("Error: Debe ingresar un número");
                    scanner.next(); 
                }
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
    
    public void registrarCliente(Scanner scanner) {
        System.out.println("\n=== REGISTRO DE NUEVO CLIENTE ===");
        
        String rut = "";
        while (true) {
            try {
                System.out.print("Ingrese Rut (ej: 12.345.678-9): ");
                rut = scanner.nextLine().trim();
                
                if (rut.isEmpty()) {
                    System.out.println("Error: El RUT no puede estar vacío");
                    continue;
                }
                
                if (!rut.matches("^\\d{1,2}\\.\\d{3}\\.\\d{3}-[0-9kK]$")) {
                    System.out.println("Error: Formato de RUT inválido. Use XX.XXX.XXX-X");
                    continue;
                }
                
                if (clientesPorRut.containsKey(rut)) {
                    System.out.println("Error: Este RUT ya está registrado");
                    continue;
                }
                
                break;
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        
        String telefono = "";
        while (true) {
            try {
                System.out.print("Ingrese teléfono (9 dígitos): ");
                telefono = scanner.nextLine().trim();
                
                if (telefono.isEmpty()) {
                    System.out.println("Error: El teléfono no puede estar vacío");
                    continue;
                }
                
                if (!telefono.matches("\\d{9}")) {
                    System.out.println("Error: El teléfono debe tener exactamente 9 dígitos");
                    continue;
                }
                
                if (clientesPorTelefono.containsKey(telefono)) {
                    System.out.println("Error: Este teléfono ya está registrado");
                    continue;
                }
                
                break;
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        
        String nombre = validarCampoTexto(scanner, "nombre", true, false);
        String apellidoPaterno = validarCampoTexto(scanner, "apellido paterno", true, false);
        String apellidoMaterno = validarCampoTexto(scanner, "apellido materno", true, false);
        String domicilio = validarCampoTexto(scanner, "domicilio", true, true);
        String comuna = validarCampoTexto(scanner, "comuna", true, false);
        
        int tipoCuenta = 0;
        while (tipoCuenta < 1 || tipoCuenta > 3) {
            try {
                System.out.println("\nSeleccione tipo de cuenta:");
                System.out.println("1. Cuenta Corriente");
                System.out.println("2. Cuenta de Ahorro");
                System.out.println("3. Cuenta de Crédito");
                tipoCuenta = obtenerOpcionMenu(scanner, 1, 3);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        int numeroCuenta;
        do {
            numeroCuenta = (int)(Math.random() * 900000000) + 100000000;
        } while (clientesPorCuenta.containsKey(numeroCuenta));
        
        CuentaBase cuenta;
        switch (tipoCuenta) {
            case 1:
                cuenta = new CuentaCorriente(numeroCuenta, 0);
                break;
            case 2:
                cuenta = new CuentaAhorro(numeroCuenta, 0);
                break;
            case 3:
                int montoInicial = obtenerEnteroPositivo(scanner, "Ingrese el monto inicial para la cuenta de crédito: ");
                cuenta = new CuentaCredito(numeroCuenta, montoInicial);
                break;
            default:
                cuenta = new CuentaCorriente(numeroCuenta, 0);
        }
        
        try {
            Cliente nuevoCliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno, 
                                            domicilio, comuna, telefono, cuenta);
            
            clientes.add(nuevoCliente);
            clientesPorRut.put(rut, nuevoCliente);
            clientesPorTelefono.put(telefono, nuevoCliente);
            clientesPorCuenta.put(numeroCuenta, nuevoCliente);
            
            System.out.println("\n¡Cliente registrado con éxito!");
            System.out.println("Número de cuenta asignado: " + numeroCuenta);
            System.out.println("Tipo de cuenta: " + cuenta.getClass().getSimpleName());
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }
    }
    
    private String validarCampoTexto(Scanner scanner, String nombreCampo, boolean obligatorio, boolean permitirNumeros) {
        while (true) {
            try {
                System.out.print("Ingrese " + nombreCampo + ": ");
                String valor = scanner.nextLine().trim();
                
                if (obligatorio && valor.isEmpty()) {
                    System.out.println("Error: El " + nombreCampo + " no puede estar vacío");
                    continue;
                }
                
                if (!permitirNumeros && valor.matches(".*\\d.*")) {
                    System.out.println("Error: El " + nombreCampo + " no puede contener números");
                    continue;
                }
                
                return valor;
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
    }
    
    public boolean estaAutenticado() {
        return clienteAutenticado != null;
    }
    
    public void cerrarSesion() {
        clienteAutenticado = null;
    }
    
    public void autenticarCliente(Scanner scanner) {
        System.out.println("\n=== AUTENTICACIÓN DE CLIENTE ===");
        System.out.print("Ingrese su RUT (ej: 12.345.678-9): ");
        String rut = scanner.nextLine();
        
        if (clientesPorRut.containsKey(rut)) {
            clienteAutenticado = clientesPorRut.get(rut);
            System.out.println("Bienvenido, " + clienteAutenticado.getNombre());
        } else {
            System.out.println("Error: RUT no registrado en el sistema.");
        }
    }
    
    public void operarConCliente(Scanner scanner) {
        if (clienteAutenticado == null) {
            System.out.println("Error: No hay cliente autenticado");
            return;
        }
        
        int opcion;
        do {
            System.out.println("\nCliente actual: " + clienteAutenticado.getNombre());
            mostrarMenuOperaciones();
            opcion = obtenerOpcionMenu(scanner, 1, 6);
            
            try {
                switch (opcion) {
                    case 1:
                        clienteAutenticado.mostrarInformacion();
                        break;
                    case 2:
                        depositar(scanner);
                        break;
                    case 3:
                        girar(scanner);
                        break;
                    case 4:
                        consultarSaldo();
                        break;
                    case 5:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    case 6:
                        cerrarSesion();
                        System.out.println("Sesión cerrada. Volviendo al menú principal...");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 5 && opcion != 6 && clienteAutenticado != null);
    }
    
    private void mostrarMenuOperaciones() {
        System.out.println("\n=== MENÚ DE OPERACIONES ===");
        System.out.println("1. Ver mis datos");
        System.out.println("2. Depositar");
        System.out.println("3. Girar");
        System.out.println("4. Consultar saldo");
        System.out.println("5. Volver al menú principal");
        System.out.println("6. Cerrar sesión");
    }
    
    private void depositar(Scanner scanner) {
        System.out.println("\n=== DEPÓSITO ===");
        int monto = obtenerEnteroPositivo(scanner, "Ingrese un monto para depositar: ");
        
        try {
            clienteAutenticado.getCuenta().depositar(monto);
            System.out.println("¡Depósito realizado de manera exitosa!");
            System.out.println("Saldo actual: " + clienteAutenticado.getCuenta().getSaldo() + " pesos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error en depósito: " + e.getMessage());
        }
    }
    
    private void girar(Scanner scanner) {
        System.out.println("\n=== GIRO ===");
        int monto = obtenerEnteroPositivo(scanner, "Ingrese un monto para girar: ");
        
        try {
            clienteAutenticado.getCuenta().girar(monto);
            System.out.println("¡Giro realizado de manera exitosa!");
            System.out.println("Saldo actual: " + clienteAutenticado.getCuenta().getSaldo() + " pesos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error en giro: " + e.getMessage());
        }
    }
    
    private void consultarSaldo() {
        System.out.println("\n=== CONSULTA DE SALDO ===");
        System.out.println("Saldo actual: " + clienteAutenticado.getCuenta().getSaldo() + " pesos");
    }
}