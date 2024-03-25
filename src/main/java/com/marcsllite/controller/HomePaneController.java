package com.marcsllite.controller;

import com.marcsllite.model.Isotope;
import com.marcsllite.model.PTableColumn;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.Date;

public class HomePaneController extends BaseController {
    @FXML GridPane homePane;
    @FXML Button btnAdd;
    @FXML Button btnEdit;
    @FXML Button btnRemove;
    @FXML TableView<Isotope> tableViewHome;
    @FXML PTableColumn<Isotope, String> tableColIsotope;
    @FXML PTableColumn<Isotope, Float> tableColHalfLife;
    @FXML PTableColumn<Isotope, Float> tableColActivity;
    @FXML PTableColumn<Isotope, Date> tableColRefDate;
    @FXML PTableColumn<Isotope, Float> tableColMass;
    @FXML Button btnCalculate;

    private static final Logger logr = LogManager.getLogger();

    public HomePaneController() {
        this(null);
    }

    public HomePaneController(PropHandler propHandler) {
        super(propHandler);
    }

    @Override
    @FXML public void initialize() {
        super.initialize();
        initTable();
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

    protected void initTable() {
        tableColIsotope.setCellValueFactory(i -> new SimpleStringProperty(i.getValue().getIsoId().getAbbr()));
        tableColHalfLife.setCellValueFactory(i -> i.getValue().getConstants().halfLifeProperty().asObject());
        tableColMass.setCellValueFactory(new PropertyValueFactory<>("mass"));
    }

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
