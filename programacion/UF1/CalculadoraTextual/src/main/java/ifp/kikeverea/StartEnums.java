package ifp.kikeverea;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class StartEnums {

    enum OPERACIONES {
        SUMA(
            "Suma", 2, 
            "Introduzca el %s número flotante a sumar: ",
            numeros -> String.format("La suma de %.2f y %.2f es %.2f",
                    numeros[0], numeros[1], (numeros[0] + numeros[1]))
        ),
        RESTA(
            "Resta", 2,
            "Introduzca el %s número flotante a restar: ",
                numeros -> String.format("La resta de %.2f y %.2f es %.2f",
                        numeros[0], numeros[1], (numeros[0] - numeros[1]))
        ),
        MULTIPLICACION(
            "Multiplicación", 2,
            "Introduzca el %s número flotante a multiplicar: ",
                numeros -> String.format("La multiplicación de %.2f y %.2f es %.2f",
                        numeros[0], numeros[1], (numeros[0] * numeros[1]))
        ),
        DIVISION(
            "División", 2,
            "Introduzca el %s número flotante a dividir: ",
            numeros -> {
                //evita la division entre 0
                if (numeros[1] == 0)
                    return "Error division por 0";

                return String.format("La división de %.2f y %.2f es %.2f",
                        numeros[0], numeros[1], (numeros[0] / numeros[1]));
            }
        ),
        MAXIMO_DE_TRES(
            "Máximo de Tres", 3,
            "Introduzca el %s número flotante de los 3: ",
            numeros -> String.format("El número %.2f es el mayor de los 3 números",
                    Math.max(Math.max(numeros[0], numeros[1]), numeros[2]))
        ),
        CAPICUA(
            "Capicúa", 1,
            "Introduzca un número para saber si es capicúa: ",
            numeros -> String.format("%s es capicúa",
                        esCapicua(String.valueOf(numeros[0].intValue()))
                        ? "SI"
                        : "NO")
        );

        private final String nombre;
        private final int numerosNecesarios;
        private final String mensajeDeSolicitud;
        private final Function<Float[], String> operacion;

        OPERACIONES(String nombre, int numerosNecesarios, String mensajeDeSolicitud,
                    Function<Float[], String> operacion)
        {
            this.nombre = nombre;
            this.numerosNecesarios = numerosNecesarios;
            this.mensajeDeSolicitud = mensajeDeSolicitud;
            this.operacion = operacion;
        }

        private String ejecutar(Float[] numeros) {
            return operacion.apply(numeros);
        }
    }

    private static final String[] ORDINALES = new String[]{"primer", "segundo", "tercer"};
    private static final String CABECERA =
            "\n***************************************\n" +
            "**** Calculadora de Calculator S.A ****\n" +
            "***************************************\n";
    private static final String MENU =
            "Introduzca una opción del menú:\n" +
                    "\t1. Sumar\n" +
                    "\t2. Restar\n" +
                    "\t3. Multiplicar\n" +
                    "\t4. Dividir\n" +
                    "\t5. Número mayor de 3 números\n" +
                    "\t6. Capicúa\n" +
                    "\t0. Salir\n" +
                    "Introduzca una opción: ";

    private static Scanner scanner;

    public static void main(String[] args) {
        //inicia el Scanner con el que se solicitara input a el usuario
        scanner = new Scanner(System.in);
        int opcion;

        //imprime la cabecera
        System.out.println(CABECERA);

        while (true) {
            //imprime el menu
            System.out.print(MENU);

            try {
                //solicita opción al usuario
                opcion = scanner.nextInt();

                if (opcion == 0)
                    //termina el programa
                    break;

                if (opcion - 1 >= OPERACIONES.values().length) {
                    //opción inexistente, vuelve a iterar
                    System.out.println("\nOpción errónea\n");
                    continue;
                }

                ejecutaOperacion(OPERACIONES.values()[opcion - 1]);
            }
            catch (InputMismatchException e) {
                System.out.println("\nEntrada Inválida\n");
                scanner.nextLine();
            }
        }

        System.out.println("\nEl programa ha finalizado");
    }

    private static void ejecutaOperacion(OPERACIONES operacion) {
        System.out.println("\n***** " + operacion.nombre + " *****");

        Float[] numeros = solicitarNumeros(
                operacion.numerosNecesarios,
                operacion.mensajeDeSolicitud);
        String resultado = operacion.ejecutar(numeros);

        imprimeResultado(resultado);
    }

    static boolean esCapicua(String numero) {
        for (int i = 0, j = numero.length() - 1; i < numero.length(); i++, j--)
            if (numero.charAt(i) != numero.charAt(j))
                return false;

        return true;
    }

    private static Float[] solicitarNumeros(int cantidad, String mensaje) {
        Float[] numeros = new Float[cantidad];
        String posicion;

        for (int i = 0; i < cantidad; i++) {
            posicion = ORDINALES[i];
            numeros[i] = solicitarNumero(String.format(mensaje, posicion));
        }

        return numeros;
    }

    static float solicitarNumero(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextFloat();
    }

    static void imprimeResultado(String resultado) {
        System.out.println(resultado);
        System.out.println("****************\n");
    }
}
