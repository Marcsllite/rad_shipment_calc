package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LimitsDaoImplTest extends DBTest {
    @InjectMocks
    LimitsDaoImpl dao;
    LimitsDaoImpl daoSpy;
    @Mock
    LimitsModel model;

    @BeforeEach
    public void setUp() {
        super.setUp();
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
        daoSpy = spy(dao);
    }

    @Test
    void testGetLimits_NoResults() {
        when(em.find(any(), any())).thenReturn(null);

        assertNull(dao.getLimits(new LimitsModelId()));
    }

    @Test
    void testGetLimits() {
        LimitsModelId limitsId = new LimitsModelId();

        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getLimits(limitsId));
    }

    @Test
    void testGetIALimited_NoResults() {
        LimitsModelId limitsId = new LimitsModelId();
        String exp = RadBigDecimal.NEG_INFINITY_DISPLAY_STRING;

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getIALimited(limitsId));

        verify(daoSpy).getLimits(limitsId);
    }

    @Test
    void testGetIALimited() {
        LimitsModelId limitsId = new LimitsModelId();
        String exp = RadBigDecimal.valueOf(1.0f).toDisplayString();

        when(em.find(any(), any())).thenReturn(model);
        when(model.getIaLimitedStr()).thenReturn(exp);

        assertEquals(exp, daoSpy.getIALimited(limitsId));

        verify(daoSpy).getLimits(limitsId);
    }

    @Test
    void testGetIAPackage_NoResults() {
        LimitsModelId limitsId = new LimitsModelId();
        String exp = RadBigDecimal.NEG_INFINITY_DISPLAY_STRING;

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getIAPackage(limitsId));

        verify(daoSpy).getLimits(limitsId);
    }

    @Test
    void testGetIAPackage() {
        LimitsModelId limitsId = new LimitsModelId();
        String exp = RadBigDecimal.valueOf(1.0f).toDisplayString();

        when(em.find(any(), any())).thenReturn(model);
        when(model.getIaPackageStr()).thenReturn(exp);

        assertEquals(exp, daoSpy.getIAPackage(limitsId));

        verify(daoSpy).getLimits(limitsId);
    }

    @Test
    void testGetLimited_NoResults() {
        LimitsModelId limitsId = new LimitsModelId();
        String exp = RadBigDecimal.NEG_INFINITY_DISPLAY_STRING;

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getLimited(limitsId));

        verify(daoSpy).getLimits(limitsId);
    }

    @Test
    void testGetLimited() {
        LimitsModelId limitsId = new LimitsModelId();
        String exp = RadBigDecimal.valueOf(1.0f).toDisplayString();

        when(em.find(any(), any())).thenReturn(model);
        when(model.getLimitedStr()).thenReturn(exp);

        assertEquals(exp, daoSpy.getLimited(limitsId));

        verify(daoSpy).getLimits(limitsId);
    }
}
