package ifp.kikeverea.ejercicio3.servidor;

import ifp.kikeverea.ejercicio3.Calculadora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static final String CERRAR_CONEXION = "SALIR";

    private ServerSocket socket;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private final Calculadora calculadora = new Calculadora();

    public void escucharEnPuerto(int puerto) throws IOException {
        socket = new ServerSocket(puerto);
        System.out.println("Servidor escuchando en puerto: " + puerto);

        clientSocket = socket.accept(); // bloquea el hilo hasta recibir una solicitud

        output = new PrintWriter(clientSocket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        gestionarSolicitudes();
    }

    private void gestionarSolicitudes() {
        while (true) {
            try {
                String solicitud = input.readLine();    // bloquea el hilo hasta recibir una nueva línea

                if (solicitud == null)
                    continue;

                if (solicitud.equalsIgnoreCase(CERRAR_CONEXION))
                    break;

                String respuesta = calculadora.realizarOperacion(solicitud);

                output.println(solicitud + " = " + respuesta);
            }
            catch (IOException e) {
                output.print("Error: no se ha podido generar una respuesta");
                e.printStackTrace();
            }
        }

        cerrarConexion();
    }

    private void cerrarConexion() {
        try {
            output.print("Conexión finalizada");
            output.close();
            input.close();
            clientSocket.close();
            socket.close();
        }
        catch (IOException e) {
            System.err.println("La conexión se ha podido cerrar correctamente");
            e.printStackTrace();
        }
    }
}
