package com.marcsllite.controller;

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
import org.junit.jupiter.api.Disabled;
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
        super(FXMLView.DETAILS, BaseController.Page.DETAILS);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (ShipmentDetailsController) getController();
        stackPaneShipmentDetails = controller.shipmentDetails;
        vBoxShipDetails = controller.vBoxShipDetails;
        datePickerShipDetails = controller.datePickerShipDetails;
        txtFieldMassShipDetails = controller.txtFieldMassShipDetails;
        comboBoxMassPrefixShipDetails = controller.comboBoxMassPrefixShipDetails;
        choiceBoxMassNameShipDetails = controller.choiceBoxMassNameShipDetails;
        choiceBoxNatureShipDetails = controller.choiceBoxNatureShipDetails;
        choiceBoxStateShipDetails = controller.choiceBoxStateShipDetails;
        choiceBoxFormShipDetails = controller.choiceBoxFormShipDetails;
        btnSaveShipDetails = controller.btnSaveShipDetails;
    }

    @Test
    void testStart() {
        FxAssert.verifyThat(stackPaneShipmentDetails, NodeMatchers.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() ->controller.hide());

        FxAssert.verifyThat(stackPaneShipmentDetails, NodeMatchers.isInvisible());

        interact(() ->controller.show());

        FxAssert.verifyThat(stackPaneShipmentDetails, NodeMatchers.isVisible());
    }

    @Disabled("FXML method it tests has not yet been implemented")
    void testShipmentDetailsHandler_btnNext() {
        clickOn(btnSaveShipDetails);
    }
}
