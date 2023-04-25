package com.marcsllite;

import javafx.stage.Stage;

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

    @Start
    public void start(Stage stage) {
        stageManager = new StageManager(stage);
        stageManager.show(view);
    }
}
