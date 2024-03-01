package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.ReportableQuantityModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReportableQuantityDaoImplTest extends DBTest {
    @InjectMocks
    ReportableQuantityDaoImpl dao;
    @Mock
    ReportableQuantityModel model;

    @Test
    public void testGetReportQuan() {
        String abbr = "abbr";

        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getReportQuan(abbr));
    }

    @Test
    public void testGetCi() {
        String abbr = "abbr";
        float exp = 1f;

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(model);
        when(model.getCurie()).thenReturn(exp);

        assertEquals(exp, daoSpy.getCi(abbr));
        verify(daoSpy).getReportQuan(abbr);
    }

    @Test
    public void testGetTBq() {
        String abbr = "abbr";
        float exp = 1f;

        ReportableQuantityDaoImpl daoSpy = spy(dao);

        when(em.find(any(), any())).thenReturn(model);
        when(model.getTeraBq()).thenReturn(exp);

        assertEquals(exp, daoSpy.getTBq(abbr));
        verify(daoSpy).getReportQuan(abbr);
    }
}