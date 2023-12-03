package ifp.kikeverea.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PersonasSAXHandler extends DefaultHandler {

    private static final int TABULACION = 2;

    private int profundidad = -1;
    private String linea;

    private void anadirALinea(String contenido) {
        linea += contenido;
    }

    private void consumirLinea() {
        System.out.println(linea);
        linea = "";
    }

    /** Comienzo de un elemento
     * @param name el nombre del elemento
     * @param attributes los atributos que describen este elemento
     */
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) {
        profundidad++;

        if (profundidad > 0)
            consumirLinea();

        String elemento = name+": ";

        if (attributes.getLength() > 0)
            elemento += formatearAtributos(attributes);

        construirLinea(elemento);
    }

    private void construirLinea(String contenido) {
        int indentado = TABULACION * profundidad;
        linea = " ".repeat(indentado) + contenido;
    }

    private String formatearAtributos(Attributes atributos) {

        StringBuilder resultado = new StringBuilder("(");
        for (int i = 0; i < atributos.getLength(); i++) {

            String nombreAtributo = atributos.getQName(i);
            String valorAtributo = atributos.getValue(i);

            resultado.append(nombreAtributo);
            resultado.append(": ");
            resultado.append(valorAtributo);

            if (i < atributos.getLength() -1) // añade una coma para separar los atributos, excepto el último
                resultado.append(", ");

        }
        resultado.append(")");

        return resultado.toString();
    }

    /**
     * Contenido en caracteres de un elemento
     * @param ch Los caracteres
     * @param start La posición inicial del array de caracteres
     * @param length El número de caracteres del array que pertenecen al elemento
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        String caracteres = new String(ch, start, length);

        if (!caracteres.isBlank())
            anadirALinea(caracteres);
    }

    /**
     * Final de un elemento
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        profundidad--;
    }

    /**
     * Final del documento
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        consumirLinea();
    }
}
