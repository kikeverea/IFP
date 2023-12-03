package ifp.kikeverea.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

    private static final int INDENT = 8;
    private static final int INDENT_FIN_ELEMENTO = 7;

    private void imprimirIndentado(String contenido, int indent) {
        System.out.println(" ".repeat(indent) + contenido);
    }

    /**
     * Comienzo del documento
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("Comienzo del documento XML");
    }

    /** Comienzo de un elemento
     * @param name el nombre del elemento
     */
    @Override
    public void startElement(String _s, String _l, String name, Attributes _a) {
        imprimirIndentado("Principio Elemento: " + name, INDENT);
    }

    /**
     * Contenido en caracteres de un elemento
     * @param ch Los caracteres
     * @param start La posición inicial del array de caracteres
     * @param length El número de caracteres del array que pertenecen al elemento
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        String caracteres = new String(ch, start, length).strip();
        imprimirIndentado("Caracteres: " + caracteres, INDENT);
    }

    /**
     * Final de un elemento
     * @param name El nombre del elemento
     */
    @Override
    public void endElement(String _u, String _l, String name) {
        imprimirIndentado("Fin Elemento: " + name, INDENT_FIN_ELEMENTO);
    }

    /**
     * Final del documento
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("Final del Documento XML");
    }
}
