package com.marcsllite.util;

import javafx.application.Platform;
import javafx.stage.Stage;
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
// import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
// import org.testfx.util.WaitForAsyncUtils;

import com.marcsllite.GUITest;

import java.security.InvalidParameterException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

@ExtendWith(MockitoExtension.class)
public class StageManagerTest extends GUITest {
    final static String propEx = Util.getString("properException");
    final static String propMsg = Util.getString("properMessage");
    final static String eMsgDefault = Util.getString("defaultMessage");
    final static String eMsgProp = Util.getString("properMessage");

    @Override
    @Start
    public void start(Stage stage) {
        stageManager = new StageManager(new Stage());
    }

    @Test
    public void testSwitchScene_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageManager.switchScene(null)
        );
        assertTrue(exception.getMessage().contains("FXML View is null"));
    }

     @ParameterizedTest
     @EnumSource(value = FXMLView.class, names = {"TEST"}, mode = EnumSource.Mode.EXCLUDE)
     public void testSwitchSceneChecker(FXMLView view) {
         Platform.runLater(() -> {
             stageManager.switchScene(view);
             Stage stage = stageManager.getPrimaryStage();
             // WaitForAsyncUtils.waitForFxEvents();

             // assertEquals(view, stageManager.getCurrentView());
             assertEquals(view.getTitle(), stage.getTitle());
             assertEquals(view.getWidth(), stage.getMinWidth(), 0.0D);
             assertEquals(view.getHeight(), stage.getMinHeight(), 0.0D);
             assertEquals(view.getMaxWidth(), stage.getMaxWidth(), 0.0D);
             assertEquals(view.getMaxHeight(), stage.getMaxHeight(), 0.0D);
             assertFalse(stage.isFullScreen());
             assertFalse(stage.isMaximized());
             assertFalse(stage.getIcons().isEmpty());
         });
         // WaitForAsyncUtils.waitForFxEvents();
     }

    @Test
    public void testShow_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageManager.show(null)
        );
        assertTrue(exception.getMessage().contains("FXML View is null"));
    }
  
    @Test
    public void testShow_EmptyView() {
        FXMLView view = FXMLView.TEST;
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageManager.show(view)
        );
        assertTrue(exception.getMessage().contains("Unable to show " + view.getName() + " scene"));
    }

     @ParameterizedTest
     @EnumSource(value = FXMLView.class, names = {"TEST"}, mode = EnumSource.Mode.EXCLUDE)
     public void testShowChecker(FXMLView view) {
         Platform.runLater(() -> {
             stageManager.show(view);
             Stage stage = stageManager.getPrimaryStage();
             // WaitForAsyncUtils.waitForFxEvents();

             assertEquals(view.getTitle(), stage.getTitle());
             assertNotNull(stage.getIcons());
         });
         // WaitForAsyncUtils.waitForFxEvents();
     }

    @Test
    public void testLoadViewNodeHierarchy_NullView() {
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(null)
        );
        assertTrue(exception.getMessage().contains("FXML View is null"));
    }
  
    @Test
    public void testLoadViewNodeHierarchy_EmptyView() {
        FXMLView view = FXMLView.TEST;
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(view)
        );
        assertTrue(exception.getMessage().contains("Unable to load FXML view " + view.getFxmlName()));
    }
  
    @ParameterizedTest
    @MethodSource("logAndThrowException_data")
    public void logAndThrowExceptionChecker(String errorMsg, Exception exception, String expectedMsg) {
        assumeFalse(propEx == null);
        assumeFalse(propEx.isBlank());

        assumeFalse(propMsg == null);
        assumeFalse(propMsg.isBlank());

        RuntimeException except = assertThrows(
            RuntimeException.class, () -> stageManager.logAndThrowException(errorMsg, exception)
        );

        assumeFalse(expectedMsg == null);
        assumeFalse(expectedMsg.isBlank());

        assertTrue(except.getMessage().contains(expectedMsg));
    }
  
    private static Object[] logAndThrowException_data() {
        Exception properException = new Exception(propEx);
        return new Object[] {
            new Object[] { null, null, eMsgDefault },
            new Object[] { "", null, eMsgDefault },
            new Object[] { propMsg, null,eMsgProp },
            new Object[] { null, properException, eMsgDefault },
            new Object[] { "", properException, eMsgDefault },
            new Object[] { propMsg, properException, eMsgProp }
        };
    }
}
