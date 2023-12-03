package ifp.kikeverea.xml;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class CreadorXML {

    private final Document documento;

    public CreadorXML(String raiz) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();

        documento = implementation.createDocument(null, raiz, null);
        documento.setXmlVersion("1.0");
    }

    public void crearArchivoXML(String ruta, List<Propiedad> propiedades) throws TransformerException {
        Element raiz = documento.getDocumentElement();
        anadirPropiedades(raiz, propiedades);

        Source source = new DOMSource(documento);
        Result result = new StreamResult(new File(ruta)); // crea el fichero de destino

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result); //escribe el contenido al fichero
    }

    private void anadirPropiedades(Element elemento, List<Propiedad> propiedades) {
        for (Propiedad propiedad : propiedades) {

            Element hijo = documento.createElement(propiedad.getNombre());
            anadirAtributos(hijo, propiedad.getAtributos());

            if (propiedad.getValor() != null) {
                // la propiedad tiene un valor de texto, es la última hoja de esta rama
                Text texto = documento.createTextNode(propiedad.getValor());
                hijo.appendChild(texto);
            } else {
                // la propiedad no tiene un valor de texto, tiene propiedades hijas
                anadirPropiedades(hijo, propiedad.getPropiedades());
            }

            elemento.appendChild(hijo);
        }
    }

    private void anadirAtributos(Element nodo, List<Atributo> atributos) {
        for (Atributo atributo : atributos)
            nodo.setAttribute(atributo.getNombre(), atributo.getValor());
    }
}
