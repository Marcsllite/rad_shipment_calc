package com.marcsllite.controller;

import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.ResourceBundle;

public class ShipmentDetailsController extends BaseController {
    @FXML private VBox vBoxShipmentDetails;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtFieldMass;
    @FXML private ChoiceBox<String> choiceBoxMassUnit;
    @FXML private ChoiceBox<String> choiceBoxMassName;
    @FXML private ChoiceBox<String> choiceBoxNature;
    @FXML private ChoiceBox<String> choiceBoxState;
    @FXML private ChoiceBox<String> choiceBoxForm;
    @FXML private Button btnSave;

    private static final Logger logr = LogManager.getLogger();
    private final PropHandler propHandler;

    public ShipmentDetailsController() {
        this((PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()));
    }

    public ShipmentDetailsController(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    @Override
    public void show() {
        vBoxShipmentDetails.setVisible(true);
    }

    @Override
    public void hide() {
        vBoxShipmentDetails.setVisible(false);
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
