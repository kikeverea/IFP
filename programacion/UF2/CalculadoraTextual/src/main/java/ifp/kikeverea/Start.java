package ifp.kikeverea;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Start {

    private static final String MENSAJE_SOLICITAR_NUMERO = "Introduzca el %s número flotante %s: ";
    private static final String[] ORDINALES = new String[]{"primer", "segundo", "tercer"};
    private static final String CABECERA =
            "\n***************************************\n" +
            "**** Calculadora de Calculator S.A ****\n" +
            "***************************************\n";
    private static final String MENU =
            "Introduzca una opción del menú:\n" +
            "\t1. Función Sumar\n" +
            "\t2. Función Restar\n" +
            "\t3. Función Multiplicar\n" +
            "\t4. Función Dividir\n" +
            "\t5. Función Número mayor de 3 números\n" +
            "\t6. Función Factorial\n" +
            "\t0. Salir\n";

    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int opcion;
        String resultado;

        // imprime la cabecera
        System.out.println(CABECERA);

        // itera hasta que el usuario introduzca la opción salir (0)
        while (true) {

            // imprime el menu
            System.out.print(MENU);

            // solicita opción al usuario
            opcion = solicitarEntero("Introduzca una opción: ");

            // si es 0, termina el programa
            if (opcion == 0)
                break;

            // ejecuta la opción
            resultado = ejecutarOpcion(opcion);

            // imprime el resultado
            System.out.println("\n*****************************************");
            System.out.println(resultado);
            System.out.println("*****************************************\n");
        }

        System.out.println("\nEl programa ha finalizado");
    }

    private static String ejecutarOpcion(int opcion) {
        float[] numeros;
        float resultado;

        switch (opcion) {
            case 1 :
                numeros = solicitarNumeros(2, "a sumar");
                resultado = suma(numeros[0], numeros[1]);
                return String.format("La suma de %.2f y %.2f es %.2f", numeros[0], numeros[1], resultado);
            case 2 :
                numeros = solicitarNumeros(2, "a restar");
                resultado = resta(numeros[0], numeros[1]);
                return String.format("La resta de %.2f y %.2f es %.2f", numeros[0], numeros[1], resultado);
            case 3 :
                numeros = solicitarNumeros(2, "a multiplicar");
                resultado = multiplicacion(numeros[0], numeros[1]);
                return String.format("La multiplicación de %.2f y %.2f es %.2f", numeros[0], numeros[1], resultado);
            case 4 :
                numeros = solicitarNumeros(2, "a dividir");
                resultado = division(numeros[0], numeros[1]);
                return  resultado != -1 ?
                        String.format("La división de %.2f y %.2f es %.2f", numeros[0], numeros[1], resultado) :
                        "Error division por 0";
            case 5 :
                numeros = solicitarNumeros(3, "de los 3");
                resultado = mayor(numeros[0], numeros[1], numeros[2]);
                return String.format("El número %.2f es el mayor", resultado);
            case 6 :
                int numero = solicitarEntero("Introduzca un número para calcular el factorial: ");
                resultado = factorial(numero);
                return  resultado != -1 ?
                        String.format("El factorial de %d es %d", numero, (int) resultado) :
                        "Error valor inferior a 0, introduzca un valor superior a 0";
            default:
                return "Opción errónea";
        }
        
    }

    public static float suma(float num1, float num2) {
        return num1 + num2;
    }

    public static float resta(float num1, float num2) {
        return num1 - num2;
    }

    public static float multiplicacion(float num1, float num2) {
        return num1 * num2;
    }

    public static float division(float num1, float num2) {
        // asegura que no se intenta dividir entre cero
        if (num2 == 0)
            return -1.0f;

        return num1 / num2;
    }

    public static float mayor(float num1, float num2, float num3) {
        return Math.max(Math.max(num1, num2), num3);
    }

    public static int factorial(int num) {
        int factorial = 1;

        // asegura que el número es mayor que 0
        if (num < 1)
            return -1;

        for (int i = 2; i <= num; i++)
            factorial *= i;

        return factorial;
    }

    private static float[] solicitarNumeros(int cantidad, String sufijo) {
        float[] numeros = new float[cantidad];
        String posicion;

        for (int i = 0; i < cantidad; i++) {
            posicion = ORDINALES[i];
            numeros[i] = solicitarNumero(String.format(MENSAJE_SOLICITAR_NUMERO, posicion, sufijo));
        }

        return numeros;
    }

    private static float solicitarNumero(String mensaje) {
        // itera hasta que el usuario introduzca un número válido
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

    private static int solicitarEntero(String mensaje) {
        return (int) solicitarNumero(mensaje);
    }
}






