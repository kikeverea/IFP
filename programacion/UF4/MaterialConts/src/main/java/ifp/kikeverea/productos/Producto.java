package ifp.kikeverea.productos;

public abstract class Producto {

    protected int id;
    protected String nombre;
    protected String tipo;
    protected float anchura;
    protected float profundidad;
    protected float altura;

    public Producto(int id, String nombre, String tipo, float anchura, float profundidad, float altura) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.anchura = anchura;
        this.profundidad = profundidad;
        this.altura = altura;
    }

    public String mostrarInfo() {
        return  "Identificación: " + id + " - " +
                "Tipo: " + tipo + " - " +
                "Nombre: " + nombre + " - " +
                "Anchura: " + anchura + " - " +
                "Profundidad: " + profundidad + " - " +
                "Altura: " + altura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getAnchura() {
        return anchura;
    }

    public void setAnchura(float anchura) {
        this.anchura = anchura;
    }

    public float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(float profundidad) {
        this.profundidad = profundidad;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
}
