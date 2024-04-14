package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.A1Model;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;
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
    private final String DEFAULT_SYMBOL = "Sy";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final NuclideModelId DEFAULT_ID = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);

    @BeforeEach
    public void setUp() {
        super.setUp();
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
    }

    @Test
    void testGetA1_NoResult() {
        when(em.find(any(), any())).thenReturn(null);

        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, dao.getA1(DEFAULT_ID));
    }

    @Test
    void testGetA1() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        when(em.find(any(), any())).thenReturn(model);
        when(model.getValue()).thenReturn(exp);

        assertEquals(exp, dao.getA1(DEFAULT_ID));
    }
}