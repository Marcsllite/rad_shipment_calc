package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ValidNuclideModelIdTest {
    @Test
    void testGetters_ValueTrimmed() {
        String exp = "expected";
        String value = "  " + exp + "   ";

        ValidNuclideModelId isoId = new ValidNuclideModelId(value, value);
        assertEquals(exp, isoId.getSymbol());
        assertEquals(exp, isoId.getMassNumber());
    }

    @Test
    void testEquals_NullObj() {
        ValidNuclideModelId isoId = new ValidNuclideModelId("", "");
        assertNotEquals(null, isoId);
    }

    @Test
    void testEquals_WrongClass() {
        ValidNuclideModelId isoId = new ValidNuclideModelId("", "");
        assertNotEquals(isoId, new Object());
    }

    @Test
    void testEquals_DiffSymbol() {
        ValidNuclideModelId isoId = new ValidNuclideModelId("", "");
        ValidNuclideModelId isoId2 = new ValidNuclideModelId("Sy", "");
        assertNotEquals(isoId, isoId2);
        assertNotSame(isoId.hashCode(), isoId2.hashCode());
    }

    @Test
    void testEquals_DiffMassNumber() {
        ValidNuclideModelId isoId = new ValidNuclideModelId("", "");
        ValidNuclideModelId isoId2 = new ValidNuclideModelId("", "MassNumber");
        assertNotEquals(isoId, isoId2);
        assertNotSame(isoId.hashCode(), isoId2.hashCode());
    }

    @Test
    void testEquals() {
        String symbol = "Sy";
        String massNumber = "MassNumber";
        ValidNuclideModelId isoId = new ValidNuclideModelId(symbol, massNumber);
        ValidNuclideModelId isoId2 = new ValidNuclideModelId(symbol, massNumber);
        assertEquals(isoId, isoId2);
        assertEquals(isoId.hashCode(), isoId2.hashCode());
    }
}
