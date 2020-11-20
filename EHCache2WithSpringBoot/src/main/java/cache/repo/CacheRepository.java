package cache.repo;

import cache.entity.CacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CacheRepository extends CrudRepository<CacheEntity, Long> {
    /*Set<CacheEntity> findAllByCacheName(String cacheName);*/

    Optional<CacheEntity> findByExecutionNameAndCacheNameAndQualifier(String executionName, String cacheName, String qualifier);
}
