package com.marcsllite.controller;

import com.marcsllite.model.Isotope;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.ResourceBundle;

public class ModifyController extends BaseController {
    @FXML private StackPane modifyPane;
    // First Page
    @FXML private VBox vBoxFirstPage;
    @FXML private TextField txtFieldIsoName;
    @FXML private TextField txtFieldA0;
    @FXML private ChoiceBox<String> choiceBoxA0Prefix;
    @FXML private ChoiceBox<String> choiceBoxA0Name;
    @FXML private VBox vBoxMoreInfo;
    @FXML private HBox hBoxAddInfoTop;
    @FXML private VBox vBoxShortLong;
    @FXML private RadioButton radioBtnShortLived;
    @FXML private ToggleGroup toggleGrpShortLong;
    @FXML private RadioButton radioBtnLongLived;
    @FXML private VBox vBoxLungAbs;
    @FXML private RadioButton radioBtnSlowLungAbs;
    @FXML private ToggleGroup toggleGrpLungAbs;
    @FXML private RadioButton radioBtnMediumLungAbs;
    @FXML private RadioButton radioBtnFastLungAbs;
    @FXML private Text txtFirstPageStatus;
    @FXML private Button btnNext;

    // Second Page
    @FXML private VBox vBoxSecondPage;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtFieldMass;
    @FXML private ChoiceBox<String> choiceBoxMassPrefix;
    @FXML private ChoiceBox<String> choiceBoxMassName;
    @FXML private ChoiceBox<String> choiceBoxNature;
    @FXML private ChoiceBox<String> choiceBoxState;
    @FXML private ChoiceBox<String> choiceBoxForm;
    @FXML private CheckBox chckBoxSameMass;
    @FXML private CheckBox chckBoxSameNSF;
    @FXML private Button btnBack;
    @FXML private Button btnFinish;
    @FXML private Text txtSecondPageStatus;

    private static final Logger logr = LogManager.getLogger();
    private final PropHandler propHandler;

    @FXML public void initialize(){
        // adding values to the choice boxes
        choiceBoxA0Prefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxA0Name.setItems(Isotope.RadUnit.getFxValues());
        choiceBoxMassPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxMassName.setItems(Isotope.Mass.getFxValues());
        choiceBoxNature.setItems(Isotope.Nature.getFxValues());
        choiceBoxState.setItems(LimitsModelId.State.getFxValues());
        choiceBoxForm.setItems(LimitsModelId.Form.getFxValues());

        // Default value for choice boxes
        choiceBoxA0Prefix.getSelectionModel().select(Conversions.SIPrefix.BASE.getVal());
        choiceBoxA0Name.getSelectionModel().select(Isotope.RadUnit.CURIE.getVal());
        choiceBoxMassPrefix.getSelectionModel().select(Conversions.SIPrefix.BASE.getVal());
        choiceBoxMassName.getSelectionModel().select(Isotope.Mass.GRAMS.getVal());
    }

    public ModifyController() {
        this((PropHandler) ResourceBundle.getBundle(PropHandler.PROP_NAME, new PropHandlerFactory()));
    }

    public ModifyController(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    @Override
    public void show() {
        modifyPane.setVisible(true);
        modifyPane.toFront();
    }

    @Override
    public void hide() {
        modifyPane.setVisible(false);
        modifyPane.toBack();
    }

    /*///////////////////////////////////////////// HOME PANE CONTROLLER /////////////////////////////////////////////*/

    /**
     * FXML function to handle any menu button pressed
     *
     * @param event the action performed
     */
    @FXML protected void modifyHandler(ActionEvent event) throws InvalidParameterException {
        if(event == null) {
            var e = new InvalidParameterException("action event cannot be null");
            logr.throwing(e);
            throw e;
        }

        else if(event.getSource() == btnNext) {
            nextBtnHandler();
        } else if(event.getSource() == chckBoxSameMass) {
            sameMassChckBoxHandler();
        } else if(event.getSource() == chckBoxSameNSF) {
            sameNSFChckBoxHandler();
        } else if(event.getSource() == btnBack) {
            backBtnHandler();
        } else if(event.getSource() == btnFinish) {
            finishBtnHandler();
        }
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Helper function to handle the next button being pressed
     */
    @FXML protected void nextBtnHandler(){
        logr.debug("User clicked the Next button on the modify pane");
        // TODO: implement clicking on next button
    }

    /**
     * Helper function to handle the same mass being pressed
     */
    @FXML protected void sameMassChckBoxHandler(){
        logr.debug("User clicked the Same Mass checkbox on the modify pane");
        // TODO: implement clicking on same mass checkbox
    }

    /**
     * Helper function to handle the same nature,state,form checkbox being clicked
     */
    @FXML protected void sameNSFChckBoxHandler(){
        logr.debug("User clicked the Same Nature, State, Form checkbox on the modify pane");
        // TODO: implement clicking on same nature,state,form checkbox
    }

    /**
     * Helper function to handle the add button being pressed
     */
    @FXML protected void backBtnHandler(){
        logr.debug("User clicked the Back button on the modify pane");
        // TODO: implement clicking on back button
    }

    /**
     * Helper function to handle the finish button being pressed
     */
    @FXML protected void finishBtnHandler(){
        logr.debug("User clicked the Finish button on the modify pane");
        // TODO: implement clicking on finish button
    }
}
