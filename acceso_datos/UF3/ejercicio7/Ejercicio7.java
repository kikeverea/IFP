package ifp.kikeverea.main;

import ifp.kikeverea.xml.LectorXML_DOM;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Ejercicio7 {

    public static void main(String[] args) {
        try {
            LectorXML_DOM lectorXML = new LectorXML_DOM("personas", "personas.xml");
            lectorXML.leerContenido();
        }
        catch (ParserConfigurationException e) {
            System.err.println("No se ha podido crear el gestor de archivos XML. Causa: " + e.getMessage());
            e.printStackTrace();
        }
        catch (SAXException | IOException e) {
            System.err.println("No se ha podido escrbir el contenido en el fichero. Causa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
