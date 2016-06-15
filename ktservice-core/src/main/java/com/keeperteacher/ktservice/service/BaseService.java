package com.keeperteacher.ktservice.service;

import com.keeperteacher.ktservice.model.PersistedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public abstract class BaseService<T extends PersistedObject> {

    @Autowired private BaseDao<T> baseDao;

    public T read(String id) {
        return baseDao.findById(id);
    }

    public List<T> list() {
        return baseDao.findAll();
    }

    public T create(T entity) {
        entity.setId(null);
        return baseDao.create(entity);
    }

    public T update(String id, T entity) {
        entity.setId(id);
        entity.setModified(new Date());
        return baseDao.update(entity);
    }

    public T delete(String id) {
        return baseDao.delete(id);
    }
}
