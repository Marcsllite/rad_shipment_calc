package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class FormAttrConverterTest {
    FormAttrConverter converter = new FormAttrConverter();

    @Test
    void testConvertToDatabaseColumn_NullForm() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void testConvertToDatabaseColumn() {
        LimitsModelId.Form form = LimitsModelId.Form.NORMAL;
        String expected = form.getVal();
        assertEquals(expected, converter.convertToDatabaseColumn(form));
    }

    @Test
    void testConvertToEntityAttribute_NullString() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void testConvertToEntityAttribute() {
        LimitsModelId.Form expected = LimitsModelId.Form.NORMAL;
        String str = expected.getVal();
        assertEquals(expected, converter.convertToEntityAttribute(str));
    }
}