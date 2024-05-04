package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@SuppressWarnings("AssertBetweenInconvertibleTypes")
@ExtendWith(MockitoExtension.class)
class ShipmentTest {
    Shipment shipment;

    @BeforeEach
    public void setUp() {
        shipment = spy(new Shipment());
    }

    @Test
    void testConstructor() {
        assertEquals(-1L, shipment.getId());
        assertNotNull(shipment.getRefDate());
        assertEquals(RadBigDecimal.NEG_INFINITY_OBJ, shipment.getMass());
        assertEquals(Conversions.MassUnit.GRAMS, shipment.getMassUnit());
        assertEquals(Nuclide.Nature.REGULAR, shipment.getNature());
        assertEquals(LimitsModelId.State.SOLID, shipment.getLimitsId().getState());
        assertEquals(LimitsModelId.Form.NORMAL, shipment.getLimitsId().getForm());
        assertNotNull(shipment.getNuclides());
    }

    @Test
    void testSetId() {
        long exp = -1L;
        shipment.setId(exp);
        assertEquals(exp, shipment.getId());
    }

    @Test
    void testSetRefDate_Null() {
        shipment.setRefDate(null);
        LocalDate actual = shipment.getRefDate();
        assertNotNull(actual);
    }

    @Test
    void testSetRefDate() {
        LocalDate exp = LocalDate.now();
        shipment.setRefDate(exp);
        assertEquals(exp, shipment.getRefDate());
    }

    @Test
    void testSetMass() {
        RadBigDecimal exp = RadBigDecimal.valueOf(-1F);
        shipment.setMassStr(exp.toDisplayString());
        assertEquals(exp, shipment.getMass());
    }

    @Test
    void testSetMassUnit_Null() {
        shipment.setMassUnit(null);
        assertEquals(Conversions.MassUnit.GRAMS, shipment.getMassUnit());
    }

    @Test
    void testSetMassUnit() {
        Conversions.MassUnit exp = Conversions.MassUnit.LITERS;
        shipment.setMassUnit(exp);
        assertEquals(exp, shipment.getMassUnit());
    }

    @Test
    void testSetNatureUnit_Null() {
        shipment.setNature(null);
        assertEquals(Nuclide.Nature.REGULAR, shipment.getNature());
    }

    @Test
    void testSetNatureUnit() {
        Nuclide.Nature exp = Nuclide.Nature.ARTICLE;
        shipment.setNature(exp);
        assertEquals(exp, shipment.getNature());
    }

    @Test
    void testSetNuclidesUnit_Null() {
        shipment.setNuclides(null);
        assertNotNull(shipment.getNuclides());
    }

    @Test
    void testSetNuclidesUnit() {
        Nuclide isotope = mock(Nuclide.class);
        ObservableList<Nuclide> exp = new ObservableListWrapper<>(new ArrayList<>());
        exp.add(isotope);
        shipment.setNuclides(exp);
        assertEquals(exp, shipment.getNuclides());
    }

    @Test
    void testEquals() {
        Shipment shipmentA = new Shipment();
        Shipment shipmentB = new Shipment();
        String str = "";

        assertNotEquals(null, shipmentA);
        assertNotEquals(shipmentA, str);

        assertEquals(shipmentA, shipmentB);
        assertEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setNuclides(new ObservableListWrapper<>(new ArrayList<>()));
        assertEquals(shipmentA, shipmentB);
        assertEquals(shipmentA.hashCode(), shipmentB.hashCode());

        Nuclide isotope = mock(Nuclide.class);
        shipmentA.setNuclides(new ObservableListWrapper<>(List.of(isotope)));
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.SPECIAL);
        shipmentA.setLimitsId(limitsId);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        limitsId = new LimitsModelId(LimitsModelId.State.LIQUID, LimitsModelId.Form.NORMAL);
        shipmentA.setLimitsId(limitsId);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setNature(Nuclide.Nature.INSTRUMENT);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setMassUnit(Conversions.MassUnit.LITERS);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setMassStr(RadBigDecimal.valueOf(-2F).toDisplayString());
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setRefDate(LocalDate.MIN);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setId(-2L);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());
    }
}