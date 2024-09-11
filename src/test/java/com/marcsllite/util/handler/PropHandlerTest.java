package com.marcsllite.util.handler;

import com.marcsllite.PropHandlerTestObj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class PropHandlerTest {
    PropHandler handler;

    @BeforeEach
    public void setUp() {
        handler = spy(new PropHandlerTestObj());
    }

    @Test
    void testSetProp_Exception() {
        String str = "str";
        InputStream stream = new ByteArrayInputStream(str.getBytes());
        NullPointerException ioe = new NullPointerException();
        doThrow(ioe).when(handler).setProp(any(Properties.class));

        InvalidParameterException ipe = assertThrows(
            InvalidParameterException.class, () -> handler.setProp(stream)
        );

        assertTrue(ipe.getMessage().contains("Failed to set properties from stream"));
    }

    @Test
    void testGetOs_NullOS() {
        handler.setOs(null);
        assertNull(handler.getOS());
    }


  
    @ParameterizedTest
    @MethodSource("replacePropString_data")
    void testReplacePropString(String key, String expected, String[] replacement) {
        assertEquals(expected, handler.replacePropString(key, replacement));
    }
  
    private static Object[] replacePropString_data() {
        return new Object[] { 
            new Object[] { null, "", new String[] { null } },
            new Object[] { "", "", new String[] { "" } },
            new Object[] { "", "", new String[] { null } },
            new Object[] { "fakeKey", "", new String[] { null } },
            new Object[] { "fakeKey", "", null },
            new Object[] { "fakeKey", "", new String[] { "ONE" } },
            new Object[] { "replacePropString_noText", "", new String[] { null } },
            new Object[] { "replacePropString_noText", "", null },
            new Object[] { "replacePropString_noText", "", new String[] { "ONE" } },
            new Object[] { "replacePropString_noReplacements", "This string doesn’t contain any replacements", new String[] { null } },
            new Object[] { "replacePropString_noReplacements", "This string doesn’t contain any replacements", null },
            new Object[] { "replacePropString_noReplacements", "This string doesn’t contain any replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_oneReplacement", "This string contains null replacements", new String[] { null } },
            new Object[] { "replacePropString_oneReplacement", "This string contains {0} replacements", null },
            new Object[] { "replacePropString_oneReplacement", "This string contains ONE replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_oneReplacement", "This string contains ONE replacements", new String[] { "ONE", "TWO" } },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect {1} for replacement", new String[] { null } },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect {1} for replacement", null },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect {1} for replacement", new String[] { "ONE" } },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect TWO for replacement", new String[] { "ONE", "TWO" } },
            new Object[] { "replacePropString_oneSameReplacements", "This string contains a replacement here: ONE, and the same replacement here: ONE", new String[] { "ONE" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains null, {1} replacements", new String[] { null } },
            new Object[] { "replacePropString_twoReplacements", "This string contains {0}, {1} replacements", null },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, {1} replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, TWO replacements", new String[] { "ONE", "TWO" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, TWO replacements", new String[] { "ONE", "TWO", "THREE" } },
            new Object[] { "replacePropString_threeReplacements", "This string contains null, {1}, {2} replacements", new String[] { null } },
            new Object[] { "replacePropString_threeReplacements", "This string contains {0}, {1}, {2} replacements", null },
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, {1}, {2} replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, TWO, {2} replacements", new String[] { "ONE", "TWO" } }, 
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, TWO, THREE replacements", new String[] { "ONE", "TWO", "THREE" } },
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, TWO, THREE replacements", new String[] { "ONE", "TWO", "THREE", "FOUR" } },
            new Object[] { "replacePropString_twoSameReplacements", "First: ONE, Second: TWO, Third: ONE, Fourth: TWO", new String[] { "ONE", "TWO" } }
        };
    }
  
    @ParameterizedTest
    @MethodSource("handleGetObject_data")
    void testHandleGetObject(String propName, String expected) {
        assertEquals(expected, handler.getString(propName));
    }
  
    private static Object[] handleGetObject_data() {
        return new Object[] { 
            new Object[] { null, "" },
            new Object[] { "", "" },
            new Object[] { "fakeKey", "" },
            new Object[] { "replacePropString_noText", "" },
            new Object[] { "mainPane", "UMass Lowell - Rad Shipment Calculator" }
        };
    }

    @ParameterizedTest
    @MethodSource("getList_data")
    void testGetList(String listName, List<String> expected) {
        assertEquals(expected, handler.getList(listName));
    }

    private static Object[] getList_data() {
        return new Object[] { 
            new Object[] { null, new ArrayList<String>()},
            new Object[] { "", new ArrayList<String>()},
            new Object[] { "fakeKey", new ArrayList<String>()},
            new Object[] { "replacePropString_noText", new ArrayList<String>()},
            new Object[] { "mainPane", new ArrayList<String>() {{
                                            add("UMass Lowell - Rad Shipment Calculator"); 
                                        }}},
            new Object[] { "getList_TrailingDelimiters", new ArrayList<String>() {{
                add("This");
                add("List"); 
                add("has"); 
                add("trailing"); 
                add("delimiters");
            }}},
            new Object[] { "getList_ProperList", new ArrayList<String>() {{
                add("This");
                add("is"); 
                add("a"); 
                add("proper"); 
                add("list");
            }}},
            new Object[] { "getList_SpacesWithinElements", new ArrayList<String>() {{
                add("element 1 has spaces");
                add("element 2 does too");
            }}},
            new Object[] { "getList_SpacesAroundElements", new ArrayList<String>() {{
                add("there are spaces around this element");
                add("spaces around here too");
            }}}
        };
    }
  
    @ParameterizedTest
    @CsvSource({"fakeKey", "replacePropString_noReplacements"})
    void testGetDouble_InvalidPropName(String propName) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> handler.getDouble(propName)
        );
        assertTrue(exception.getMessage().contains("is not a number"));
    }
  
    @ParameterizedTest
    @MethodSource("getDouble_data")
    void testGetDouble(String propName, double expected) {
        assertEquals(expected, handler.getDouble(propName));
    }
  
    private static Object[] getDouble_data() {
        return new Object[] { 
            new Object[] { null, Double.MIN_VALUE },
            new Object[] { "", Double.MIN_VALUE },
            new Object[] { "mainWidth", 600.0 }
        };
    }
  
    @ParameterizedTest
    @MethodSource("getFileText_data")
    void testGetFileText(String file, String expected) {
        assertEquals(expected, handler.getFileText(file));
    }
  
    private static Object[] getFileText_data() {
        return new Object[] {
            new Object[] { null, "" },
            new Object[] { "", "" },
            new Object[] { "invalid/path", "" },
            new Object[] { "/text/test.txt", "This File is For Testing A Main Function\n" }
        };
    }
}
