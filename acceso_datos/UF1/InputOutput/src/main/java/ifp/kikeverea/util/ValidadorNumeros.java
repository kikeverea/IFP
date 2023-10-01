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
