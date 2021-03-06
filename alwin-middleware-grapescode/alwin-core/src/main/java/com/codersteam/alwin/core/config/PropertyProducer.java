package com.codersteam.alwin.core.config;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Adam Stepnowski
 */
@Stateless
public class PropertyProducer {

    private Properties properties;

    @Property
    @Produces
    public String produceString(final InjectionPoint ip) {
        return this.properties.getProperty(getKey(ip));
    }

    @Property
    @Produces
    public int produceInt(final InjectionPoint ip) {
        return Integer.valueOf(this.properties.getProperty(getKey(ip)));
    }

    @Property
    @Produces
    public boolean produceBoolean(final InjectionPoint ip) {
        return Boolean.valueOf(this.properties.getProperty(getKey(ip)));
    }

    private String getKey(final InjectionPoint ip) {
        return (ip.getAnnotated().isAnnotationPresent(Property.class)
                && !ip.getAnnotated().getAnnotation(Property.class).value().isEmpty())
                ? ip.getAnnotated().getAnnotation(Property.class).value() : ip.getMember().getName();
    }

    @PostConstruct
    public void init() {
        this.properties = new Properties();
        final InputStream stream = PropertyProducer.class.getResourceAsStream("/application.properties");
        if (stream == null) {
            throw new IllegalStateException("No properties!!!");
        }
        try {
            this.properties.load(stream);
        } catch (final IOException e) {
            throw new IllegalStateException("Configuration could not be loaded!");
        }
    }
}