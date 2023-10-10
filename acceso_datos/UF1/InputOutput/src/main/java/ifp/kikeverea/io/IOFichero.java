package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface IOFichero<T> {
    Collection<T> leerContenido(File fichero) throws IOException, DatosNoContienenPersonasException;
    Collection<T> leerContenido(File fichero, FiltroLectura<Persona> filtro) throws IOException, DatosNoContienenPersonasException;
    void escribirEnFichero(File fichero, Collection<T> objects) throws IOException;
    void escribirEnFichero(File fichero, Collection<T> objects, boolean anadir) throws IOException;
}
