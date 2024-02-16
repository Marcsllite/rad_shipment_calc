package com.marcsllite.model;


import com.marcsllite.GUITest;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class IsotopeConstantsTest {
    IsotopeConstants constants = new IsotopeConstants(GUITest.propHandler);
    private final float DEFAULT_INT = -2.0f;
    
    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_INT, constants.getDefaultVal(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getA1(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getA2(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getDecayConstant(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getExemptConcentration(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getExemptLimit(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getHalfLife(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getIaLimitedLimit(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getIaPackageLimit(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getLimitedLimit(), 0.0f);
        assertEquals(DEFAULT_INT, constants.getReportableQuan(), 0.0f);
    }
}