package ifp.kikeverea.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUsuario {

    private final Scanner scanner;
    
    public InputUsuario(Scanner scanner) {
        this.scanner = scanner;
    }

    public String solicitarTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int solicitarEntero(String mensaje) {
        return solicitarEntero(mensaje, ValidadorNumeros.sinValidacion());
    }
    
    public int solicitarEntero(String mensaje, ValidadorNumeros validador) {
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

    public double solicitarDecimal(String mensaje) {
        return solicitarDecimal(mensaje, ValidadorNumeros.sinValidacion());
    }

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
