package com.marcsllite.controller;

import com.marcsllite.model.Isotope;
import com.marcsllite.model.PTableColumn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;

public class HomePaneController extends BaseController {
    @FXML GridPane homePane;
    @FXML Button btnAdd;
    @FXML Button btnEdit;
    @FXML Button btnRemove;
    @FXML TableView<Isotope> tableView;
    @FXML PTableColumn<Isotope, Label> tableColIsotope;
    @FXML PTableColumn<Isotope, Label> tableColHalfLife;
    @FXML PTableColumn<Isotope, Label> tableColActivity;
    @FXML PTableColumn<Isotope, Label> tableColRefDate;
    @FXML PTableColumn<Isotope, Label> tableColMass;
    @FXML Button btnCalculate;

    private static final Logger logr = LogManager.getLogger();

    @Override
    @FXML public void initialize() {
        super.initialize();
    }

    @Override
    public void show() {
        homePane.setVisible(true);
        homePane.toFront();
    }

    @Override
    public void hide() {
        homePane.setVisible(false);
        homePane.toBack();
    }

    /*///////////////////////////////////////////// HOME PANE CONTROLLER /////////////////////////////////////////////*/

    /**
     * FXML function to handle any menu button pressed
     *
     * @param event the action performed
     */
    @FXML protected void homePaneHandler(ActionEvent event) throws InvalidParameterException {
        if(event == null) {
            var e = new InvalidParameterException("action event cannot be null");
            logr.throwing(e);
            throw e;
        }

        else if(event.getSource() == btnAdd) {
            addBtnHandler();
        } else if(event.getSource() == btnEdit) {
            editBtnHandler();
        } else if(event.getSource() == btnRemove) {
            removeBtnHandler();
        } else if(event.getSource() == btnCalculate) {
            calculateBtnHandler();
        }
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Helper function to handle the add button being pressed
     */
    @FXML protected void addBtnHandler(){
        logr.debug("User clicked the Add button on the home pane");
        // TODO: implement clicking on add button
    }

    /**
     * Helper function to handle the edit button being pressed
     */
    @FXML protected void editBtnHandler(){
        logr.debug("User clicked the Edit button on the home pane");
        // TODO: implement clicking on edit button
    }

    /**
     * Helper function to handle the remove button being pressed
     */
    @FXML protected void removeBtnHandler(){
        logr.debug("User clicked the Remove button on the home pane");
        // TODO: implement clicking on remove button
    }

    /**
     * Helper function to handle the calculate button being pressed
     */
    @FXML protected void calculateBtnHandler(){
        logr.debug("User clicked the Calculate button on the home pane");
        // TODO: implement clicking on calculate button
    }
}
