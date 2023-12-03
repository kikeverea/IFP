package ifp.kikeverea.persona;

import java.util.ArrayList;
import java.util.List;

public abstract class PropiedadCompuesta implements Propiedad {

    private final String nombre;
    private final List<Atributo> atributos = new ArrayList<>();

    public PropiedadCompuesta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    protected void anadirAtributo(Atributo atributo) {
        atributos.add(atributo);
    }

    @Override
    public String getValor() {
        // Propiedades con hijos no tienen un valor textual
        return null;
    }

    @Override
    public List<Atributo> getAtributos() {
        return atributos;
    }
}
