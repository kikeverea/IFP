package ifp.kikeverea;

import java.util.List;

public class Entidad {

    private final String nombre;
    private final Atributo clavePrimaria;
    private final List<Atributo> atributos;

    public Entidad(String nombre, List<Atributo> atributos) {
        this.clavePrimaria = extraerPrimaryKey(atributos);
        this.nombre = nombre;
        this.atributos = atributos;
    }

    private Atributo extraerPrimaryKey(List<Atributo> atributos) {
        return atributos
                .stream()
                .filter(Atributo::esPrimaryKey)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("La entidad debe tener una clave primaria (primary key)"));
    }

    public String getNombre() {
        return nombre;
    }

    public Atributo getClavePrimaria() {
        return clavePrimaria;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public Objeto nuevaInstancia() {
        return new Objeto(this);
    }
}
