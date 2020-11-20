package cache.service;

import cache.dto.CacheDto;
import cache.entity.CacheEntity;
import cache.repo.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CacheService {

    @Autowired
    private CacheRepository repository;

    @Cacheable(value = "TCCache10", key = "#testCaseId + '-' + #qualifier")
    public Optional<CacheDto> getUniqueReference(String cacheName, String testCaseId, String qualifier) throws InterruptedException {
        Thread.sleep(4000l);

        return Arrays.asList(
                new CacheDto("TC-001", "UNIQUEREF", "1234567890"),
                new CacheDto("TC-001", "SOMEBIC1", "1234567890"),
                new CacheDto("TC-002", "UNIQUEREF", "1234567890"),
                new CacheDto("TC-002", "SOMEBIC2", "1234567890"),
                new CacheDto("TC-003", "UNIQUEREF", "1234567890")
        ).stream()
                .filter(c -> c.getTestCaseId().equals(testCaseId) && c.getQualifier().equals(qualifier))
                .findFirst();
    }

    @CachePut(value = "TCCache10", key = "'EXEC-001' + #dto.testCaseId + #dto.qualifier")
    @Transactional
    public CacheEntity addCacheDto(CacheDto dto) {
        CacheEntity cacheEntity = new CacheEntity();

        cacheEntity.setCacheName("TCCache10");
        cacheEntity.setValue(String.valueOf(dto.getValue()));
        cacheEntity.setExecutionName("EXEC-001");
        cacheEntity.setQualifier(dto.getTestCaseId() + "-" + dto.getQualifier());

        Optional<CacheEntity> optional = this.findCacheEntity(dto);

        if (optional.isPresent()) {
            System.err.println(String.format("Updating CacheEntity: %d", optional.get().getId()));
            cacheEntity = optional.get();
            cacheEntity.setValue(String.valueOf(dto.getValue()));
            this.repository.save(cacheEntity);
        } else {
            this.repository.save(cacheEntity);
            System.err.println(String.format("Adding new CacheEntity: %s : %s : %s",
                    cacheEntity.getExecutionName(),
                    cacheEntity.getCacheName(),
                    cacheEntity.getQualifier()));
        }

        return this.repository.save(optional.orElse(cacheEntity));
        /*return findCacheEntity(dto).orElse(null);*/
    }

    @Cacheable(value = "TCCache10", key = "'EXEC-001' + #dto.testCaseId + #dto.qualifier")
    public Optional<CacheEntity> findCacheEntity(CacheDto dto){
        System.err.println(String.format("Searching CacheEntity: %s : %s : %s",
                "EXEC-001",
                "TCCache10",
                dto.getTestCaseId() + "-" + dto.getQualifier()));
        return this.repository.findByExecutionNameAndCacheNameAndQualifier("EXEC-001",
                "TCCache10",
                dto.getTestCaseId() + "-" + dto.getQualifier());
    }

    public Optional<CacheEntity> findCacheEntity(long id) {
        System.err.println("Finding Cache Entity with ID: " + id);
        return this.repository.findById(id);
    }
}
