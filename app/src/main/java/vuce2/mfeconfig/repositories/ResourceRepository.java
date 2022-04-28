package vuce2.mfeconfig.repositories;

import vuce2.mfeconfig.models.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends MongoRepository<Resource,String>
{
}
