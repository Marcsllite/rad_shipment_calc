package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.HalfLifeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class HalfLifeDaoImplTest extends DBTest {
    @InjectMocks
    HalfLifeDaoImpl dao;
    @Mock
    HalfLifeModel model;

    @BeforeEach
    public void setUp() {
        super.setUp();
        when(emHandler.getEntityManager()).thenReturn(em);
    }

    @Test
    void testGetA2_NoResult() {
        String abbr = "abbr";

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(-123456789.0f, dao.getHalfLife(abbr));
    }

    @Test
    void testGetHalfLife() {
        String abbr = "abbr";
        float exp = 1f;

        when(em.find(any(), any())).thenReturn(model);
        when(model.getValue()).thenReturn(exp);

        assertEquals(exp, dao.getHalfLife(abbr));
    }
}