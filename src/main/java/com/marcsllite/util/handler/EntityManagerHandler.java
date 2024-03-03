package com.marcsllite.util.handler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerHandler {
    private static final String PERSISTENCE_UNIT_NAME = "com.marcsllite.db";
    private EntityManagerFactory factory;
    private EntityManager em;

    private EntityManagerHandler() { }

    private static class EntityManagerHandlerSingleton {
        private static final EntityManagerHandler INSTANCE = new EntityManagerHandler();
    }

    public static EntityManagerHandler getInstance() {
        return EntityManagerHandlerSingleton.INSTANCE;
    }

    public EntityManagerFactory getFactory() {
        if(factory == null) {
            setFactory(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
        }

        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public EntityManager getEntityManager() {
        if(em == null) {
            setEntityManager(getFactory().createEntityManager());
        }

        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
