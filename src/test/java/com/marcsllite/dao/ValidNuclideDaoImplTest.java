package com.marcsllite.dao;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.ValidNuclideModel;
import com.marcsllite.model.db.ValidNuclideModelId;
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

class ValidNuclideDaoImplTest extends DBTest {
    @InjectMocks
    ValidNuclideDaoImpl dao;
    ValidNuclideDaoImpl daoSpy;
    @Mock
    ValidNuclideModel model;

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
        List<ValidNuclideModel> list = new ArrayList<>();
        list.add(model);

        when(em.createQuery(anyString())).thenReturn(querySpy);
        when(querySpy.getResultList()).thenReturn(list);

        List<ValidNuclideModel> ret = daoSpy.getAllValidIsotopes();

        assertEquals(1, ret.size());
        assertEquals(model, ret.get(0));
    }

    @Test
    void testGetValidIsotope_NoResults() {
        ValidNuclideModelId isoId = new ValidNuclideModelId("", "");

        when(em.find(any(), any())).thenReturn(null);

        assertNull(dao.getValidIsotope(isoId));
    }

    @Test
    void testGetValidIsotope() {
        ValidNuclideModelId isoId = new ValidNuclideModelId("", "");

        when(em.find(any(), any())).thenReturn(model);

        assertEquals(model, dao.getValidIsotope(isoId));
    }

    @Test
    void testGetValidIsotopesNameNotation_NoResults() {
        ValidNuclideModelId isoId = new ValidNuclideModelId("", "");
        String exp = "";

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getValidIsotopeNameNotation(isoId));

        verify(daoSpy).getValidIsotope(isoId);
    }

    @Test
    void testGetValidIsotopesNameNotation() {
        ValidNuclideModelId isoId1 = new ValidNuclideModelId(null, null);
        ValidNuclideModelId isoId2 = new ValidNuclideModelId(null, "Num2");
        ValidNuclideModelId isoId3 = new ValidNuclideModelId("AA", null);
        ValidNuclideModelId isoId4 = new ValidNuclideModelId("BB", "Num4");

        ValidNuclideModel model1 = new ValidNuclideModel(isoId1);
        ValidNuclideModel model2 = new ValidNuclideModel(isoId2);
        ValidNuclideModel model3 = new ValidNuclideModel(isoId3);
        ValidNuclideModel model4 = new ValidNuclideModel(isoId4);

        when(em.find(any(), any())).thenReturn(model1, model2, model3, model4);

        assertEquals(model1.getNameIsoNotation(), daoSpy.getValidIsotopeNameNotation(isoId1));
        assertEquals(model2.getNameIsoNotation(), daoSpy.getValidIsotopeNameNotation(isoId2));
        assertEquals(model3.getNameIsoNotation(), daoSpy.getValidIsotopeNameNotation(isoId3));
        assertEquals(model4.getNameIsoNotation(), daoSpy.getValidIsotopeNameNotation(isoId4));

        verify(daoSpy).getValidIsotope(isoId1);
        verify(daoSpy).getValidIsotope(isoId2);
        verify(daoSpy).getValidIsotope(isoId3);
        verify(daoSpy).getValidIsotope(isoId4);
    }

    @Test
    void testGetValidIsotopesAbbrNotation_NoResults() {
        ValidNuclideModelId isoId = new ValidNuclideModelId("", "");
        String exp = "";

        when(em.find(any(), any())).thenReturn(null);

        assertEquals(exp, daoSpy.getValidIsotopeAbbrNotation(isoId));

        verify(daoSpy).getValidIsotope(isoId);
    }

    @Test
    void testGetValidIsotopesAbbrNotation() {
        ValidNuclideModelId isoId1 = new ValidNuclideModelId(null, null);
        ValidNuclideModelId isoId2 = new ValidNuclideModelId(null, "Num2");
        ValidNuclideModelId isoId3 = new ValidNuclideModelId("AA", null);
        ValidNuclideModelId isoId4 = new ValidNuclideModelId("BB", "Num4");

        ValidNuclideModel model1 = new ValidNuclideModel(isoId1);
        ValidNuclideModel model2 = new ValidNuclideModel(isoId2);
        ValidNuclideModel model3 = new ValidNuclideModel(isoId3);
        ValidNuclideModel model4 = new ValidNuclideModel(isoId4);

        when(em.find(any(), any())).thenReturn(model1, model2, model3, model4);

        assertEquals(model1.getAbbrIsoNotation(), daoSpy.getValidIsotopeAbbrNotation(isoId1));
        assertEquals(model2.getAbbrIsoNotation(), daoSpy.getValidIsotopeAbbrNotation(isoId2));
        assertEquals(model3.getAbbrIsoNotation(), daoSpy.getValidIsotopeAbbrNotation(isoId3));
        assertEquals(model4.getAbbrIsoNotation(), daoSpy.getValidIsotopeAbbrNotation(isoId4));

        verify(daoSpy).getValidIsotope(isoId1);
        verify(daoSpy).getValidIsotope(isoId2);
        verify(daoSpy).getValidIsotope(isoId3);
        verify(daoSpy).getValidIsotope(isoId4);
    }
}
