package ifp.kikeverea.xml;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class LectorXML_DOM {

    private static final int TABULACION = 2;

    private final Document documento;
    private final String raiz;

    public LectorXML_DOM(String raiz, String ruta) throws ParserConfigurationException, IOException, SAXException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        this.raiz = raiz;
        this.documento = builder.parse(new File(ruta)); //abre fichero para lectura
    }

    /**
     * Lee el contenido del documento XML de la ruta pasada a esta instancia y lo imprime por salida estándar
     */
    public void leerContenido() {
        NodeList personas = documento.getElementsByTagName(raiz);
        Node nodoRaiz = personas.item(0);

        imprimirNodo(nodoRaiz, 0);
    }


    private void imprimirNodo(Node nodo, int profundidad) {
        if (nodo.getNodeType() != Node.ELEMENT_NODE) // omite nodos que no sean elementos (metadata)
            return;

        int indentacion = TABULACION * profundidad;

        imprimirNombre(nodo, indentacion);
        imprimirAtributos(nodo.getAttributes());

        NodeList hijos = nodo.getChildNodes();
        boolean finalDeRama = hijos.getLength() == 1; // un solo hijo (texto), asume que es el último nodo de la rama

        if (finalDeRama)
            imprimirTexto(nodo);
        else
            imprimirHijos(hijos, profundidad);
    }

    private void imprimirHijos(NodeList hijos, int profundidad) {
        System.out.println();
        for (int i = 0; i < hijos.getLength(); i++)
            imprimirNodo(hijos.item(i), profundidad + 1);
    }

    private void imprimirNombre(Node nodo, int indentacion) {
        System.out.print(" ".repeat(indentacion) + nodo.getNodeName());
    }

    private void imprimirAtributos(NamedNodeMap atributos) {
        if (atributos == null || atributos.getLength() == 0)
            return;

        System.out.print(" (");
        for (int i = 0; i < atributos.getLength(); i++) {
            imprimirAtributo(atributos.item(i));

            if (i < atributos.getLength() - 1) // añade coma para separar atributos, excepto el último
                System.out.print(", ");
        }
        System.out.print(")");
    }

    private void imprimirAtributo(Node atributo) {
        System.out.print(atributo.getNodeName()+": " + atributo.getTextContent());
    }

    private void imprimirTexto(Node nodo) {
        System.out.println(": " + nodo.getTextContent());
    }
}
