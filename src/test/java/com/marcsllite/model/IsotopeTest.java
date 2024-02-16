package com.marcsllite.model;

import com.marcsllite.GUITest;
import com.marcsllite.model.db.LimitsModel;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class IsotopeTest {
    private final String DEFAULT_NAME = "Name";
    private final String DEFAULT_ABBR = "Abbr";
    Isotope iso = new Isotope(GUITest.propHandler, DEFAULT_NAME, DEFAULT_ABBR);
    private final Isotope.MassUnit DEFAULT_MASS_UNIT = Isotope.MassUnit.GRAMS;
    private final Isotope.Nature DEFAULT_NATURE = Isotope.Nature.REGULAR;
    private final LimitsModel.State DEFAULT_STATE = LimitsModel.State.SOLID;
    private final LimitsModel.Form DEFAULT_FORM = LimitsModel.Form.NORMAL;
    private final Isotope.IsoClass DEFAULT_CLASS = Isotope.IsoClass.TBD;

    @Test
    public void testInit() {
        assertEquals(DEFAULT_NAME, iso.getName());
        assertEquals(DEFAULT_ABBR, iso.getAbbr());
        assertEquals(DEFAULT_NATURE, iso.getNature());
        assertEquals(DEFAULT_STATE, iso.getState());
        assertEquals(DEFAULT_FORM, iso.getForm());
        assertEquals(DEFAULT_MASS_UNIT, iso.getMassUnit());
        assertEquals(DEFAULT_CLASS, iso.getIsoClass());
        assertNotNull(iso.getConstants());
    }
}
