package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.util.handler.EntityManagerHandler;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AbstractDaoTest extends DBTest {
    @Entity(name = "Name")
    static class ConcreteModel implements Serializable {
        private static final long serialVersionUID = -574402544990187774L;
        @Id String id;
    }

    @Entity
    static class ConcreteModelNoName implements Serializable {
        private static final long serialVersionUID = -5769071373571756264L;
        @Id String id;
    }

    AbstractDao<ConcreteModel, String> dao;
    final ConcreteModel model = new ConcreteModel();

    @Override
    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        super.setUp();
        dao = (AbstractDao<ConcreteModel, String>) mock(AbstractDao.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    void testFindById_NullId() {
        IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class, ()-> dao.findById(null)
        );
        assertEquals("id cannot be null", iae.getMessage());
    }

    @Test
    void testFindById_NoResult() {
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        when(em.find(any(), any())).thenReturn(null);
        when(dao.getEntityClass()).thenReturn(ConcreteModel.class);

        try {
            assertNull(dao.findById("id"));
        } catch(Exception e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    void testFindById() {
        ConcreteModel exp = new ConcreteModel();

        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        when(em.find(any(), any())).thenReturn(exp);

        try {
            assertEquals(exp, dao.findById("id"));
        } catch(Exception e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    void testFindAll() {
        when(dao.getEntityClass()).thenReturn(ConcreteModel.class);
        String queryStr = String.format("select a from %s as a", dao.getEntityName());
        ConcreteModel obj = new ConcreteModel();
        List<ConcreteModel> exp = Collections.singletonList(obj);

        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        when(em.createQuery(queryStr)).thenReturn(query);
        when(query.getResultList()).thenReturn(exp);

        assertEquals(exp, dao.findAll());
        verify(em).createQuery(queryStr);
    }

    @Test
    void testFindSingleResult_NullQuery() {
        IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class, ()-> dao.findSingleResult(null)
        );
        assertEquals("query cannot be null", iae.getMessage());
    }

    @Test
    void testFindSingleResult() {
        String queryStr = "select 1";
        ConcreteModel exp = new ConcreteModel();

        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        when(em.createQuery(queryStr)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(exp);

        try {
            assertEquals(exp, dao.findSingleResult(queryStr));
        } catch(Exception e) {
            fail("No exception should be thrown");
        }

        verify(em).createQuery(queryStr);
        verify(query).getSingleResult();
    }

    @Test
    void testCount() {
        when(dao.getEntityClass()).thenReturn(ConcreteModel.class);
        String queryStr = String.format("select count(a) from %s as a", dao.getEntityName());
        long exp = 1L;

        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        when(em.createQuery(queryStr)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(exp);

        assertEquals(exp, dao.count());
        verify(em).createQuery(queryStr);
        verify(query).getSingleResult();
    }

    @Test
    void testAttach_NullEntity() {
        IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class, ()-> dao.attach(null)
        );
        assertEquals("entity cannot be null", iae.getMessage());
    }

    @Test
    void testAttach_Exception() {
        when(dao.getEntityClass()).thenReturn(ConcreteModel.class);
        String exp = String.format("Attempted to attach a stale %s entity", dao.getEntityName());

        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        when(em.merge(model)).thenThrow(new OptimisticLockException());

        StaleStateException sse = assertThrows(
            StaleStateException.class, ()-> dao.attach(model)
        );
        assertEquals(exp, sse.getMessage());
    }

    @Test
    void testAttach() {
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        when(em.merge(model)).thenReturn(model);

        assertEquals(model, dao.attach(model));
        verify(dao).flush();
    }

    @Test
    void testRemove_NullId() {
        IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class, ()-> dao.remove(null)
        );
        assertEquals("id cannot be null", iae.getMessage());
    }

    @Test
    void testRemove_NotFound() {
        String id = "id";

        when(dao.getEntityClass()).thenReturn(ConcreteModel.class);
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        when(em.getReference(dao.getEntityClass(), id)).thenThrow(new EntityNotFoundException());

        try {
            dao.remove(id);
        } catch(Exception e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    void testRemove() {
        String id = "id";

        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        when(em.getReference(dao.getEntityClass(), id)).thenReturn(model);
        doNothing().when(em).remove(model);

        try {
            dao.remove(id);
        } catch(Exception e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    void testFlush() {
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        doNothing().when(em).flush();

        dao.flush();

        verify(em).flush();
    }

    @Test
    void testPersist_NullEntity() {
        IllegalArgumentException iae = assertThrows(
            IllegalArgumentException.class, ()-> dao.persist(null)
        );
        assertEquals("entity cannot be null", iae.getMessage());
    }

    @Test
    void testPersist() {
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        doNothing().when(em).persist(model);

        dao.persist(model);

        verify(em).persist(model);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetEntityName_NoName() {
        AbstractDao<ConcreteModelNoName, String> dao2 = (AbstractDao<ConcreteModelNoName, String>) mock(AbstractDao.class, Mockito.CALLS_REAL_METHODS);
        when(dao2.getEntityClass()).thenReturn(ConcreteModelNoName.class);
        assertEquals(ConcreteModelNoName.class.getSimpleName(), dao2.getEntityName());
    }

    @Test
    void testGetEntityName() {
        when(dao.getEntityClass()).thenReturn(ConcreteModel.class);
        assertEquals(ConcreteModel.class.getAnnotation(Entity.class).name(), dao.getEntityName());
    }

    @Test
    void testGetEntityManager_NullInstance() {
        super.tearDown();
        staticEmHandler = mockStatic(EntityManagerHandler.class);
        staticEmHandler.when(EntityManagerHandler::getInstance).thenReturn(null);

        IllegalStateException ise = assertThrows(
            IllegalStateException.class, () -> dao.getEntityManager()
        );
        assertEquals("Entity Manager Handler cannot be null.", ise.getMessage());
    }

    @Test
    void testGetEntityManager_Closed() {
        super.tearDown();
        staticEmHandler = mockStatic(EntityManagerHandler.class);
        staticEmHandler.when(EntityManagerHandler::getInstance).thenReturn(emHandler);
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(false);

        IllegalStateException ise = assertThrows(
            IllegalStateException.class, () -> dao.getEntityManager()
        );
        assertEquals("Entity Manager is closed.", ise.getMessage());
    }

    @Test
    void testGetEntityManager() {
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        assertEquals(em, dao.getEntityManager());
    }
}
