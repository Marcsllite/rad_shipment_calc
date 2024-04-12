package com.marcsllite.model;

import com.marcsllite.DBTest;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IsotopeTest extends DBTest {
    private final float DEFAULT_NUM = -1f;
    private static final String DEFAULT_NAME = "Abbreviation";
    private static final String DEFAULT_ABBR = "Abbr";
    private static final IsotopeModelId isoId = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
    private final Conversions.MassUnit DEFAULT_MASS = Conversions.MassUnit.GRAMS;
    private final Conversions.RadUnit DEFAULT_RAD_UNIT = Conversions.RadUnit.CURIE;
    private final Isotope.Nature DEFAULT_NATURE = Isotope.Nature.REGULAR;
    private final LimitsModelId limitsId = new LimitsModelId();
    private final Isotope.IsoClass DEFAULT_CLASS = Isotope.IsoClass.TBD;
    @InjectMocks
    Isotope iso;

    @BeforeAll
    public void beforeAll() {
        System.setProperty("keepPlatformOpen", "true");
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        iso = spy(new Isotope(isoId) {
            @Override
            public PropHandler getPropHandler() {
                return new PropHandlerTestObj();
            }
        });
    }

    @Test
    void testInit() {
        assertEquals(isoId, iso.getIsoId());
        assertEquals(DEFAULT_NATURE, iso.getNature());
        assertEquals(limitsId, iso.getLimitsId());
        assertEquals(DEFAULT_MASS, iso.getMassUnit());
        assertEquals(DEFAULT_RAD_UNIT, iso.getInitActivityUnit());
        assertEquals(DEFAULT_CLASS, iso.getIsoClass());
        assertNotNull(iso.getConstants());
        assertNotNull(iso.getRefDate());
    }

    @Test
    void testToMass() {
        String str = "";
        Conversions.MassUnit actual = Conversions.MassUnit.toMass(str);
        assertNull(actual);
        
        str = "grams";
        actual = Conversions.MassUnit.toMass(str);
        assertEquals(Conversions.MassUnit.GRAMS, actual);

        str = "GRAMS";
        actual = Conversions.MassUnit.toMass(str);
        assertEquals(Conversions.MassUnit.GRAMS, actual);
        
        str = "fake";
        actual = Conversions.MassUnit.toMass(str);
        assertNull(actual);
    }

    @Test
    void testToRadUnit() {
        String str = "";
        Conversions.RadUnit actual = Conversions.RadUnit.toRadUnit(str);
        assertNull(actual);

        str = "ci";
        actual = Conversions.RadUnit.toRadUnit(str);
        assertEquals(Conversions.RadUnit.CURIE, actual);

        str = "CI";
        actual = Conversions.RadUnit.toRadUnit(str);
        assertEquals(Conversions.RadUnit.CURIE, actual);

        str = "fake";
        actual = Conversions.RadUnit.toRadUnit(str);
        assertNull(actual);
    }

    @Test
    void testToIsoClass() {
        String str = "";
        Isotope.IsoClass actual = Isotope.IsoClass.toIsoClass(str);
        assertNull(actual);

        str = "exempt";
        actual = Isotope.IsoClass.toIsoClass(str);
        assertEquals(Isotope.IsoClass.EXEMPT, actual);

        str = "EXEMPT";
        actual = Isotope.IsoClass.toIsoClass(str);
        assertEquals(Isotope.IsoClass.EXEMPT, actual);

        str = "fake";
        actual = Isotope.IsoClass.toIsoClass(str);
        assertNull(actual);
    }

    @Test
    void testToNature() {
        String str = "";
        Isotope.Nature actual = Isotope.Nature.toNature(str);
        assertNull(actual);

        str = "regular";
        actual = Isotope.Nature.toNature(str);
        assertEquals(Isotope.Nature.REGULAR, actual);

        str = "REGULAR";
        actual = Isotope.Nature.toNature(str);
        assertEquals(Isotope.Nature.REGULAR, actual);

        str = "fake";
        actual = Isotope.Nature.toNature(str);
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
        IsotopeConstants exp = mock(IsotopeConstants.class);
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
    void testGetAbbr_NullProp() {
        when(iso.abbrProperty()).thenReturn(null);
        assertEquals("", iso.getAbbr());
    }

    @Test
    void testSetAbbr_Null() {
        iso.setAbbr(null);
        assertEquals("", iso.getAbbr());
    }

    @Test
    void testSetAbbr_NullProp() {
        when(iso.abbrProperty())
            .thenReturn(null)
            .thenCallRealMethod();
        String exp = "Abbr";
        iso.setAbbr(exp);
        assertEquals(exp, iso.getAbbr());
    }

    @Test
    void testSetAbbr() {
        String exp = "Abbr";
        iso.setAbbr(exp);
        assertEquals(exp, iso.getAbbr());
    }

    @Test
    void testGetMass_NullProp() {
        when(iso.massProperty()).thenReturn(null);
        assertEquals(DEFAULT_NUM, iso.getMass());
    }

    @Test
    void testSetMass_NullProp() {
        float exp = 5F;
        when(iso.massProperty())
            .thenReturn(null)
            .thenCallRealMethod();
        iso.setMass(exp);
        assertEquals(exp, iso.getMass());
    }

    @Test
    void testSetMass() {
        float exp = 5F;
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
        float exp = 94F;
        iso.setInitActivity(exp);
        assertEquals(exp, iso.getInitActivity());
    }

    @Test
    void testSetInitActivity() {
        float exp = 94F;
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
        IsotopeModelId isoId = new IsotopeModelId();
        Isotope isotope1 = mock(Isotope.class, CALLS_REAL_METHODS);
        isotope1.setIsoId(isoId);
        Isotope isotope2 = mock(Isotope.class, CALLS_REAL_METHODS);
        isotope2.setIsoId(isoId);

        String str = "";

        assertNotEquals(null, isotope1);
        assertNotEquals(isotope1, str);

        assertEquals(isotope1.hashCode(), isotope1.hashCode());
        assertEquals(isotope1, isotope1);

        LimitsModelId limitsId = new LimitsModelId();
        isotope1.setLimitsId(limitsId);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setNature(Isotope.Nature.INSTRUMENT);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setInitActivityUnit(Conversions.RadUnit.BECQUEREL);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setInitActivity(-2F);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setMassUnit(Conversions.MassUnit.LITERS);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setMass(-2F);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setIsoClass(Isotope.IsoClass.TYPE_B_HIGHWAY);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isoId = new IsotopeModelId("fake", "fke");
        isotope1.setIsoId(isoId);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());
    }
}
