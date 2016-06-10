package repositories;

import models.BaseModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository<T extends BaseModel> {

    protected Configuration configuration = null;
    protected ServiceRegistryBuilder registry = null;
    protected ServiceRegistry serviceRegistry = null;

    protected Class<T> classT;

    public BaseRepository() {
        try {
            configuration = new Configuration().configure("hibernate.cfg.xml");
            registry = new ServiceRegistryBuilder();
            registry.applySettings(configuration.getProperties());
            serviceRegistry = registry.buildServiceRegistry();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public T getById(int id) throws InstantiationException, IllegalAccessException {
        return this.getAll().stream().filter(item -> item.getId() == id).findFirst().orElse(null);
    }

    public List<T> getAll() {
        List<T> items = new ArrayList<>();

        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        Transaction tran = null;

        try {
            tran = session.beginTransaction();
            items = session.createCriteria(classT).list();
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
        } finally {
            session.close();
        }

        return items;
    }

    public void save(T item) {
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        Transaction tran = null;

        try {
            tran = session.beginTransaction();
            session.saveOrUpdate(item);
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
        } finally {
            session.close();
        }
    }

    public void delete(int id) throws IllegalAccessException, InstantiationException {
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        Transaction tran = null;

        try {
            tran = session.beginTransaction();
            session.delete(getById(id));
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
        } finally {
            session.close();
        }
    }
}