package ifp.kikeverea;

import java.util.Scanner;

public class Start {

    private static final String MENSAJE_SOLICITAR_NUMERO = "Introduzca el %s número flotante a %s";
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        int opcion;
        while (true) {
            mostrarMenu();
            opcion = scanner.nextInt();

            if (opcion == 0) {
                break;
            }

            ejecutarOpcion(opcion);
       }
    }

    private static void mostrarMenu() {
        //TODO
    }

    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 :
                ejecutarSuma();
                break;
//            case 2 :
//                ejecutarResta();
//                break;
//            case 3 :
//                ejecutarMultiplicacion();
//                break;
//            case 4 :
//                ejecutarDivision();
//                break;
//            case 5 :
//                ejecutarMaximoDeTres();
//                break;
//            case 6 :
//                ejecutarCapicua();
//                break;
        }
    }

    private static void ejecutarSuma() {
        float n1, n2, resultado;

        //primer numero
        System.out.printf(MENSAJE_SOLICITAR_NUMERO+"\n", "primer", "sumar");
        n1 = scanner.nextFloat();

        //segundo numero
        System.out.printf(MENSAJE_SOLICITAR_NUMERO+"\n", "segundo", "sumar");
        n2 = scanner.nextFloat();

        resultado = n1 + n2;
        System.out.printf("La suma de %f y %f es %f", n1, n2, resultado);
    }
}






