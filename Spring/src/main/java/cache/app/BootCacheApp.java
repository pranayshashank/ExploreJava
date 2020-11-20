package cache.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cache"})
public class BootCacheApp {
    public static void main(String[] args) {
        SpringApplication.run(BootCacheApp.class, args);
    }
}
