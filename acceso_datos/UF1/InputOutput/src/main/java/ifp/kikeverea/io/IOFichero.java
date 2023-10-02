package ifp.kikeverea.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IOFichero<T> {
    Collection<T> leerContenido(File fichero) throws IOException;
    Collection<T> leerContenido(File fichero, FiltroLectura filtro) throws IOException;
    void escribirEnFichero(File fichero, Collection<T> objects) throws IOException;
    void escribirEnFichero(File fichero, Collection<T> objects, boolean anadir) throws IOException;
}
