package ifp.kikeverea.app;

public class Calculadora {

    private Operaciones operacion;
    private String numero1 = "";
    private String numero2 = "";

    public String anadirNumero(String numero) {
        if (operacion == null) {
            numero1 = agregarNumero(numero1, numero);
            return numero1;
        }
        else {
            numero2 = agregarNumero(numero2, numero);
            return numero2;
        }
    }

    private String agregarNumero(String numero, String agregar) {
        return numero.equals("0") ? agregar : numero + agregar;
    }

    public void establecerOperacion(String signo) throws Exception {
        if (numero1.isBlank()) {
            throw new Exception("No se puede establecer una operacón sin haber introducido ningún número");
        }

        operacion = Operaciones.conSigno(signo);

        if (operacion == Operaciones.PAR)
            numero2 = "0";
    }

    public String ejecutarOperacion() throws Exception {
        try {
            float n1 = Float.parseFloat(numero1);
            float n2 = Float.parseFloat(numero2);
            return operacion.operar(n1, n2);
        }
        catch (NumberFormatException e) {
            throw new Exception("Número '"+numero1+"' ó '"+numero2+"' invalido");
        }
    }

    public void clear() {
        numero1 = "";
        numero2 = "";
        operacion = null;
    }

    public enum Operaciones {
        SUMA {
            @Override
            public String operar(float n1, float n2) {
                return formatoResultado(n1 + n2);
            }
        },
        RESTA {
            @Override
            public String operar(float n1, float n2) {
                return formatoResultado(n1 - n2);
            }
        },
        MULTIPLICACION {
            @Override
            public String operar(float n1, float n2) {
                return formatoResultado(n1 * n2);
            }
        },
        DIVISION {
            @Override
            public String operar(float n1, float n2) {
                return n2 == 0 ? "Indeterminación" : formatoResultado(n1 / n2);
            }
        },
        PAR {
            @Override
            public String operar(float n1, float n2) {
                return n1 % 2 == 0 ? "Par" : "Impar";
            }
        };
        
        String formatoResultado(float resultado) {
            return  (int) resultado == resultado ?
                    Integer.toString((int) resultado) :
                    Float.toString(resultado);
        }

        public abstract String operar(float n1, float n2);

        public static Operaciones conSigno(String signo) {
            switch (signo.toLowerCase()) {
                case "+" :
                    return SUMA;
                case "-" :
                    return RESTA;
                case "*" :
                    return MULTIPLICACION;
                case "/" :
                    return DIVISION;
                case "par" :
                    return PAR;
                default:
                    throw new IllegalArgumentException("No existe operación con signo " + signo);
            }
        }
    }
}
