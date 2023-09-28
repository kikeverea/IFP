package ifp.kikeverea.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BufferedIOFichero implements IOFichero {

    public String leerContenido(String ruta) throws IOException {

        File fichero = new File(ruta);
        StringBuilder sb = new StringBuilder();

        // Crea un nuevo FileReader para el fichero
        try (FileReader reader = new FileReader(fichero)) {
            int c;
            while ((c = reader.read()) != -1)
                // anexa caracteres al StringBuilder hasta que el reader llega al final del stream
                sb.append((char) c);
        }

        return sb.toString();
    }

    @Override
    public void escribirEnFichero(String ruta, String contenido) throws IOException {
        File fichero = new File(ruta);

        // Crea un nuevo FileWriter para el fichero
        try (FileWriter writer = new FileWriter(fichero)) {
            int c;
            while ((c = reader.read()) != -1)
                // anexa caracteres al StringBuilder hasta que el reader llega al final del stream
                sb.append((char) c);
        }
    }
}
