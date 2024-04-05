package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.ExemptLimitModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExemptLimitDaoImplTest extends DBTest {
    @InjectMocks
    ExemptLimitDaoImpl dao;
    @Mock
    ExemptLimitModel model;

    @BeforeEach
    public void setUp() {
        super.setUp();
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
    }

    @Test
    void testGetA2_NoResult() {
        String abbr = "abbr";

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(-123456789.0f, dao.getExemptLimit(abbr));
    }

    @Test
    void testGetExemptLimit() {
        String abbr = "abbr";
        float exp = 1f;

        when(em.find(any(), any())).thenReturn(model);
        when(model.getValue()).thenReturn(exp);

        assertEquals(exp, dao.getExemptLimit(abbr));
    }
}