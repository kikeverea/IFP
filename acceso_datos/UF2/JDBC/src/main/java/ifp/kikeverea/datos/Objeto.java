package ifp.kikeverea.datos;

import ifp.kikeverea.bd.Entidad;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Objeto {

    private final List<ValorAtributo> valores;
    private final ValorAtributo clavePrimaria;

    private Objeto(Entidad entidad) {
        clavePrimaria = new ValorAtributo(entidad.getClavePrimaria());
        valores = entidad.getAtributos()
                .stream()
                .filter(atributo -> !atributo.esClavePrimaria())
                .map(ValorAtributo::new)
                .collect(Collectors.toList());
        valores.add(clavePrimaria);
    }

    public static Objeto instanciaDe(Entidad entidad) {
        return new Objeto(entidad);
    }

    public void setValor(String nombreAtributo, Object valor) {
        ValorAtributo valorAtributo = getAtributo(nombreAtributo);
        valorAtributo.setValor(valor);
    }

    public void setClavePrimaria(Object valor) {
        clavePrimaria.setValor(valor);
    }

    public ValorAtributo getClavePrimaria() {
        return clavePrimaria;
    }

    public List<ValorAtributo> getValoresAtributos() {
        return valores;
    }

    public ValorAtributo getAtributo(String nombreAtributo) {
        return valores
                .stream()
                .filter(attr -> attr.nombreAtributo().equals(nombreAtributo))
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("El objeto no contiene el atributo " + nombreAtributo));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objeto objeto = (Objeto) o;

        return valores.equals(objeto.valores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valores);
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "valores=" + valores +
                '}';
    }
}
