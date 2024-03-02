package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class ShipmentDetailsControllerGUITest extends GUITest {
    ShipmentDetailsController controller;

    public ShipmentDetailsControllerGUITest() {
        super(FXMLView.DETAILS);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (ShipmentDetailsController) getController();
    }

    @Test
    void testStart() {
        FxAssert.verifyThat(FXIds.STACKPANE_SHIPMENT_DETAILS, NodeMatchers.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() -> {
            StackPane detailsPane = getNode(FXIds.STACKPANE_SHIPMENT_DETAILS);

            controller.hide();

            FxAssert.verifyThat(detailsPane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(detailsPane, NodeMatchers.isVisible());
        });
    }

    @Test
    void testShipmentDetailsHandler_btnNext() {
        clickOn(FXIds.BTN_SAVE);
    }
}
