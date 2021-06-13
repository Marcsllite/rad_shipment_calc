package com.marcsllite.util;

import com.marcsllite.App;
import com.marcsllite.PrimaryController;
import com.marcsllite.SecondaryController;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testfx.api.FxToolkit;
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
            new Object[] { FXMLView.SECONDARY }
        };
    }
  
    public void start(Stage stage) throws Exception {}
  
    @Before
    public void beforeEachTest() throws Exception {
        ApplicationTest.launch(App.class, new String[0]);
    }
  
    @After
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();
        release(new javafx.scene.input.KeyCode[0]);
        release(new javafx.scene.input.MouseButton[0]);
    }
  
    @Test
    public void switchScene_NullView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            RuntimeException exception = Assert.assertThrows(
                RuntimeException.class, () -> stageManager.switchScene(null)
            );
            Assert.assertTrue(exception.getMessage().contains("FXML View is null"));
        });
    }

    @Test
    @Parameters(method = "data")
    public void switchSceneChecker(FXMLView view) {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            stageManager.switchScene(view);
            Stage stage = stageManager.getPrimaryStage();

            Assert.assertEquals(view.getWidth(), stage.getScene().getWidth(), 0.0D);
            Assert.assertEquals(view.getHeight(), stage.getScene().getHeight(), 0.0D);
            Assert.assertEquals(view.getWidth(), stage.getMinWidth(), 0.0D);
            Assert.assertEquals(view.getHeight(), stage.getMinHeight(), 0.0D);
            Assert.assertEquals(view.getMaxWidth(), stage.getMaxWidth(), 0.0D);
            Assert.assertEquals(view.getMaxHeight(), stage.getMaxHeight(), 0.0D);
        });
    }
  
    @Test
    public void getController_AppFxml() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            stageManager.switchScene(FXMLView.PRIMARY);
            Assert.assertEquals(PrimaryController.class, stageManager.getController().getClass());
        });
    }
  
    @Test
    public void getController_MenuFxml() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            stageManager.switchScene(FXMLView.SECONDARY);
            Assert.assertEquals(SecondaryController.class, stageManager.getController().getClass());
        });
    }
  
    @Test
    public void show_NullView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            RuntimeException exception = Assert.assertThrows(
                RuntimeException.class, () -> stageManager.show(null)
            );
            Assert.assertTrue(exception.getMessage().contains("FXML View is null"));
        });
    }
  
    @Test
    public void show_EmptyView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            FXMLView view = FXMLView.TEST;
            RuntimeException exception = Assert.assertThrows(
                RuntimeException.class, () -> stageManager.show(view)
            );
            Assert.assertTrue(exception.getMessage().contains("Unable to show scene titled " + view.getTitle()));
        });
    }
  
    @Test
    @Parameters(method = "data")
    public void showChecker(FXMLView view) {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            stageManager.show(view);
            Stage stage = stageManager.getPrimaryStage();

            Assert.assertEquals(view.getTitle(), stage.getTitle());
            Assert.assertNotNull(stage.getIcons());
        });
    }
  
    @Test
    public void loadViewNodeHierarchy_NullView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            RuntimeException exception = Assert.assertThrows(
                RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(null)
            );
            Assert.assertTrue(exception.getMessage().contains("FXML View is null"));
        });
    }
  
    @Test
    public void loadViewNodeHierarchy_EmptyView() {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            FXMLView view = FXMLView.TEST;
            RuntimeException exception = Assert.assertThrows(
                RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(view)
            );
            Assert.assertTrue(exception.getMessage().contains("Unable to load FXML view " + view.getFxmlName()));
            
        });
    }
  
    @Test
    @Parameters(method = "logAndThrowException_data")
    public void logAndThrowException_NullMsg_NullException(String errorMsg, Exception exception, String expectedMsg) {
        Platform.runLater(() -> {
            StageManager stageManager = new StageManager(new Stage());
            RuntimeException except = Assert.assertThrows(
                RuntimeException.class, () -> stageManager.logAndThrowException(errorMsg, exception)
            );
            Assert.assertTrue(except.getMessage().contains(App.getString(expectedMsg)));
        });
    }
  
    private Object[] logAndThrowException_data() {
        Exception properException = new Exception(App.getString("properException"));
        return new Object[] {
            new Object[] { null, null, "defaultMessage" },
            new Object[] { "", null, "defaultMessage" },
            new Object[] { App.getString("properMessage"), null,"properMessage" },
            new Object[] { null, properException, "defaultMessage" },
            new Object[] { "", properException, "defaultMessage" },
            new Object[] { App.getString("properMessage"), properException, "properMessage" }
        };
    }
}
