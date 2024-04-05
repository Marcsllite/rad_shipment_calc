package com.marcsllite.model.db;

import com.marcsllite.model.Isotope;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class NatureAttrConverterTest {
    NatureAttrConverter converter = new NatureAttrConverter();

    @Test
    void testConvertToDatabaseColumn_NullNature() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void testConvertToDatabaseColumn() {
        Isotope.Nature form = Isotope.Nature.REGULAR;
        String expected = form.getVal().toLowerCase();
        assertEquals(expected, converter.convertToDatabaseColumn(form));
    }

    @Test
    void testConvertToEntityAttribute_NullString() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void testConvertToEntityAttribute() {
        Isotope.Nature expected = Isotope.Nature.REGULAR;
        String str = expected.getVal();
        assertEquals(expected, converter.convertToEntityAttribute(str));
    }
}