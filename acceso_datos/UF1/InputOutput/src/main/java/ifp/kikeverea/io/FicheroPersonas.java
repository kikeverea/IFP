package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Clase que encapsula la creación y validación de ficheros y facilita la lectura y escritura de instancias de Persona
 * a través de la interfaz IOFichero.
 */
public class FicheroPersonas {

    private File fichero;
    private final List<Persona> buffer = new ArrayList<>(3);
    private final IOFichero<Persona> io;

    /**
     * Constructor
     * @param io Clase encargada de la entrada y salida de datos del fichero
     */
    public FicheroPersonas(IOFichero<Persona> io) {
        this.io = io;
    }

    public boolean establecerRuta(String ruta) {
        fichero = new File(ruta);

        if (!rutaDeTrabajoValida()) {
            System.out.println("La ruta del directorio de trabajo no existe");
            return false;
        }

        return true;
    }

    private boolean rutaDeTrabajoValida() {
        String rutaDirectorioDeTrabajo = fichero.getParent();
        return rutaDirectorioDeTrabajo == null || new File(rutaDirectorioDeTrabajo).exists();
    }

    public boolean existe() {
        return fichero.exists();
    }

    /**
     * Añade una persona al buffer de esta clase
     * @param persona Persona que se añade al buffer
     */
    public void anadirPersona(Persona persona) {
        buffer.add(persona);
    }

    /**
     * Escribe el contenido del buffer en la ruta establecida
     * @param anadir Si es true, añade el contenido al final del fichero, de lo contrario sobreescribe el conrenido del fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    public void escribirFichero(boolean anadir) throws IOException {
        io.escribirEnFichero(fichero, buffer, anadir);
    }

    /**
     * Lee el contenido de Personas del fichero en la ruta establecida
     * @return Una colección de Personas que representan el contenido del fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     * @throws DatosNoContienenPersonasException si el fichero no contiene personas
     */
    public Collection<Persona> leerFichero() throws IOException, DatosNoContienenPersonasException {
        return io.leerContenido(fichero);
    }

    /**
     * Lee el contenido de Personas con un nombre determinado del fichero en la ruta establecida
     * @param nombre El nombre de la(s) Persona(s) que se quiere obtener
     * @return Una colección de Personas con el nombre proporcionado
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     * @throws DatosNoContienenPersonasException si el fichero no contiene personas
     */
    public Collection<Persona> leerConNombre(String nombre) throws IOException, DatosNoContienenPersonasException {
        return io.leerContenido(fichero, new FiltroNombrePersona(nombre));
    }
}
