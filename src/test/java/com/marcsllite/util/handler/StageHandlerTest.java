package com.marcsllite.util.handler;

import com.marcsllite.controller.GUITest;
import com.marcsllite.util.FXMLView;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.testfx.api.FxToolkit;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StageHandlerTest {
    final static String propMsg = "This is a proper message";
    final static String defaultMessage = StageHandler.DEFAULT_MSG;
    static StageHandler stageHandler;
    static Stage stage;
    
    @BeforeAll
    public static void setup() throws TimeoutException {
        stage = FxToolkit.registerPrimaryStage();
        stageHandler = new StageHandler(stage, GUITest.propHandler);
    }

    @Test
    public void testSwitchScene_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.switchScene(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }

     @ParameterizedTest
     @EnumSource(value = FXMLView.class)
     public void testSwitchScene(FXMLView view) {
         Platform.runLater(() -> {
             stageHandler.switchScene(view);
             Stage stage = stageHandler.getPrimaryStage();

             assertEquals(view, stageHandler.getCurrentView());
             assertEquals(view.getTitle(), stage.getTitle());
             assertEquals(view.getWidth(), stage.getMinWidth(), 0.0D);
             assertEquals(view.getHeight(), stage.getMinHeight(), 0.0D);
             assertEquals(view.getMaxWidth(), stage.getMaxWidth(), 0.0D);
             assertEquals(view.getMaxHeight(), stage.getMaxHeight(), 0.0D);
             assertFalse(stage.isFullScreen());
             assertFalse(stage.isMaximized());
             assertFalse(stage.getIcons().isEmpty());
         });
     }

    @Test
    public void testShow_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.show(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }
  
    @Test
    @SetSystemProperty(key = "keepPlatformOpen",value = "true")
    public void testShow_EmptyView() {
        FXMLView view = FXMLView.TEST;
        try {
            stageHandler.show(view);
        } catch(Exception e) {
            fail("An exception should not have been thrown");
        }
    }

    @Test
    public void testLoadViewNodeHierarchy_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.loadViewNodeHierarchy(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }

    @Test
    public void testLoadViewNodeHierarchy_EmptyProperties() {
        PropHandler propHandler =  new PropHandler() {
            @Override
            protected Object handleGetObject(String key) {
                if(key == null || key.isBlank()) return "";
                return ("defaultNum".equals(key))? "-2.0" : "";
            }

            @Override
            protected Set<String> handleKeySet() {
                return new HashSet<>();
            }
        };

        stageHandler = new StageHandler(stage, propHandler);
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageHandler.loadViewNodeHierarchy(FXMLView.TEST)
        );
        assertTrue(exception.getMessage().contains("The resource bundle contains no values."));
    }

    @Test
    public void testLoadViewNodeHierarchy_EmptyView() {
        FXMLView view = FXMLView.TEST;

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageHandler.loadViewNodeHierarchy(view)
        );
        String expected = "Unable to load FXML view " + view.getFxmlName();
        String expected2 = "The resource bundle contains no values.";
        assertTrue(exception.getMessage().contains(expected));
        assertFalse(exception.getMessage().contains(expected2));
    }
  
    @ParameterizedTest
    @MethodSource("logAndThrowException_data")
    public void testLogAndThrowException(String errorMsg, Exception exception, String expectedMsg) {
        RuntimeException except = assertThrows(
            RuntimeException.class, () -> stageHandler.logAndThrowException(errorMsg, exception)
        );
        assertEquals(expectedMsg, except.getMessage());
    }
  
    private static Object[] logAndThrowException_data() {
        Exception properException = new Exception(propMsg);
        return new Object[] {
            new Object[] { null, null, defaultMessage },
            new Object[] { "", null, defaultMessage },
            new Object[] { propMsg, null, propMsg },
            new Object[] { null, properException, defaultMessage },
            new Object[] { "", properException, defaultMessage },
            new Object[] { propMsg, properException, propMsg }
        };
    }
}
