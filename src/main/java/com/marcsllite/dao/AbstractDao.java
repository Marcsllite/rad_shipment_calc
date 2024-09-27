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
    private static final Logger logr = LogManager.getLogger(AbstractDao.class);
    private static final String COUNT_ENTITIES_QUERY = "select count(a) from %s as a";
    private static final String QUERY_ALL = "select a from %s as a";

    protected AbstractDao() {
        entityClass = (Class<E>) (
            (ParameterizedType) getClass().getGenericSuperclass()
        ).getActualTypeArguments()[0];
    }

    public E findById(I id) throws IllegalArgumentException {
        if(id == null) throw new IllegalArgumentException("id cannot be null");
        E entity = getEntityManager().find(getEntityClass(), id);
        if(entity == null) {
            logr.error("Attempted to fetch {} entity that does not exist. ID: \"{}\"", getEntityClass().getSimpleName(), id);
        }
        return entity;
    }

    public List<E> findAll() {
        String query = String.format(QUERY_ALL, getEntityName());
        return getEntityManager().createQuery(query).getResultList();
    }

    public Object findSingleResult(String query) throws IllegalArgumentException {
        if(query == null) throw new IllegalArgumentException("query cannot be null");
        return getEntityManager()
            .createQuery(query)
            .getSingleResult();
    }

    public int count() {
        String query = String.format(AbstractDao.COUNT_ENTITIES_QUERY, getEntityName());
        return ((Long) getEntityManager().createQuery(query).getSingleResult()).intValue();
    }

    public E attach(E entity) throws IllegalArgumentException, StaleStateException {
        if(entity == null) throw new IllegalArgumentException("entity cannot be null");
        try {
            E mergedEntity = getEntityManager().merge(entity);
            flush();
            return mergedEntity;
        } catch (OptimisticLockException ole) {
            String msg = String.format("Attempted to attach a stale %s entity", getEntityName());
            logr.catching(ole);
            var sse = new StaleStateException(msg);
            logr.throwing(sse);
            throw sse;
        }
    }

    public void remove(I id) throws IllegalArgumentException, NoResultException {
        if(id == null) throw new IllegalArgumentException("id cannot be null");
        E ref;
        try {
            ref = getEntityManager().getReference(getEntityClass(), id);
            getEntityManager().remove(ref);
        } catch (EntityNotFoundException enf) {
            logr.catching(enf);
            logr.warn("Attempted to remove an unknown {} entity. ID: \"{}\"", getEntityClass().getSimpleName(), id);
        }
    }

    public void flush() {
        getEntityManager().flush();
    }

    public void persist(E entity) throws IllegalArgumentException {
        if(entity == null) throw new IllegalArgumentException("entity cannot be null");
        getEntityManager().persist(entity);
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }

    protected String getEntityName() {
        Entity entity = getEntityClass().getAnnotation(Entity.class);
        String name = entity.name();
        if(name.isBlank()) {
            name = getEntityClass().getSimpleName();
        }
        return name;
    }

    public EntityManager getEntityManager() throws IllegalStateException {
        IllegalStateException ise = null;
        if(EntityManagerHandler.getInstance() == null) {
            ise = new IllegalStateException("Entity Manager Handler cannot be null.");
        } else if(!EntityManagerHandler.getInstance().getEntityManager().isOpen()) {
            ise = new IllegalStateException("Entity Manager is closed.");
        }

        if(ise != null) {
            logr.throwing(Level.FATAL, ise);
            throw ise;
        }

        return EntityManagerHandler.getInstance().getEntityManager();
    }
}
