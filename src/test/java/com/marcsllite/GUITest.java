package com.marcsllite;

import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.StageManager;

@ExtendWith(ApplicationExtension.class)
public abstract class GUITest extends FxRobot {
    FXMLView view;
    protected StageManager stageManager;

    @BeforeAll
    public static void setupSpec() throws Exception {
        // if (Boolean.getBoolean("headless")) {
        // General
        System.setProperty("java.awt.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("javafx.verbose","true");

        // Prism
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("prism.debug","true");
        // }
    }

    @Start
    public void start(Stage stage) {
        stageManager = new StageManager(stage);
        stageManager.show(view);
    }
}
