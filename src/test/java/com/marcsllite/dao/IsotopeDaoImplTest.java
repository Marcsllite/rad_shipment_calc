package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IsotopeDaoImplTest extends DBTest {
    @InjectMocks
    IsotopeDaoImpl dao;
    IsotopeDaoImpl daoSpy;
    @Mock
    IsotopeModel model;

    @BeforeEach
    public void setUp() {
        super.setUp();
        daoSpy = spy(dao);
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
    }

    @Test
    void testGetAllIsotopes() {
        Query querySpy = spy(query);
        List<IsotopeModel> list = new ArrayList<>();
        list.add(model);

        when(em.createQuery(anyString())).thenReturn(querySpy);
        when(querySpy.getResultList()).thenReturn(list);

        List<IsotopeModel> ret = daoSpy.getAllIsotopes();

        assertEquals(1, ret.size());
        assertEquals(model, ret.get(0));
    }

    @Test
    void testGetIsotope_NoResults() {
        IsotopeModelId isoId = new IsotopeModelId("", "");

        when(em.find(any(), any())).thenReturn(null);

        assertNull(dao.getIsotope(isoId));
    }

    @Test
    void testGetIsotope() {
        IsotopeModelId isoId = new IsotopeModelId("", "");

        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getIsotope(isoId));
    }

    @Test
    void testIsotopeName_NoResults() {
        IsotopeModelId isoId = new IsotopeModelId("", "");
        String exp = "";

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getIsotopeName(isoId));

        verify(daoSpy).getIsotope(isoId);
    }

    @Test
    void testIsotopeName() {
        IsotopeModelId isoId1 = new IsotopeModelId(null, null);
        IsotopeModelId isoId2 = new IsotopeModelId(null, "Abbr2");
        IsotopeModelId isoId3 = new IsotopeModelId("Name3", null);
        IsotopeModelId isoId4 = new IsotopeModelId("Name4", "Abbr4");

        when(em.find(any(), any())).thenReturn(model);
        when(model.getIsotopeId()).thenReturn(isoId1, isoId2, isoId3, isoId4);

        assertEquals(isoId1.getName(), daoSpy.getIsotopeName(isoId1));
        assertEquals(isoId2.getName(), daoSpy.getIsotopeName(isoId2));
        assertEquals(isoId3.getName(), daoSpy.getIsotopeName(isoId3));
        assertEquals(isoId4.getName(), daoSpy.getIsotopeName(isoId4));

        verify(daoSpy).getIsotope(isoId1);
        verify(daoSpy).getIsotope(isoId2);
        verify(daoSpy).getIsotope(isoId3);
        verify(daoSpy).getIsotope(isoId4);
    }

    @Test
    void testIsotopeAbbr_NoResults() {
        IsotopeModelId isoId = new IsotopeModelId("", "");
        String exp = "";

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getIsotopeAbbr(isoId));

        verify(daoSpy).getIsotope(isoId);
    }

    @Test
    void testIsotopeAbbr() {
        IsotopeModelId isoId1 = new IsotopeModelId(null, null);
        IsotopeModelId isoId2 = new IsotopeModelId(null, "Abbr2");
        IsotopeModelId isoId3 = new IsotopeModelId("Name3", null);
        IsotopeModelId isoId4 = new IsotopeModelId("Name4", "Abbr4");

        when(em.find(any(), any())).thenReturn(model);
        when(model.getIsotopeId()).thenReturn(isoId1, isoId2, isoId3, isoId4);

        assertEquals(isoId1.getAbbr(), daoSpy.getIsotopeAbbr(isoId1));
        assertEquals(isoId2.getAbbr(), daoSpy.getIsotopeAbbr(isoId2));
        assertEquals(isoId3.getAbbr(), daoSpy.getIsotopeAbbr(isoId3));
        assertEquals(isoId4.getAbbr(), daoSpy.getIsotopeAbbr(isoId4));

        verify(daoSpy).getIsotope(isoId1);
        verify(daoSpy).getIsotope(isoId2);
        verify(daoSpy).getIsotope(isoId3);
        verify(daoSpy).getIsotope(isoId4);
    }
}
