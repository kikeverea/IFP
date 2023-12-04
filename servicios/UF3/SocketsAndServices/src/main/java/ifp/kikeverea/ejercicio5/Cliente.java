package ifp.kikeverea.ejercicio5;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Cliente {

    private final FTPClient cliente = new FTPClient();

    public boolean iniciarConexion(String servidor, int puerto) {
        try {
            cliente.connect(servidor, puerto);
            cliente.enterLocalPassiveMode();
            return FTPReply.isPositiveCompletion(cliente.getReplyCode());
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String usuario, String contrasena) {
        try {
            cliente.login(usuario, contrasena);
            return FTPReply.isPositiveCompletion(cliente.getReplyCode());
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String listarArchivos(String ruta) {
        try {
            String archivos = Arrays
                    .stream(cliente.listFiles(ruta))
                    .map(FTPFile::getName)
                    .collect(Collectors.joining(", "));

            return FTPReply.isPositiveCompletion(cliente.getReplyCode())
                    ? archivos :
                    error();
        }
        catch (IOException e) {
            return error(e);
        }
    }

    public String subirArchivo(String local, String remoto) {
        try (InputStream flujoDesdeLocal = new FileInputStream(local)) {
            cliente.appendFile(remoto, flujoDesdeLocal);

            return FTPReply.isPositiveCompletion(cliente.getReplyCode())
                    ? "Archivo '" + local + "' subido a '" + remoto + "'"
                    : error();
        }
        catch (IOException e) {
            return error(e);
        }
    }

    public String descargarArchivo(String local, String remoto) {
        try (OutputStream flujoHaciaLocal = new FileOutputStream(local)) {
            cliente.retrieveFile(remoto, flujoHaciaLocal);
            flujoHaciaLocal.flush();

            return FTPReply.isPositiveCompletion(cliente.getReplyCode())
                    ? "Archivo '" + remoto + "' descargado a '" + local + "'"
                    : error();
        }
        catch (IOException e) {
            return error(e);
        }
    }

    private String error() {
        return "Error " + cliente.getReplyCode() + ": " + cliente.getReplyString();
    }

    private String error(IOException e) {
        return "Error de Entrada/Salida: " + e.getMessage();
    }
}
