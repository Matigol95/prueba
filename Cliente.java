public class Cliente implements Mostrable {
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
    private CuentaBase cuenta;
    
    public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno,
                  String domicilio, String comuna, String telefono, CuentaBase cuenta) {
        setRut(rut);
        setNombre(nombre);
        setApellidoPaterno(apellidoPaterno);
        setApellidoMaterno(apellidoMaterno);
        setDomicilio(domicilio);
        setComuna(comuna);
        setTelefono(telefono);
        this.cuenta = cuenta;
    }
    
    public String getRut() { return rut; }
    public String getNombre() { return nombre; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public String getDomicilio() { return domicilio; }
    public String getComuna() { return comuna; }
    public String getTelefono() { return telefono; }
    public CuentaBase getCuenta() { return cuenta; }
    
    public void setRut(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT no puede estar vacío");
        }
        if (!validarFormatoRUT(rut)) {
            throw new IllegalArgumentException("Formato de RUT inválido. Use XX.XXX.XXX-X");
        }
        this.rut = rut.trim();
    }
    
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (contieneNumeros(nombre)) {
            throw new IllegalArgumentException("El nombre no puede contener números");
        }
        this.nombre = nombre.trim();
    }
    
    public void setApellidoPaterno(String apellidoPaterno) {
        if (apellidoPaterno == null || apellidoPaterno.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido paterno no puede estar vacío");
        }
        if (contieneNumeros(apellidoPaterno)) {
            throw new IllegalArgumentException("El apellido no puede contener números");
        }
        this.apellidoPaterno = apellidoPaterno.trim();
    }
    
    public void setApellidoMaterno(String apellidoMaterno) {
        if (apellidoMaterno == null || apellidoMaterno.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido materno no puede estar vacío");
        }
        if (contieneNumeros(apellidoMaterno)) {
            throw new IllegalArgumentException("El apellido no puede contener números");
        }
        this.apellidoMaterno = apellidoMaterno.trim();
    }
    
    public void setDomicilio(String domicilio) {
        if (domicilio == null || domicilio.trim().isEmpty()) {
            throw new IllegalArgumentException("El domicilio no puede estar vacío");
        }
        this.domicilio = domicilio.trim();
    }
    
    public void setComuna(String comuna) {
        if (comuna == null || comuna.trim().isEmpty()) {
            throw new IllegalArgumentException("La comuna no puede estar vacía");
        }
        if (contieneNumeros(comuna)) {
            throw new IllegalArgumentException("La comuna no puede contener números");
        }
        this.comuna = comuna.trim();
    }
    
    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (!validarTelefono(telefono)) {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 9 dígitos");
        }
        this.telefono = telefono.trim();
    }
    
    private boolean contieneNumeros(String texto) {
        return texto.matches(".*\\d.*");
    }
    
    private boolean validarFormatoRUT(String rut) {
        return rut.matches("^\\d{1,2}\\.\\d{3}\\.\\d{3}-[0-9kK]$");
    }
    
    private boolean validarTelefono(String telefono) {
        return telefono.matches("\\d{9}");
    }
    
    @Override
    public void mostrarInformacion() {
        mostrarDatos();
    }
    
    public void mostrarDatos() {
        System.out.println("\n=== MIS DATOS ===");
        System.out.println("Rut: " + rut);
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido paterno: " + apellidoPaterno);
        System.out.println("Apellido materno: " + apellidoMaterno);
        System.out.println("Domicilio: " + domicilio);
        System.out.println("Comuna: " + comuna);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Tipo de cuenta: " + cuenta.getClass().getSimpleName());
        System.out.println("Número de cuenta: " + cuenta.getNumero());
        System.out.println("Saldo actual: " + cuenta.getSaldo() + " pesos");
    }
}