package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.model.db.NuclideModelId;
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

class NuclideDaoImplTest extends DBTest {
    @InjectMocks
    NuclideDaoImpl dao;
    NuclideDaoImpl daoSpy;
    @Mock
    NuclideModel model;

    @BeforeEach
    public void setUp() {
        super.setUp();
        daoSpy = spy(dao);
        when(emHandler.getEntityManager()).thenReturn(em);
        when(em.isOpen()).thenReturn(true);
    }

    @Test
    void testGetAllValidIsotopes() {
        Query querySpy = spy(query);
        List<NuclideModel> list = new ArrayList<>();
        list.add(model);

        when(em.createQuery(anyString())).thenReturn(querySpy);
        when(querySpy.getResultList()).thenReturn(list);

        List<NuclideModel> ret = daoSpy.getAllNuclides();

        assertEquals(1, ret.size());
        assertEquals(model, ret.get(0));
    }

    @Test
    void testGetValidIsotope_NoResults() {
        NuclideModelId isoId = new NuclideModelId("", "");

        when(em.find(any(), any())).thenReturn(null);

        assertNull(dao.getNuclide(isoId));
    }

    @Test
    void testGetValidIsotope() {
        NuclideModelId isoId = new NuclideModelId("", "");

        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getNuclide(isoId));
    }

    @Test
    void testGetValidIsotopesNameNotation_NoResults() {
        NuclideModelId isoId = new NuclideModelId("", "");
        String exp = "";

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getNuclideNameNotation(isoId));

        verify(daoSpy).getNuclide(isoId);
    }

    @Test
    void testGetValidIsotopesNameNotation() {
        NuclideModelId isoId1 = new NuclideModelId(null, null);
        NuclideModelId isoId2 = new NuclideModelId(null, "Num2");
        NuclideModelId isoId3 = new NuclideModelId("AA", null);
        NuclideModelId isoId4 = new NuclideModelId("BB", "Num4");

        NuclideModel model1 = new NuclideModel(1, "name1", isoId1);
        NuclideModel model2 = new NuclideModel(2, "name2", isoId2);
        NuclideModel model3 = new NuclideModel(3, "name3", isoId3);
        NuclideModel model4 = new NuclideModel(4, "name4", isoId4);

        when(em.find(any(), any())).thenReturn(model1, model2, model3, model4);

        assertEquals(model1.getNameNotation(), daoSpy.getNuclideNameNotation(isoId1));
        assertEquals(model2.getNameNotation(), daoSpy.getNuclideNameNotation(isoId2));
        assertEquals(model3.getNameNotation(), daoSpy.getNuclideNameNotation(isoId3));
        assertEquals(model4.getNameNotation(), daoSpy.getNuclideNameNotation(isoId4));

        verify(daoSpy).getNuclide(isoId1);
        verify(daoSpy).getNuclide(isoId2);
        verify(daoSpy).getNuclide(isoId3);
        verify(daoSpy).getNuclide(isoId4);
    }

    @Test
    void testGetValidIsotopesAbbrNotation_NoResults() {
        NuclideModelId isoId = new NuclideModelId("", "");
        String exp = "";

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getNuclideAbbrNotation(isoId));

        verify(daoSpy).getNuclide(isoId);
    }

    @Test
    void testGetValidIsotopesAbbrNotation() {
        NuclideModelId isoId1 = new NuclideModelId(null, null);
        NuclideModelId isoId2 = new NuclideModelId(null, "Num2");
        NuclideModelId isoId3 = new NuclideModelId("AA", null);
        NuclideModelId isoId4 = new NuclideModelId("BB", "Num4");

        NuclideModel model1 = new NuclideModel(1, "name1", isoId1);
        NuclideModel model2 = new NuclideModel(2, "name2", isoId2);
        NuclideModel model3 = new NuclideModel(3, "name3", isoId3);
        NuclideModel model4 = new NuclideModel(4, "name4", isoId4);

        when(em.find(any(), any())).thenReturn(model1, model2, model3, model4);

        assertEquals(model1.getAbbrNotation(), daoSpy.getNuclideAbbrNotation(isoId1));
        assertEquals(model2.getAbbrNotation(), daoSpy.getNuclideAbbrNotation(isoId2));
        assertEquals(model3.getAbbrNotation(), daoSpy.getNuclideAbbrNotation(isoId3));
        assertEquals(model4.getAbbrNotation(), daoSpy.getNuclideAbbrNotation(isoId4));

        verify(daoSpy).getNuclide(isoId1);
        verify(daoSpy).getNuclide(isoId2);
        verify(daoSpy).getNuclide(isoId3);
        verify(daoSpy).getNuclide(isoId4);
    }
}
