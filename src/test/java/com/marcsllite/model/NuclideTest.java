package com.marcsllite.model;

import com.marcsllite.DBTest;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import com.marcsllite.util.handler.PropHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;

import java.time.LocalDate;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@SuppressWarnings("AssertBetweenInconvertibleTypes")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NuclideTest extends DBTest {
    private final RadBigDecimal DEFAULT_NUM = RadBigDecimal.NEG_INFINITY_OBJ;
    private final String DEFAULT_NAME = "Symbol";
    private final String DEFAULT_SYMBOL = "Sy";
    private final String DEFAULT_MASS_NUMBER = "1";
    private final NuclideModelId DEFAULT_ID = new NuclideModelId(DEFAULT_SYMBOL, DEFAULT_MASS_NUMBER);
    private final Conversions.MassUnit DEFAULT_MASS = Conversions.MassUnit.GRAMS;
    private final Conversions.RadUnit DEFAULT_RAD_UNIT = Conversions.RadUnit.CURIE;
    private final Nuclide.Nature DEFAULT_NATURE = Nuclide.Nature.REGULAR;
    private final LimitsModelId limitsId = new LimitsModelId();
    private final Nuclide.NuclideClass DEFAULT_CLASS = Nuclide.NuclideClass.TBD;
    @InjectMocks
    Nuclide iso;

    @BeforeAll
    public void beforeAll() {
        System.setProperty("keepPlatformOpen", "true");
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        iso = spy(new Nuclide(DEFAULT_NAME, DEFAULT_ID) {
            @Override
            public PropHandler getPropHandler() {
                return new PropHandlerTestObj();
            }
        });
    }

    @Test
    void testInit() {
        assertEquals(DEFAULT_ID, iso.getNuclideId());
        assertEquals(DEFAULT_NATURE, iso.getNature());
        assertEquals(limitsId, iso.getLimitsId());
        assertEquals(DEFAULT_MASS, iso.getMassUnit());
        assertEquals(DEFAULT_RAD_UNIT, iso.getInitActivityUnit());
        assertEquals(DEFAULT_CLASS, iso.getNuclideClass());
        assertNotNull(iso.getConstants());
        assertNotNull(iso.getRefDate());
    }

    @Test
    void testToNuclideClass() {
        String str = "";
        Nuclide.NuclideClass actual = Nuclide.NuclideClass.toNuclideClass(str);
        assertNull(actual);

        str = "Non-Radioactive";
        actual = Nuclide.NuclideClass.toNuclideClass(str);
        assertEquals(Nuclide.NuclideClass.EXEMPT, actual);

        str = "NON-RADIOACTIVE";
        actual = Nuclide.NuclideClass.toNuclideClass(str);
        assertEquals(Nuclide.NuclideClass.EXEMPT, actual);

        str = "fake";
        actual = Nuclide.NuclideClass.toNuclideClass(str);
        assertNull(actual);
    }

    @Test
    void testToNature() {
        String str = "";
        Nuclide.Nature actual = Nuclide.Nature.toNature(str);
        assertNull(actual);

        str = "regular";
        actual = Nuclide.Nature.toNature(str);
        assertEquals(Nuclide.Nature.REGULAR, actual);

        str = "REGULAR";
        actual = Nuclide.Nature.toNature(str);
        assertEquals(Nuclide.Nature.REGULAR, actual);

        str = "fake";
        actual = Nuclide.Nature.toNature(str);
        assertNull(actual);
    }

    @Test
    void testSetPropHandler_Null() {
        iso.setPropHandler(null);
        assertNotNull(iso.getPropHandler());
    }

    @Test
    void testSetPropHandler() {
        PropHandler exp = new PropHandlerTestObj();
        iso.setPropHandler(exp);
        assertEquals(exp.keySet(), iso.getPropHandler().keySet());
    }

    @Test
    void testSetConstants_Null() {
        iso.setConstants(null);
        assertNotNull(iso.getConstants());
    }

    @Test
    void testSetConstants() {
        NuclideConstants exp = mock(NuclideConstants.class);
        iso.setConstants(exp);
        assertEquals(exp, iso.getConstants());
    }

    @Test
    void testGetName_NullProp() {
        when(iso.nameProperty()).thenReturn(null);
        assertEquals("", iso.getName());
    }

    @Test
    void testSetName_Null() {
        iso.setName(null);
        assertEquals("", iso.getName());
    }

    @Test
    void testSetName_NullProp() {
        when(iso.nameProperty())
            .thenReturn(null)
            .thenCallRealMethod();
        String exp = "Name";
        iso.setName(exp);
        assertEquals(exp, iso.getName());
    }

    @Test
    void testSetName() {
        String exp = "Name";
        iso.setName(exp);
        assertEquals(exp, iso.getName());
    }

    @Test
    void testGetMassNumber_NullProp() {
        when(iso.massNumberProperty()).thenReturn(null);
        assertEquals("", iso.getMassNumber());
    }

    @Test
    void testSetMassNumber_Null() {
        iso.setMassNumber(null);
        assertEquals("", iso.getMassNumber());
    }

    @Test
    void testSetMassNumber_NullProp() {
        when(iso.massNumberProperty())
            .thenReturn(null)
            .thenCallRealMethod();
        String exp = "MassNumber";
        iso.setMassNumber(exp);
        assertEquals(exp, iso.getMassNumber());
    }

    @Test
    void testSetMassNumber() {
        String exp = "MassNumber";
        iso.setMassNumber(exp);
        assertEquals(exp, iso.getMassNumber());
    }

    @Test
    void testGetMass_NullProp() {
        when(iso.massProperty()).thenReturn(null);
        assertEquals(DEFAULT_NUM, iso.getMass());
    }

    @Test
    void testSetMass_NullProp() {
        RadBigDecimal exp = RadBigDecimal.valueOf(5F);
        when(iso.massProperty())
            .thenReturn(null)
            .thenCallRealMethod();
        iso.setMass(exp);
        assertEquals(exp, iso.getMass());
    }

    @Test
    void testSetMass() {
        RadBigDecimal exp = RadBigDecimal.valueOf(5F);
        iso.setMass(exp);
        assertEquals(exp, iso.getMass());
    }


    @Test
    void testGetInitActivity_NullProp() {
        when(iso.initActivityProperty()).thenReturn(null);
        assertEquals(DEFAULT_NUM, iso.getInitActivity());
    }

    @Test
    void testSetInitActivity_NullProp() {
        when(iso.initActivityProperty())
            .thenReturn(null)
            .thenCallRealMethod();
        RadBigDecimal exp = RadBigDecimal.valueOf(94F);
        iso.setInitActivity(exp);
        assertEquals(exp, iso.getInitActivity());
    }

    @Test
    void testSetInitActivity() {
        RadBigDecimal exp = RadBigDecimal.valueOf(94F);
        iso.setInitActivity(exp);
        assertEquals(exp, iso.getInitActivity());
    }
    
    @Test
    void testSetRefDate_Null() {
        iso.setRefDate(null);
        LocalDate actual = iso.getRefDate();
        assertNotNull(actual);
    }

    @Test
    void testSetRefDate() {
        LocalDate exp = LocalDate.now();
        iso.setRefDate(exp);
        assertEquals(exp, iso.getRefDate());
    }
    
    @Test
    void testEquals() {
        NuclideModelId DEFAULT_ID = new NuclideModelId();
        Nuclide isotope1 = mock(Nuclide.class, CALLS_REAL_METHODS);
        isotope1.setNuclideId(DEFAULT_ID);
        Nuclide isotope2 = mock(Nuclide.class, CALLS_REAL_METHODS);
        isotope2.setNuclideId(DEFAULT_ID);

        String str = "";

        assertNotEquals(null, isotope1);
        assertNotEquals(isotope1, str);

        assertEquals(isotope1.hashCode(), isotope1.hashCode());
        assertEquals(isotope1, isotope1);

        LimitsModelId limitsId = new LimitsModelId();
        isotope1.setLimitsId(limitsId);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setNature(Nuclide.Nature.INSTRUMENT);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setInitActivityUnit(Conversions.RadUnit.BECQUEREL);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setInitActivity(RadBigDecimal.NEG_INFINITY_OBJ);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setMassUnit(Conversions.MassUnit.LITERS);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setMass(RadBigDecimal.NEG_INFINITY_OBJ);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setNuclideClass(Nuclide.NuclideClass.TYPE_B_HIGHWAY);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        DEFAULT_ID = new NuclideModelId("fake", "fke");
        isotope1.setNuclideId(DEFAULT_ID);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());
    }
}
