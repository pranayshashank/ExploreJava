package cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import java.io.File;
import java.util.Objects;
import java.util.Random;

public class CentralCache {

    private final CacheManager cacheManager;

    public CentralCache(String storagePath){
        this.cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File(storagePath, "myData")))
                /*.withCache("Fruits", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class,
                        String.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(10, MemoryUnit.MB)
                                .offheap(50, MemoryUnit.MB)
                                .disk(100, MemoryUnit.MB, true)))*/
                .build(true);
        init();
    }

    private void init(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            cacheManager.close();
        }));
    }

    public void addCache(String regionName){
        Cache cache = this.cacheManager.getCache(regionName, String.class, String.class);
        if (Objects.isNull(cache)){
            System.out.println("creating cache region: " + regionName);
            cacheManager.createCache(regionName,
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class,
                            String.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(10, MemoryUnit.MB)
                    .offheap(50, MemoryUnit.MB)
                    .disk(100, MemoryUnit.MB, true)));
        } else {
            System.out.println("Exists cache region: " + regionName);
        }
    }

    public void put (String region, String key, String value){
        this.cacheManager.getCache(region, String.class, String.class).put(key, value);
    }

    public Object get (String region, String key){
        return this.cacheManager.getCache(region, String.class, String.class).get(key);
    }

    public static void main(String[] args) {
        System.out.println("creating an instance of CentralCache");
        CentralCache cache = new CentralCache("./central.cache");

        cache.processForRegion("Vegetables", true);
        cache.processForRegion("Fruits", true);
        cache.processForRegion("Animals", true);
        cache.processForRegion("Birds", false);


    }

    private void processForRegion(String region, boolean readOnly){
        this.addCache(region);

        Random random = new Random(100);

        if (!readOnly){
            for (int i=1; i<=5;i++){
                String val = String.valueOf(random.nextInt(999));
                System.out.println("put to cache: " + region + "-" +i + " : " + val);
                this.put(region, region + "-" + i, val);
            }
        }

        System.out.println(region + "-1: " + this.get(region, region + "-1"));
        System.out.println(region + "-2: " + this.get(region, region + "-2"));
        System.out.println(region + "-3: " + this.get(region, region + "-3"));
        System.out.println(region + "-4: " + this.get(region, region + "-4"));
        System.out.println(region + "-5: " + this.get(region, region + "-5"));
    }

}
