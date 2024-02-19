package com.marcsllite.model;

import com.marcsllite.GUITest;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class IsotopeTest {
    private final String DEFAULT_NAME = "Name";
    private final String DEFAULT_ABBR = "Abbr";
    Isotope iso = new Isotope(GUITest.propHandler, new IsotopeModelId(DEFAULT_NAME, DEFAULT_ABBR));
    private final Isotope.Mass DEFAULT_MASS = Isotope.Mass.GRAMS;
    private final Isotope.RadUnit DEFAULT_RAD_UNIT = Isotope.RadUnit.CURIE;
    private final Isotope.Nature DEFAULT_NATURE = Isotope.Nature.REGULAR;
    private final LimitsModelId.State DEFAULT_STATE = LimitsModelId.State.SOLID;
    private final LimitsModelId.Form DEFAULT_FORM = LimitsModelId.Form.NORMAL;
    private final Isotope.IsoClass DEFAULT_CLASS = Isotope.IsoClass.TBD;

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
