package ifp.kikeverea.datos;

import java.util.ArrayList;
import java.util.List;

public abstract class PropiedadCompuesta implements Propiedad {

    private final String nombre;
    private final List<Atributo> atributos = new ArrayList<>();

    public PropiedadCompuesta(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Da acceso al nombre de esta instancia de Propiedad
     * @return el nombre de la Propiedad
     */
    @Override
    public String getNombre() {
        return nombre;
    }

    protected void anadirAtributo(Atributo atributo) {
        atributos.add(atributo);
    }

    /**
     * Da acceso al valor de esta instancia de Propiedad
     * @return el valor de la Propiedad
     */
    @Override
    public String getValor() {
        throw new UnsupportedOperationException("Propiedades con hijos no tienen un valor textual");
    }

    /**
     * Da acceso a los atributos que describen esta instancia de Propiedad
     * @return Una lista con todos los atributos de esta Propiedad
     */
    @Override
    public List<Atributo> getAtributos() {
        return atributos;
    }
}
