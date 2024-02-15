package com.marcsllite.model;

import com.marcsllite.GUITest;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class IsotopeTest {
    Isotope iso = new Isotope(GUITest.propHandler);

    @Test
    public void testInit() {
        assertEquals(Isotope.MassUnit.GRAMS, iso.getMassUnit());
        assertNotNull(iso.getConstants());
    }
}
