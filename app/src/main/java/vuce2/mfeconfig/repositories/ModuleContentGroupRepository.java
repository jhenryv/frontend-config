package vuce2.mfeconfig.repositories;

import vuce2.mfeconfig.models.ModuleContentGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleContentGroupRepository extends MongoRepository<ModuleContentGroup,String> {
    Optional<ModuleContentGroup> findByName(String name);
    List<ModuleContentGroup> findByIdUser(String idUser);
}
