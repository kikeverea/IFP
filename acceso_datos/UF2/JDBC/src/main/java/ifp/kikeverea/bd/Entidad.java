package ifp.kikeverea.bd;

import java.util.List;
import java.util.Objects;

public class Entidad {

    private final String nombre;
    private final Atributo clavePrimaria;
    private final List<Atributo> atributos;

    public Entidad(String nombre, List<Atributo> atributos) {
        this.clavePrimaria = extraerClavePrimaria(atributos);
        this.nombre = nombre;
        this.atributos = atributos;
    }

    private Atributo extraerClavePrimaria(List<Atributo> atributos) {
        return atributos
                .stream()
                .filter(Atributo::esClavePrimaria)
                .findAny()
                .orElse(Atributo.nuevoAtributo("id").deTipo(
                        TipoAtributo.NUMERO,
                        ClausulaAtributo.PRIMARY_KEY,
                        ClausulaAtributo.AUTO_INCREMENT));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entidad entidad = (Entidad) o;
        return nombre.equals(entidad.nombre) && atributos.equals(entidad.atributos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, atributos);
    }

    @Override
    public String toString() {
        return "Entidad{" +
                "nombre='" + nombre + '\'' +
                ", atributos=" + atributos +
                '}';
    }
}
