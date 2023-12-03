package ifp.kikeverea.ejercicio3;

import java.io.IOException;

public class MainServer {

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor();
            servidor.escucharEnPuerto(3000);

            System.out.println("Servidor desconectado");
        }
        catch (IOException e) {
            System.err.println("No se ha podido iniciar el servidor");
            e.printStackTrace();
        }
    }
}
