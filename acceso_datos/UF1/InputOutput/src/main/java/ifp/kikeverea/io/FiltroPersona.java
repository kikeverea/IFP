package ifp.kikeverea.io;

public class FiltroPersona {

    private final String atributo;
    private final String valor;

    public FiltroPersona(String atributo, String valor) {
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
