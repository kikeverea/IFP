package ifp.kikeverea.io;

public class FiltroLectura {

    private final String atributo;
    private final String valor;

    public FiltroLectura(String atributo, String valor) {
        this.atributo = atributo;
        this.valor = valor;
    }

    public String atributo() {
        return atributo;
    }

    public String valor() {
        return valor;
    }
}
