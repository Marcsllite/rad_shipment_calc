package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

@ExtendWith(ApplicationExtension.class)
public class MainControllerGUITest extends GUITest {

    @Start
    void start(Stage stage) {
        super.start(stage, FXMLView.MAIN);
    }

    @Test
    public void testStart() {
        FxAssert.verifyThat(FXIds.MENU_PANE, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.HOME_PANE, NodeMatchers.isVisible());
        FxAssert.verifyThat(FXIds.REF_PANE, NodeMatchers.isInvisible());
    }
}
