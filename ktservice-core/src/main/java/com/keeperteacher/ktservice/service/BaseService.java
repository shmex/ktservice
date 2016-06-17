package com.keeperteacher.ktservice.service;

import com.keeperteacher.ktservice.exception.ResourceNotFoundException;
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
    public T read(String id) throws ResourceNotFoundException {
        T entity = baseDao.findById(id);
        if(entity == null) {
            throw new ResourceNotFoundException("Could not find team with id: " + id);
        }
        return entity;
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
