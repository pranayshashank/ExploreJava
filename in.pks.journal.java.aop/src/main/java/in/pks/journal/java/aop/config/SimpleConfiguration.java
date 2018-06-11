package in.pks.journal.java.aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan (basePackages={"in.pks.journal.java.aop.aspect", "in.pks.journal.java.aop.util", "in.pks.journal.java.aop.annotation"})
public class SimpleConfiguration {

}
