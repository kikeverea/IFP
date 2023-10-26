package ifp.kikeverea.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que facilita la solicitud de datos al usuario por entrada estándar
 */
public class InputUsuario {

    private final Scanner scanner;
    
    public InputUsuario(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Solicita una entrada de texto al usuario
     * @param mensaje El mensaje que se imprime al usuario al pedir la entrada 
     * @return El texto proporcionado por el usuario
     */
    public String solicitarTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().strip();
    }

    public String solicitarSoloTexto(String mensaje) {
        while (true) {
            String texto = solicitarTexto(mensaje);

            if (texto.matches("^-?\\d+(\\.\\d+)?$"))
                System.out.println("El texto no puede ser únicamente numérico");

            else return texto;
        }

    }

    /**
     * Solicita una entrada de entero al usuario
     * @param mensaje El mensaje que se imprime al usuario al pedir la entrada 
     * @return El entero proporcionado por el usuario
     */
    public int solicitarEntero(String mensaje) {
        return solicitarEntero(mensaje, ValidadorNumeros.sinValidacion());
    }

    /**
     * Solicita una entrada de entero al usuario, hasta que ésta sea válida
     * @param mensaje El mensaje que se imprime al usuario al pedir la entrada
     * @param validador el objeto que dará validez a la entrada del usuario
     * @return El entero proporcionado por el usuario, una vez validado
     */
    public int solicitarEntero(String mensaje, ValidadorNumeros validador) {
        while (true) {
            try {
                // imprime el mensaje al usuario en pantalla y lee el siguiente entero introducido
                System.out.print(mensaje);
                int entero = scanner.nextInt();

                // consume el resto de la línea
                scanner.nextLine();

                // comprueba validez
                if (!validador.validarNumero(entero)) {
                    System.out.println(validador.mensajeError());
                    continue;
                }

                return entero;
            }
            catch (InputMismatchException e) {
                resolverInputNumericoInvalido("Por favor, introducir un número entero.");
            }
        }
    }

    /**
     * Solicita una entrada de número al usuario
     * @param mensaje El mensaje que se imprime al usuario al pedir la entrada
     * @return El número proporcionado por el usuario
     */
    public double solicitarDecimal(String mensaje) {
        return solicitarDecimal(mensaje, ValidadorNumeros.sinValidacion());
    }

    /**
     * Solicita una entrada de número al usuario, hasta que ésta sea válida
     * @param mensaje El mensaje que se imprime al usuario al pedir la entrada
     * @param validador el objeto que dará validez a la entrada del usuario
     * @return El número proporcionado por el usuario, una vez validado
     */
    public double solicitarDecimal(String mensaje, ValidadorNumeros validador) {
        while (true) {
            try {
                // imprime el mensaje al usuario en pantalla y lee el siguiente flotante introducido
                System.out.print(mensaje);
                double decimal = scanner.nextDouble();

                // consume el resto de la línea
                scanner.nextLine();

                // comprobar validez
                if (!validador.validarNumero(decimal)) {
                    System.out.println(validador.mensajeError());
                    continue;
                }

                return decimal;
            }
            catch (InputMismatchException e) {
                resolverInputNumericoInvalido("Por favor, introducir un número.");
            }
        }
    }

    public OpcionMenu solicitarOpcionMenu(Menu menu) {
        int numTipo = solicitarEntero(menu.mostrar(), ValidadorNumeros.enIntervalo(0, menu.count() -1));
        return menu.getOpcion(numTipo);
    }

    private void resolverInputNumericoInvalido(String mensaje) {
        // imprime el mensaje en pantalla
        System.out.println(mensaje);

        // consume el resto de la línea
        scanner.nextLine();
    }
}
