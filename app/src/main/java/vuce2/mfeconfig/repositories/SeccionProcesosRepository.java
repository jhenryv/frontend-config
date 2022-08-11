package vuce2.mfeconfig.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import vuce2.mfeconfig.models.SeccionProcesos;

public interface SeccionProcesosRepository extends MongoRepository<SeccionProcesos,String> {
}
