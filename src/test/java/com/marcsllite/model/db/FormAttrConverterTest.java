package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class FormAttrConverterTest {
    FormAttrConverter converter = new FormAttrConverter();

    @Test
    public void testConvertToDatabaseColumn_NullForm() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    public void testConvertToDatabaseColumn() {
        LimitsModelId.Form form = LimitsModelId.Form.NORMAL;
        String expected = form.getVal();
        assertEquals(expected, converter.convertToDatabaseColumn(form));
    }

    @Test
    public void testConvertToEntityAttribute_NullString() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    public void testConvertToEntityAttribute() {
        LimitsModelId.Form expected = LimitsModelId.Form.NORMAL;
        String str = expected.getVal();
        assertEquals(expected, converter.convertToEntityAttribute(str));
    }
}