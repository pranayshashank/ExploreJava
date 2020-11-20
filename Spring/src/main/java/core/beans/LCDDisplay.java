package core.beans;

import org.springframework.beans.factory.DisposableBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/*@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = ConfigurableBeanFactory.SCOPE_SINGLETON)*/
public class LCDDisplay implements DisplayUnit, DisposableBean {
    private final String resolution = "720x600";
    private final long id = System.currentTimeMillis();

    @Override
    public String display(String message) {
        return String.format("LCD Displays: %s", message);
    }

    public String getResolution() {
        return resolution;
    }

    public long getId() {
        return id;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(String.format("%s: Calling destroy()",
                this.getClass().getName()));
    }

    @Override
    public void init() {
        System.out.println(String.format("%s: Calling init()",
                this.getClass().getName()));
    }

    @Override
    public void shutdown() {
        System.out.println(String.format("%s: Calling shutdown()",
                this.getClass().getName()));
    }
}
