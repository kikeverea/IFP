package ifp.kikeverea.persona;

public class Atributo {

    private final String nombre;
    private final String valor;

    public Atributo(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public Atributo(String nombre, boolean valor) {
        this(nombre, valor ? "SI" : "NO");
    }

    public String getNombre() {
        return nombre;
    }

    public String getValor() {
        return valor;
    }
}
