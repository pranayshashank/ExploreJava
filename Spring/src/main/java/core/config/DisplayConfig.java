package core.config;

import core.beans.LCDDisplay;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * This is the first lesson describing how to create a Java configuration
 * of Spring beans.
 * <p>
 * 1. First you need to mark a class as configuration class by adding
 *
 * @org.springframework.context.annotation.Configuration Annotation to it.
 * <p>
 * 2. To create a bean, one need to create a method that returns
 * a specific type of bean needed.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"core.service", "core.aop", "core.beans"})
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class DisplayConfig {

    @Bean(initMethod = "init", destroyMethod = "shutdown", name = "LCDDisplay")
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public LCDDisplay getLCDDisplay() {
        return new LCDDisplay();
    }
}
