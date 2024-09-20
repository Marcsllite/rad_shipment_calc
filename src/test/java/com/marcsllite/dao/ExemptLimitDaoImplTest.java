package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.ExemptLimitModel;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExemptLimitDaoImplTest extends DBTest {
    @InjectMocks
    ExemptLimitDaoImpl dao;
    @Mock
    ExemptLimitModel model;
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

        assertEquals(exp, dao.getExemptLimit(DEFAULT_ID));
    }

    @Test
    void testGetExemptLimit() {
        String exp = RadBigDecimal.valueOf(1.0f).toDisplayString();

        when(em.find(any(), any())).thenReturn(model);
        when(model.getDecFloatStr()).thenReturn(exp);

        assertEquals(exp, dao.getExemptLimit(DEFAULT_ID));
    }
}