public abstract class CuentaBase {
    protected int numero;
    protected int saldo;
    
    public CuentaBase(int numero, int saldo) {
        this.numero = numero;
        this.saldo = saldo;
    }
    
    public int getNumero() { return numero; }
    public int getSaldo() { return saldo; }
    
    public void depositar(int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser positivo");
        }
        saldo += monto;
    }
    
    public void girar(int monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a girar debe ser positivo");
        }
        if (monto > saldo) {
            throw new IllegalArgumentException("No puede gastar más dinero del disponible en la cuenta");
        }
        saldo -= monto;
    }
}