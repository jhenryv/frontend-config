package vuce2.mfeconfig.repositories;

import vuce2.mfeconfig.models.ModuleConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleConfigRepository extends MongoRepository<ModuleConfig, String> {
    List<ModuleConfig> findByRemoteName(String remoteName);
    Optional<ModuleConfig> findByRemoteNameAndExposedModule(String remoteName,String exposedModule);
}
