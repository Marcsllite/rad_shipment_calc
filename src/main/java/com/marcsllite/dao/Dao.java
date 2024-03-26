package com.marcsllite.dao;

import java.util.List;

public interface Dao<E, I> {
    E findById(I id);

    List<E> findAll();

    Object findSingleResult(String query);

    E attach(E entity);

    void remove(I id);

    void flush();

    void persist(E entity);
}
