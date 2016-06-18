package com.keeperteacher.ktservice.core.service;

import com.keeperteacher.ktservice.core.model.PersistedObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public abstract class BaseDao<T extends PersistedObject> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseDao.class);
    private Class<T> persistentClass;
    @Autowired protected SessionFactory sessionFactory;

    public BaseDao() {
        // reflection magic
        persistentClass = (Class<T>) ((ParameterizedType)getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public T findById(String id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(persistentClass, id);
    }

    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM " + persistentClass.getCanonicalName()).list();
    }

    public T create(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.save(entity);
        return entity;
    }

    public T update(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.update(entity);
        return entity;
    }

    public T delete(String id) {
        Session session = sessionFactory.getCurrentSession();
        T entity = session.get(persistentClass, id);
        session.delete(entity);
        return entity;
    }
}
