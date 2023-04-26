package com.marcsllite;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;

import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.StageManager;

import javafx.stage.Stage;

import org.testfx.matcher.base.NodeMatchers;

public class AppTest extends GUITest {

    @Override
    @Start
    public void start(Stage stage) {
        view = FXMLView.MAIN;
        stageManager = new StageManager(stage);
        stageManager.show(view);
    }

    @Test
    public void testStart() {
        FxAssert.verifyThat(FXIds.MENU_PANE, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.HOME_PANE, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.REF_PANE, NodeMatchers.isInvisible());
    }
}
