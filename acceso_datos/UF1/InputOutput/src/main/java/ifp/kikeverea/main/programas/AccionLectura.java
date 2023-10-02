package ifp.kikeverea.main.programas;

public enum AccionLectura {
    LEER_TODO(1),
    LEER_NOMBRE(2);

    private final int numero;

    AccionLectura(int numero) {
        this.numero = numero;
    }

    public static AccionLectura determinarAccion(int numero) {
        return numero == LEER_TODO.numero ? LEER_TODO : LEER_NOMBRE;
    }
}
