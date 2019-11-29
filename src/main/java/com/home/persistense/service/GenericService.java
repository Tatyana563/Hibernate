package com.home.persistense.service;

import java.util.Collection;
import java.util.Optional;

public interface GenericService<T, P> {
    void createOrUpdate(T entity);

    Collection<T> findAll();

    Optional<T> findById(int id);

    void remove(int id);

}


