package ifp.kikeverea.util;

public interface OpcionMenu {
    String mensaje(String... args);

    static OpcionMenu opcionSimple(String mensaje) {
        return new OpcionSimple(mensaje);
    }

    //TODO opcionCompuesta(String... mensajes)

    class OpcionSimple implements OpcionMenu {

        private final String mensaje;

        private OpcionSimple(String mensaje) {
            this.mensaje = mensaje;
        }

        @Override
        public String mensaje(String... args) {
            return mensaje;
        }
    }
}
