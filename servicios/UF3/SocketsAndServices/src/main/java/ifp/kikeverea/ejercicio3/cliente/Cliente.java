package ifp.kikeverea.ejercicio3.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Cliente {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public String comenzarConexion(String ip, int puerto) throws SocketTimeoutException {
        try {
            socket = new Socket(ip, puerto);

            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            return "Conexión establecida";
        }
        catch (IOException e) {
            if (e instanceof SocketTimeoutException)
                throw (SocketTimeoutException) e;

            return "No se ha podido crear el cliente. Causa: " + e.getMessage();
        }
    }

    public boolean estaConectado() {
        return socket.isConnected();
    }

    public String enviarMensaje(String mensaje) {
        try {
            output.println(mensaje);
            return input.readLine();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return "No se ha podido enviar el mensaje. Causa: " + e.getMessage();
        }
    }

    public void cerrarConexion() {
        try {
            output.close();
            input.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
