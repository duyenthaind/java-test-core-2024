package org.example.service;

/**
 * @author duyenthai
 */
public class HibernateAnimalEntryPoint extends HibernateEntryPoint {

    public HibernateAnimalEntryPoint() {
        super("hibernate.cfg.xml");
    }

    public static HibernateAnimalEntryPoint getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        public static final HibernateAnimalEntryPoint INSTANCE = new HibernateAnimalEntryPoint();
    }
}
