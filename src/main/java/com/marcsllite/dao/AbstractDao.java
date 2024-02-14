package com.marcsllite.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.StaleStateException;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unchecked")
public class AbstractDao<ENTITY, ID> {
    @PersistenceContext
    private EntityManager em;
    private final Class<ENTITY> entityClass;
    private static final Logger logr = LogManager.getLogger();

    private static final String COUNT_ENTITIES_QUERY = "select count(a) from %s as a";

    public AbstractDao() {
        entityClass = (Class<ENTITY>) (
            (ParameterizedType) getClass().getGenericSuperclass()
        ).getActualTypeArguments()[0];
    }

    public AbstractDao(EntityManager em) {
        this();

    }

    public ENTITY findById(ID id) {
        ENTITY entity = em.find(entityClass, id);
        if(entity == null) {
            String msg = String.format("Attempted to fetch an entity that does not exist. ID: %s", id);
            var nre = new NoResultException(msg);
            logr.throwing(nre);
            throw nre;
        }
        return entity;
    }

    public int count() {
        String query = String.format(AbstractDao.COUNT_ENTITIES_QUERY, getEntityName());
        return ((Long) em.createQuery(query).getSingleResult()).intValue();
    }

    public ENTITY attach(ENTITY entity) {
        try {
            ENTITY mergedEntity = em.merge(entity);
            flush();
            return mergedEntity;
        } catch (OptimisticLockException ole) {
            logr.catching(ole);
            var sse = new StaleStateException("Attempted to attach a stale entity");
            logr.throwing(sse);
            throw sse;
        }
    }

    public void remove(ID id) {
        ENTITY ref;
        try {
            ref = em.getReference(entityClass, id);
            em.remove(ref);
        } catch (EntityNotFoundException enf) {
            logr.catching(enf);
            String msg = String.format("Attempted to remove an unknown entity object. ID: %s", id);
            var nre = new NoResultException(msg);
            logr.throwing(nre);
        }
    }

    public void flush() {
        em.flush();
    }

    public void persist(ENTITY entity) {
        em.persist(entity);
    }

    protected String getEntityName() {
        Entity entity = entityClass.getAnnotation(Entity.class);
        String name = entity.name();
        if(name.isBlank()) {
            name = entityClass.getSimpleName();
        }
        return name;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
