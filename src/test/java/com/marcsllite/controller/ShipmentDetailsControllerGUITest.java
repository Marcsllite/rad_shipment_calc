package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.util.FXMLView;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class ShipmentDetailsControllerGUITest extends GUITest {
    ShipmentDetailsController controller;
    StackPane stackPaneShipmentDetails;
    VBox vBoxShipDetails;
    DatePicker datePickerShipDetails;
    TextField txtFieldMassShipDetails;
    ComboBox<String> comboBoxMassPrefixShipDetails;
    ChoiceBox<String> choiceBoxMassNameShipDetails;
    ChoiceBox<String> choiceBoxNatureShipDetails;
    ChoiceBox<String> choiceBoxStateShipDetails;
    ChoiceBox<String> choiceBoxFormShipDetails;
    Button btnSaveShipDetails;

    public ShipmentDetailsControllerGUITest() {
        super(FXMLView.DETAILS);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (ShipmentDetailsController) getController();
        stackPaneShipmentDetails = getNode(FXIds.STACKPANE_SHIPMENT_DETAILS);
        vBoxShipDetails = getNode(FXIds.VBOX_SHIP_DETAILS);
        datePickerShipDetails = getNode(FXIds.DATE_PICKER_SHIP_DETAILS);
        txtFieldMassShipDetails = getNode(FXIds.TXTFIELD_MASS_SHIP_DETAILS);
        comboBoxMassPrefixShipDetails = getNode(FXIds.COMBOBOX_MASS_PREFIX_SHIP_DETAILS);
        choiceBoxMassNameShipDetails = getNode(FXIds.CHOICEBOX_MASS_NAME_SHIP_DETAILS);
        choiceBoxNatureShipDetails = getNode(FXIds.CHOICEBOX_NATURE_SHIP_DETAILS);
        choiceBoxStateShipDetails = getNode(FXIds.CHOICEBOX_STATE_SHIP_DETAILS);
        choiceBoxFormShipDetails = getNode(FXIds.CHOICEBOX_FORM_SHIP_DETAILS);
        btnSaveShipDetails = getNode(FXIds.BTN_SAVE_SHIP_DETAILS);
    }

    @Test
    void testStart() {
        FxAssert.verifyThat(stackPaneShipmentDetails, NodeMatchers.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() -> {
            controller.hide();

            FxAssert.verifyThat(stackPaneShipmentDetails, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(stackPaneShipmentDetails, NodeMatchers.isVisible());
        });
    }

    @Test
    void testShipmentDetailsHandler_btnNext() {
        clickOn(btnSaveShipDetails);
    }
}
