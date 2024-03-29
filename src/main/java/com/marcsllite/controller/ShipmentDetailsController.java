package com.marcsllite.controller;

import com.marcsllite.util.handler.PropHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.InvalidParameterException;

public class ShipmentDetailsController extends BaseController {
    @FXML private StackPane shipmentDetails;
    @FXML private VBox vBoxShipDetails;
    @FXML private DatePicker datePickerShipDetails;
    @FXML private TextField txtFieldMassShipDetails;
    @FXML private ComboBox<String> comboBoxMassPrefixShipDetails;
    @FXML private ChoiceBox<String> choiceBoxMassNameShipDetails;
    @FXML private ChoiceBox<String> choiceBoxNatureShipDetails;
    @FXML private ChoiceBox<String> choiceBoxStateShipDetails;
    @FXML private ChoiceBox<String> choiceBoxFormShipDetails;
    @FXML private Button btnSaveShipDetails;

    private static final Logger logr = LogManager.getLogger();

    public ShipmentDetailsController() throws IOException {
        this(null);
    }

    public ShipmentDetailsController(PropHandler propHandler) throws IOException {
        super(propHandler);
    }

    @Override
    public void show() {
        shipmentDetails.setVisible(true);
    }

    @Override
    public void hide() {
        shipmentDetails.setVisible(false);
    }

    /**
     * FXML function to handle any menu button pressed
     *
     * @param event the action performed
     */
    @FXML protected void shipmentDetailsHandler(ActionEvent event) throws InvalidParameterException {
        if(event == null) {
            var e = new InvalidParameterException("action event cannot be null");
            logr.throwing(e);
            throw e;
        } else if(event.getSource() == btnSaveShipDetails) {
            saveBtnHandler();
        }
    }

    /**
     * Helper function to handle the save button being pressed
     */
    @FXML protected void saveBtnHandler(){
        logr.debug("User clicked the Save button on the shipment details pane");
        // TODO: implement clicking on save button
    }
}
