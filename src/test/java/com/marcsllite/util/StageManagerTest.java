package com.marcsllite.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.security.InvalidParameterException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class StageManagerTest {
    static String propEx = Util.getString("properException");
    static String propMsg = Util.getString("properMessage");
    static String eMsgDefault = Util.getString("defaultMessage");
    static String eMsgProp = Util.getString("properMessage");
    Stage stage;
    StageManager stageManager;

    @Start
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
    }

    @BeforeEach
    public void setUp() {
        Platform.runLater(() -> {
            stageManager = new StageManager(stage);
        });
        WaitForAsyncUtils.waitForFxEvents();
        propEx = Util.getString("properException");
        propMsg = Util.getString("properMessage");
        eMsgDefault = Util.getString("defaultMessage");
        eMsgProp = Util.getString("properMessage");
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        stageManager = null;
        propEx = null;
        propMsg = null;
        eMsgDefault = null;
        eMsgProp = null;
    }

    @Test
    void testSwitchScene_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageManager.switchScene(null)
        );
        assertTrue(exception.getMessage().contains("FXML View is null"));
    }

    @ParameterizedTest
    @EnumSource(value = FXMLView.class, names = {"TEST"}, mode = EnumSource.Mode.EXCLUDE)
    void testSwitchSceneChecker(FXMLView view) {
        Platform.runLater(() -> {
            stageManager.switchScene(view);
            Stage stage = stageManager.getPrimaryStage();
            WaitForAsyncUtils.waitForFxEvents();

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
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testShow_NullView() {
        InvalidParameterException exception = assertThrows(
            InvalidParameterException.class, () -> stageManager.show(null)
        );
        assertTrue(exception.getMessage().contains("FXML View is null"));
    }
  
    @Test
    void testShow_EmptyView() {
        FXMLView view = FXMLView.TEST;
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageManager.show(view)
        );
        assertTrue(exception.getMessage().contains("Unable to show " + view.getName() + " scene"));
    }

    @ParameterizedTest
    @EnumSource(value = FXMLView.class, names = {"TEST"}, mode = EnumSource.Mode.EXCLUDE)
    void testShowChecker(FXMLView view) {
        Platform.runLater(() -> {
            stageManager.show(view);
            Stage stage = stageManager.getPrimaryStage();
            WaitForAsyncUtils.waitForFxEvents();

            assertEquals(view.getTitle(), stage.getTitle());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testLoadViewNodeHierarchy_NullView() {
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(null)
        );
        assertTrue(exception.getMessage().contains("FXML View is null"));
    }
  
    @Test
    void testLoadViewNodeHierarchy_EmptyView() {
        FXMLView view = FXMLView.TEST;
        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> stageManager.loadViewNodeHierarchy(view)
        );
        assertTrue(exception.getMessage().contains("Unable to load FXML view " + view.getFxmlName()));
    }
  
    @ParameterizedTest
    @MethodSource("logAndThrowException_data")
    void logAndThrowExceptionChecker(String errorMsg, Exception exception, String expectedMsg) {
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
