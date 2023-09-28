package ifp.kikeverea.io;

import java.io.File;
import java.io.IOException;

public interface IOFichero {
    String leerContenido(File ruta) throws IOException;
    void escribirEnFichero(File ruta, String contenido) throws IOException;
}
