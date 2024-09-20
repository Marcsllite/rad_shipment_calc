package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.A2Model;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class A2DaoImplTest extends DBTest {
    @InjectMocks
    A2DaoImpl dao;
    @Mock
    A2Model model;
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
    void testGetA2_NoResult() {
        String exp = RadBigDecimal.NEG_INFINITY_DISPLAY_STRING;
        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, dao.getA2(DEFAULT_ID));
    }

    @Test
    void testGetA2() {
        String exp = RadBigDecimal.valueOf(1.0f).toDisplayString();

        when(em.find(any(), any())).thenReturn(model);
        when(model.getDecFloatStr()).thenReturn(exp);

        assertEquals(exp, dao.getA2(DEFAULT_ID));
    }
}