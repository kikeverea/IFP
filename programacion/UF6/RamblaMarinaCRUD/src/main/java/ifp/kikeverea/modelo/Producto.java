package ifp.kikeverea.modelo;

import java.util.Objects;

public class Producto {

    private int id;
    private String nombre;
    private float precio;
    private int cantidad;

    public Producto(String nombre, float precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    Producto(int id, String nombre, float precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return  Float.compare(producto.precio, precio) == 0 &&
                cantidad == producto.cantidad &&
                Objects.equals(nombre, producto.nombre);
    }

    @Override
    public String toString() {
        return  nombre + " - " +
                id + " - " +
                String.format("%.2f€", precio) + " - " +
                cantidad + (cantidad == 1 ? " unidad" : " unidades");
    }
}
