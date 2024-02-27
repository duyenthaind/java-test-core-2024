package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author duyenthai
 */
public class ApplicationServiceDbImpl implements ApplicationService {

    private static final Logger LOGGER = LogManager.getLogger("ApplicationServiceDb");

    @Override
    public void process() {
        int setupX = HibernateAnimalEntryPoint.getInstance().setup();
        if (setupX != 0) {
            LOGGER.error("Setup db error ");
            System.exit(1);
        }
    }
}
