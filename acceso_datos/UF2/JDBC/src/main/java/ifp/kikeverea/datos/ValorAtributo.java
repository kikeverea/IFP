package ifp.kikeverea.datos;

import ifp.kikeverea.bd.Atributo;
import ifp.kikeverea.bd.TipoAtributo;

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

    public TipoAtributo tipo() {
        return atributo.getTipo();
    }

    public Object valor() {
        return valor;
    }

    public boolean esClavePrimaria() {
        return atributo.esClavePrimaria();
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
        String valor = tipo() == TipoAtributo.TEXTO ? "'"+this.valor+"'" : this.valor.toString();
        return atributo.getNombre()+": "+valor;
    }
}
