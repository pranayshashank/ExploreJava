package core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<Map<String, Object>> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        Properties properties = factory.getObject();
        String[] arr = properties.getProperty("spring.profiles", "").split(",");
        for (String alias : arr) {
            loadAuxiliaryPropertySources(alias, properties);
        }

        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }

    private void loadAuxiliaryPropertySources(String alias, Properties properties) throws IOException {
        if (Objects.nonNull(alias) && !"".equals(alias.trim())) {
            Properties prop = null;
            Resource resource = new ClassPathResource("application-" + alias + ".yml");

            if (resource.exists()) {
                YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
                factory.setResources(resource);
                prop = factory.getObject();
            } else {
                resource = new ClassPathResource("application-" + alias + ".properties");
                if(resource.exists()){
                    PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
                    factoryBean.setSingleton(false);
                    factoryBean.setLocation(resource);
                    factoryBean.setProperties(properties);
                    prop = factoryBean.getObject();
                }
            }

            if (Objects.nonNull(prop)) {
                properties.putAll(prop);
            }
        }
    }
}
