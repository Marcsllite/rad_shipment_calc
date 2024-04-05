package com.marcsllite.model;

import com.marcsllite.DBTest;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.handler.PropHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IsotopeTest extends DBTest {
    private final float DEFAULT_NUM = -2.0f;
    private static final String DEFAULT_NAME = "Abbreviation";
    private static final String DEFAULT_ABBR = "Abbr";
    private static final IsotopeModelId isoId = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
    private final Isotope.MassUnit DEFAULT_MASS = Isotope.MassUnit.GRAMS;
    private final Isotope.RadUnit DEFAULT_RAD_UNIT = Isotope.RadUnit.CURIE;
    private final Isotope.Nature DEFAULT_NATURE = Isotope.Nature.REGULAR;
    private final LimitsModelId limitsId = new LimitsModelId();
    private final Isotope.IsoClass DEFAULT_CLASS = Isotope.IsoClass.TBD;
    @InjectMocks
    Isotope iso;

    @BeforeAll
    public void beforeAll() {
        System.setProperty("keepPlatformOpen", "true");
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
        System.out.println(iso.getConstants().toString());
    }

    @Test
    void testToMass() {
        String str = "";
        Isotope.MassUnit actual = Isotope.MassUnit.toMass(str);
        assertNull(actual);
        
        str = "grams";
        actual = Isotope.MassUnit.toMass(str);
        assertEquals(Isotope.MassUnit.GRAMS, actual);

        str = "GRAMS";
        actual = Isotope.MassUnit.toMass(str);
        assertEquals(Isotope.MassUnit.GRAMS, actual);
        
        str = "fake";
        actual = Isotope.MassUnit.toMass(str);
        assertNull(actual);
    }

    @Test
    void testToRadUnit() {
        String str = "";
        Isotope.RadUnit actual = Isotope.RadUnit.toRadUnit(str);
        assertNull(actual);

        str = "ci";
        actual = Isotope.RadUnit.toRadUnit(str);
        assertEquals(Isotope.RadUnit.CURIE, actual);

        str = "CI";
        actual = Isotope.RadUnit.toRadUnit(str);
        assertEquals(Isotope.RadUnit.CURIE, actual);

        str = "fake";
        actual = Isotope.RadUnit.toRadUnit(str);
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

        isotope1.setInitActivityUnit(Isotope.RadUnit.BECQUEREL);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setInitActivty(-2F);
        assertNotEquals(isotope1, isotope2);
        assertNotEquals(isotope1.hashCode(), isotope2.hashCode());

        isotope1.setMassUnit(Isotope.MassUnit.LITERS);
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
