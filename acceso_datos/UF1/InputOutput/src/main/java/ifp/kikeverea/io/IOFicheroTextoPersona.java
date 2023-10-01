package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class IOFicheroTextoPersona implements IOFichero<Persona> {

    /**
     * Lee el contenido de un fichero de texto. Asume que el fichero existe
     * @param fichero Fichero del cual se lee el contenido
     * @return Colección de Personas contenidas en el fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    public Collection<Persona> leerContenido(File fichero) throws IOException {

        List<Persona> personas = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        // Crea un nuevo lector (FileReader) para el fichero
        try (FileReader reader = new FileReader(fichero)) {
            int i;
            while ((i = reader.read()) != -1) {
                char c = (char) i;
                // fin de línea, añade la Persona a la lista y vacía el StringBuilder
                if (c == '\n') {
                    personas.add(Persona.fromString(sb.toString()));
                    sb.delete(0, sb.length());
                }
                else sb.append(c);
            }

            // añade la última persona en el StringBuilder, si éste no está vacío
            if(sb.length() > 0)
                personas.add(Persona.fromString(sb.toString()));
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
    public void escribirEnFichero(File fichero, Collection<Persona> objetos) throws IOException {
        escribirEnFichero(fichero, objetos, false);
    }

    /**
     * Escribe contenido a un fichero
     * @param fichero El fichero en el que escribirá el contenido
     * @param objetos Las Personas a escribir en el fichero
     * @param anadir Si es true, añade el contenido al final del archivo. Si es false los datos existentes en el fichero serán eliminados durante la escritura
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    @Override
    public void escribirEnFichero(File fichero, Collection<Persona> objetos, boolean anadir) throws IOException {
        // convierte la collección de personas a String
        String personasString = personasEnString(objetos);

        // Crea un nuevo escritor (FileWriter) para el fichero
        try (FileWriter writer = new FileWriter(fichero, anadir)) {

            // Si opción añadir y el fichero no está vacío, añadir un salto de línea antes de escribir
            if (anadir && fichero.length() > 0)
                writer.append('\n');

            for (char c : personasString.toCharArray()) {
                // Añade cada caracter al fichero
                writer.append(c);
            }
        }
    }

    private String personasEnString(Collection<Persona> personas) {
        return personas
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}