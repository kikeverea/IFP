package ifp.kikeverea;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del caballo 1: ");
        String nombre1 = scanner.nextLine();

        System.out.print("Nombre del caballo 2: ");
        String nombre2 = scanner.nextLine();

        System.out.print("Nombre del caballo 3: ");
        String nombre3 = scanner.nextLine();

        Caballo caballo1 = new Caballo(nombre1);
        Caballo caballo2 = new Caballo(nombre2);
        Caballo caballo3 = new Caballo(nombre3);

        Thread thread1 = new Thread(caballo1);
        Thread thread2 = new Thread(caballo2);
        Thread thread3 = new Thread(caballo3);

        // ejecuta los hilos
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            // suspende este hilo hasta que los hilos correspondientes a cada caballo
            // terminan su ejecución y mueren
            thread1.join();
            thread2.join();
            thread3.join();

            String ganador =
                caballo1.masRapidoQue(caballo2) && caballo1.masRapidoQue(caballo3)
                    ? caballo1.getNombre()
                    : caballo2.masRapidoQue(caballo3)
                        ? caballo2.getNombre()
                        : caballo3.getNombre();

            System.out.println("El ganador es: " + ganador);
        }
        catch (InterruptedException e) {
            System.err.println("La carrera ha sido suspendida." +
                    "No se ha podido determinar ningún ganador");
        }
    }
}