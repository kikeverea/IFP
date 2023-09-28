package ifp.kikeverea.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOFicheroImpl implements IOFichero {

    /**
     * Lee el contenido de un fichero de texto. Asume que el fichero existe
     * @param fichero Fichero del cual se lee el contenido
     * @return String con el contenido del fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    public String leerContenido(File fichero) throws IOException {

        StringBuilder sb = new StringBuilder();

        // Crea un nuevo lector (FileReader) para el fichero
        try (FileReader reader = new FileReader(fichero)) {
            int c;
            while ((c = reader.read()) != -1)
                // anexa caracteres al StringBuilder hasta que el reader llega al final del stream
                sb.append((char) c);
        }

        return sb.toString();
    }

    /**
     * Escribe contenido a un fichero. Si el fichero ya tenía contenido, este será eliminado en el proceso de escritura
     * @param fichero El fichero en el que escribirá el contenido
     * @param contenido El contenido a escribir en el fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    @Override
    public void escribirEnFichero(File fichero, String contenido) throws IOException {
        escibirContenido(fichero, contenido, false);
    }

    /**
     * Añade contenido al final de un fichero. El contenido del fichero no será modificado en el proceso de escritura
     * @param fichero El fichero al que se le añadirá el contenido
     * @param contenido El contenido a añadir al fichero
     * @throws IOException Si el fichero no existe, o hay excepciones de tipo input/output
     */
    @Override
    public void anadirEnFichero(File fichero, String contenido) throws IOException {
        escibirContenido(fichero, contenido, true);
    }

    private void escibirContenido(File fichero, String contenido, boolean append) throws IOException {
        // Crea un nuevo escritor (FileWriter) para el fichero
        try (FileWriter writer = new FileWriter(fichero, append)) {
            for (char c : contenido.toCharArray())
                // Añade cada caracter al final del fichero
                writer.append(c);
        }
    }
}