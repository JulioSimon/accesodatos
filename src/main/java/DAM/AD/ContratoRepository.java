package DAM.AD;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/*
 * Jpa incluye metodos muy utilies para almacenar nueva informacion
 * en la base de datos como save() o findAll() que devuelve todos los contratos
 * 
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.core-concepts
 * 
 * En este caso, estamos extendiendo la interfaz ListCrudRepository, ya que esta
 * nos proporciona metodos para trabajar fácilmente con listas de datos.
 * 
 * https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/ListCrudRepository.html
 */

 @Repository
public interface ContratoRepository extends ListCrudRepository<Contrato, Long> {}
