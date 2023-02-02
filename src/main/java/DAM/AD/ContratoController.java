package DAM.AD;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Controller
public class ContratoController {

    @Autowired
    ContratoService service;

    /*
     * Descargamos los contratos de la url de la junta y
     * parseamos el XML construyendo una lista de los 
     * contratos procesados, luego los guardamos en la 
     * base de datos.
     */
    public List<Contrato> obtenerContratos(String url){

        List<Contrato> result = new ArrayList<Contrato>();

        try {

            // Establecemos conexion
            URLConnection conn = new URL(url).openConnection();

            // Obtenemos el stream de datos
            InputStream is = conn.getInputStream();

            // Creamos instancia para parsear el XML
            DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docbuilder = docfactory.newDocumentBuilder();

            // Creamos documento a partir del stream de datos
            Document doc = docbuilder.parse(is);

            // Extraemos todos los nodos Row
            NodeList contratosElementos = doc.getElementsByTagName("Row");

            /*
             * Comenzamos en 1, puesto que el primer nodo contiene
             * los nombres de las columnas.
             */
            for (int elemCounter = 1; elemCounter < contratosElementos.getLength(); elemCounter++){

                // Accedemos al nodo correspondiente a la iteración
                Node contratoNode = contratosElementos.item(elemCounter);

                // Comprobamos que el nodo en cuestión sea del tipo elemento
                if(contratoNode.getNodeType() == Node.ELEMENT_NODE){

                    // Casteamos el nodo a elemento.
                    Element contratoElement = (Element) contratoNode;

                    // Obtenemos todos los elementos con datos
                    NodeList contratoData = contratoElement.getElementsByTagName("Data");

                    // Comprobamos que disponemos de todos los datos necesarios para construir un contrato
                    if(contratoData.getLength() == 8){

                        Contrato ct = new Contrato(
                            contratoData.item(0).getTextContent(), // NIF
                            contratoData.item(1).getTextContent(), // Adjudicatario
                            contratoData.item(2).getTextContent(), // Objeto generico
                            contratoData.item(3).getTextContent(), // Objeto
                            contratoData.item(4).getTextContent(), // Fecha adjudicacion
                            contratoData.item(5).getTextContent(), // Importe
                            contratoData.item(6).getTextContent(), // Proveedores consultados
                            contratoData.item(7).getTextContent()  // Tipo contrato
                        );

                        // Añadimos el contrato a la lista
                        result.add(ct);

                    }

                }
            }

            // Guardamos los contratos en la base de datos
            result = service.guardarContratos(result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public boolean exportarContratosXML(List<Contrato> contratos){

        boolean result = false;

        // Vamos a guardar el archivo XML en la carpeta home del usuario
        String xmlPath = System.getProperty("user.home") + File.separator + "contratos.xml";

        try {

            // Creamos un documento vacio
            DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docbuilder = docfactory.newDocumentBuilder();
            Document doc = docbuilder.newDocument();

            // Creamos el elemento root y lo añadimos al documento
            Element root = doc.createElement("contratos");
            doc.appendChild(root);

            // Creamos los elementos contrato y lo añadimos al root
            for(Contrato ct : contratos){

                // Creamos el elemento contrato
                Element contrato = doc.createElement("contrato");
                Attr attr = doc.createAttribute("id");
                attr.setValue(Long.toString(ct.getId()));
                contrato.setAttributeNode(attr);

                /*
                 * Creamos los elementos del contrato
                 */

                // NIF
                Element nif = doc.createElement("nif");
                nif.appendChild(doc.createTextNode(ct.getNif()));
                contrato.appendChild(nif);

                // Adjudicatario
                Element adjudicatario = doc.createElement("adjudicatario");
                adjudicatario.appendChild(doc.createTextNode(ct.getAdjudicatario()));
                contrato.appendChild(adjudicatario);

                // Objeto generico
                Element objetogenerico = doc.createElement("objetogenerico");
                objetogenerico.appendChild(doc.createTextNode(ct.getObjetoGenerico()));
                contrato.appendChild(objetogenerico);

                // Objeto
                Element objeto = doc.createElement("objeto");
                objeto.appendChild(doc.createTextNode(ct.getObjeto()));
                contrato.appendChild(objeto);

                // Fecha adjudicacion
                Element fechaadjudicacion = doc.createElement("fechaadjudicacion");
                fechaadjudicacion.appendChild(doc.createTextNode(ct.getFechaAdjudicacion()));
                contrato.appendChild(fechaadjudicacion);

                // Importe
                Element importe = doc.createElement("importe");
                importe.appendChild(doc.createTextNode(ct.getImporte()));
                contrato.appendChild(importe);

                // Proveedores consultados
                Element proveedoresconsultados = doc.createElement("proveedoresconsultados");
                proveedoresconsultados.appendChild(doc.createTextNode(ct.getProveedoresConsultados()));
                contrato.appendChild(proveedoresconsultados);

                // Adjuntamos el elemento contrato al elemento root
                root.appendChild(contrato);

            }

            /*
             * Ahora transformamos el doc DOM a un archivo XML
             */
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource ds = new DOMSource(doc);
            StreamResult sr = new StreamResult(new File(xmlPath));
            t.transform(ds, sr);

            System.out.println("Archivo creado en: " + xmlPath);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

}
