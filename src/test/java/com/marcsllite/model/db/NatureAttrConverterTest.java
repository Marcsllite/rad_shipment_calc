package com.marcsllite.model.db;

import com.marcsllite.model.Nuclide;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class NatureAttrConverterTest {
    final NatureAttrConverter converter = new NatureAttrConverter();

    @Test
    void testConvertToDatabaseColumn_NullNature() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void testConvertToDatabaseColumn() {
        Nuclide.Nature form = Nuclide.Nature.REGULAR;
        String expected = form.getVal().toLowerCase();
        assertEquals(expected, converter.convertToDatabaseColumn(form));
    }

    @Test
    void testConvertToEntityAttribute_NullString() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void testConvertToEntityAttribute() {
        Nuclide.Nature expected = Nuclide.Nature.REGULAR;
        String str = expected.getVal();
        assertEquals(expected, converter.convertToEntityAttribute(str));
    }
}