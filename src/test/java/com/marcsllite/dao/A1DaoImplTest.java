package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.A1Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class A1DaoImplTest extends DBTest {
    @InjectMocks
    A1DaoImpl dao;
    @Mock
    A1Model model;

    @BeforeEach
    public void setUp() {
        super.setUp();
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
    }

    @Test
    void testGetA1_NoResult() {
        String abbr = "abbr";

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(-123456789.0f, dao.getA1(abbr));
    }

    @Test
    void testGetA1() {
        String abbr = "abbr";
        float exp = 1f;

        when(em.find(any(), any())).thenReturn(model);
        when(model.getValue()).thenReturn(exp);

        assertEquals(exp, dao.getA1(abbr));
    }
}