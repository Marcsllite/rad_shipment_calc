package com.marcsllite.dao;

import jakarta.persistence.Query;

import java.util.List;

public interface Dao<ENTITY, ID> {
    ENTITY findById(ID id);

    List<ENTITY> findAll();

    List<ENTITY> findSingleResult(Query query);

    ENTITY attach(ENTITY entity);

    void remove(ID id);

    void flush();

    void persist(ENTITY entity);
}
