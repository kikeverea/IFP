package ifp.kikeverea.vehiculos;

public class VehiculoGasolina extends Vehiculo {

    private String contaminacion;
    private String tamanoDeposito;

    public VehiculoGasolina(String marca, String color, float precio, float longitud, float peso) {
        super(marca, color, precio, longitud, peso);
    }

    public String getContaminacion() {
        return contaminacion;
    }

    public void setContaminacion(String contaminacion) {
        this.contaminacion = contaminacion;
    }

    public String getTamanoDeposito() {
        return tamanoDeposito;
    }

    public void setTamanoDeposito(String tamanoDeposito) {
        this.tamanoDeposito = tamanoDeposito;
    }

    @Override
    public String mostrarInfo() {
        return  super.mostrarInfo() + " - " +
                "Contaminación:" + contaminacion + " - " +
                "Tamaño Depósito:" + tamanoDeposito;
    }
}
