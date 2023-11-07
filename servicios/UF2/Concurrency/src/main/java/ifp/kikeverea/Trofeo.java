package ifp.kikeverea;

public class Trofeo {

    private static final String TROFEO_SUPERIOR = " _______ ";
    private static final String TROFEO_MEDIO = "(\\__1__/)";
    private static final String TROFEO_BAJO = "  _|_|_  ";
    private static final String PADDING = "   ";

    public static String superior(int posicion) {
        return posicion == 1 ? PADDING + TROFEO_SUPERIOR : "";
    }

    public static String medio(int posicion) {
        return posicion == 1 ? PADDING + TROFEO_MEDIO : PADDING + "(  "+posicion+"  )";
    }

    public static String bajo(int posicion) {
        return posicion == 1 ? PADDING + TROFEO_BAJO : "";
    }
}
