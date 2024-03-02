package com.marcsllite.dao;

import jakarta.persistence.Query;

import java.util.List;

public interface Dao<E, I> {
    E findById(I id);

    List<E> findAll();

    List<E> findSingleResult(Query query);

    E attach(E entity);

    void remove(I id);

    void flush();

    void persist(E entity);
}
