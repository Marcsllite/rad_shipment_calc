package com.marcsllite.util.handler;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXMLView;
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

public class StageHandlerTest {
    final static String propMsg = "This is a proper message";
    final static String defaultMessage = StageHandler.DEFAULT_MSG;
    static StageHandler stageHandler;
    
    @BeforeAll
    public static void setup() throws TimeoutException {
        stageHandler = new StageHandler(FxToolkit.registerPrimaryStage(), GUITest.propHandler);
    }

    @Test
    public void testSwitchScene_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageHandler.switchScene(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
    }

     @ParameterizedTest
     @EnumSource(value = FXMLView.class, names = {"TEST", "MAIN", "MENU", "HOME"}, mode = EnumSource.Mode.EXCLUDE)
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
    public void testShow_EmptyView() {
        FXMLView view = FXMLView.TEST;
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageHandler.show(view)
        );
        String exp = "Unable to show " + view.getName() + " scene";
        assertEquals(exp, exception.getMessage());
    }

     @ParameterizedTest
     @EnumSource(value = FXMLView.class, names = {"TEST", "MAIN", "MENU", "HOME"}, mode = EnumSource.Mode.EXCLUDE)
     public void testShow(FXMLView view) {
         Platform.runLater(() -> {
             stageHandler.show(view);
             Stage stage = stageHandler.getPrimaryStage();

             assertEquals(view.getTitle(), stage.getTitle());
             assertNotNull(stage.getIcons());
         });
     }

    @Test
    public void testLoadViewNodeHierarchy_NullView() {
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageHandler.loadViewNodeHierarchy(null)
        );
        assertEquals("FXML View is null", exception.getMessage());
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
