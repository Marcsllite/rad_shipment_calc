package com.marcsllite.model.db;

import com.marcsllite.model.Nuclide;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
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
    void testSetMassStr() {
        RadBigDecimal exp = RadBigDecimal.valueOf(1.0f);
        model.setMassStr(exp.toString());
        assertEquals(exp, model.getMass());
        assertEquals(exp.toString(), model.getMassStr());
    }

    @Test
    void testSetMassUnit_Null() {
        model.setMassUnit(null);
        assertEquals(Conversions.MassUnit.GRAMS, model.getMassUnit());
    }

    @Test
    void testSetMassUnit() {
        Conversions.MassUnit exp = Conversions.MassUnit.LITERS;
        model.setMassUnit(exp);
        assertEquals(exp, model.getMassUnit());
    }

    @Test
    void testSetNatureUnit_Null() {
        model.setNature(null);
        assertEquals(Nuclide.Nature.REGULAR, model.getNature());
    }

    @Test
    void testSetNatureUnit() {
        Nuclide.Nature exp = Nuclide.Nature.ARTICLE;
        model.setNature(exp);
        assertEquals(exp, model.getNature());
    }

    @Test
    void testSetNuclidesUnit_Null() {
        model.setNuclides(null);
        assertNotNull(model.getNuclides());
    }

    @Test
    void testSetNuclidesUnit() {
        NuclideModel isotope = mock(NuclideModel.class);
        List<NuclideModel> exp = new ArrayList<>();
        exp.add(isotope);
        model.setNuclides(exp);
        assertEquals(exp, model.getNuclides());
    }

    @Test
    void testSetVersion() {
        long version = -1L;
        model.setVersion(version);
        assertEquals(version, model.getVersion());
    }
}