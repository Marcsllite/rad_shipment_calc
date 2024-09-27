package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.PTableColumn;
import com.marcsllite.model.Shipment;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

public class HomePaneController extends BaseController {
    private static final Logger logr = LogManager.getLogger(HomePaneController.class);
    @FXML GridPane homePane;
    @FXML Button btnAdd;
    @FXML Button btnEdit;
    @FXML Button btnRemove;
    @FXML TableView<Nuclide> tableViewHome;
    @FXML PTableColumn<Nuclide, String> tableColNuclide;
    @FXML PTableColumn<Nuclide, String> tableColHalfLife;
    @FXML PTableColumn<Nuclide, String> tableColActivity;
    @FXML PTableColumn<Nuclide, LocalDate> tableColRefDate;
    @FXML PTableColumn<Nuclide, String> tableColMass;
    @FXML Button btnCalculate;
    private Shipment shipment;

    public HomePaneController() throws IOException {
        this(null);
    }

    public HomePaneController(PropHandler propHandler) throws IOException {
        super(propHandler, Page.HOME);
        this.shipment = new Shipment();
    }

    @Override
    @FXML public void initialize() {
        super.initialize();
        initTable();

        btnEdit.disableProperty().bind(Bindings.notEqual(1, Bindings.size(tableViewHome.getSelectionModel().getSelectedItems())));
        btnRemove.disableProperty().bind(Bindings.equal(0, Bindings.size(tableViewHome.getSelectionModel().getSelectedItems())));
        btnCalculate.disableProperty().bind(Bindings.isEmpty(tableViewHome.getItems()));
        setInit(true);
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

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public boolean isNuclideInTable(Nuclide nuclide) {
        if(nuclide == null ||
            getShipment() == null ||
            getShipment()
                .getNuclides()
                .isEmpty()) {
            return false;
        }
        return getShipment().getNuclides()
            .stream()
            .anyMatch(i -> i.getNuclideId().equals(nuclide.getNuclideId()) &&
                i.getLifeSpan().equals(nuclide.getLifeSpan()) &&
                i.getLungAbsorption().equals(nuclide.getLungAbsorption()));
    }

    public void updateNuclide(Nuclide newV) {
        if(newV != null) {
            try {
                if(tableViewHome.getSelectionModel().getSelectedItems().size() == 1) {
                    getShipment().getNuclides().set(
                        tableViewHome.getSelectionModel().getSelectedIndex(), newV
                    );
                }
            } catch (Exception ignored) {
                // exception is ignored
            }
        }
    }

    public List<Nuclide> getSelectedNuclides() {
        return tableViewHome.getSelectionModel().getSelectedItems();
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
        tableColNuclide.setCellValueFactory(new PropertyValueFactory<>("displayNameNotation"));
        tableColHalfLife.setCellValueFactory(i -> i.getValue().getConstants().displayHalfLifeProperty());
        tableColActivity.setCellValueFactory(new PropertyValueFactory<>("displayInitActivity"));
        tableColRefDate.setCellValueFactory(new PropertyValueFactory<>("refDate"));
        tableColMass.setCellValueFactory(new PropertyValueFactory<>("displayMass"));

        tableViewHome.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewHome.itemsProperty().bind(getShipment().nuclidesProperty());
    }

    protected void clearTable() {
        if(!tableViewHome.getSelectionModel().isEmpty()) {
            tableViewHome.getSelectionModel().clearSelection();
        }
    }

    /**
     * Helper function to handle the add button being pressed
     */
    @FXML protected void addBtnHandler() {
        logr.debug("User clicked the Add button on the home pane");
        clearTable();
        App.getStageHandler().showModal(FXMLView.MODIFY, Page.ADD);
    }

    /**
     * Helper function to handle the edit button being pressed
     */
    @FXML protected void editBtnHandler() {
        logr.debug("User clicked the Edit button on the home pane");
        App.getStageHandler().showModal(FXMLView.MODIFY, Page.EDIT);
    }

    /**
     * Helper function to handle the remove button being pressed
     * @throws RuntimeException if removal failed
     */
    @SuppressWarnings("java:S112")
    @FXML protected void removeBtnHandler() throws RuntimeException {
        logr.debug("User clicked the Remove button on the home pane");
        if(!getShipment().remove(tableViewHome.getSelectionModel().getSelectedItems())) {
            RuntimeException rte = new RuntimeException("Failed to remove the selection");
            logr.throwing(rte);
            throw rte;
        }
    }

    /**
     * Helper function to handle the calculate button being pressed
     */
    @FXML protected void calculateBtnHandler() {
        logr.debug("User clicked the Calculate button on the home pane");
        clearTable();
    }
}
