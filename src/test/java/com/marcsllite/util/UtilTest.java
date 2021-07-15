package com.marcsllite.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UtilTest {

    @Nested
    @DisplayName("Util Class UI Tests")
    @ExtendWith(ApplicationExtension.class)
    @TestInstance(Lifecycle.PER_CLASS) 
    class UtilTestUI {
        // Stage stage;
        ButtonBase btnBase;
        Button btn2;
        VBox vBox;
        String btnTxt = "Button Base";
        String msg = "button was fired";

        @Start
        public void start(Stage stage) throws Exception {
            btnBase = new Button(btnTxt);
            btn2 = new Button("Button 2");

            btnBase.setOnAction( 
                (event) -> btnBase.setText(msg)
            );

            vBox = new VBox(10, btnBase, btn2);

            stage.setScene(new Scene(vBox, 100, 100));
            stage.show();
        }

        @Test
        public void fireBtnOnEnter_SpaceKey_Focused(FxRobot robot) {
            Platform.runLater(() -> {
                Util.fireBtnOnEnter(btnBase);

                btnBase.requestFocus();

                robot.press(KeyCode.SPACE).release(KeyCode.SPACE);

                assertNotNull(btnBase.getOnKeyPressed());
            });
            WaitForAsyncUtils.waitForFxEvents();
            Assertions.assertThat(btnBase).hasText(btnTxt);
        }

        @Test
        public void fireBtnOnEnter_SpaceKey_NotFocused(FxRobot robot) {
            Platform.runLater(() -> {
                Util.fireBtnOnEnter(btnBase);
                
                btn2.requestFocus();
                
                robot.press(KeyCode.SPACE).release(KeyCode.SPACE);

                assertNotNull(btnBase.getOnKeyPressed());
            });
            WaitForAsyncUtils.waitForFxEvents();
            Assertions.assertThat(btnBase).hasText(btnTxt);
        }

        @Test
        public void fireBtnOnEnter_EnterKey_Focused(FxRobot robot) {
            Platform.runLater(() -> {
                Util.fireBtnOnEnter(btnBase);

                btnBase.requestFocus();
            
                robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
                                                
                assertNotNull(btnBase.getOnKeyPressed());
                assumeTrue(btnBase.isFocused());
            });
            WaitForAsyncUtils.waitForFxEvents();
            Assertions.assertThat(btnBase).hasText(msg);
        }

        @Test 
        public void fireBtnOnEnter_EnterKey_NotFocused(FxRobot robot) {
            Platform.runLater(() -> {
                Util.fireBtnOnEnter(btnBase);

                btn2.requestFocus();

                robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

                assertNotNull(btnBase.getOnKeyPressed());
            });
            WaitForAsyncUtils.waitForFxEvents();
            Assertions.assertThat(btnBase).hasText(btnTxt);
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalid/path"})
    public void setPropExceptionChecker(String path) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> Util.setProp(path)
        );
        
        assertTrue(exception.getMessage().contains("Failed to set properties from path: " + path));
    }
  
    @Test
    public void getOs_NullOS() {
        String expected = System.getProperty("os.name").toLowerCase();
        Util.setOs(null);
        assertEquals(expected, Util.getOs());
    }

    @ParameterizedTest
    @CsvSource({
        "win, windows",
        "mac, mac",
        "nix, unix",
        "nux, unix",
        "aix, unix",
        "sunos, solaris",
        "none, noSupport"
    })
    public void getCurrentOSChecker(String osName, String expected) {
        Util.setOs(osName);
        assertEquals(Util.getString(expected), Util.getCurrentOS());
    }
  
    @Test
    public void parseStringToReplace() {
        assertEquals(new ArrayList<String>(), 
        Util.parseStringsToReplace(null));
    }
  
    @ParameterizedTest
    @MethodSource("replacePropString_data")
    public void replacePropStringChecker(String key, String expected, String[] replacement) {
        if (replacement != null) {
            switch (replacement.length) {
                case 1:
                    assertEquals(expected, Util.replacePropString(key, replacement[0]));
                    break;
                case 2:
                    assertEquals(expected, Util.replacePropString(key, replacement[0], replacement[1]));
                    break;
                case 3:
                    assertEquals(expected, Util.replacePropString(key, replacement[0], replacement[1], replacement[2]));
                    break;
                case 4:
                    assertEquals(expected, Util.replacePropString(key, replacement[0], replacement[1], replacement[2], replacement[3]));
                    break;
            } 
        } else {
            assertEquals(expected, Util.replacePropString(key));
            assertEquals(expected, Util.replacePropString(key, (String[]) null));
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
            new Object[] { "replacePropString_noReplacements", Util.getString("replacePropString_noReplacements"), new String[] { null } },
            new Object[] { "replacePropString_noReplacements", Util.getString("replacePropString_noReplacements"), null },
            new Object[] { "replacePropString_noReplacements", Util.getString("replacePropString_noReplacements"), new String[] { "ONE" } },
            new Object[] { "replacePropString_oneReplacement", Util.getString("replacePropString_oneReplacement"), new String[] { null } },
            new Object[] { "replacePropString_oneReplacement", Util.getString("replacePropString_oneReplacement"), null },
            new Object[] { "replacePropString_oneReplacement", "This string contains ONE replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_oneReplacement", "This string contains ONE replacements", new String[] { "ONE", "TWO" } },
            new Object[] { "replacePropString_wrongNumber", Util.getString("replacePropString_wrongNumber"), new String[] { null } },
            new Object[] { "replacePropString_wrongNumber", Util.getString("replacePropString_wrongNumber"), null },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect ONE for replacement", new String[] { "ONE" } },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect ONE for replacement", new String[] { "ONE", "TWO" } }, 
            new Object[] { "replacePropString_oneSameReplacements", "This string contains a replacement here: ONE, and the same replacement here: ONE", new String[] { "ONE" } },
            new Object[] { "replacePropString_twoReplacements", Util.getString("replacePropString_twoReplacements"), new String[] { null } },
            new Object[] { "replacePropString_twoReplacements", Util.getString("replacePropString_twoReplacements"), null },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, {1} replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, TWO replacements", new String[] { "ONE", "TWO" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, TWO replacements", new String[] { "ONE", "TWO", "THREE" } },
            new Object[] { "replacePropString_threeReplacements", Util.getString("replacePropString_threeReplacements"), new String[] { null } },
            new Object[] { "replacePropString_threeReplacements", Util.getString("replacePropString_threeReplacements"), null },
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, {1}, {2} replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, TWO, {2} replacements", new String[] { "ONE", "TWO" } }, 
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, TWO, THREE replacements", new String[] { "ONE", "TWO", "THREE" } },
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, TWO, THREE replacements", new String[] { "ONE", "TWO", "THREE", "FOUR" } },
            new Object[] { "replacePropString_twoSameReplacements", "First: ONE, Second: TWO, Third: ONE, Fourth: TWO", new String[] { "ONE", "TWO" } }
        };
    }
  
    @ParameterizedTest
    @MethodSource("getString_data")
    public void getStringChecker(String propName, String expected) {
        assertEquals(expected, Util.getString(propName));
    }
  
    private static Object[] getString_data() {
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
    public void getListChecker(String listName, List<String> expected) {
        assertEquals(expected, Util.getList(listName));
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
    public void getIntExceptionChecker(String propName) {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> Util.getInt(propName)
        );
        assertTrue(exception.getMessage().contains("Value is not a number"));
    }
  
    @ParameterizedTest
    @MethodSource("getInt_data")
    public void getIntChecker(String propName, int expected) {
        assertEquals(expected, Util.getInt(propName));
    }
  
    private static Object[] getInt_data() {
        return new Object[] { 
            new Object[] { null, Integer.MIN_VALUE },
            new Object[] { "", Integer.MIN_VALUE },
            new Object[] { "mainWidth", 600 }
        };
    }
  
    @ParameterizedTest
    @MethodSource("getFileText_data")
    public void getFileTextChecker(String file, String expected) {
        assertEquals(expected, Util.getFileText(file));
    }
  
    private static Object[] getFileText_data() {
        return new Object[] {
            new Object[] { null, "" },
            new Object[] { "", "" },
            new Object[] { "invalid/path", "" },
            new Object[] { "/text/test.txt", "This File is For Testing A Main Function\n" }
        };
    }

    @Test
    public void fireBtnOnEnter_NullBtn() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> Util.fireBtnOnEnter(null)
        );
        assertTrue(exception.getMessage().contains("button base cannot be null"));
    }
}
