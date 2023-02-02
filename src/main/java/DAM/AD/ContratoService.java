package DAM.AD;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratoService {
    
    @Autowired
    ContratoRepository repository;

    /*
     * Guardamos la lista de contrato en la base de datos.
     * Para ello usamos el repositorio JPA enlazado a la
     * entidad Contrato.
     */
    public List<Contrato> guardarContratos(List<Contrato> contratos){

        // Inicializamos la lista de contratos
        List<Contrato> result = new ArrayList<Contrato>();

        try {

            /*
             * Guardamos todos los contratos en la base de datos.
             * El metodo saveAll devuelve la lista de contratos que 
             * se han almacenado en la base de datos.
             * 
             * https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/ListCrudRepository.html#saveAll(java.lang.Iterable)
             */
            result = repository.saveAll(contratos);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

}
