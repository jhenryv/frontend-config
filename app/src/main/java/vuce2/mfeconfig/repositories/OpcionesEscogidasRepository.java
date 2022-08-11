package vuce2.mfeconfig.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import vuce2.mfeconfig.models.OpcionesEscogidas;


public interface OpcionesEscogidasRepository extends MongoRepository<OpcionesEscogidas,String> {
}
