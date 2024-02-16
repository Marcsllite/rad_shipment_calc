package com.marcsllite.model;


import com.marcsllite.GUITest;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class IsotopeConstantsTest {
    IsotopeConstants constants = new IsotopeConstants(GUITest.propHandler);
    private final float DEFAULT_INT = -2.0f;
    
    @Test
    public void testConstructor_PropHandler() {
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

    @Test
    public void testConstructor_WithValues() {
        float val = 1.5f;
        
        constants = new IsotopeConstants(GUITest.propHandler,
            val,
            val,
            val,
            val,
            val,
            val,
            val,
            val,
            val,
            val);
        
        assertEquals(DEFAULT_INT, constants.getDefaultVal(), 0.0f);
        assertEquals(val, constants.getA1(), 0.0f);
        assertEquals(val, constants.getA2(), 0.0f);
        assertEquals(val, constants.getDecayConstant(), 0.0f);
        assertEquals(val, constants.getExemptConcentration(), 0.0f);
        assertEquals(val, constants.getExemptLimit(), 0.0f);
        assertEquals(val, constants.getHalfLife(), 0.0f);
        assertEquals(val, constants.getIaLimitedLimit(), 0.0f);
        assertEquals(val, constants.getIaPackageLimit(), 0.0f);
        assertEquals(val, constants.getLimitedLimit(), 0.0f);
        assertEquals(val, constants.getReportableQuan(), 0.0f);
    }
}