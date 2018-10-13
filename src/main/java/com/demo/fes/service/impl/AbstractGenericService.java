package com.demo.fes.service.impl;

import com.demo.fes.exception.OperationException;
import com.demo.fes.repository.GenericRepository;
import com.demo.fes.service.IGenericService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * Generic service that should be extended by another services
 * It provides CRUD operations and injects useful services that can be used on other services
 *
 * @param <E> entity class
 * @param <K> primary key type
 */
public abstract class AbstractGenericService<E, K extends Serializable> implements IGenericService<E, K> {
    private final GenericRepository<E, K> repository;

    AbstractGenericService(GenericRepository<E, K> repository) {
        this.repository = repository;
    }

    // with entity manager we can create custom SQL queries
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public E save(E object) throws OperationException {
        return repository.save(object);
    }

    @Override
    public List<E> getAll() {
        return repository.findAll();
    }

    @Override
    public E retrieve(K id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void update(E object) {
        repository.save(object);
    }

    @Override
    public void remove(E object) throws OperationException {
        repository.delete(object);
    }

    @Override
    public void removeById(K id) {
        repository.deleteById(id);
    }

    @Override
    public boolean doesExist(K id) {
        return repository.findById(id).orElse(null) != null;
    }

    String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + request.getContextPath();
    }
}
