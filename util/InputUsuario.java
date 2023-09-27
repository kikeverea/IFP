package ifp.kikeverea;

import java.util.InputMismatchException;
import java.util.Scanner;

class InputUsuario {

    private final Scanner scanner;
    private final ValidadorNumeros validador;

    public InputUsuario(Scanner scanner) {
        this(scanner, ValidadorNumeros.sinValidacion());
    }

    public InputUsuario(Scanner scanner, ValidadorNumeros validador) {
        this.scanner = scanner;
        this.validador = validador;
    }

    public String solicitarTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int solicitarEntero(String mensaje) {
        while (true) {
            try {
                // imprime el mensaje al usuario en pantalla y lee el siguiente entero introducido
                System.out.print(mensaje);
                int entero = scanner.nextInt();

                // consume el resto de la línea
                scanner.nextLine();

                // comprobar validez
                if (!validador.validarNumero(entero)) {
                    System.out.println(validador.mensajeError());
                    continue;
                }

                return entero;
            }
            catch (InputMismatchException e) {
                resolverInputInvalido("Por favor, introducir un número entero.");
            }
        }
    }

    public float solicitarDecimal(String mensaje) {
        while (true) {
            try {
                // imprime el mensaje al usuario en pantalla y lee el siguiente flotante introducido
                System.out.print(mensaje);
                float decimal = scanner.nextFloat();

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
                resolverInputInvalido("Por favor, introducir un número.");
            }
        }
    }

    private void resolverInputInvalido(String mensaje) {
        // imprime el mensaje en pantalla
        System.out.println("Ha introducido un valor erróneo en uno de los campos. " + mensaje);

        // consume el resto de la línea
        scanner.nextLine();
    }
}
