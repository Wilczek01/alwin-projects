package com.codersteam.alwin.common.prop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

/**
 * Klasa dostępu do zewnętrznych parametrów konfigurujących aplikację
 */
@Singleton
@Startup
public class AlwinProperties {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String ALWIN_PROPERTIES = "alwin.properties";
    private final Properties properties = new Properties();

    @PostConstruct
    public void init() {
        final String propertiesFile = System.getProperty(ALWIN_PROPERTIES);
        try {
            final FileInputStream fis = new FileInputStream(propertiesFile);
            properties.load(fis);
        } catch (final IOException e) {
            LOG.error(String.format("Server is not configured correctly. %s file is missing", ALWIN_PROPERTIES));
            throw new IllegalStateException(String.format("Unable to load configuration file %s", ALWIN_PROPERTIES), e);
        }
    }

    public String getProperty(final AlwinPropertyKey key) {
        return properties.getProperty(key.getKey());
    }

}
