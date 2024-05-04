package com.marcsllite.model;


import com.marcsllite.DBTest;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.service.DBService;
import com.marcsllite.util.RadBigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("AssertBetweenInconvertibleTypes")
class NuclideConstantsTest extends DBTest {
    RadBigDecimal DEFAULT_NUM = RadBigDecimal.NEG_INFINITY_OBJ;
    NuclideConstants constants;
    private final String DEFAULT_SYMBOL = "Sy";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final NuclideModelId DEFAULT_ID = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);
    LimitsModelId.State DEFAULT_STATE = LimitsModelId.State.SOLID;
    LimitsModelId.Form DEFAULT_FORM = LimitsModelId.Form.NORMAL;

    @BeforeEach
    public void setUp() {
        super.setUp();
        constants = new NuclideConstants() {
            @Override
            public DBService getDbService() {
                return dbService;
            }
        };
    }

    @Test
    void testConstructor() {
        constants = new NuclideConstants();

        assertEquals(DEFAULT_NUM, constants.getA1());
        assertEquals(DEFAULT_NUM, constants.getA2());
        assertEquals(DEFAULT_NUM, constants.getDecayConstant());
        assertEquals(DEFAULT_NUM, constants.getExemptConcentration());
        assertEquals(DEFAULT_NUM, constants.getExemptLimit());
        assertEquals(DEFAULT_NUM, constants.getHalfLife());
        assertEquals(DEFAULT_NUM, constants.getIaLimitedLimit());
        assertEquals(DEFAULT_NUM, constants.getIaPackageLimit());
        assertEquals(DEFAULT_NUM, constants.getLimitedLimit());
        assertEquals(DEFAULT_NUM, constants.getCurieReportQuan());
        assertEquals(DEFAULT_NUM, constants.getTeraBqReportQuan());
    }

    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    void testDbInit() {
        NuclideModelId nuclideId = DEFAULT_ID;
        LimitsModelId limitsId = new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM);
        RadBigDecimal expected = RadBigDecimal.valueOf(3.14159f);
        String expectedStr = expected.toDisplayString();
        NuclideConstants constantsSpy = spy(constants);
        
        when(dbService.getA1(nuclideId)).thenReturn(expectedStr);
        when(dbService.getA2(nuclideId)).thenReturn(expectedStr);
        when(dbService.getDecayConstant(nuclideId)).thenReturn(expectedStr);
        when(dbService.getExemptConcentration(nuclideId)).thenReturn(expectedStr);
        when(dbService.getExemptLimit(nuclideId)).thenReturn(expectedStr);
        when(dbService.getHalfLife(nuclideId)).thenReturn(expectedStr);
        when(dbService.getIALimited(limitsId)).thenReturn(expectedStr);
        when(dbService.getIAPackage(limitsId)).thenReturn(expectedStr);
        when(dbService.getLimited(limitsId)).thenReturn(expectedStr);
        when(dbService.getCiReportQuan(nuclideId)).thenReturn(expectedStr);
        when(dbService.getTBqReportQuan(nuclideId)).thenReturn(expectedStr);

        assertFalse(constantsSpy.isInit());
        constantsSpy.dbInit(nuclideId, limitsId);
        constantsSpy.dbInit(nuclideId, limitsId);
        assertTrue(constantsSpy.isInit());
        
        assertEquals(expected, constantsSpy.getA1());
        assertEquals(expected, constantsSpy.getA2());
        assertEquals(expected, constantsSpy.getDecayConstant());
        assertEquals(expected, constantsSpy.getExemptConcentration());
        assertEquals(expected, constantsSpy.getExemptLimit());
        assertEquals(expected, constantsSpy.getHalfLife());
        assertEquals(expected, constantsSpy.getIaLimitedLimit());
        assertEquals(expected, constantsSpy.getIaPackageLimit());
        assertEquals(expected, constantsSpy.getLimitedLimit());
        assertEquals(expected, constantsSpy.getCurieReportQuan());
        assertEquals(expected, constantsSpy.getTeraBqReportQuan());
        
        verify(constantsSpy).setA1Str(anyString());
        verify(constantsSpy).setA2Str(anyString());
        verify(constantsSpy).setDecayConstantStr(anyString());
        verify(constantsSpy).setExemptConcentrationStr(anyString());
        verify(constantsSpy).setExemptLimitStr(anyString());
        verify(constantsSpy).setHalfLifeStr(anyString());
        verify(constantsSpy).setIaLimitedLimitStr(anyString());
        verify(constantsSpy).setIaPackageLimitStr(anyString());
        verify(constantsSpy).setLimitedLimitStr(anyString());
        verify(constantsSpy).setCurieReportQuanStr(anyString());
        verify(constantsSpy).setTeraBqReportQuanStr(anyString());
    }

    @Test
    void testEquals() {
        String val = RadBigDecimal.valueOf(-512F).toDisplayString();
        NuclideConstants constants1 = new NuclideConstants();
        NuclideConstants constants2 = new NuclideConstants();
        String str = "";

        assertNotEquals(null, constants1);
        assertNotEquals(constants1, str);

        assertEquals(constants1, constants2);
        assertEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setTeraBqReportQuanStr(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setLimitedLimitStr(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setIaPackageLimitStr(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setIaLimitedLimitStr(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setHalfLifeStr(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setExemptLimitStr(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setExemptConcentrationStr(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setDecayConstantStr(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setA2Str(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());

        constants1.setA1Str(val);
        assertNotEquals(constants1, constants2);
        assertNotEquals(constants1.hashCode(), constants2.hashCode());
    }
}