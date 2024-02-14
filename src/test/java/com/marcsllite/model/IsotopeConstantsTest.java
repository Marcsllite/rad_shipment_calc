package com.marcsllite.model;


import com.marcsllite.GUITest;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class IsotopeConstantsTest {
    IsotopeConstants constants = new IsotopeConstants(GUITest.propHandler);

    @Test
    public void testConstructor() {
        float expected = -2.0f;
        assertEquals(expected, constants.defaultVal, 0.0f);
        assertEquals(expected, constants.a1.get(), 0.0f);
        assertEquals(expected, constants.a2.get(), 0.0f);
        assertEquals(expected, constants.decayConstant.get(), 0.0f);
        assertEquals(expected, constants.exemptConcentration.get(), 0.0f);
        assertEquals(expected, constants.exemptLimit.get(), 0.0f);
        assertEquals(expected, constants.halfLife.get(), 0.0f);
        assertEquals(expected, constants.iaLimitedMultiplier.get(), 0.0f);
        assertEquals(expected, constants.licenseLimit.get(), 0.0f);
        assertEquals(expected, constants.limitedLimit.get(), 0.0f);
        assertEquals(expected, constants.reportableQuan.get(), 0.0f);
    }
}