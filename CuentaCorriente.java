public class CuentaCorriente extends CuentaBase {
    public CuentaCorriente(int numero, int saldo) {
        super(numero, saldo);
    }
    
    @Override
    public String toString() {
        return "Cuenta Corriente #" + numero + " (Saldo: " + saldo + ")";
    }
}