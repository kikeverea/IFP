package ifp.kikeverea;

public class AvatarCaballo {

    private static final String CRIN = " ,--,";
    private static final String MORRO = "/ /\\|";

    private static final int LONGITUD_CABALLO = CRIN.length();

    private final String nombre;
    private final int paddingCaballo;
    private final int paddingNombre;
    private final int longitud;

    public AvatarCaballo(String nombre) {
        this.nombre = nombre.length() % 2 == 0 ? nombre+" " : nombre;
        this.longitud = Math.max(LONGITUD_CABALLO, this.nombre.length());

        this.paddingCaballo = (longitud - LONGITUD_CABALLO) / 2;
        this.paddingNombre = (longitud - this.nombre.length()) / 2;
    }

    public int longitud() {
        return longitud;
    }

    public String crin(int padding) {
        return figura(CRIN, padding + paddingCaballo);
    }

    public String morro(int padding) {
        return figura(MORRO, padding + paddingCaballo);
    }

    public String nombre(int padding) {
        return figura(nombre, padding + paddingNombre);
    }

    private String figura(String figura, int padding) {
        return " ".repeat(padding) + figura + " ".repeat(padding);
    }
}
