package com.marcsllite.controller;

import com.marcsllite.ControllerFactoryTestObj;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.OSUtil;
import com.marcsllite.util.handler.StageHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SplashScreenControllerGUITest extends FxRobot {
    SplashScreenController controller;
    final FXMLView view = FXMLView.SPLASH;
    StageHandler stageHandler;
    final PropHandlerTestObj testPropHandler = new PropHandlerTestObj();
    DBService dbService;
    GridPane gridPaneSplash;
    Label labelSplash;
    ProgressBar progressSplash;

    @BeforeAll
    public void beforeAll() {
        dbService = new DBServiceImpl(testPropHandler);
    }

    @Start
    public void start(Stage stage) throws IOException {
        stageHandler = new StageHandler(stage, testPropHandler, new ControllerFactoryTestObj(dbService));

        stageHandler.showSplashScreen();
        controller = (SplashScreenController) stageHandler.getController();
        controller.setDbService(dbService);

        gridPaneSplash = controller.gridPaneSplash;
        labelSplash = controller.labelSplash;
        progressSplash = controller.progressSplash;
    }

    @Stop
    public void stop() {
        stageHandler.close();
    }

    @BeforeEach
    public void beforeEach() {
        Assumptions.assumeTrue(FxToolkit.isFXApplicationThreadRunning());
    }

    @Test
    void testBaseStageAssertions() {
        Stage stage = stageHandler.getSecondaryStage();

        assertEquals(view, stageHandler.getCurrentView());
        assertEquals(view.getTitle(), stage.getTitle());
        assertEquals(view.getWidth(), stage.getMinWidth(), 0.0D);
        assertEquals(view.getHeight(), stage.getMinHeight(), 0.0D);
        assertEquals(view.getMaxWidth(), stage.getMaxWidth(), 0.0D);
        assertEquals(view.getMaxHeight(), stage.getMaxHeight(), 0.0D);
        assertFalse(stage.isFullScreen());
        if(!OSUtil.isMac()) {
            assertFalse(stage.isMaximized());
        }
        assertFalse(stage.getIcons().isEmpty());
    }
}