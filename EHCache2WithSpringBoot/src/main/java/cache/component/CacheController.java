package cache.component;

import cache.dto.CacheDto;
import cache.entity.CacheEntity;
import cache.service.CacheService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class CacheController {

    @Autowired
    private CacheService service;

    @Autowired
    private Configuration ehCacheConfiguration;

    @Autowired
    private CacheManager ehCacheManager;

    @GetMapping("/query")
    public CacheDto getUniqueRef(@RequestParam(value = "tcId") String testCaseId) throws InterruptedException {
        return this.service.getUniqueReference("TCCache10", testCaseId, "UNIQUEREF")
                .orElse(null);
    }

    @GetMapping("/find")
    public Optional<CacheEntity> getCacheEntity(@RequestParam long id) {
        return this.service.findCacheEntity(id);
    }

    @GetMapping("/find2")
    public Optional<CacheEntity> getCacheEntity(@RequestBody CacheDto dto) {
        return this.service.findCacheEntity(dto);
    }

    @PostMapping("/add")
    public CacheEntity addEntity(@RequestBody CacheDto cacheDto/*Map<String, String> map*/){
        /*CacheDto cacheDto = new CacheDto(map.get("testCaseId"),
                map.get("qualifier"),
                map.get("value"));*/
        return this.service.addCacheDto(cacheDto);
    }

    /*@GetMapping("/query2")
    public CacheDto getUniqueRef(@RequestParam(value = "cache") String cacheName, @RequestParam(value = "tcId") String testCaseId) throws InterruptedException {
        return this.service.getUniqueReference(cacheName, testCaseId, "UNIQUEREF")
                .orElse(null);
    }

    @PostMapping("/cache/add")
    public void addCachingRegion(@RequestParam(value = "name") String name) {
        if(this.ehCacheManager.cacheExists(name)){
            this.ehCacheManager.removeCache(name);
        }
    }

    @PostMapping("/cache/remove")
    public void removedCache(@RequestParam(value = "name") String name) {
        this.ehCacheManager.removeCache(name);
    }*/
}
