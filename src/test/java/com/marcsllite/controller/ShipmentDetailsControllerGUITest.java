package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

@ExtendWith(ApplicationExtension.class)
public class ShipmentDetailsControllerGUITest extends GUITest {
    ShipmentDetailsController controller;
    @Start
    public void start(Stage stage) {
        super.start(stage, FXMLView.DETAILS);
        controller = (ShipmentDetailsController) getController(ShipmentDetailsController.class);
    }

    @Test
    public void testStart() {
        FxAssert.verifyThat(FXIds.STACKPANE_SHIPMENT_DETAILS, NodeMatchers.isVisible());
    }

    @Test
    public void testHideShow() {
        interact(() -> {
            StackPane detailsPane = getNode(FXIds.STACKPANE_SHIPMENT_DETAILS);

            controller.hide();

            FxAssert.verifyThat(detailsPane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(detailsPane, NodeMatchers.isVisible());
        });
    }

    @Test
    public void testShipmentDetailsHandler_btnNext() {
        clickOn(FXIds.BTN_SAVE);
    }
}
