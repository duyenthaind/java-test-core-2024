package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entity.Animal;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

/**
 * @author duyenthai
 */
public class DbReadService implements ReadService {

    private static final Logger LOGGER = LogManager.getLogger("DbReadService");

    @Override
    public List<Animal> read() {
        Session session = HibernateAnimalEntryPoint.getInstance().getSessionFactory().openSession();
        try {
            return session.createQuery("from Animal ", Animal.class).list();
        } catch (Exception ex) {
            LOGGER.error("Read error ", ex);
        } finally {
            session.close();
        }
        return Collections.emptyList();
    }
}
