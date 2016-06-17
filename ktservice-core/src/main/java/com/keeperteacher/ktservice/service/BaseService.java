package com.keeperteacher.ktservice.service;

import com.keeperteacher.ktservice.model.PersistedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public abstract class BaseService<T extends PersistedObject> {

    @Autowired private BaseDao<T> baseDao;

    @Transactional(readOnly = true)
    public List<T> list() {
        return baseDao.findAll();
    }

    @Transactional(readOnly = true)
    public T read(String id) {
        return baseDao.findById(id);
    }

    @Transactional
    public T create(T entity) {
        entity.setId(null);
        return baseDao.create(entity);
    }

    @Transactional
    public T update(String id, T entity) {
        entity.setId(id);
        entity.setModified(new Date());
        return baseDao.update(entity);
    }

    @Transactional
    public T delete(String id) {
        return baseDao.delete(id);
    }
}
