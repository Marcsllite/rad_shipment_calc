package com.marcsllite.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.security.InvalidParameterException;

import com.marcsllite.PrimaryController;
import com.marcsllite.SecondaryController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class StageManagerTest {
    final static String propEx = Util.getString("properException");
    final static String propMsg = Util.getString("properMessage");
    StageManager stageManager;

    @BeforeEach
    void startup() {
        Platform.runLater(() -> {
            stageManager = new StageManager(new Stage());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testSwitchScene_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageManager.switchScene(null)
        );
        assertTrue(exception.getMessage().contains("FXML View is null"));
    }

    @ParameterizedTest
    @EnumSource(value = FXMLView.class, names = {"PRIMARY", "SECONDARY"})
    public void testSwitchSceneChecker(FXMLView view) {
        Platform.runLater(() -> {
            stageManager.switchScene(view);
            Stage stage = stageManager.getPrimaryStage();

            assertEquals(view.getTitle(), stage.getTitle());
            assertEquals(view.getWidth(), stage.getScene().getWidth(), 0.0D);
            assertEquals(view.getHeight(), stage.getScene().getHeight(), 0.0D);
            assertEquals(view.getWidth(), stage.getMinWidth(), 0.0D);
            assertEquals(view.getHeight(), stage.getMinHeight(), 0.0D);
            assertEquals(view.getMaxWidth(), stage.getMaxWidth(), 0.0D);
            assertEquals(view.getMaxHeight(), stage.getMaxHeight(), 0.0D);
        });
    }
  
    @Test
    public void testGetController_PrimaryFxml() {
        Platform.runLater(() -> {
            stageManager.switchScene(FXMLView.PRIMARY);
            assertEquals(PrimaryController.class, stageManager.getController().getClass());
        });
    }
  
    @Test
    public void testGetController_SecondaryFxml() {
        Platform.runLater(() -> {
            stageManager.switchScene(FXMLView.SECONDARY);
            assertEquals(SecondaryController.class, stageManager.getController().getClass());
        });
    }

    @Test
    public void testGetController_TestFxml() {
        Platform.runLater(() -> {
            stageManager.switchScene(FXMLView.SECONDARY);
            assertEquals(SecondaryController.class, stageManager.getController().getClass());
        });
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
        assertTrue(exception.getMessage().contains("Unable to show scene titled " + view.getTitle()));
    }

    @ParameterizedTest
    @EnumSource(value = FXMLView.class, names = {"PRIMARY", "SECONDARY"})
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
    public void testLogAndThrowException_NullMsg_NullException(String errorMsg, Exception exception, String expectedMsg) {
        // Platform.runLater(() -> {
            assumeFalse(propEx == null);
            assumeFalse(propEx.isBlank());

            assumeFalse(propMsg == null);
            assumeFalse(propMsg.isBlank());

            RuntimeException except = assertThrows(
                RuntimeException.class, () -> stageManager.logAndThrowException(errorMsg, exception)
            );
            String eMsg = Util.getString(expectedMsg);

            assumeFalse(eMsg == null);
            assumeFalse(eMsg.isBlank());

            assertTrue(except.getMessage().contains(eMsg));
        // });
    }
  
    private static Object[] logAndThrowException_data() {
        Exception properException = new Exception(propEx);
        return new Object[] {
            new Object[] { null, null, "defaultMessage" },
            new Object[] { "", null, "defaultMessage" },
            new Object[] { propMsg, null,"properMessage" },
            new Object[] { null, properException, "defaultMessage" },
            new Object[] { "", properException, "defaultMessage" },
            new Object[] { propMsg, properException, "properMessage" }
        };
    }
}
