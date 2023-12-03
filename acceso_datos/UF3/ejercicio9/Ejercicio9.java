package ifp.kikeverea.main;

import ifp.kikeverea.xml.PersonasSAXHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class Ejercicio9 {

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            PersonasSAXHandler handler = new PersonasSAXHandler();

            parser.parse("personas.xml", handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error durante la lectura SAX. Causa: " + e.getMessage());
        }
    }
}
