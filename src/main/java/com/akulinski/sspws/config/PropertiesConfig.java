package com.akulinski.sspws.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class PropertiesConfig {

    private static final String APPLICATION_DEV_PROPERTIES = "application-dev.properties";
    private static final String APPLICATION_PROD_PROPERTIES = "application-prod.properties";
    private static final String APPLICATION_COMMON_PROPERTIES = "application.properties";

    @Bean
    @Profile("dev")
    public PropertyPlaceholderConfigurer propertiesDev() {

        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[2];
        resources[0] = new ClassPathResource(APPLICATION_COMMON_PROPERTIES);
        resources[1] = new ClassPathResource(APPLICATION_DEV_PROPERTIES);

        ppc.setLocations(resources);
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

    @Bean
    @Profile("prod")
    public PropertyPlaceholderConfigurer propertiesProd() {

        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[2];
        ppc.setLocations(resources);
        ppc.setIgnoreUnresolvablePlaceholders(true);
        resources[0] = new ClassPathResource(APPLICATION_COMMON_PROPERTIES);
        resources[1] = new ClassPathResource(APPLICATION_PROD_PROPERTIES);
        return ppc;
    }

}

