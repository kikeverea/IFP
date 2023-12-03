package ifp.kikeverea.ejercicio3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Cliente {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public String comenzarConexion(String ip, int puerto) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, puerto), 3000);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            String respuesta = input.readLine();

            return respuesta != null ? respuesta : "No se ha recibido respuesta del servidor";
        }
        catch (SocketTimeoutException e) {
            return "No se ha podido establecer una conexión con el servidor. Causa: " + e.getMessage();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return "No se ha podido establecer una conexión con el servidor. Causa: " + e.getMessage();
        }
    }

    public String enviarMensaje(String mensaje) {
        try {
            output.print(mensaje);
            return input.readLine();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return "No se ha podido enviar el mensaje. Causa: " + e.getMessage();
        }
    }

    public void cerrarConexion() throws IOException {
        output.close();
        input.close();
        socket.close();
    }
}
