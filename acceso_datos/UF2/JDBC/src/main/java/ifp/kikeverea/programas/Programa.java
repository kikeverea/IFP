package ifp.kikeverea.programas;

public class Programa {

    public static void imprimirResultado(String resultado) {
        System.out.println("----------------------------------------");
        System.out.println(resultado);
        System.out.println("----------------------------------------");
    }

    public static void imprimirError(String resultado) {
        System.err.println("----------------------------------------");
        System.err.println(resultado);
        System.err.println("----------------------------------------");
    }

    public static <T> T operacionCancelada() {
        imprimirResultado("Operación cancelada");
        return null;
    }
}
