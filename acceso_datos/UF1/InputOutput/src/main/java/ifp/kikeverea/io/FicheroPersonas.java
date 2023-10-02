package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public void anadirPersona(Persona persona) {
        buffer.add(persona);
    }

    public void escribirFichero(boolean anadir) throws IOException {
        io.escribirEnFichero(fichero, buffer, anadir);
    }

    public Collection<Persona> leerFichero() throws IOException {
        return io.leerContenido(fichero);
    }

    public Collection<Persona> leerConNombre(String nombre) throws IOException {
        return io.leerContenido(fichero, new FiltroLectura("Nombre", nombre));
    }
}
