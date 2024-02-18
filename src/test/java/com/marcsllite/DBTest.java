package com.marcsllite;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;

public abstract class DBTest {
    @PersistenceUnit
    protected EntityManagerFactory factory;
    @PersistenceUnit
    protected EntityManager em;

    private final String PERSISTENCE_UNIT_NAME = "com.marcsllite.db.test";

    protected void initPU() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
    }
}
