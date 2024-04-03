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
import static org.mockito.Mockito.spy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IsotopeTest extends DBTest {
    private final float DEFAULT_NUM = -2.0f;
    private static final String DEFAULT_NAME = "Abbreviation";
    private static final String DEFAULT_ABBR = "Abbr";
    private static final IsotopeModelId isoId = new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR);
    private final Isotope.Mass DEFAULT_MASS = Isotope.Mass.GRAMS;
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
        Isotope.Mass actual = Isotope.Mass.toMass(str);
        assertNull(actual);
        
        str = "grams";
        actual = Isotope.Mass.toMass(str);
        assertEquals(Isotope.Mass.GRAMS, actual);

        str = "GRAMS";
        actual = Isotope.Mass.toMass(str);
        assertEquals(Isotope.Mass.GRAMS, actual);
        
        str = "fake";
        actual = Isotope.Mass.toMass(str);
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
}
