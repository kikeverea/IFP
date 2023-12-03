package ifp.kikeverea.ejercicio3.cliente;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Main {

    private static final String FINALIZAR_PROGRAMA = "SALIR";

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        boolean conectado = conectarConElServidor(cliente);

        if (!conectado) {
            System.err.println("Error crítico. Finalizando el programa");
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Introducir operación, o 'salir' para finalizar el programa");
            String operacion = scanner.nextLine();

            String respuesta = cliente.enviarMensaje(operacion);

            if (operacion.equalsIgnoreCase(FINALIZAR_PROGRAMA)) {
                System.out.println("Programa finalizado");
                System.out.println(respuesta);
                cliente.cerrarConexion();
                break;
            }

            System.out.println(respuesta);
        }
    }

    private static boolean conectarConElServidor(Cliente cliente) {
        try {
            System.out.println("Conectando con el servidor... ");
            String respuesta = cliente.comenzarConexion("localhost", 3000);
            System.out.println(respuesta);
            return cliente.estaConectado();
        }
        catch (SocketTimeoutException e) {
            System.err.println("No se ha podido establecer una conexión con el servidor");
            System.err.println("Causa: el tiempo de espera ha terminado antes de poder establecer una conexión");
            return false;
        }
    }
}
