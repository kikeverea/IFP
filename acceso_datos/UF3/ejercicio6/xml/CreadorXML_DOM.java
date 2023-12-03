package ifp.kikeverea.xml;

import ifp.kikeverea.datos.Atributo;
import ifp.kikeverea.datos.Propiedad;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CreadorXML_DOM {

    private final Document documento;

    public CreadorXML_DOM(String raiz) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();

        documento = implementation.createDocument(null, raiz, null);
        documento.setXmlVersion("1.0");
    }

    /**
     * Escribe en un archivo con la ruta dada, el contenido de las propiedades pasadas a este método, con formato XML
     * @param ruta la ruta del archivo
     * @param datos representación de los datos, estructurados en forma de árbol
     * @throws TransformerException condición excepcional durante el proceso escritura de datos
     */
    public void escribirContenidoXML(String ruta, Propiedad... datos) throws TransformerException {
        Element raiz = documento.getDocumentElement();
        anadirPropiedades(raiz, Arrays.asList(datos));

        Source source = new DOMSource(documento);
        Result result = new StreamResult(new File(ruta)); // abre el fichero de destino

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result); //escribe el contenido al fichero
    }

    private void anadirPropiedades(Element elemento, List<Propiedad> propiedades) {
        for (Propiedad propiedad : propiedades)
            anadirPropiedad(elemento, propiedad);
    }

    private void anadirPropiedad(Element elemento, Propiedad propiedad) {
        Element hijo = documento.createElement(propiedad.getNombre());
        anadirAtributos(hijo, propiedad.getAtributos());

        if (propiedad.getPropiedades().isEmpty()) {
            // la propiedad no tiene propiedades hijas, es la última hoja de esta rama y tiene valor textual
            Text texto = documento.createTextNode(propiedad.getValor());
            hijo.appendChild(texto);
        } else
            anadirPropiedades(hijo, propiedad.getPropiedades());

        elemento.appendChild(hijo);
    }

    private void anadirAtributos(Element nodo, List<Atributo> atributos) {
        for (Atributo atributo : atributos)
            nodo.setAttribute(atributo.getNombre(), atributo.getValor());
    }
}
