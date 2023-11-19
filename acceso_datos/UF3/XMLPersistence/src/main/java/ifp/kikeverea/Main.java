package ifp.kikeverea;

import ifp.kikeverea.persona.Persona;
import ifp.kikeverea.xml.AdaptadorXML;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Persona persona =
                Persona.conNombre("Persona")
                        .appellidos("Maria", "de los", "Palotes")
                        .nif("23925905V")
                        .nacionalidad("Español")
                        .email("persona@gmail.com")
                        .telefono("555 555 555")
                        .direccion("Carrer de la Barca 19, 1-1")
                        .provincia("Girona")
                        .ciudad("Girona")
                        .codigoPostal("17004")
                        .extranjero(false)
                        .mayorDeEdad(true)
                        .construir();

        try {
            AdaptadorXML adaptadorXML = new AdaptadorXML("personas");
            adaptadorXML.crearArchivoXML("personas.xml", List.of(persona));
        }
        catch (ParserConfigurationException e) {
            System.err.println("No se ha podido crear el gestor de archivos XML. Causa: " + e.getMessage());
            e.printStackTrace();
        }
        catch (TransformerException e) {
            System.err.println("No se ha podido escrbir el contenido en el fichero. Causa: " + e.getMessage());
        }
    }
}
