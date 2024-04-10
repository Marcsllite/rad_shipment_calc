package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.model.Isotope;
import com.marcsllite.model.PTableColumn;
import com.marcsllite.model.Shipment;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.PropHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
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
    @FXML GridPane homePane;
    @FXML Button btnAdd;
    @FXML Button btnEdit;
    @FXML Button btnRemove;
    @FXML TableView<Isotope> tableViewHome;
    @FXML PTableColumn<Isotope, String> tableColIsotope;
    @FXML PTableColumn<Isotope, Float> tableColHalfLife;
    @FXML PTableColumn<Isotope, String> tableColActivity;
    @FXML PTableColumn<Isotope, LocalDate> tableColRefDate;
    @FXML PTableColumn<Isotope, String> tableColMass;
    @FXML Button btnCalculate;
    private Shipment shipment;
    SimpleListProperty<Isotope> selectedIsotopes = new SimpleListProperty<>();

    private static final Logger logr = LogManager.getLogger();

    public HomePaneController() throws IOException {
        this(null);
    }

    public HomePaneController(PropHandler propHandler) throws IOException {
        super(propHandler);
        setPage(Page.HOME);
        setShipment(new Shipment());
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

    public boolean isIsoInTable(Isotope isotope) {
        if(isotope == null || getShipment().getIsotopes().isEmpty()) {
            return false;
        }
        return getShipment().getIsotopes()
            .stream()
            .anyMatch(i -> i.getAbbr().equals(isotope.getAbbr()) &&
                i.getLifeSpan().equals(isotope.getLifeSpan()) &&
                i.getLungAbsorption().equals(isotope.getLungAbsorption()));
    }

    public void updateIsotope(Isotope newV) {
        getShipment().getIsotopes().set(
            tableViewHome.getSelectionModel().getSelectedIndex(), newV
        );
    }

    public List<Isotope> getSelectedIsotopes() {
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
        tableColIsotope.setCellValueFactory(new PropertyValueFactory<>("abbr"));
        tableColHalfLife.setCellValueFactory(i -> i.getValue().getConstants().halfLifeProperty().asObject());
        tableColActivity.setCellValueFactory(new PropertyValueFactory<>("strInitActivity"));
        tableColRefDate.setCellValueFactory(new PropertyValueFactory<>("refDate"));
        tableColMass.setCellValueFactory(new PropertyValueFactory<>("strMass"));

        tableViewHome.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewHome.itemsProperty().bind(getShipment().isotopesProperty());
    }

    /**
     * Helper function to handle the add button being pressed
     */
    @FXML protected void addBtnHandler() {
        logr.debug("User clicked the Add button on the home pane");
        tableViewHome.getSelectionModel().clearSelection();
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
     */
    @FXML protected void removeBtnHandler() {
        logr.debug("User clicked the Remove button on the home pane");
        getShipment().remove(tableViewHome.getSelectionModel().getSelectedItems());
    }

    /**
     * Helper function to handle the calculate button being pressed
     */
    @FXML protected void calculateBtnHandler() {
        logr.debug("User clicked the Calculate button on the home pane");
        tableViewHome.getSelectionModel().clearSelection();
    }
}
