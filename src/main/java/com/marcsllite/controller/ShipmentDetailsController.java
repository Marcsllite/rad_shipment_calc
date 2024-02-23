package com.marcsllite.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;

public class ShipmentDetailsController extends BaseController {
    @FXML private StackPane shipmentDetails;
    @FXML private VBox vBoxShipmentDetails;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtFieldMass;
    @FXML private ChoiceBox<String> choiceBoxMassPrefix;
    @FXML private ChoiceBox<String> choiceBoxMassName;
    @FXML private ChoiceBox<String> choiceBoxNature;
    @FXML private ChoiceBox<String> choiceBoxState;
    @FXML private ChoiceBox<String> choiceBoxForm;
    @FXML private Button btnSave;

    private static final Logger logr = LogManager.getLogger();

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
        } else if(event.getSource() == btnSave) {
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
