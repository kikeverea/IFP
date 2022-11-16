package ifp.kikeverea;

import ifp.kikeverea.edificios.Edificio;
import ifp.kikeverea.productos.Producto;
import ifp.kikeverea.vehiculos.Vehiculo;

public class Empresa {

    private Vehiculo vehiculo1;
    private Vehiculo vehiculo2;
    private Edificio edificio1;
    private Edificio edificio2;
    private Edificio edificio3;
    private Producto producto1;
    private Producto producto2;
    private Producto producto3;
    private Producto producto4;

    public String mostrarInfo() {
        return  "Vehículos:\n" + 
                    mostrarInfo(vehiculo1, 1) +
                    mostrarInfo(vehiculo2, 2) +
                "Edificios:\n" + 
                    mostrarInfo(edificio1, 1) +
                    mostrarInfo(edificio2, 2) +
                    mostrarInfo(edificio3, 3) +
                "Productos:\n" +
                    mostrarInfo(producto1, 1) +
                    mostrarInfo(producto2, 2) +
                    mostrarInfo(producto3, 3) +
                    mostrarInfo(producto4, 4);
    }

    private String mostrarInfo(Vehiculo vehiculo, int n) {
        return vehiculo != null ? darFormato("Vehículo", n, vehiculo.mostrarInfo()) : "";
    }

    private String mostrarInfo(Edificio edificio, int n) {
        return edificio != null ? darFormato("Edificio", n, edificio.mostrarInfo()) : "";
    }

    private String mostrarInfo(Producto producto, int n) {
        return producto != null ? darFormato("Producto", n, producto.mostrarInfo()) : "";
    }

    private String darFormato(String objeto, int numero, String info) {
        return "\t" + objeto + " " + numero + ": " + info + "\n";
    }

    public Vehiculo getVehiculo1() {
        return vehiculo1;
    }

    public void setVehiculo1(Vehiculo vehiculo1) {
        this.vehiculo1 = vehiculo1;
    }

    public Vehiculo getVehiculo2() {
        return vehiculo2;
    }

    public void setVehiculo2(Vehiculo vehiculo2) {
        this.vehiculo2 = vehiculo2;
    }

    public Edificio getEdificio1() {
        return edificio1;
    }

    public void setEdificio1(Edificio edificio1) {
        this.edificio1 = edificio1;
    }

    public Edificio getEdificio2() {
        return edificio2;
    }

    public void setEdificio2(Edificio edificio2) {
        this.edificio2 = edificio2;
    }

    public Edificio getEdificio3() {
        return edificio3;
    }

    public void setEdificio3(Edificio edificio3) {
        this.edificio3 = edificio3;
    }

    public Producto getProducto1() {
        return producto1;
    }

    public void setProducto1(Producto producto1) {
        this.producto1 = producto1;
    }

    public Producto getProducto2() {
        return producto2;
    }

    public void setProducto2(Producto producto2) {
        this.producto2 = producto2;
    }

    public Producto getProducto3() {
        return producto3;
    }

    public void setProducto3(Producto producto3) {
        this.producto3 = producto3;
    }

    public Producto getProducto4() {
        return producto4;
    }

    public void setProducto4(Producto producto4) {
        this.producto4 = producto4;
    }
}
