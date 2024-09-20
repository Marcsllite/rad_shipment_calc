package com.marcsllite.model;

import com.marcsllite.DBTest;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import com.marcsllite.util.handler.StageHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@SuppressWarnings("AssertBetweenInconvertibleTypes")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NuclideTest extends DBTest {
    private final RadBigDecimal DEFAULT_NUM = RadBigDecimal.NEG_INFINITY_OBJ;
    private final int DEFAULT_ATOMIC_NUMBER = 1;
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
        System.setProperty(StageHandler.KEEP_PLATFORM_OPEN_PROPERTY, "true");
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        iso = spy(new Nuclide(DEFAULT_ATOMIC_NUMBER, DEFAULT_NAME, DEFAULT_ID));
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
    void testSetName_Null() {
        iso.setName(null);
        assertEquals("", iso.getName());
    }

    @Test
    void testSetName() {
        String exp = "Name";
        iso.setName(exp);
        assertEquals(exp, iso.getName());
    }

    @Test
    void testSetMassStr() {
        RadBigDecimal exp = RadBigDecimal.valueOf(5F);
        iso.setMassStr(exp.toDisplayString());
        assertEquals(exp, iso.getMass());
    }

    @Test
    void testSetInitActivityStr() {
        RadBigDecimal exp = RadBigDecimal.valueOf(94F);
        iso.setInitActivityStr(exp.toDisplayString());
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
        Nuclide nuclide1 = new Nuclide();
        nuclide1.setNuclideId(DEFAULT_ID);
        Nuclide nuclide2 = new Nuclide();
        nuclide2.setNuclideId(DEFAULT_ID);

        String str = "";

        assertNotEquals(null, nuclide1);
        assertNotEquals(nuclide1, str);

        assertEquals(nuclide1.hashCode(), nuclide1.hashCode());
        assertEquals(nuclide1, nuclide1);

        LimitsModelId limitsId = new LimitsModelId(LimitsModelId.State.LIQUID, LimitsModelId.Form.NORMAL);
        nuclide1.setLimitsId(limitsId);
        assertNotEquals(nuclide1, nuclide2);
        assertNotEquals(nuclide1.hashCode(), nuclide2.hashCode());

        limitsId = new LimitsModelId(LimitsModelId.State.SOLID, LimitsModelId.Form.SPECIAL);
        nuclide1.setLimitsId(limitsId);
        assertNotEquals(nuclide1, nuclide2);
        assertNotEquals(nuclide1.hashCode(), nuclide2.hashCode());

        nuclide1.setNature(Nuclide.Nature.INSTRUMENT);
        assertNotEquals(nuclide1, nuclide2);
        assertNotEquals(nuclide1.hashCode(), nuclide2.hashCode());

        nuclide1.setInitActivityUnit(Conversions.RadUnit.BECQUEREL);
        assertNotEquals(nuclide1, nuclide2);
        assertNotEquals(nuclide1.hashCode(), nuclide2.hashCode());

        nuclide1.setInitActivityStr(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING);
        assertNotEquals(nuclide1, nuclide2);
        assertNotEquals(nuclide1.hashCode(), nuclide2.hashCode());

        nuclide1.setMassUnit(Conversions.MassUnit.LITERS);
        assertNotEquals(nuclide1, nuclide2);
        assertNotEquals(nuclide1.hashCode(), nuclide2.hashCode());

        nuclide1.setMassStr(RadBigDecimal.NEG_INFINITY_DISPLAY_STRING);
        assertNotEquals(nuclide1, nuclide2);
        assertNotEquals(nuclide1.hashCode(), nuclide2.hashCode());

        nuclide1.setNuclideClass(Nuclide.NuclideClass.TYPE_B_HIGHWAY);
        assertNotEquals(nuclide1, nuclide2);
        assertNotEquals(nuclide1.hashCode(), nuclide2.hashCode());

        DEFAULT_ID = new NuclideModelId("fake", "fke");
        nuclide1.setNuclideId(DEFAULT_ID);
        assertNotEquals(nuclide1, nuclide2);
        assertNotEquals(nuclide1.hashCode(), nuclide2.hashCode());
    }
}
