package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.LimitsModel;
import com.marcsllite.model.db.LimitsModelId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LimitsDaoImplTest extends DBTest {
    @InjectMocks
    LimitsDaoImpl dao;
    LimitsDaoImpl daoSpy;
    @Mock
    LimitsModel model;

    @BeforeEach
    public void setUp() {
        daoSpy = spy(dao);
    }

    @Test
    public void testGetLimits() {
        LimitsModelId limitsId = new LimitsModelId();

        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getLimits(limitsId));
    }

    @Test
    public void testGetIALimited() {
        LimitsModelId limitsId = new LimitsModelId();
        float exp = 1f;

        when(em.find(any(), any())).thenReturn(model);
        when(model.getIa_limited()).thenReturn(exp);

        assertEquals(exp, daoSpy.getIALimited(limitsId));

        verify(daoSpy).getLimits(limitsId);
    }

    @Test
    public void testGetIAPackage() {
        LimitsModelId limitsId = new LimitsModelId();
        float exp = 1f;

        when(em.find(any(), any())).thenReturn(model);
        when(model.getIa_package()).thenReturn(exp);

        assertEquals(exp, daoSpy.getIAPackage(limitsId));

        verify(daoSpy).getLimits(limitsId);
    }

    @Test
    public void testGetLimited() {
        LimitsModelId limitsId = new LimitsModelId();
        float exp = 1f;

        when(em.find(any(), any())).thenReturn(model);
        when(model.getLimited()).thenReturn(exp);

        assertEquals(exp, daoSpy.getLimited(limitsId));

        verify(daoSpy).getLimits(limitsId);
    }
}