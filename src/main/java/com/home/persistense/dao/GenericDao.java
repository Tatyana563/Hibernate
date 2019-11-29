package com.home.persistense.dao;

import java.util.Collection;
import java.util.Optional;

public interface GenericDao<T,P> {
    void save(T entity);
    Collection<T> findAll();
    Optional<T> findById(P id);
    void delete(T entity);
}
