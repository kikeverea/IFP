package ifp.kikeverea.vehiculos;

public abstract class Vehiculo {

    protected String marca;
    protected String color;
    protected float precio;
    protected float peso;
    protected float longitud;

    public Vehiculo(String marca, String color, float precio, float longitud, float peso) {
        this.marca = marca;
        this.color = color;
        this.precio = precio;
        this.longitud = longitud;
        this.peso = peso;
    }

    public String mostrarInfo() {
        return  "Marca: " + marca + " - " +
                "Color: " + color + " - " +
                "Precio: " + precio + " - " +
                "Longitud: " + longitud + " - " +
                "Peso: " + peso;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
}
