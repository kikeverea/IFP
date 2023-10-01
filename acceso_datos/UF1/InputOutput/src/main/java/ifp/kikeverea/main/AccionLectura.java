package ifp.kikeverea.main;

public enum AccionLectura {
    LEER_TODO(1),
    LEER_NOMBRE(2);

    private int numero;

    AccionLectura(int numero) {
        this.numero = numero;
    }

    public static AccionLectura determinarAccion(int numero) {
        return numero == 1 ? LEER_TODO : LEER_NOMBRE;
    }
}
