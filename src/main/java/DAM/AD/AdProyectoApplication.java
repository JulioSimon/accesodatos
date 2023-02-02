package DAM.AD;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdProyectoApplication {

	@Value("${url.juntaandalucia.xml}")
	private String externalXML;

	@Autowired
	ContratoController cc;

	public static void main(String[] args) {
		SpringApplication.run(AdProyectoApplication.class, args);
	}

	@Bean
	public CommandLineRunner consola(){
		return (args) -> {

			// Inicializamos la lista de contratos
			List<Contrato> contratos = new ArrayList<Contrato>();

			/*
			 * Descargamos los contratos de la URL proporcionada
			 * los procesamos y guardamos en la base de datos.
			 */
			contratos = cc.obtenerContratos(externalXML);

			/*
			 * Exportamos los contratos a un archivo
			 * XML
			 */
			cc.exportarContratosXML(contratos);

		};
	}

}
