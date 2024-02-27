package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dto.BaseResponse;
import org.example.entity.Animal;
import org.example.service.HibernateAnimalEntryPoint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * @author duyenthai
 */
public class DbAnimalRepository implements AnimalRepository {

    private static final Logger LOGGER = LogManager.getLogger("DbAnimalRepository");
    private final SessionFactory sessionFactory = HibernateAnimalEntryPoint.getInstance().getSessionFactory();

    @Override
    public Animal save(Animal animal) {
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try {
            txn = session.beginTransaction();
            session.save(animal);
            txn.commit();
            return animal;
        } catch (Exception ex) {
            if (txn != null) {
                txn.rollback();
            }
            LOGGER.error("Save error ", ex);
        }
        return null;
    }

    @Override
    public BaseResponse batchSave(List<Animal> animals) {
        BaseResponse response = new BaseResponse();
        response.setR(0);
        response.setMsg("ok");
        Session session = sessionFactory.openSession();
        Transaction txn = null;
        try {
            txn = session.beginTransaction();
            int counter = 0;
            for (Animal a : animals) {
                session.persist(a);
                counter++;
                if (counter % 50 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            txn.commit();
        } catch (Exception ex) {
            if (txn != null) {
                txn.rollback();
            }
            response.setR(1);
            response.setMsg(ex.getMessage());
            LOGGER.error("Save error ", ex);
        }
        return response;
    }
}
