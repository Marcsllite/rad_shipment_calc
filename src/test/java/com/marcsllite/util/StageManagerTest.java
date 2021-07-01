package com.marcsllite.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;

import com.marcsllite.PrimaryController;
import com.marcsllite.SecondaryController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.stage.Stage;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class StageManagerTest extends ApplicationTest {
    private Object[] data() {
        return new Object[] {
            new Object[] { FXMLView.PRIMARY },
            new Object[] { FXMLView.SECONDARY },
            new Object[] { FXMLView.TEST }
        };
    }
  
    public void start(Stage stage) throws Exception {}
  
    @Test
    public void switchScene_NullView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            RuntimeException exception = assertThrows(
                RuntimeException.class, () -> stageManager.switchScene(null)
            );
            assertTrue(exception.getMessage().contains("FXML View is null"));
        });
    }

    @Test
    @Parameters(method = "data")
    public void switchSceneChecker(FXMLView view) {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            stageManager.switchScene(view);
            Stage stage = stageManager.getPrimaryStage();

            assertEquals(view.getName(), stage.getTitle());
            assertEquals(view.getWidth(), stage.getScene().getWidth(), 0.0D);
            assertEquals(view.getHeight(), stage.getScene().getHeight(), 0.0D);
            assertEquals(view.getWidth(), stage.getMinWidth(), 0.0D);
            assertEquals(view.getHeight(), stage.getMinHeight(), 0.0D);
            assertEquals(view.getMaxWidth(), stage.getMaxWidth(), 0.0D);
            assertEquals(view.getMaxHeight(), stage.getMaxHeight(), 0.0D);
        });
    }
  
    @Test
    public void getController_PrimaryFxml() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            stageManager.switchScene(FXMLView.PRIMARY);
            assertEquals(PrimaryController.class, stageManager.getController().getClass());
        });
    }
  
    @Test
    public void getController_SecondaryFxml() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            stageManager.switchScene(FXMLView.SECONDARY);
            assertEquals(SecondaryController.class, stageManager.getController().getClass());
        });
    }

    @Test
    public void getController_TestFxml() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            stageManager.switchScene(FXMLView.SECONDARY);
            assertEquals(SecondaryController.class, stageManager.getController().getClass());
        });
    }
  
    @Test
    public void show_NullView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            RuntimeException exception = assertThrows(
                RuntimeException.class, () -> stageManager.show(null)
            );
            assertTrue(exception.getMessage().contains("FXML View is null"));
        });
    }
  
    @Test
    public void show_EmptyView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            FXMLView view = FXMLView.TEST;
            RuntimeException exception = assertThrows(
                RuntimeException.class, () -> stageManager.show(view)
            );
            assertTrue(exception.getMessage().contains("Unable to show scene titled " + view.getTitle()));
        });
    }
  
    @Test
    @Parameters(method = "data")
    public void showChecker(FXMLView view) {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            stageManager.show(view);
            Stage stage = stageManager.getPrimaryStage();

            assertEquals(view.getTitle(), stage.getTitle());
            assertNotNull(stage.getIcons());
        });
    }
  
    @Test
    public void loadViewNodeHierarchy_NullView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            RuntimeException exception = assertThrows(
                RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(null)
            );
            assertTrue(exception.getMessage().contains("FXML View is null"));
        });
    }
  
    @Test
    public void loadViewNodeHierarchy_EmptyView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            FXMLView view = FXMLView.TEST;
            RuntimeException exception = assertThrows(
                RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(view)
            );
            assertTrue(exception.getMessage().contains("Unable to load FXML view " + view.getFxmlName()));
            
        });
    }

    String propEx = Util.getString("properException");
    String propMsg = Util.getString("properMessage");
  
    @Test
    @Parameters(method = "logAndThrowException_data")
    public void logAndThrowException_NullMsg_NullException(String errorMsg, Exception exception, String expectedMsg) {
        Platform.runLater(() -> {
            assumeNotNull(propEx);
            assumeFalse(propEx.isBlank());

            assumeNotNull(propMsg);
            assumeFalse(propMsg.isBlank());

            StageManager stageManager = new StageManager(new Stage());
            RuntimeException except = assertThrows(
                RuntimeException.class, () -> stageManager.logAndThrowException(errorMsg, exception)
            );
            String eMsg = Util.getString(expectedMsg);

            assumeNotNull(eMsg);
            assumeFalse(eMsg.isBlank());

            assertTrue(except.getMessage().contains(eMsg));
        });
    }
  
    private Object[] logAndThrowException_data() {
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
