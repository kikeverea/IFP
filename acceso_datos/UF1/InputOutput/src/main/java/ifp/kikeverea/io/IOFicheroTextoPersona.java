package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IOFicheroTextoPersona implements IOFichero<Persona> {

    /**
     * Lee el contenido de un fichero de texto. Asume que el fichero existe
     * @param fichero Fichero del cual se lee el contenido
     * @return Colección de Personas contenidas en el fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    public Collection<Persona> leerContenido(File fichero) throws IOException, DatosNoContienenPersonasException {
        return leerContenido(fichero, null);
    }

    /**
     * Lee el contenido de un fichero de texto. Asume que el fichero existe
     * @param fichero Fichero del cual se lee el contenido
     * @param filtro Filtro que se aplica a la lectura del fichero
     * @return Collección de personas contenidas en el fichero, que han pasado el filtro
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    public Collection<Persona> leerContenido(File fichero, FiltroLectura<Persona> filtro) throws IOException,
            DatosNoContienenPersonasException
    {
        List<Persona> personas = new ArrayList<>();

        // Crea un nuevo flujo de entrada (BufferedReader)
        try (FileInputStream fis = new FileInputStream(fichero);
            InputStreamReader is = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(is))
        {
            String linea;
            while ((linea = reader.readLine()) != null) { // itera hasta llegar al final del archivo (linea == null)

                Persona persona = Persona.fromString(linea);

                if (filtro == null || filtro.pasaFiltro(persona))
                    personas.add(persona);
            }
        }

        return personas;
    }

    /**
     * Escribe contenido a un fichero. Si el fichero ya tenía contenido, este será eliminado en el proceso de escritura
     * @param fichero El fichero en el que escribirá el contenido
     * @param objetos Las Personas a escribir en el fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    @Override
    public void escribirEnFichero(File fichero, Collection<Persona> personas) throws IOException {
        escribirEnFichero(fichero, personas, false);
    }

    /**
     * Escribe contenido a un fichero
     * @param fichero El fichero en el que escribirá el contenido
     * @param personas Las Personas a escribir en el fichero
     * @param anadir Si es true, añade el contenido al final del archivo. Si es false los datos existentes en el fichero serán eliminados durante la escritura
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    @Override
    public void escribirEnFichero(File fichero, Collection<Persona> personas, boolean anadir) throws IOException {
        // Crea un nuevo escritor (FileWriter) para el fichero
        try (FileOutputStream fos = new FileOutputStream(fichero, anadir);
            OutputStreamWriter sw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(sw))
        {
            for (Persona persona : personas) {
                writer.write(persona.toString());
                writer.newLine();
            }
        }
    }
}