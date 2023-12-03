package ifp.kikeverea.ejercicio3;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculadora {

    private static final String REGEX_NUM_DECIMAL = "[0-9]+(?:\\.[0-9]+)?";
    private static final Pattern OPERACION =
            Pattern.compile("^("+REGEX_NUM_DECIMAL+")([+\\-*/])("+REGEX_NUM_DECIMAL+")$");

    private final NumberFormat formato;

    public Calculadora() {
        formato = new DecimalFormat("0.###########");
        formato.setRoundingMode(RoundingMode.DOWN);
    }

    public String realizarOperacion(String operacion) {
        operacion = operacion.replaceAll(" ", "");      // elimina espacios en blanco
        Matcher formato = OPERACION.matcher(operacion);

        return formato.matches()
                ? realizarOperacion(formato.group(1), formato.group(2), formato.group(3))
                : "Formato de operación inválido. Debe ser: 'numero' 'operación' 'numero'";
    }

    private String realizarOperacion(String operando1, String operador, String operando2) {
        try {
            Double num1 = Double.valueOf(operando1);
            Double num2 = Double.valueOf(operando2);

            return calcularResultado(num1, num2, operador);
        }
        catch (NumberFormatException e) {
            return "Formato de número inválido";
        }
    }

    private String calcularResultado(Double num1, Double num2, String operacion) {
        return switch (operacion) {
            case "+" -> darFormato(num1 + num2);
            case "-" -> darFormato(num1 - num2);
            case "*" -> darFormato(num1 * num2);
            case "/" -> num2.equals(0.0d)
                        ? "Error: División entre cero"
                        : darFormato(num1 / num2);
            default -> throw new IllegalArgumentException("Operation '" + operacion + "' not valid");
        };
    }

    private String darFormato(Number numero) {
        if (numero.intValue() == numero.doubleValue())
            return  "" + numero.intValue();

        if (numero.doubleValue() < 0.00000000001)
            return "" + numero.doubleValue();

        return formato.format(numero.doubleValue());
    }
}
