package cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication// (scanBasePackages = {"cache.component", "cache.config", "cache.entity", "cache.repo", "cache.service"})
public class BootCacheApp {
    public static void main(String[] args) {
        SpringApplication.run(BootCacheApp.class, args);
    }
}
