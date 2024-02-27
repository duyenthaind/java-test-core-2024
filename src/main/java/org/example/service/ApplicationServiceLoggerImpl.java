package org.example.service;

import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;

/**
 * @author duyenthai
 */
public class ApplicationServiceLoggerImpl implements ApplicationService {
    @Override
    public void process() {
        String configDir = System.getProperty("user.dir") + File.separator + "config";
        String logConfig = configDir + File.separator + "log4j2.xml";
        Configurator.initialize("Log4j2", logConfig);
    }
}
