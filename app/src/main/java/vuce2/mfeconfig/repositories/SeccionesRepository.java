package vuce2.mfeconfig.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import vuce2.mfeconfig.models.Secciones;

public interface SeccionesRepository extends MongoRepository<Secciones,String> {
}
