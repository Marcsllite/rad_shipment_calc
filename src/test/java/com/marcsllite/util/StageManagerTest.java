package com.marcsllite.util;

import javafx.application.Platform;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.api.FxToolkit;

import java.security.InvalidParameterException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StageManagerTest {
    final static String propMsg = "This is a proper message";
    final static String defaultMessage = StageManager.DEFAULT_MSG;
    static StageManager stageManager;
    
    @BeforeAll
    public static void setup() throws TimeoutException {
        stageManager = new StageManager(FxToolkit.registerPrimaryStage());
    }

    @Test
    public void testSwitchScene_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageManager.switchScene(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }

     @ParameterizedTest
     @EnumSource(value = FXMLView.class, names = {"TEST", "MAIN", "MENU"}, mode = EnumSource.Mode.EXCLUDE)
     public void testSwitchSceneChecker(FXMLView view) {
         Platform.runLater(() -> {
             stageManager.switchScene(view);
             Stage stage = stageManager.getPrimaryStage();

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
            InvalidParameterException.class, () -> stageManager.show(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }
  
    @Test
    public void testShow_EmptyView() {
        FXMLView view = FXMLView.TEST;
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageManager.show(view)
        );
        String exp = "Unable to show " + view.getName() + " scene";
        assertEquals(exp, exception.getMessage());
    }

     @ParameterizedTest
     @EnumSource(value = FXMLView.class, names = {"TEST", "MAIN", "MENU"}, mode = EnumSource.Mode.EXCLUDE)
     public void testShowChecker(FXMLView view) {
         Platform.runLater(() -> {
             stageManager.show(view);
             Stage stage = stageManager.getPrimaryStage();

             assertEquals(view.getTitle(), stage.getTitle());
             assertNotNull(stage.getIcons());
         });
     }

    @Test
    public void testLoadViewNodeHierarchy_NullView() {
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }
  
    @Test
    public void testLoadViewNodeHierarchy_EmptyView() {
        FXMLView view = FXMLView.TEST;
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(view)
        );
        String expected = "Unable to load FXML view " + view.getFxmlName();
        assertTrue(exception.getMessage().contains(expected));
    }
  
    @ParameterizedTest
    @MethodSource("logAndThrowException_data")
    public void logAndThrowExceptionChecker(String errorMsg, Exception exception, String expectedMsg) {
        RuntimeException except = assertThrows(
            RuntimeException.class, () -> stageManager.logAndThrowException(errorMsg, exception)
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
