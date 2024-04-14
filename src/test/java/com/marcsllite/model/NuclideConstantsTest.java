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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

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

        constants.dbInit(nuclideId, limitsId);

        assertEquals(expected, constants.getA1());
        assertEquals(expected, constants.getA2());
        assertEquals(expected, constants.getDecayConstant());
        assertEquals(expected, constants.getExemptConcentration());
        assertEquals(expected, constants.getExemptLimit());
        assertEquals(expected, constants.getHalfLife());
        assertEquals(expected, constants.getIaLimitedLimit());
        assertEquals(expected, constants.getIaPackageLimit());
        assertEquals(expected, constants.getLimitedLimit());
        assertEquals(expected, constants.getCurieReportQuan());
        assertEquals(expected, constants.getTeraBqReportQuan());
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