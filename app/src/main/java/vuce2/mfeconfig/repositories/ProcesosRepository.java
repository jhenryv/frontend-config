package vuce2.mfeconfig.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import vuce2.mfeconfig.models.Procesos;

public interface ProcesosRepository extends MongoRepository<Procesos,String> {
}
