package rad.shipment.calculator.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import rad.shipment.calculator.helpers.Isotope;

import java.security.InvalidParameterException;
import java.util.logging.Logger;

public class HomePaneController {
    // Declaring FXML objects
    @FXML Button btnAdd;
    @FXML Button btnEdit;
    @FXML Button btnRemove;
    @FXML private TableView<Isotope> tableView;
    @FXML Button btnCalculate;

    // Declaring variables
    private static final Logger LOGR = Logger.getLogger(HomePaneController.class.getName());  // getting logger
    // Integer property to keep track of the number of selected rows in the table at any given time
    private static final IntegerProperty selectedTableRows = new SimpleIntegerProperty();
    private MainController mainController;

    /*///////////////////////////////////////////////// START/SETUP //////////////////////////////////////////////////*/

    /**
     * FXML Function to initialize GUI (run when the edit.fxml file is loaded by the FXMLLoader)
     */
    @FXML private void initialize(){
        initTable();

        // Allowing the user to press enter to select a button if focused on it
        MainController.setBtnFireOnEnter(btnAdd);
        MainController.setBtnFireOnEnter(btnEdit);
        MainController.setBtnFireOnEnter(btnRemove);
        MainController.setBtnFireOnEnter(btnCalculate);

        //disabling edit button if more than 1 or no rows are selected
        btnEdit.disableProperty().bind(
                Bindings.or(Bindings.isEmpty(getSelectedRows()), selectedTableRows.greaterThan(1)));

        // disabling remove button if no row is selected
        btnRemove.disableProperty().bind(Bindings.isEmpty(getSelectedRows()));
    }

    /**
     * Function to connect the Menu Pane Controller with the current MainController
     * (Allows the Menu Pane Controller to have access to the other controllers in the MainController through this link)
     *
     * @param mainController the instance of the current MainController
     */
    void injectMainController(MainController mainController){ setMainController(mainController); }

    /**
     * Helper function to initialize table columns to display their respective info correctly
     */
    protected void initTable(){
        // allowing the user to select more than one row in the table
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // keeping track of the number of rows the user has selected using the tableView's getSelectedRows function
        getSelectedRows().addListener((ListChangeListener<Isotope>) row -> {
            while(row.next()){
                if(row.wasAdded() || row.wasRemoved()) selectedTableRows.set(getSelectedRows().size());
            }
        });

        // allowing the user to press the delete or backspace key to remove selected isotope rows from the table
        tableView.setOnKeyPressed( event -> {
            if ((event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE)) removeBtnHandler();
        });

        // un-selecting selected rows if user clicks the add button or the calculate button
        tableView.focusedProperty().addListener((observable, oldV, newV) -> {
            if(!newV && (btnAdd.isPressed() || btnCalculate.isPressed())) tableView.getSelectionModel().clearSelection();
        });
    }

    /*///////////////////////////////////////////// MENU PANE CONTROLLER /////////////////////////////////////////////*/

    /**
     * FXML function to handle any button pressed on the home pane
     *
     * @param event the action performed
     */
    @FXML private void homePaneHandler(ActionEvent event) {
        if(event == null) throw new InvalidParameterException("action event cannot be null");

        else if(event.getSource() == btnAdd) addBtnHandler();
        else if(event.getSource() == btnEdit) editBtnHandler();
        else if(event.getSource() == btnRemove) removeBtnHandler();
        else if(event.getSource() == btnCalculate) calculateBtnHandler();
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/
    /**
     * Helper function to handle the add button being pressed
     */
    private void addBtnHandler(){
        LOGR.info("User clicked the " + btnAdd.getText() + " button.");
    }

    /**
     * Helper function to handle the edit button being pressed
     */
    private void editBtnHandler(){
        LOGR.info("User clicked the " + btnEdit.getText() + " button.");
    }

    /**
     * Helper function to handle the remove button being pressed
     */
    private void removeBtnHandler(){
        LOGR.info("User clicked the " + btnRemove.getText() + " button.");
    }

    /**
     * Helper function to handle the calculate button being pressed
     */
    private void calculateBtnHandler(){
        LOGR.info("User clicked the " + btnCalculate.getText() + " button.");
    }

    /**
     * Helper function to get the list of selected rows
     *
     * @return the Observable list of DeviceInfo which represents
     *          all the selected Devices in teh table at that time
     */
    ObservableList<Isotope> getSelectedRows(){
        return tableView.getSelectionModel().getSelectedItems();
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/
    /**
     * Getter function to get the mainController instance
     *
     * @return the main controller instance
     */
    protected MainController getMainController() { return mainController; }

    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/
    /**
     * Setter function to set the mainController instance
     *
     * @param mainController the new mainController instance
     */
    protected void setMainController(MainController mainController) {
        if(mainController == null) throw new InvalidParameterException("mainController cannot be null");
        this.mainController = mainController;
    }
}
