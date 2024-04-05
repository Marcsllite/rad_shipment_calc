package com.marcsllite.model.db;

import com.marcsllite.model.Isotope;
import com.marcsllite.util.Conversions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ShipmentModelTest {
    ShipmentModel model;

    @BeforeEach
    public void setUp() {
        model = new ShipmentModel();
    }

    @Test
    void testConstructor() {
        assertEquals(Conversions.SIPrefix.BASE, model.getBasePrefix());
    }

    @Test
    void testSetId() {
        long exp = -1L;
        model.setId(exp);
        assertEquals(exp, model.getId());
    }

    @Test
    void testSetRefDate_Null() {
        model.setRefDate(null);
        LocalDate actual = model.getRefDate();
        assertNotNull(actual);
    }

    @Test
    void testSetRefDate() {
        LocalDate exp = LocalDate.now();
        model.setRefDate(exp);
        assertEquals(exp, model.getRefDate());
    }

    @Test
    void testSetMass() {
        float exp = -1F;
        model.setMass(exp);
        assertEquals(exp, model.getMass());
    }

    @Test
    void testSetMassUnit_Null() {
        model.setMassUnit(null);
        assertEquals(Isotope.MassUnit.GRAMS, model.getMassUnit());
    }

    @Test
    void testSetMassUnit() {
        Isotope.MassUnit exp = Isotope.MassUnit.LITERS;
        model.setMassUnit(exp);
        assertEquals(exp, model.getMassUnit());
    }

    @Test
    void testSetNatureUnit_Null() {
        model.setNature(null);
        assertEquals(Isotope.Nature.REGULAR, model.getNature());
    }

    @Test
    void testSetNatureUnit() {
        Isotope.Nature exp = Isotope.Nature.ARTICLE;
        model.setNature(exp);
        assertEquals(exp, model.getNature());
    }

    @Test
    void testSetStateUnit_Null() {
        model.setState(null);
        assertEquals(LimitsModelId.State.SOLID, model.getState());
    }

    @Test
    void testSetStateUnit() {
        LimitsModelId.State exp = LimitsModelId.State.GAS;
        model.setState(exp);
        assertEquals(exp, model.getState());
    }

    @Test
    void testSetFormUnit_Null() {
        model.setForm(null);
        assertEquals(LimitsModelId.Form.NORMAL, model.getForm());
    }

    @Test
    void testSetFormUnit() {
        LimitsModelId.Form exp = LimitsModelId.Form.SPECIAL;
        model.setForm(exp);
        assertEquals(exp, model.getForm());
    }

    @Test
    void testSetIsotopesUnit_Null() {
        model.setIsotopes(null);
        assertNotNull(model.getIsotopes());
    }

    @Test
    void testSetIsotopesUnit() {
        IsotopeModel isotope = mock(IsotopeModel.class);
        List<IsotopeModel> exp = new ArrayList<>();
        exp.add(isotope);
        model.setIsotopes(exp);
        assertEquals(exp, model.getIsotopes());
    }

    @Test
    void testSetVersion() {
        long version = -1L;
        model.setVersion(version);
        assertEquals(version, model.getVersion());
    }
}