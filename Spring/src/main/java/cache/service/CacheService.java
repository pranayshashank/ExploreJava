package cache.service;

import cache.dto.CacheDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CacheService {

    @Cacheable(value = "TCCache", key = "#{testCaseId}-#{qualifier}")
    public Optional<CacheDto> getUniqueReference(String testCaseId, String qualifier) throws InterruptedException {
        Thread.sleep(4000l);

        return Arrays.asList(
                new CacheDto("TC-001", "UNIQUEREF", "1234567890"),
                new CacheDto("TC-001", "SOMEBIC1", "1234567890"),
                new CacheDto("TC-002", "UNIQUEREF", "1234567890"),
                new CacheDto("TC-002", "SOMEBIC2", "1234567890"),
                new CacheDto("TC-003", "UNIQUEREF", "1234567890")
        ).stream()
                .filter(c->c.getTestCaseId().equals(testCaseId) && c.getQualifier().equals(qualifier))
                .findFirst();
    }
}
