package ifp.kikeverea.util;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Stream;

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

    /**
     * Solicita una entrada de texto al usuario. El texto debe contener letras, no puede consistir únicamente de números
     * @param mensaje El mensaje que se imprime al usuario al pedir la entrada
     * @return El texto proporcionado por el usuario
     */
    public String solicitarSoloTexto(String mensaje) {
        while (true) {
            String texto = solicitarTexto(mensaje);

            if (texto.matches("^-?\\d+(\\.\\d+)?$")) // si el texto consiste únicamente en números
                System.out.println("El texto no puede ser únicamente numérico");

            else return texto;
        }
    }

    /**
     * Solicita al usuario elegir entre las opciones 'Si' o 'No', y sus variantes
     * @param mensaje El mensaje que se imprime al usuario al pedir la entrada
     * @return true si el texto entrado por el usuario se encuentra es igual a Si o sus variantes, false de lo contrario
     */
    public boolean solicitarSioNo(String mensaje) {
        return solicitarEleccion(mensaje,
                new String[]{"SI", "Si", "si", "S", "s"},
                new String[]{"NO", "No", "no", "N", "n"});
    }

    /**
     * Solicita al usuario elegir entre una opción positiva y una negativa
     * @param mensaje El mensaje que se imprime al usuario al pedir la entrada
     * @param positivos Los valores que representan la opción positiva
     * @param negativos Los valores que representan la opción negativa
     * @return true si el texto entrado por el usuario se encuentra entre los valores positivos, false de lo contrario
     */
    public boolean solicitarEleccion(String mensaje, String[] positivos, String[] negativos) {
        // une las dos arrays
        String[] valoresValidos = Stream.concat(Arrays.stream(positivos), Arrays.stream(negativos)).toArray(String[]::new);

        while (true) {
            String texto = solicitarTexto(mensaje);
            boolean valido = Arrays.asList(valoresValidos).contains(texto);

            if (valido)
                return Arrays.asList(positivos).contains(texto);

            System.out.println("Por favor, introducir uno de los siguientes valores: " + String.join(", ", valoresValidos));
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

    public <T> T solicitarOpcionMenu(Menu<T> menu) {
        int numeroOpcion = solicitarEntero(menu.mostrar(), ValidadorNumeros.enIntervalo(menu.min(), menu.max()));
        return menu.getOpcion(numeroOpcion);
    }

    private void resolverInputNumericoInvalido(String mensaje) {
        // imprime el mensaje en pantalla
        System.out.println(mensaje);

        // consume el resto de la línea
        scanner.nextLine();
    }
}
