package ifp.kikeverea.vehiculos;

public class VehiculoElectrico extends Vehiculo {

    private float potencia;

    public VehiculoElectrico(String marca, String color, float precio, float longitud,
                             float peso, float potencia)
    {
        super(marca, color, precio, longitud, peso);
        this.potencia = potencia;
    }

    public float getPotencia() {
        return potencia;
    }

    public void setPotencia(float potencia) {
        this.potencia = potencia;
    }

    @Override
    public String mostrarInfo() {
        return  super.mostrarInfo() + " - " +
                "Potencia: " + potencia;
    }
}
