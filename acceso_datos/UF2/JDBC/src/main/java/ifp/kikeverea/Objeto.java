package ifp.kikeverea;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Objeto {

    private final List<ValorAtributo> valores;

    Objeto(Entidad entidad) {
        this.valores = entidad.getAtributos()
                .stream()
                .map(ValorAtributo::new)
                .collect(Collectors.toList());
    }

    public void setValor(String nombreAtributo, Object valor) {
        ValorAtributo valorAtributo = getAtributo(nombreAtributo);
        valorAtributo.setValor(valor);
    }

    public List<ValorAtributo> getAtributos() {
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
}
