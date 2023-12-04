package ifp.kikeverea.ejercicio5;

public class Main {

    public static void main(String[] args) {
        Cliente clienteFtp = new Cliente();

        boolean conectado = clienteFtp.iniciarConexion("demo.wftpserver.com", 21);

        if (!conectado) {
            System.out.println("No se ha podido establecer una conexión. Finalizando programa");
            System.exit(1);
        }

        boolean login = clienteFtp.login("demo", "demo");

        if (!login) {
            System.out.println("No se ha podido iniciar sesión. Finalizando programa");
            System.exit(1);
        }

        System.out.println("Archivos en raíz:");
        listarArchivos(clienteFtp, "/");

        System.out.println("Archivos en /download:");
        listarArchivos(clienteFtp, "/download");

        System.out.println("Descargando archivo 'version.txt'");
        descargarArchivo(clienteFtp, "version.txt", "/download/version.txt");

        System.out.println("Subiendo archivo 'version.txt' a '/upload/version2.txt'");
        subirArchivo(clienteFtp, "version.txt", "/upload/version2.txt");
    }

    private static void listarArchivos(Cliente clienteFtp, String ruta) {
        String respuesta = clienteFtp.listarArchivos(ruta);
        System.out.println(respuesta);
        System.out.println();
    }

    private static void subirArchivo(Cliente clienteFtp, String local, String remoto) {
        String respuesta = clienteFtp.subirArchivo(local, remoto);
        System.out.println(respuesta);
        System.out.println();
    }

    private static void descargarArchivo(Cliente clienteFtp, String local, String remoto) {
        String respuesta = clienteFtp.descargarArchivo(local, remoto);
        System.out.println(respuesta);
        System.out.println();
    }
}
