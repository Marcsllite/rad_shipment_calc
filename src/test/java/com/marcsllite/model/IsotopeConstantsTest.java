package com.marcsllite.model;


import com.marcsllite.DBTest;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.service.DBService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

class IsotopeConstantsTest extends DBTest {
    float DEFAULT_NUM = -2.0f;
    IsotopeConstants constants;
    String DEFAULT_NAME = "Abbreviation";
    String DEFAULT_ABBR = "Abbr";
    LimitsModelId.State DEFAULT_STATE = LimitsModelId.State.SOLID;
    LimitsModelId.Form DEFAULT_FORM = LimitsModelId.Form.NORMAL;

    @BeforeEach
    public void setUp() {
        super.setUp();
        constants = new IsotopeConstants() {
            @Override
            public DBService getDbService() {
                return dbService;
            }
        };
    }

    @Test
    void testConstructor() {
        constants = new IsotopeConstants();

        assertEquals(DEFAULT_NUM, IsotopeConstants.getDefaultVal(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getA1(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getA2(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getDecayConstant(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getExemptConcentration(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getExemptLimit(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getHalfLife(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getIaLimitedLimit(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getIaPackageLimit(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getLimitedLimit(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getCurieReportQuan(), 0.0f);
        assertEquals(DEFAULT_NUM, constants.getTeraBqReportQuan(), 0.0f);
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testDbInit() {
        IsotopeModelId isoId = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
        LimitsModelId limitsId = new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM);
        float expected = 3.14159f;
        
        when(dbService.getA1(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getA2(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getDecayConstant(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getExemptConcentration(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getExemptLimit(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getHalfLife(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getIALimited(limitsId)).thenReturn(expected);
        when(dbService.getIAPackage(limitsId)).thenReturn(expected);
        when(dbService.getLimited(limitsId)).thenReturn(expected);
        when(dbService.getCiReportQuan(isoId.getAbbr())).thenReturn(expected);
        when(dbService.getTBqReportQuan(isoId.getAbbr())).thenReturn(expected);

        constants.dbInit(isoId, limitsId);

        assertEquals(expected, constants.getA1(), 0.0f);
        assertEquals(expected, constants.getA2(), 0.0f);
        assertEquals(expected, constants.getDecayConstant(), 0.0f);
        assertEquals(expected, constants.getExemptConcentration(), 0.0f);
        assertEquals(expected, constants.getExemptLimit(), 0.0f);
        assertEquals(expected, constants.getHalfLife(), 0.0f);
        assertEquals(expected, constants.getIaLimitedLimit(), 0.0f);
        assertEquals(expected, constants.getIaPackageLimit(), 0.0f);
        assertEquals(expected, constants.getLimitedLimit(), 0.0f);
        assertEquals(expected, constants.getCurieReportQuan(), 0.0f);
        assertEquals(expected, constants.getTeraBqReportQuan(), 0.0f);
    }

    @Test
    void testEquals() {
        float val = -512F;
        IsotopeConstants constants1 = new IsotopeConstants();
        IsotopeConstants constants2 = new IsotopeConstants();
        String str = "";

        assertNotEquals(null, constants1);
        assertNotEquals(constants1, str);

        assertEquals(constants1, constants2);
        assertEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setTeraBqReportQuan(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setLimitedLimit(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setIaPackageLimit(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setIaLimitedLimit(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setHalfLife(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setExemptLimit(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setExemptConcentration(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setDecayConstant(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setA2(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setA1(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());
    }
}