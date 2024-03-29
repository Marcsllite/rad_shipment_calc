package com.marcsllite.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.security.InvalidParameterException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropManagerTest {
    AbstractMap<String, String> stringsMap = new HashMap<>();
    PropManager manager =  new PropManager() {
        @Override
        protected Object handleGetObject(String key) {
            if(key == null || key.isBlank()) return "";
            return (stringsMap.get(key) == null)? "" : stringsMap.get(key);
        }

        @Override
        protected Set<String> handleKeySet() {
            return stringsMap.keySet();
        }
    };

    void init() {
        stringsMap.put("fakeKey", "");
        stringsMap.put("replacePropStringRegex", "(\\{\\d+})");
        stringsMap.put("properMessage", "This is a proper message");
        stringsMap.put("properException", "This is a proper Exception");
        stringsMap.put("replacePropString_noReplacements", "This string doesn't contain any replacements");
        stringsMap.put("replacePropString_oneReplacement", "This string contains {0} replacements");
        stringsMap.put("replacePropString_wrongNumber", "This string contains the incorrect {1} for replacement");
        stringsMap.put("replacePropString_twoReplacements", "This string contains {0}, {1} replacements");
        stringsMap.put("replacePropString_threeReplacements", "This string contains {0}, {1}, {2} replacements");
        stringsMap.put("replacePropString_oneSameReplacements", "This string contains a replacement here: {0}, and the same replacement here: {0}");
        stringsMap.put("replacePropString_twoSameReplacements", "First: {0}, Second: {1}, Third: {0}, Fourth: {1}");
        stringsMap.put("getList_TrailingDelimiters", "This|List|has|trailing|delimiters||||");
        stringsMap.put("getList_SpacesWithinElements", "element 1 has spaces|element 2 does too");
        stringsMap.put("getList_SpacesAroundElements", "     there are spaces around this element    |   spaces around here too    ");
        stringsMap.put("getList_ProperList", "This|is|a|proper|list");
        stringsMap.put("mainPane", "UMass Lowell - Rad Shipment Calculator");
        stringsMap.put("mainWidth", "600.0");
    }

    @BeforeEach
    public void setUp() {
        init();
    }

    @AfterEach
    public void tearDown() {
        stringsMap.clear();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalid/name"})
    void setPropExceptionChecker(String name) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> manager.setProp(name)
        );
        
        assertTrue(exception.getMessage().contains("Failed to set properties from resource bundle: " + name));
    }
  
    @Test
    void getOs_NullOS() {
        manager.setOs(null);
        assertNull(manager.getOS());
    }

    @Test
    void parseOSChecker() {
        String osName = "win";
        assertEquals(OS.Windows, manager.parseOS(osName));

        osName = "mac";
        assertEquals(OS.MAC, manager.parseOS(osName));

        osName = "nix";
        assertEquals(OS.Unix, manager.parseOS(osName));

        osName = "nux";
        assertEquals(OS.Unix, manager.parseOS(osName));

        osName = "aix";
        assertEquals(OS.Unix, manager.parseOS(osName));

        osName = "sunos";
        assertEquals(OS.Solaris, manager.parseOS(osName));

        osName = "fake";
        assertEquals(OS.NOT_SUPPORTED, manager.parseOS(osName));

        assertEquals(OS.NOT_SUPPORTED, manager.parseOS(null));
    }
  
    @Test
    void parseStringToReplace() {
        assertEquals(new ArrayList<String>(), 
        manager.parseStringsToReplace(null));
    }
  
    @ParameterizedTest
    @MethodSource("replacePropString_data")
    void replacePropStringChecker(String key, String expected, String[] replacement) {
        if (replacement != null) {
            switch (replacement.length) {
                case 1:
                    assertEquals(expected, manager.replacePropString(key, replacement[0]));
                    break;
                case 2:
                    assertEquals(expected, manager.replacePropString(key, replacement[0], replacement[1]));
                    break;
                case 3:
                    assertEquals(expected, manager.replacePropString(key, replacement[0], replacement[1], replacement[2]));
                    break;
                case 4:
                    assertEquals(expected, manager.replacePropString(key, replacement[0], replacement[1], replacement[2], replacement[3]));
                    break;
            } 
        } else {
            assertEquals(expected, manager.replacePropString(key));
            assertEquals(expected, manager.replacePropString(key, (String[]) null));
        } 
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
            new Object[] { "replacePropString_noReplacements", "This string doesn't contain any replacements", new String[] { null } },
            new Object[] { "replacePropString_noReplacements", "This string doesn't contain any replacements", null },
            new Object[] { "replacePropString_noReplacements", "This string doesn't contain any replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_oneReplacement", "This string contains {0} replacements", new String[] { null } },
            new Object[] { "replacePropString_oneReplacement", "This string contains {0} replacements", null },
            new Object[] { "replacePropString_oneReplacement", "This string contains ONE replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_oneReplacement", "This string contains ONE replacements", new String[] { "ONE", "TWO" } },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect {1} for replacement", new String[] { null } },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect {1} for replacement", null },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect ONE for replacement", new String[] { "ONE" } },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect ONE for replacement", new String[] { "ONE", "TWO" } }, 
            new Object[] { "replacePropString_oneSameReplacements", "This string contains a replacement here: ONE, and the same replacement here: ONE", new String[] { "ONE" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains {0}, {1} replacements", new String[] { null } },
            new Object[] { "replacePropString_twoReplacements", "This string contains {0}, {1} replacements", null },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, {1} replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, TWO replacements", new String[] { "ONE", "TWO" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, TWO replacements", new String[] { "ONE", "TWO", "THREE" } },
            new Object[] { "replacePropString_threeReplacements", "This string contains {0}, {1}, {2} replacements", new String[] { null } },
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
    void handleGetObjectChecker(String propName, String expected) {
        assertEquals(expected, manager.getString(propName));
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
    void getListChecker(String listName, List<String> expected) {
        assertEquals(expected, manager.getList(listName));
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
    void getDoubleExceptionChecker(String propName) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> manager.getDouble(propName)
        );
        assertTrue(exception.getMessage().contains("is not a number"));
    }
  
    @ParameterizedTest
    @MethodSource("getNumber_data")
    void getNumberChecker(String propName, double expected) {
        assertEquals(expected, manager.getDouble(propName));
    }
  
    private static Object[] getNumber_data() {
        return new Object[] { 
            new Object[] { null, Double.MIN_VALUE },
            new Object[] { "", Double.MIN_VALUE },
            new Object[] { "mainWidth", 600.0 }
        };
    }
  
    @ParameterizedTest
    @MethodSource("getFileText_data")
    void getFileTextChecker(String file, String expected) {
        assertEquals(expected, manager.getFileText(file));
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
