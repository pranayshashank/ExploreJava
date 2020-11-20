package core.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component ("LEDDisplayBean")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LEDDisplay implements DisplayUnit, BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, DisposableBean {
    private final String resolution = "1080x900";
    private final long id = System.currentTimeMillis();

    public LEDDisplay(){
        System.out.println(String.format("%s: Instantiating ...",
                this.getClass().getName()));
    }

    @Override
    public String display(String message) {
        return String.format("%s: %s", this.getClass().getName(), message);
    }

    public String getResolution() {
        return resolution;
    }

    @Override
    public long getId() {
        return id;
    }

    @PostConstruct
    @Override
    public void init() {
        System.out.println(String.format("%s: Calling init()",
                this.getClass().getName()));
    }

    @PreDestroy
    @Override
    public void shutdown() {
        System.out.println(String.format("%s: Calling shutdown()",
                this.getClass().getName()));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(String.format("%s: Setting Bean Factory",
                this.getClass().getName()));
    }

    @Override
    public void setBeanName(String s) {
        System.out.println(String.format("%s: Setting Bean Name to: %s",
                this.getClass().getName(),
                s));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(String.format("%s: Setting ApplicationContext",
                this.getClass().getName()));
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(String.format("%s: Calling destroy()",
                this.getClass().getName()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(String.format("%s: Calling afterPropertiesSet()",
                this.getClass().getName()));
    }
}
