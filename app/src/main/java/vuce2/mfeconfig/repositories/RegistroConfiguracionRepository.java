package vuce2.mfeconfig.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import vuce2.mfeconfig.models.RegistroConfiguracion;

import java.util.List;

public interface RegistroConfiguracionRepository extends MongoRepository<RegistroConfiguracion,String> {

    List<RegistroConfiguracion> findByIdEntidadAndIdSeccion(String idEntidad,String idSeccion);

}
