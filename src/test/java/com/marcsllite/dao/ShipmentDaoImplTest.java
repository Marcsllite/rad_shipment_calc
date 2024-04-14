package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.ShipmentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ShipmentDaoImplTest extends DBTest {
    @InjectMocks
    ShipmentDaoImpl dao;
    @Mock
    ShipmentModel model;

    @BeforeEach
    public void setUp() {
        super.setUp();
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
    }

    @Test
    void testGetShipment_NoResult() {
        long id = -1L;

        when(em.find(any(), any())).thenReturn(null);

        assertNull(dao.getShipment(id));
    }

    @Test
    void testGetShipment() {
        long id = -1L;

        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getShipment(id));
    }
}