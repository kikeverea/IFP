package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FicheroPersonas {

    public enum Estado {
        NO_EXISTE,
        CREADO,
        CON_CONTENIDO
    }

    private File fichero;
    private Estado estado = Estado.NO_EXISTE;
    private final List<Persona> personas = new ArrayList<>();
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

        if (fichero.exists()) {
            estado = Estado.CON_CONTENIDO;
            return true;
        }

        return true;
    }

    private boolean rutaDeTrabajoValida() {
        String rutaDirectorioDeTrabajo = fichero.getParent();
        return rutaDirectorioDeTrabajo == null || new File(rutaDirectorioDeTrabajo).exists();
    }

    public Estado getEstado() {
        return estado;
    }

    public void anadirPersona(Persona persona) {
        personas.add(persona);
    }

    public void escribirFichero(boolean anadir) throws IOException {
        io.escribirEnFichero(fichero, personas, anadir);
    }

    public Collection<Persona> leerFichero() throws IOException {
        return io.leerContenido(fichero);
    }
}