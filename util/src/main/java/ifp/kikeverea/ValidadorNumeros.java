package ifp.kikeverea.util;

public interface ValidadorNumeros {

    boolean validarNumero(double numero);
    String mensajeError();

    static ValidadorNumeros soloPositivos() {
        return new ValidadorPositivos();
    }
    static ValidadorNumeros sinValidacion() {
        return new NoValidador();
    }
    static ValidadorNumeros enIntervalo(int start, int end) {
        return new ValidadorEnIntervalo(start, end);
    }

    /**
     * Clase que valida números solo si son positivos
     */
    class ValidadorPositivos implements ValidadorNumeros {
        @Override
        public boolean validarNumero(double numero) {
            return numero >= 0;
        }

        @Override
        public String mensajeError() {
            return "Por favor, introducir un número positivo";
        }
    }

    /**
     * Clase que valida números solo si se encuentran dentro del intervalo establecido
     */
    class ValidadorEnIntervalo implements ValidadorNumeros {

        private final int start;
        private final int end;

        public ValidadorEnIntervalo(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean validarNumero(double numero) {
            return numero >= start && numero <= end;
        }

        @Override
        public String mensajeError() {
            return "Por favor, introducir un número entre " + start + " y " + end;
        }
    }

    /**
     * Clase que ignora la validación de números
     */
    class NoValidador implements ValidadorNumeros {
        @Override
        public boolean validarNumero(double numero) {
            return true;
        }

        @Override
        public String mensajeError() {
            return "";
        }
    }
}
