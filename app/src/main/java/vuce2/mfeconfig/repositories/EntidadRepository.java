package vuce2.mfeconfig.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vuce2.mfeconfig.models.Entidad;

@Repository
public interface EntidadRepository extends MongoRepository<Entidad, String> {
}
