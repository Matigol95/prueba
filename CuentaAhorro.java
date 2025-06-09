public class CuentaAhorro extends CuentaBase {
    public CuentaAhorro(int numero, int saldo) {
        super(numero, saldo);
    }
    
    @Override
    public String toString() {
        return "Cuenta Ahorro #" + numero + " (Saldo: " + saldo + ")";
    }
}