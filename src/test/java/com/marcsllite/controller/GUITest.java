package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.PropHandler;
import com.marcsllite.util.handler.StageHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;
import org.testfx.matcher.base.NodeMatchers;

import java.util.Set;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

@ExtendWith(ApplicationExtension.class)
public class GUITest extends FxRobot {
    protected FXMLView view;
    protected App.AppPane root;
    public static final PropHandler propHandler =  new PropHandler() {
        @Override
        protected Object handleGetObject(String key) {
            if(key == null || key.isBlank()) return "";
            return ("defaultNum".equals(key))? "-2.0" : "";
        }

        @Override
        protected Set<String> handleKeySet() {
            return Set.of("defaultNum");
        }
    };

    protected void start(Stage stage, FXMLView view) {
        this.view = view;
        root = new App.AppPane(stage, view, propHandler);
        root.getStageHandler().switchScene(view);
        stage.show();
    }

    @Stop
    protected void stop() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        root = null;
        view = null;
    }

    @Nested
    @DisplayName("Main Controller GUI Test")
    public class MainControllerGUITest {

        @Start
        public void start(Stage stage) {
            GUITest.this.start(stage, FXMLView.MAIN);
        }

        @Test
        public void testStart() {
            FxAssert.verifyThat(FXIds.GRIDPANE_MENU, NodeMatchers.isVisible());
            FxAssert.verifyThat(FXIds.GRIDPANE_HOME, NodeMatchers.isVisible());
            FxAssert.verifyThat(FXIds.GRIDPANE_REFERENCE, NodeMatchers.isInvisible());

            StageHandler stageHandler = root.getStageHandler();
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
        }
    }

    @Nested
    @DisplayName("Menu Pane Controller GUI Test")
    public class MenuPaneControllerGUITest {
        @Start
        public void start(Stage stage) {
            GUITest.this.start(stage, FXMLView.MENU);
        }

        @Test
        public void testStart() {
            FxAssert.verifyThat(FXIds.GRIDPANE_MENU, NodeMatchers.isVisible());

            StageHandler stageHandler = root.getStageHandler();
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
        }
    }

    @Nested
    @DisplayName("Home Pane Controller GUI Test")
    public class HomePaneControllerGUITest {
        @Start
        public void start(Stage stage) {
            GUITest.this.start(stage, FXMLView.HOME);
        }

        @Test
        public void testStart() {
            FxAssert.verifyThat(FXIds.GRIDPANE_HOME, NodeMatchers.isVisible());

            StageHandler stageHandler = root.getStageHandler();
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
        }
    }

    @Nested
    @DisplayName("Reference Pane Controller GUI Test")
    public class ReferencePaneControllerGUITest {
        @Start
        public void start(Stage stage) {
            GUITest.this.start(stage, FXMLView.REFERENCE);
        }

        @Test
        public void testStart() {
            FxAssert.verifyThat(FXIds.GRIDPANE_REFERENCE, NodeMatchers.isVisible());

            StageHandler stageHandler = root.getStageHandler();
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
        }
    }

    @Nested
    @DisplayName("Modify Controller GUI Test")
    public class ModifyControllerGUITest {
        @Start
        public void start(Stage stage) {
            GUITest.this.start(stage, FXMLView.MODIFY);
        }

        @Test
        public void testStart() {
            FxAssert.verifyThat(FXIds.STACKPANE_MODIFY, NodeMatchers.isVisible());

            StageHandler stageHandler = root.getStageHandler();
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
        }
    }

    @Nested
    @DisplayName("Shipment Details Controller GUI Test")
    public class ShipmentDetailsControllerGUITest {
        @Start
        public void start(Stage stage) {
            GUITest.this.start(stage, FXMLView.DETAILS);
        }

        @Test
        public void testStart() {
            FxAssert.verifyThat(FXIds.STACKPANE_SHIPMENT_DETAILS, NodeMatchers.isVisible());

            StageHandler stageHandler = root.getStageHandler();
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
        }
    }

    @Nested
    @DisplayName("Summary Pane Controller GUI Test")
    public class SummaryPaneControllerGUITest {
        @Start
        public void start(Stage stage) {
            GUITest.this.start(stage, FXMLView.SUMMARY);
        }

        @Test
        public void testStart() {
            FxAssert.verifyThat(FXIds.ANCHORPANE_SUMMARY, NodeMatchers.isVisible());

            StageHandler stageHandler = root.getStageHandler();
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
        }
    }
}