package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.StageHandler;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

@ExtendWith(ApplicationExtension.class)
public class ShipmentDetailsControllerGUITest extends GUITest {
    @Start
    public void start(Stage stage) {
        super.start(stage, FXMLView.DETAILS);
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
