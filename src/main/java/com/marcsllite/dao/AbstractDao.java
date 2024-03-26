package com.marcsllite.dao;

import com.marcsllite.util.handler.EntityManagerHandler;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OptimisticLockException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.StaleStateException;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractDao<E extends Serializable, I> implements Dao<E, I> {
    private final Class<E> entityClass;
    private static final Logger logr = LogManager.getLogger();
    private static final String COUNT_ENTITIES_QUERY = "select count(a) from %s as a";
    private static final String QUERY_ALL = "select a from %s as a";

    protected AbstractDao() {
        entityClass = (Class<E>) (
            (ParameterizedType) getClass().getGenericSuperclass()
        ).getActualTypeArguments()[0];
    }

    public E findById(I id) {
        E entity = getEntityManager().find(entityClass, id);
        if(entity == null) {
            logr.error("Attempted to fetch {} entity that does not exist. ID: \"{}\"", entityClass.getSimpleName(), id);
        }
        return entity;
    }

    public List<E> findAll() {
        String query = String.format(QUERY_ALL, getEntityName());
        return getEntityManager().createQuery(query).getResultList();
    }

    public Object findSingleResult(String query) {
        return getEntityManager()
            .createQuery(query)
            .getSingleResult();
    }

    public int count() {
        String query = String.format(AbstractDao.COUNT_ENTITIES_QUERY, getEntityName());
        return ((Long) getEntityManager().createQuery(query).getSingleResult()).intValue();
    }

    public E attach(E entity) throws StaleStateException {
        try {
            E mergedEntity = getEntityManager().merge(entity);
            flush();
            return mergedEntity;
        } catch (OptimisticLockException ole) {
            String msg = String.format("Attempted to attach a stale %s entity", entityClass.getSimpleName());
            logr.catching(ole);
            var sse = new StaleStateException(msg);
            logr.throwing(sse);
            throw sse;
        }
    }

    public void remove(I id) throws NoResultException {
        E ref;
        try {
            ref = getEntityManager().getReference(entityClass, id);
            getEntityManager().remove(ref);
        } catch (EntityNotFoundException enf) {
            logr.catching(enf);
            logr.warn("Attempted to remove an unknown {} entity. ID: \"{}\"", entityClass.getSimpleName(), id);
        }
    }

    public void flush() {
        getEntityManager().flush();
    }

    public void persist(E entity) {
        getEntityManager().persist(entity);
    }

    protected String getEntityName() {
        Entity entity = entityClass.getAnnotation(Entity.class);
        String name = entity.name();
        if(name.isBlank()) {
            name = entityClass.getSimpleName();
        }
        return name;
    }

    public EntityManager getEntityManager() throws IllegalStateException {
        if(EntityManagerHandler.getInstance() == null) {
            IllegalStateException ise = new IllegalStateException("Entity Manager Handler cannot be null.");
            logr.throwing(Level.FATAL, ise);
            throw ise;
        }
        return EntityManagerHandler.getInstance().getEntityManager();
    }
}
