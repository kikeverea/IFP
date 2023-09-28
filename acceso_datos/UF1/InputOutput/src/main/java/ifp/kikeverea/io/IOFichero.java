package ifp.kikeverea.io;

import java.io.File;
import java.io.IOException;

public interface IOFichero {
    String leerContenido(File fichero) throws IOException;
    void escribirEnFichero(File fichero, String contenido) throws IOException;
    void anadirEnFichero(File fichero, String contenido) throws IOException;
}
