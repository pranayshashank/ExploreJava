package cache.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public net.sf.ehcache.config.Configuration ehCacheConfiguration() {
        return new net.sf.ehcache.config.Configuration();
    }

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration cacheConfig10Sec = new CacheConfiguration();
        cacheConfig10Sec.setName("TCCache10");
        cacheConfig10Sec.setMemoryStoreEvictionPolicy("LRU");
        cacheConfig10Sec.setMaxEntriesLocalHeap(1000l);
        cacheConfig10Sec.setTimeToLiveSeconds(100l);

        CacheConfiguration cacheConfig20Sec = new CacheConfiguration();
        cacheConfig20Sec.setName("TCCache20");
        cacheConfig20Sec.setMemoryStoreEvictionPolicy("LRU");
        cacheConfig20Sec.setMaxEntriesLocalHeap(1000l);
        cacheConfig20Sec.setTimeToLiveSeconds(20l);

        net.sf.ehcache.config.Configuration config = ehCacheConfiguration();
        config.addCache(cacheConfig10Sec);
        config.addCache(cacheConfig20Sec);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}
