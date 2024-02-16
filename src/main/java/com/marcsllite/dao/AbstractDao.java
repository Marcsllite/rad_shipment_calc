package com.marcsllite.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.StaleStateException;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractDao<ENTITY, ID> implements Dao<ENTITY, ID> {
    @PersistenceUnit
    private EntityManagerFactory factory;
    @PersistenceContext
    private EntityManager em;
    private final Class<ENTITY> entityClass;
    private static final Logger logr = LogManager.getLogger();

    private static final String COUNT_ENTITIES_QUERY = "select count(a) from %s as a";
    private static final String QUERY_ALL = "select a from %s as a";

    public AbstractDao() {
        factory = Persistence.createEntityManagerFactory("com.marcsllite.db");
        entityClass = (Class<ENTITY>) (
            (ParameterizedType) getClass().getGenericSuperclass()
        ).getActualTypeArguments()[0];
    }

    public AbstractDao(EntityManager em) {
        this();
        setEntityManager(em);
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

    public List<ENTITY> findAll() {
        String query = String.format(QUERY_ALL, getEntityName());
        return em.createQuery(query).getResultList();
    }

    public List<ENTITY> findSingleResult(Query query) {
        List<ENTITY> res = query.getResultList();
        if(res.isEmpty()) {
            var nre = new NoResultException();
            logr.throwing(nre);
            throw nre;
        }
        if(res.size() > 1) {
            var nure = new NonUniqueResultException();
            logr.throwing(nure);
            throw nure;
        }
        return res;
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
