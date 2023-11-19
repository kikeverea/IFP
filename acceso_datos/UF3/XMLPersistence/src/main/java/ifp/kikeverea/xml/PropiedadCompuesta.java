package ifp.kikeverea.xml;

import java.util.ArrayList;
import java.util.List;

public abstract class PropiedadCompuesta implements Propiedad {

    private final String nombre;
    private List<Atributo> atributos;

    public PropiedadCompuesta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    protected void anadirAtributo(Atributo atributo) {
        if (atributos == null)
            atributos = new ArrayList<>();

        atributos.add(atributo);
    }

    @Override
    public String getValor() {
        // propiedades con hijos no pueden tener un valor textual
        return null;
    }

    @Override
    public List<Atributo> getAtributos() {
        return atributos;
    }
}
