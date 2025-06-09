public class CuentaCredito extends CuentaBase {
    public CuentaCredito(int numero, int saldo) {
        super(numero, saldo);
    }
    
    @Override
    public String toString() {
        return "Cuenta Crédito #" + numero + " (Saldo: " + saldo + ")";
    }
}