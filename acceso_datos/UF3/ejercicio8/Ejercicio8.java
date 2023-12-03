package ifp.kikeverea.main;

import ifp.kikeverea.xml.SAXHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class Ejercicio8 {

    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SAXHandler handler = new SAXHandler();

            parser.parse("galaxias.xml", handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error durante la lectura SAX. Causa: " + e.getMessage());
        }
    }
}
