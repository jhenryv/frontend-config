package vuce2.mfeconfig.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import vuce2.mfeconfig.models.Procesos;

import java.util.List;

public interface ProcesosRepository extends MongoRepository<Procesos,String> {
    List<Procesos> findByIdEntidadAndProcedimientoContaining(String idEntidad, String procedimiento);
}
