package com.keeperteacher.ktservice.service;

import com.keeperteacher.ktservice.model.PersistedObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public abstract class BaseDao<T extends PersistedObject> {

    private Class<T> persistentClass;
    @Autowired private SessionFactory sessionFactory;

    public BaseDao() {
        // reflection magic
        persistentClass = (Class<T>) ((ParameterizedType)getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public T findById(String id) {
        Session session = sessionFactory.openSession();
        return session.load(persistentClass, id);
    }

    public List<T> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM " + persistentClass.getCanonicalName()).list();
    }

    public T create(T entity) {
        Session session = sessionFactory.openSession();
        session.save(entity);
        return entity;
    }

    public T update(T entity) {
        Session session = sessionFactory.openSession();
        session.update(entity);
        return entity;
    }

    public T delete(String id) {
        Session session = sessionFactory.openSession();
        T entity = session.load(persistentClass, id);
        session.delete(entity);
        return entity;
    }
}
