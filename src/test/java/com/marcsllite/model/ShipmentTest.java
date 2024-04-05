package com.marcsllite.model;

import com.marcsllite.model.db.LimitsModelId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

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
        assertEquals(-1F, shipment.getMass());
        assertEquals(Isotope.MassUnit.GRAMS, shipment.getMassUnit());
        assertEquals(Isotope.Nature.REGULAR, shipment.getNature());
        assertEquals(LimitsModelId.State.SOLID, shipment.getState());
        assertEquals(LimitsModelId.Form.NORMAL, shipment.getForm());
        assertNotNull(shipment.getIsotopes());
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
        float exp = -1F;
        shipment.setMass(exp);
        assertEquals(exp, shipment.getMass());
    }

    @Test
    void testSetMassUnit_Null() {
        shipment.setMassUnit(null);
        assertEquals(Isotope.MassUnit.GRAMS, shipment.getMassUnit());
    }

    @Test
    void testSetMassUnit() {
        Isotope.MassUnit exp = Isotope.MassUnit.LITERS;
        shipment.setMassUnit(exp);
        assertEquals(exp, shipment.getMassUnit());
    }

    @Test
    void testSetNatureUnit_Null() {
        shipment.setNature(null);
        assertEquals(Isotope.Nature.REGULAR, shipment.getNature());
    }

    @Test
    void testSetNatureUnit() {
        Isotope.Nature exp = Isotope.Nature.ARTICLE;
        shipment.setNature(exp);
        assertEquals(exp, shipment.getNature());
    }

    @Test
    void testSetStateUnit_Null() {
        shipment.setState(null);
        assertEquals(LimitsModelId.State.SOLID, shipment.getState());
    }

    @Test
    void testSetStateUnit() {
        LimitsModelId.State exp = LimitsModelId.State.GAS;
        shipment.setState(exp);
        assertEquals(exp, shipment.getState());
    }

    @Test
    void testSetFormUnit_Null() {
        shipment.setForm(null);
        assertEquals(LimitsModelId.Form.NORMAL, shipment.getForm());
    }

    @Test
    void testSetFormUnit() {
        LimitsModelId.Form exp = LimitsModelId.Form.SPECIAL;
        shipment.setForm(exp);
        assertEquals(exp, shipment.getForm());
    }

    @Test
    void testSetIsotopesUnit_Null() {
        shipment.setIsotopes(null);
        assertNotNull(shipment.getIsotopes());
    }

    @Test
    void testSetIsotopesUnit() {
        Isotope isotope = mock(Isotope.class);
        List<Isotope> exp = new ArrayList<>();
        exp.add(isotope);
        shipment.setIsotopes(exp);
        assertEquals(exp, shipment.getIsotopes());
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

        shipmentA.setIsotopes(new ArrayList<>());
        assertEquals(shipmentA, shipmentB);
        assertEquals(shipmentA.hashCode(), shipmentB.hashCode());

        Isotope isotope = mock(Isotope.class);
        shipmentA.setIsotopes(Collections.singletonList(isotope));
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setForm(LimitsModelId.Form.SPECIAL);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setState(LimitsModelId.State.LIQUID);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setNature(Isotope.Nature.INSTRUMENT);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setMassUnit(Isotope.MassUnit.LITERS);
        assertNotEquals(shipmentA, shipmentB);
        assertNotEquals(shipmentA.hashCode(), shipmentB.hashCode());

        shipmentA.setMass(-2F);
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