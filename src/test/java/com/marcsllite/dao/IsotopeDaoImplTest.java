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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IsotopeDaoImplTest extends DBTest {
    @InjectMocks
    IsotopeDaoImpl dao;
    IsotopeDaoImpl daoSpy;
    @Mock
    IsotopeModel model;

    @BeforeEach
    public void setUp() {
        daoSpy = spy(dao);
    }

    @Test
    public void testGetMatchingIsotopes() {
        Query querySpy = spy(query);
        String substr = "substr";
        List<IsotopeModel> list = new ArrayList<>();
        list.add(model);

        when(em.createNamedQuery(any())).thenReturn(querySpy);
        when(querySpy.setParameter(anyString(), any())).thenReturn(querySpy);
        when(querySpy.getResultList()).thenReturn(list);

        List<IsotopeModel> ret = daoSpy.getMatchingIsotopes(substr);

        assertEquals(1, ret.size());
        assertEquals(model, ret.get(0));
        verify(querySpy).setParameter("substr", substr);
    }

    @Test
    public void testGetIsotope() {
        IsotopeModelId isoId = new IsotopeModelId("", "");

        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getIsotope(isoId));
    }

    @Test
    public void testIsotopeName() {
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
    public void testIsotopeAbbr() {
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
