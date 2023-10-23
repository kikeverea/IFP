package ifp.kikeverea;

import java.util.Objects;

public class ValorAtributo {

    private final Atributo atributo;
    private Object valor;

    public ValorAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public String nombreAtributo() {
        return atributo.getNombre();
    }

    public int tipoSql() {
        return atributo.getTipo().getTipoSql();
    }

    public Object valor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValorAtributo that = (ValorAtributo) o;
        return Objects.equals(atributo, that.atributo) && Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atributo, valor);
    }

    @Override
    public String toString() {
        return "ValorAtributo{" +
                "atributo=" + atributo +
                ", valor=" + valor +
                '}';
    }
}
