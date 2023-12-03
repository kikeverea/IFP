package ifp.kikeverea.ejercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static final String CERRAR_CONEXION = "SALIR";

    private final ServerSocket socket;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private final Calculadora calculadora = new Calculadora();

    public Servidor() throws IOException {
        socket = new ServerSocket();
    }

    public void escucharEnPuerto(int puerto) throws IOException {
        socket.bind(new InetSocketAddress("localhost", puerto));

        clientSocket = socket.accept(); // bloquea el hilo hasta recibir una solicitud

        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream());

        output.print("Conexión establecida");
        gestionarSolicitudes();
    }

    private void gestionarSolicitudes() {
        String solicitud;
        do {
            try {
                solicitud = input.readLine();    // bloquea el hilo hasta recibir una nueva línea

                String respuesta = calculadora.realizarOperacion(solicitud);
                output.print(respuesta);
            }
            catch (IOException e) {
                output.print("Error: no se ha podido generar una respuesta");
                e.printStackTrace();
                break;
            }
        }
        while (!solicitud.equalsIgnoreCase(CERRAR_CONEXION));

        cerrarConexion();
    }

    private void cerrarConexion() {
        try {
            output.print("Conexión finalizada");
            output.close();
            input.close();
            socket.close();
        }
        catch (IOException e) {
            System.err.println("La conexión se ha podido cerrar correctamente");
            e.printStackTrace();
        }
    }
}
