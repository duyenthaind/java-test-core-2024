package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

/**
 * @author duyenthai
 */
public class HibernateEntryPoint {

    private static final Logger LOGGER = LogManager.getLogger("HibernateEntryPoint");

    private final String configFile;
    private SessionFactory sessionFactory;

    public HibernateEntryPoint(String configFile) {
        this.configFile = configFile;
    }

    public int setup() {
        String configDir = System.getProperty("user.dir") + File.separator + "config";
        try {
            if (sessionFactory == null) {
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .configure(new File(configDir + File.separator + configFile));

                StandardServiceRegistry standardRegistry = builder.build();
                Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
                sessionFactory = metaData.getSessionFactoryBuilder().build();

                return 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Initial SessionFactory creation failed.", ex);
        }
        return -1;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
