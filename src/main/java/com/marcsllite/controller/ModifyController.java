package com.marcsllite.controller;

import com.marcsllite.model.Isotope;
import com.marcsllite.model.db.IsotopeModel;
import com.marcsllite.model.db.IsotopeModelId;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.handler.PropHandler;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyController extends BaseController {
    private static final Logger logr = LogManager.getLogger();
    @FXML private StackPane modifyPane;
    // First Page
    @FXML private VBox vBoxFirstPage;
    @FXML private TextField txtFieldIsoName;
    @FXML private TextField txtFieldA0;
    @FXML private ComboBox<String> comboBoxA0Prefix;
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
    @FXML private ComboBox<String> comboBoxMassPrefix;
    @FXML private ChoiceBox<String> choiceBoxMassName;
    @FXML private ChoiceBox<String> choiceBoxNature;
    @FXML private ChoiceBox<String> choiceBoxState;
    @FXML private ChoiceBox<String> choiceBoxForm;
    @FXML private CheckBox chckBoxSameMass;
    @FXML private CheckBox chckBoxSameNSF;
    @FXML private Button btnBack;
    @FXML private Button btnFinish;
    @FXML private Text txtSecondPageStatus;
    ObservableList<IsotopeModel> isotopes = getDbService().getAllIsotopeModels();
    FilteredList<IsotopeModel> searchFilteredIsos =  new FilteredList<>(isotopes, null);

    public ModifyController() throws IOException {
        this(null);
    }

    public ModifyController(BaseController.Page page) throws IOException {
        this(page, null);
    }

    public ModifyController(BaseController.Page page, PropHandler propHandler) throws IOException {
        super(propHandler);
        setPage(page);
    }

    public FilteredList<IsotopeModel> getSearchFilteredIsos() {
        return searchFilteredIsos;
    }

    @Override
    @FXML public void initialize() {
        super.initialize();

        setupDropDownItems();

        txtFieldA0.textProperty().addListener((observable, oldV, newV) -> {
            if(newV == null || newV.isBlank()) {
                btnNext.setDisable(true);
            } else {
                // if user inputs any non-numerical characters, remove them
                String newTxt = newV.replaceAll("\\D", "");
                String name = txtFieldIsoName.getText();
                btnNext.setDisable(name == null || name.isBlank() ||
                        searchFilteredIsos.size() != 1 || newTxt.isBlank());

                txtFieldA0.setText(newTxt);
            }
        });

        bindMassInputDisabledProp();
        bindNSFInputDisableProp();

        // visibility of the node will decide if it is rendered by the parent pane
        hBoxAddInfoTop.managedProperty().bind(hBoxAddInfoTop.visibleProperty());
        vBoxShortLong.managedProperty().bind(vBoxShortLong.visibleProperty());
        vBoxLungAbs.managedProperty().bind(vBoxLungAbs.visibleProperty());
        txtFirstPageStatus.managedProperty().bind(txtFirstPageStatus.visibleProperty());
        txtSecondPageStatus.managedProperty().bind(txtSecondPageStatus.visibleProperty());
        btnBack.managedProperty().bind(btnBack.visibleProperty());

        if(Page.ADD.equals(getPage())) {
            initAddPage();
        } else if(Page.EDIT.equals(getPage())) {
            initEditPage();
        }

        showPage(1);
        setInit(true);
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

    protected void initAddPage() {
        btnNext.setDisable(true);

        // Default value for choice boxes
        comboBoxA0Prefix.getSelectionModel().select(Conversions.SIPrefix.BASE.getVal());
        choiceBoxA0Name.getSelectionModel().select(Isotope.RadUnit.CURIE.getVal());
        comboBoxMassPrefix.getSelectionModel().select(Conversions.SIPrefix.BASE.getVal());
        choiceBoxMassName.getSelectionModel().select(Isotope.Mass.GRAMS.getVal());

        setupNameListener();

        txtFirstPageStatus.setVisible(false);
        setShortLong(false);
        setLungAbs(false);
    }

    protected void initEditPage() {
        initAddPage();
    }

    protected void setupDropDownItems() {
        comboBoxA0Prefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxA0Name.setItems(Isotope.RadUnit.getFxValues());
        comboBoxMassPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxMassName.setItems(Isotope.Mass.getFxValues());
        choiceBoxNature.setItems(Isotope.Nature.getFxValues());
        choiceBoxState.setItems(LimitsModelId.State.getFxValues());
        choiceBoxForm.setItems(LimitsModelId.Form.getFxValues());
    }

    protected void showPage(int pageNum) {
        vBoxFirstPage.setVisible(pageNum == 1);
        vBoxSecondPage.setVisible(pageNum == 2);

        if(pageNum == 1) {
            vBoxSecondPage.toBack();
        } else {
            vBoxFirstPage.toBack();
        }
    }

    protected void setShortLong(boolean isShortLong) {
        hBoxAddInfoTop.setVisible(isShortLong || vBoxLungAbs.isVisible());
        vBoxShortLong.setVisible(isShortLong);
    }

    protected void setLungAbs(boolean isLungAbs) {
        hBoxAddInfoTop.setVisible(isLungAbs || vBoxShortLong.isVisible());
        vBoxLungAbs.setVisible(isLungAbs);
    }

    protected void setupNameListener() {
        FilteredList<IsotopeModel> filteredLungAbsIsos =  new FilteredList<>(isotopes, null);

        txtFieldIsoName.textProperty().addListener(
            (observable, oldV, newV) -> {
                if (newV == null || newV.isBlank()) {
                    searchFilteredIsos.setPredicate(isotope -> false);
                    filteredLungAbsIsos.setPredicate(isotope -> false);
                    setShortLong(false);
                    setLungAbs(false);
                    btnNext.setDisable(true);
                } else {
                    searchFilteredIsos.setPredicate(validIsoFilteringPredicate(newV));
                    filteredLungAbsIsos.setPredicate(lungAbsFilteringPredicate(newV));
                    setShortLong(isShortLongIso(newV));
                    setLungAbs(!isShortLongIso(newV) && !filteredLungAbsIsos.isEmpty());
                    String a0 = txtFieldA0.getText();
                    btnNext.setDisable(searchFilteredIsos.size() != 1 ||
                        a0 == null || a0.isBlank());
                }
            }
        );
    }

    protected void bindMassInputDisabledProp() {
        txtFieldMass.disableProperty().bind(chckBoxSameMass.selectedProperty());
        comboBoxMassPrefix.disableProperty().bind(chckBoxSameMass.selectedProperty());
        choiceBoxMassName.disableProperty().bind(chckBoxSameMass.selectedProperty());
    }

    protected void bindNSFInputDisableProp() {
        choiceBoxNature.disableProperty().bind(chckBoxSameNSF.selectedProperty());
        choiceBoxState.disableProperty().bind(chckBoxSameNSF.selectedProperty());
        choiceBoxForm.disableProperty().bind(chckBoxSameNSF.selectedProperty());
    }

    protected Predicate<IsotopeModel> validIsoFilteringPredicate(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }
        return isotope -> isValidIso(isotope, str);
    }

    protected Predicate<IsotopeModel> lungAbsFilteringPredicate(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }
        return isotope -> isLungAbsIso(isotope, str);
    }

    protected boolean isValidIso(IsotopeModel isotope, String str) {
        if(isotope == null || str == null || str.isBlank()) {
            return false;
        }


        String abbr = isotope.getIsotopeId().getAbbr();
        String name = isotope.getIsotopeId().getName();

        return abbr.equalsIgnoreCase(str) || name.equalsIgnoreCase(str);
    }

    protected boolean isLungAbsIso(IsotopeModel isotope, String str) {
        if(isotope == null || str == null || str.isBlank()) {
            return false;
        }

        String searchStr = str.toLowerCase();

        String abbr = isotope.getIsotopeId().getAbbr().toLowerCase();
        String name = isotope.getIsotopeId().getName().toLowerCase();
        Pattern pattern = Pattern.compile("[sfm]$");
        Matcher matchAbbr = pattern.matcher(abbr);
        Matcher matchName = pattern.matcher(name);

        return (abbr.contains(searchStr) && matchAbbr.find()) ||
            (name.contains(searchStr) && matchName.find());
    }

    protected boolean isShortLongIso(String str) {
        List<IsotopeModelId> shortLongIsoModels = new ArrayList<>(2);
        shortLongIsoModels.add(new IsotopeModelId("Europium-150","Eu-150"));
        shortLongIsoModels.add(new IsotopeModelId("Neptunium-236", "Np-236"));

        if (str == null || str.isBlank()) {
            return false;
        }

        String searchStr = str.toLowerCase();

        return shortLongIsoModels.stream().anyMatch(modelId ->
            modelId.getAbbr().toLowerCase().contains(searchStr) ||
                modelId.getName().toLowerCase().contains(searchStr));
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
        logr.debug("User clicked the Next button on the {} pane", getPage());
        showPage(2);
    }

    /**
     * Helper function to handle the add button being pressed
     */
    @FXML protected void backBtnHandler(){
        logr.debug("User clicked the Back button on the {} pane", getPage());
        showPage(1);
    }

    /**
     * Helper function to handle the finish button being pressed
     */
    @FXML protected void finishBtnHandler(){
        logr.debug("User clicked the Finish button on the {} pane", getPage());
        // TODO: implement clicking on finish button
    }
}
