package ifp.kikeverea.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PersonasHandler extends DefaultHandler {

    private static final int TABULACION = 2;

    private int profundidad = -1;
    private String linea;

    private void actualizarLinea(String contenido) {
        int indentado = TABULACION * profundidad;
        linea = " ".repeat(indentado) + contenido;
    }

    private void anadirALinea(String contenido) {
        linea += contenido;
    }

    private void consumirLinea() {
        System.out.println(linea);
        linea = "";
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) {
        profundidad++;

        if (profundidad > 0)
            consumirLinea();

        String elemento = name+": ";

        if (attributes.getLength() > 0)
            elemento += formatAtributos(attributes);

        actualizarLinea(elemento);
    }

    private String formatAtributos(Attributes atributos) {

        StringBuilder resultado = new StringBuilder("(");
        for (int i = 0; i < atributos.getLength(); i++) {
            resultado.append(atributos.getQName(i));
            resultado.append(": ");
            resultado.append(atributos.getValue(i));

            if (i < atributos.getLength() -1)
                resultado.append(", ");

        }
        resultado.append(")");

        return resultado.toString();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String caracteres = new String(ch, start, length).strip();

        if (!caracteres.isBlank())
            anadirALinea(caracteres);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        profundidad--;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        consumirLinea();
    }
}
