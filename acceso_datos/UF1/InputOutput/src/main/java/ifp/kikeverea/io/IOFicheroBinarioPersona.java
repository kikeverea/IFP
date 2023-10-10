package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;

import java.io.*;
import java.util.Collection;
import java.util.stream.Collectors;

public class IOFicheroBinarioPersona implements IOFichero<Persona> {

    /**
     * Lee objetos tipo Persona de un fichero binario. Asume que el fichero existe
     * @param fichero Fichero del cual se lee los objetos de Persona
     * @return Colección de Personas contenidas en el fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     * @throws DatosNoContienenPersonasException Si el fichero contiene instancias de Persona
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Persona> leerContenido(File fichero) throws IOException, DatosNoContienenPersonasException {
        try (FileInputStream fis = new FileInputStream(fichero);
             BufferedInputStream bs = new BufferedInputStream(fis);
             ObjectInputStream reader = new ObjectInputStream(bs))
        {
            return (Collection<Persona>) reader.readObject();   // lee y reconstruye un objeto de Persona del fichero
        }
        catch (ClassNotFoundException e) {
            throw new DatosNoContienenPersonasException("El fichero no contiene objetos de Persona");
        }
    }

    /**
     * Lee objetos tipo Persona de un fichero binario, que pasen el filtro dado. Asume que el fichero existe
     * @param fichero Fichero del cual se lee el contenido
     * @param filtro El filtro a aplicar a la lectura
     * @return Colección de Personas contenidas en el fichero, que pasan el filtro
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     * @throws DatosNoContienenPersonasException Si el fichero no contiene instancias de Persona
     */
    @Override
    public Collection<Persona> leerContenido(File fichero, FiltroLectura<Persona> filtro) throws IOException,
            DatosNoContienenPersonasException
    {
        return leerContenido(fichero)
                .stream()
                .filter(filtro::pasaFiltro)
                .collect(Collectors.toList());
    }

    /**
     * Escribe instancias de Persona en un fichero binario
     * @param fichero Fichero del cual se lee el contenido
     * @param personas Las instancias de Persona a escribir en el fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    @Override
    public void escribirEnFichero(File fichero, Collection<Persona> personas) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fichero);
             BufferedOutputStream bs = new BufferedOutputStream(fos);
             ObjectOutputStream writer = new ObjectOutputStream(bs)) // crea un flujo de salida de objetos al fichero
        {
            writer.writeObject(personas);   // escribe un objeto de Persona serializado en el fichero
        }
    }

    /**
     * Escribe instancias de Persona en un fichero binario
     * @param fichero Fichero del cual se lee el contenido
     * @param personas Las instancias de Persona a escribir en el fichero
     * @param anadir Este parámetro será ignorado
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    @Override
    public void escribirEnFichero(File fichero, Collection<Persona> personas, boolean anadir) throws IOException {
        // esta clase no da soporte a 'append'. Convoca la versión de este método sin append
        escribirEnFichero(fichero, personas);
    }
}
