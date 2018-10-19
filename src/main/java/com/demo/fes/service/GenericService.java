package com.demo.fes.service;

import com.demo.fes.exception.OperationException;

import java.io.Serializable;

public interface GenericService<E, K extends Serializable> {
    E save(E entity) throws OperationException;
//    List<E> getAll();
//    E retrieve(K id);
//    void update(E dto);
//    void remove(E dto) throws OperationException;
//    void removeById(K id);
//    boolean doesExist(K id);
}
