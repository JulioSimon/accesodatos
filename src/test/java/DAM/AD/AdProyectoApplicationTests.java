package DAM.AD;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

@SpringBootTest
class AdProyectoApplicationTests {

	@Value("${url.juntaandalucia.xml}")
	private String externalXML;

	@Autowired
    ContratoRepository repository;

	@Test
	void contextLoads() {
	}

	/*
	 * Con este test comprobamos que la base de datos
	 * está operativa, que la creación de una nueva entidad 
	 * Contrato es consistente con su estructura y que 
	 * los datos se almacenan satisfactoriamente en la
	 * base de datos.
	 */
    @Test
    void testSaveAll(){

        // Creamos un nuevo contrato
        Contrato contrato = new Contrato(
            "123123123A",
            "JULIO SIMON SL",
            "SERVICIO",
            "INFORMATICO",
            "01-02-2023",
            "100 €",
            "5",
            "MENOR"
        );

        // Inicializamos una lista de contratos
        List<Contrato> lista = new ArrayList<Contrato>();

        // Agregamos el contrato a la lista
        lista.add(contrato);

        // Guardamos la lista en la base de datos
        List<Contrato> contratosGuardados = repository.saveAll(lista);

        // Comprobamos que los contratos han sido guardados comprobando la lista original con la devuelta por el metodo saveAll.
        assertEquals(lista, contratosGuardados);

    }

	/*
	 * Con este test comprobamos que la url externa del
	 * arhivo XML es accesible y es un documento XML válido.
	 */
	@Test
	void testRemoteUrlIsValidXML() throws MalformedURLException, IOException, ParserConfigurationException, SAXException{

		// Establecemos conexion
		URLConnection conn = new URL(externalXML).openConnection();

		// Obtenemos el stream de datos
		InputStream is = conn.getInputStream();

		// Creamos instancia para parsear el XML
		DocumentBuilderFactory docfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docbuilder = docfactory.newDocumentBuilder();

		// Al parsear el documento, el metodo comprueba que el stream de datos 
		// corresponde a un documento XML valido.
		docbuilder.parse(is);

	}

}
