package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.model.db.ReportableQuantityModel;
import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReportableQuantityDaoImplTest extends DBTest {
    @InjectMocks
    ReportableQuantityDaoImpl dao;
    @Mock
    ReportableQuantityModel model;
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
    void testGetReportQuan_NoResults() {
        when(em.find(any(), any())).thenReturn(null);

        assertNull(dao.getReportQuan(DEFAULT_ID));
    }

    @Test
    void testGetReportQuan() {
        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getReportQuan(DEFAULT_ID));
    }

    @Test
    void testGetCi_NoResults() {
        RadBigDecimal exp = RadBigDecimal.NEG_INFINITY_OBJ;

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getCi(DEFAULT_ID));
        verify(daoSpy).getReportQuan(DEFAULT_ID);
    }

    @Test
    void testGetCi() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(model);
        when(model.getCurie()).thenReturn(exp);

        assertEquals(exp, daoSpy.getCi(DEFAULT_ID));
        verify(daoSpy).getReportQuan(DEFAULT_ID);
    }

    @Test
    void testGetTBq_NoResults() {
        RadBigDecimal exp = RadBigDecimal.NEG_INFINITY_OBJ;

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getTBq(DEFAULT_ID));
        verify(daoSpy).getReportQuan(DEFAULT_ID);
    }

    @Test
    void testGetTBq() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(model);
        when(model.getTeraBq()).thenReturn(exp);

        assertEquals(exp, daoSpy.getTBq(DEFAULT_ID));
        verify(daoSpy).getReportQuan(DEFAULT_ID);
    }
}