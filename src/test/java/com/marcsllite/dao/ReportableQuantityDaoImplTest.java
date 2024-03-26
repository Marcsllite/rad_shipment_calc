package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.ReportableQuantityModel;
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

    @BeforeEach
    public void setUp() {
        super.setUp();
        when(emHandler.getEntityManager()).thenReturn(em);
    }

    @Test
    void testGetReportQuan_NoResults() {
        String abbr = "abbr";

        when(em.find(any(), any())).thenReturn(null);

        assertNull(dao.getReportQuan(abbr));
    }

    @Test
    void testGetReportQuan() {
        String abbr = "abbr";

        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getReportQuan(abbr));
    }

    @Test
    void testGetCi_NoResults() {
        String abbr = "abbr";
        float exp = -123456789.0f;

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getCi(abbr));
        verify(daoSpy).getReportQuan(abbr);
    }

    @Test
    void testGetCi() {
        String abbr = "abbr";
        float exp = 1f;

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(model);
        when(model.getCurie()).thenReturn(exp);

        assertEquals(exp, daoSpy.getCi(abbr));
        verify(daoSpy).getReportQuan(abbr);
    }

    @Test
    void testGetTBq_NoResults() {
        String abbr = "abbr";
        float exp = -123456789.0f;

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getTBq(abbr));
        verify(daoSpy).getReportQuan(abbr);
    }

    @Test
    void testGetTBq() {
        String abbr = "abbr";
        float exp = 1f;

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(model);
        when(model.getTeraBq()).thenReturn(exp);

        assertEquals(exp, daoSpy.getTBq(abbr));
        verify(daoSpy).getReportQuan(abbr);
    }
}