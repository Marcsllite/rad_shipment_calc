package com.marcsllite.model;

import com.marcsllite.controller.GUITest;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class IsotopeTest {
    private static final String DEFAULT_NAME = "Name";
    private static final String DEFAULT_ABBR = "Abbr";
    static Isotope iso;
    private final Isotope.Mass DEFAULT_MASS = Isotope.Mass.GRAMS;
    private final Isotope.RadUnit DEFAULT_RAD_UNIT = Isotope.RadUnit.CURIE;
    private final Isotope.Nature DEFAULT_NATURE = Isotope.Nature.REGULAR;
    private final LimitsModelId.State DEFAULT_STATE = LimitsModelId.State.SOLID;
    private final LimitsModelId.Form DEFAULT_FORM = LimitsModelId.Form.NORMAL;
    private final Isotope.IsoClass DEFAULT_CLASS = Isotope.IsoClass.TBD;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("keepPlatformOpen", "true");
        iso = new Isotope(GUITest.propHandler, new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR));
    }

    @Test
    public void testInit() {
        assertEquals(DEFAULT_NAME, iso.getName());
        assertEquals(DEFAULT_ABBR, iso.getAbbr());
        assertEquals(DEFAULT_NATURE, iso.getNature());
        assertEquals(DEFAULT_STATE, iso.getState());
        assertEquals(DEFAULT_FORM, iso.getForm());
        assertEquals(DEFAULT_MASS, iso.getMass());
        assertEquals(DEFAULT_RAD_UNIT, iso.getRadUnit());
        assertEquals(DEFAULT_CLASS, iso.getIsoClass());
        assertNotNull(iso.getConstants());
        System.out.println(iso.getConstants().toString());
    }
}
