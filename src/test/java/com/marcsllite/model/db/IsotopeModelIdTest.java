package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IsotopeModelIdTest {
    @Test
    void testEquals_NullObj() {
        IsotopeModelId isoId = new IsotopeModelId("", "");
        assertNotEquals(null, isoId);
    }

    @Test
    void testEquals_WrongClass() {
        IsotopeModelId isoId = new IsotopeModelId("", "");
        assertNotEquals(isoId, new Object());
    }

    @Test
    void testEquals_DiffName() {
        IsotopeModelId isoId = new IsotopeModelId("", "");
        IsotopeModelId isoId2 = new IsotopeModelId("Name", "");
        assertNotEquals(isoId, isoId2);
        assertNotSame(isoId.hashCode(), isoId2.hashCode());
    }

    @Test
    void testEquals_DiffAbbr() {
        IsotopeModelId isoId = new IsotopeModelId("", "");
        IsotopeModelId isoId2 = new IsotopeModelId("", "Abbr");
        assertNotEquals(isoId, isoId2);
        assertNotSame(isoId.hashCode(), isoId2.hashCode());
    }

    @Test
    void testEquals() {
        String name = "Name";
        String abbr = "Abbr";
        IsotopeModelId isoId = new IsotopeModelId(name, abbr);
        IsotopeModelId isoId2 = new IsotopeModelId(name, abbr);
        assertEquals(isoId, isoId2);
        assertEquals(isoId.hashCode(), isoId2.hashCode());
    }
}
