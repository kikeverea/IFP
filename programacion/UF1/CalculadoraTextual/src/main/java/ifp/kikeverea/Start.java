package ifp.kikeverea;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Start {

    private static final String MENSAJE_SOLICITAR_NUMERO = "Introduzca el %s número flotante %s: ";
    private static final String[] ORDINALES = new String[]{"primer", "segundo", "tercer"};
    private static final String CABECERA =
            "\n***************************************\n" +
            "**** Calculadora de Calculator S.A ****\n" +
            "**************************************\n";
    private static final String MENU =
            "Introduzca una opción del menú:\n" +
            "\t1. Sumar\n" +
            "\t2. Restar\n" +
            "\t3. Multiplicar\n" +
            "\t4. Dividir\n" +
            "\t5. Número mayor de 3 números\n" +
            "\t6. Capicúa\n" +
            "\t0. Salir\n";

    private static Scanner scanner;

    public static void main(String[] args) {
        // inicia el Scanner con el que se solicitara input a el usuario
        scanner = new Scanner(System.in);
        int opcion;

        // imprime la cabecera
        System.out.println(CABECERA);

        // itera hasta que el usuario introduzca la opción salir (0)
        while (true) {

            // imprime el menu
            System.out.print(MENU);

            // solicita opción al usuario
            opcion = (int) solicitarNumero("Introduzca una opción: ");

            if (opcion == 0) {
                // termina el programa
                break;
            }

            // línea en blanco
            System.out.println();

            ejecutarOpcion(opcion);

            // línea en blanco
            System.out.println();
       }

        System.out.println("El programa ha finalizado");
    }

    static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 :
                ejecutarSuma();
                break;
            case 2 :
                ejecutarResta();
                break;
            case 3 :
                ejecutarMultiplicacion();
                break;
            case 4 :
                ejecutarDivision();
                break;
            case 5 :
                ejecutarMaximoDeTres();
                break;
            case 6 :
                ejecutarCapicua();
                break;
            default:
                System.out.println("Opción errónea");
                break;
        }
    }

    static void ejecutarSuma() {
        float[] numeros = solicitarNumeros(2, "Suma", "a sumar");
        float resultado = numeros[0] + numeros[1];

        imprimeResultado("La suma de %.2f y %.2f es %.2f", numeros[0], numeros[1], resultado);
    }

    static void ejecutarResta() {
        float[] numeros = solicitarNumeros(2, "Resta", "a restar");
        float resultado = numeros[0] - numeros[1];

        imprimeResultado("La resta de %.2f y %.2f es %.2f", numeros[0], numeros[1], resultado);
    }

    static void ejecutarMultiplicacion() {
        float[] numeros = solicitarNumeros(2, "Multiplicación", "a multiplicar");
        float resultado = numeros[0] * numeros[1];

        imprimeResultado("La multiplicación de %.2f y %.2f es %.2f", numeros[0], numeros[1], resultado);
    }

    static void ejecutarDivision() {
        float[] numeros = solicitarNumeros(2, "División", "a dividir");
        float resultado;

        // asegura que no se intenta dividir entre cero
        if (numeros[1] == 0) {
            System.out.println("Error division por 0");
            return;
        }

        resultado = numeros[0] / numeros[1];
        imprimeResultado("La división de %.2f y %.2f es %.2f", numeros[0], numeros[1], resultado);
    }

    static void ejecutarMaximoDeTres() {
        float[] numeros = solicitarNumeros(3, "Máximo de Tres", "de los tres");
        float resultado = Math.max(Math.max(numeros[0], numeros[1]), numeros[2]);

        imprimeResultado("EL número %.2f es el mayor de los 3 números", resultado);
    }

    private static void ejecutarCapicua() {
        System.out.println("***** Capicúa *****");
        System.out.println("Introduzca un número para saber si es capicúa: ");

        // solicita un int al usuario para evitar problemas con nextLine(), de haberse
        // escogido cualquiera de las otras opciones que utilizan nextFloat(), antes que esta.
        int numero = scanner.nextInt();
        boolean esCapicua = esCapicua(String.valueOf(numero));

        imprimeResultado(String.format("%s es capicúa", esCapicua ? "SI" : "NO"));
    }

    static boolean esCapicua(String numero) {
        for (int i = 0, j = numero.length() - 1; i < numero.length(); i++, j--)
            if (numero.charAt(i) != numero.charAt(j))
                return false;

        return true;
    }

    private static float[] solicitarNumeros(int cantidad, String operacion, String accion) {
        float[] numeros = new float[cantidad];
        String posicion;

        System.out.println("***** " + operacion + " *****");

        for (int i = 0; i < cantidad; i++) {
            posicion = ORDINALES[i];
            numeros[i] = solicitarNumero(String.format(MENSAJE_SOLICITAR_NUMERO, posicion, accion));
        }

        return numeros;
    }

    private static float solicitarNumero(String mensaje) {
        // itera hasta que el usuario introduce un número válido
        while (true) {
            try {
                System.out.print(mensaje);
                return scanner.nextFloat();
            }
            catch (InputMismatchException e) {
                // consume el resto de la línea, para evitar un bucle infinito
                scanner.nextLine();
                System.out.println("Entrada no válida, por favor introduzca un número");
            }
        }
    }

    static void imprimeResultado(String mensaje, Number... args) {
        System.out.printf(mensaje + "\n", (Object[]) args);
        System.out.println("****************");
    }
}






