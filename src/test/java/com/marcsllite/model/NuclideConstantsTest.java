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
import static org.mockito.ArgumentMatchers.any;
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

        assertEquals(DEFAULT_NUM, NuclideConstants.getDefaultVal());
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
        NuclideModelId nuclideId = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);
        LimitsModelId limitsId = new LimitsModelId(DEFAULT_STATE, DEFAULT_FORM);
        RadBigDecimal expected = RadBigDecimal.valueOf(3.14159f);
        NuclideConstants constantsSpy = spy(constants);
        
        when(dbService.getA1(nuclideId)).thenReturn(expected);
        when(dbService.getA2(nuclideId)).thenReturn(expected);
        when(dbService.getDecayConstant(nuclideId)).thenReturn(expected);
        when(dbService.getExemptConcentration(nuclideId)).thenReturn(expected);
        when(dbService.getExemptLimit(nuclideId)).thenReturn(expected);
        when(dbService.getHalfLife(nuclideId)).thenReturn(expected);
        when(dbService.getIALimited(limitsId)).thenReturn(expected);
        when(dbService.getIAPackage(limitsId)).thenReturn(expected);
        when(dbService.getLimited(limitsId)).thenReturn(expected);
        when(dbService.getCiReportQuan(nuclideId)).thenReturn(expected);
        when(dbService.getTBqReportQuan(nuclideId)).thenReturn(expected);

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
        
        verify(constantsSpy).setA1(any(RadBigDecimal.class));
        verify(constantsSpy).setA2(any(RadBigDecimal.class));
        verify(constantsSpy).setDecayConstant(any(RadBigDecimal.class));
        verify(constantsSpy).setExemptConcentration(any(RadBigDecimal.class));
        verify(constantsSpy).setExemptLimit(any(RadBigDecimal.class));
        verify(constantsSpy).setHalfLife(any(RadBigDecimal.class));
        verify(constantsSpy).setIaLimitedLimit(any(RadBigDecimal.class));
        verify(constantsSpy).setIaPackageLimit(any(RadBigDecimal.class));
        verify(constantsSpy).setLimitedLimit(any(RadBigDecimal.class));
        verify(constantsSpy).setCurieReportQuan(any(RadBigDecimal.class));
        verify(constantsSpy).setTeraBqReportQuan(any(RadBigDecimal.class));
    }

    @Test
    void testEquals() {
        RadBigDecimal val = RadBigDecimal.valueOf(-512F);
        NuclideConstants constants1 = new NuclideConstants();
        NuclideConstants constants2 = new NuclideConstants();
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