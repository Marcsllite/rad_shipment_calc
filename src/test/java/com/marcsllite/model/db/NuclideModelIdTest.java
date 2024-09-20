package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NuclideModelIdTest {
    @Test
    void testGetters_ValueTrimmed() {
        String exp = "expected";
        String value = "  " + exp + "   ";

        NuclideModelId isoId = new NuclideModelId(value, value);
        assertEquals(exp, isoId.getSymbol());
        assertEquals(exp, isoId.getMassNumber());
    }

    @Test
    void testEquals_NullObj() {
        NuclideModelId isoId = new NuclideModelId("", "");
        assertNotEquals(null, isoId);
    }

    @Test
    void testEquals_WrongClass() {
        NuclideModelId isoId = new NuclideModelId("", "");
        assertNotEquals(isoId, new Object());
    }

    @Test
    void testEquals_DiffSymbol() {
        NuclideModelId isoId = new NuclideModelId("", "");
        NuclideModelId isoId2 = new NuclideModelId("Sy", "");
        assertNotEquals(isoId, isoId2);
        assertNotSame(isoId.hashCode(), isoId2.hashCode());
    }

    @Test
    void testEquals_DiffMassNumber() {
        NuclideModelId isoId = new NuclideModelId("", "");
        NuclideModelId isoId2 = new NuclideModelId("", "MassNumber");
        assertNotEquals(isoId, isoId2);
        assertNotSame(isoId.hashCode(), isoId2.hashCode());
    }

    @Test
    void testEquals() {
        String symbol = "Sy";
        String massNumber = "MassNumber";
        NuclideModelId isoId = new NuclideModelId(symbol, massNumber);
        NuclideModelId isoId2 = new NuclideModelId(symbol, massNumber);
        assertEquals(isoId, isoId2);
        assertEquals(isoId.hashCode(), isoId2.hashCode());
    }
}
