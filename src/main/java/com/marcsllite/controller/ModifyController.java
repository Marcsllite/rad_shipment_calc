package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.model.Isotope;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.IsotopeModel;
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
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
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
    @FXML private ChoiceBox<String> choiceBoxA0RadUnit;
    @FXML private VBox vBoxMoreInfo;
    @FXML private HBox hBoxAddInfoTop;
    @FXML private VBox vBoxLifeSpan;
    @FXML private ToggleGroup toggleGrpLifeSpan;
    @FXML private RadioButton radioBtnShortLived;
    @FXML private RadioButton radioBtnLongLived;
    @FXML private VBox vBoxLungAbs;
    @FXML private ToggleGroup toggleGrpLungAbs;
    @FXML private RadioButton radioBtnSlowLungAbs;
    @FXML private RadioButton radioBtnMediumLungAbs;
    @FXML private RadioButton radioBtnFastLungAbs;
    @FXML private Separator separatorAddInfo;
    @FXML private Text txtFirstPageStatus;
    @FXML private Button btnNext;

    // Second Page
    @FXML private VBox vBoxSecondPage;
    @FXML private DatePicker datePicker;
    @FXML private TextField txtFieldMass;
    @FXML private ComboBox<String> comboBoxMassPrefix;
    @FXML private ChoiceBox<String> choiceBoxMassUnit;
    @FXML private ChoiceBox<String> choiceBoxNature;
    @FXML private ChoiceBox<String> choiceBoxState;
    @FXML private ChoiceBox<String> choiceBoxForm;
    @FXML private CheckBox chckBoxSameMass;
    @FXML private CheckBox chckBoxSameNSF;
    @FXML private Button btnBack;
    @FXML private Button btnFinish;
    @FXML private Text txtSecondPageStatus;
    Isotope editingIso;
    ObservableList<IsotopeModel> isotopes = getDbService().getAllIsotopeModels();
    FilteredList<IsotopeModel> searchFilteredIsos =  new FilteredList<>(isotopes, null);
    FilteredList<IsotopeModel> filteredLifeSpanIsos = new FilteredList<>(isotopes, null);
    FilteredList<IsotopeModel> filteredLungAbsIsos = new FilteredList<>(isotopes, null);

    public ModifyController(BaseController.Page page) throws IOException {
        this(page, null);
    }

    public ModifyController(BaseController.Page page, PropHandler propHandler) throws IOException {
        super(propHandler);
        setPage(page);
    }

    public FilteredList<IsotopeModel> getFilteredLifeSpanIsos() {
        return filteredLifeSpanIsos;
    }

    public FilteredList<IsotopeModel> getFilteredLungAbsIsos() {
        return filteredLungAbsIsos;
    }

    public FilteredList<IsotopeModel> getSearchFilteredIsos() {
        return searchFilteredIsos;
    }

    @Override
    @FXML public void initialize() {
        if(!isInit()) {
            super.initialize();

            reset();
            setupDropDownItems();
            setRadioBtnTxt();
            setRadioBtnUserData();
            setupListeners();
            bindMassInputDisabledProp();
            bindNSFInputDisableProp();
            bindManagedPropToVisibility();

            if(Page.ADD.equals(getPage())) {
                initAddPage();
            } else if(Page.EDIT.equals(getPage())) {
                initEditPage();
            }

            showPage(1);
            setInit(true);
        }
    }


    private void bindManagedPropToVisibility() {
        hBoxAddInfoTop.managedProperty().unbind();
        vBoxLifeSpan.managedProperty().unbind();
        vBoxLungAbs.managedProperty().unbind();
        separatorAddInfo.managedProperty().unbind();
        txtFirstPageStatus.managedProperty().unbind();
        txtSecondPageStatus.managedProperty().unbind();

        // visibility of the node will decide if it is rendered by the parent pane
        hBoxAddInfoTop.managedProperty().bind(hBoxAddInfoTop.visibleProperty());
        vBoxLifeSpan.managedProperty().bind(vBoxLifeSpan.visibleProperty());
        vBoxLungAbs.managedProperty().bind(vBoxLungAbs.visibleProperty());
        separatorAddInfo.managedProperty().bind(separatorAddInfo.visibleProperty());
        txtFirstPageStatus.managedProperty().bind(txtFirstPageStatus.visibleProperty());
        txtSecondPageStatus.managedProperty().bind(txtSecondPageStatus.visibleProperty());
    }

    private void massListener(String str) {
        if(str == null || str.isBlank()) {
            btnFinish.setDisable(true);
        } else {
            // if user inputs any non-numerical characters, remove them
            String newTxt = str.replaceAll("\\D", "");
            btnFinish.setDisable(newTxt.isBlank());
            txtFieldMass.setText(newTxt);
        }
    }

    public Isotope getEditingIso() {
        return editingIso;
    }

    public void setEditingIso(Isotope editingIso) {
        this.editingIso = editingIso;
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

    protected void reset() {
        resetFirstPage();
        resetSecondPage();
    }
    
    protected void resetFirstPage() {
        txtFieldIsoName.setText(null);
        txtFieldA0.setText(null);
        setLungAbsVisible(false);
        setLifeSpanVisible(false);
        txtFirstPageStatus.setVisible(false);
        txtFirstPageStatus.setText(null);
        selectDefaultFirstPageDropDownValues();
    }
    
    protected void resetSecondPage() {
        datePicker.setValue(null);
        txtFieldMass.setText(null);
        chckBoxSameMass.setSelected(false);
        chckBoxSameNSF.setSelected(false);

        btnFinish.setText("Finish");
        txtSecondPageStatus.setVisible(false);
        txtSecondPageStatus.setText(null);
        selectDefaultSecondPageDropDownValues();
    }

    protected void setupListeners() {
        txtFieldIsoName.textProperty().removeListener((observable, oldV, newV) -> nameListener(newV));
        txtFieldA0.textProperty().removeListener((observable, oldV, newV) -> initialActivityListener(newV));
        txtFieldMass.textProperty().removeListener(((observable, oldV, newV) -> massListener(newV)));
        toggleGrpLifeSpan.selectedToggleProperty().removeListener((observable, oldV, newV) -> radioBtnListener(newV));
        toggleGrpLungAbs.selectedToggleProperty().removeListener((observable, oldV, newV) -> radioBtnListener(newV));

        txtFieldIsoName.textProperty().addListener((observable, oldV, newV) -> nameListener(newV));
        txtFieldA0.textProperty().addListener((observable, oldV, newV) -> initialActivityListener(newV));
        txtFieldMass.textProperty().addListener(((observable, oldV, newV) -> massListener(newV)));
        toggleGrpLifeSpan.selectedToggleProperty().addListener((observable, oldV, newV) -> radioBtnListener(newV));
        toggleGrpLungAbs.selectedToggleProperty().addListener((observable, oldV, newV) -> radioBtnListener(newV));
    }


    
    protected void initAddPage() {
        setEditingIso(null);
        btnNext.setDisable(true);
        btnFinish.setDisable(true);

        selectDefaultDropDownValues();
        datePicker.setValue(LocalDate.now());

        btnFinish.setText("Add");
        txtFirstPageStatus.setVisible(false);
        txtSecondPageStatus.setVisible(false);
        setLifeSpanVisible(false);
        setLungAbsVisible(false);
    }

    protected void initEditPage() {
        setEditingIso(getMain().getHomePaneController().getSelectedIsotopes().get(0));
        btnNext.setDisable(false);
        btnFinish.setDisable(false);
        
        setFirstPageValues();
        setSecondPageValues();
    }

    private void setFirstPageValues() {
        if(getEditingIso() != null) {
            txtFieldIsoName.setText(getEditingIso().getName());
            txtFieldA0.setText(String.valueOf(getEditingIso().getInitActivity()));
            comboBoxA0Prefix.getSelectionModel().select(getEditingIso().getInitActivityPrefix().getVal());
            choiceBoxA0RadUnit.getSelectionModel().select(getEditingIso().getMassUnit().getVal());
    
            txtFirstPageStatus.setVisible(false);
            txtFirstPageStatus.setText(null);
    
            String abbr = getEditingIso().getAbbr().toLowerCase();
            Pattern lungAbsPattern = Pattern.compile("[sfm]$");
            Matcher lungAbsMatch = lungAbsPattern.matcher(abbr);
            Pattern shortLongPattern = Pattern.compile("\\)$");
            Matcher shortLongMatch = shortLongPattern.matcher(abbr);
    
            if(shortLongMatch.find()) {
                setLifeSpanVisible(true);
                Optional<Toggle> toggle = toggleGrpLifeSpan.getToggles()
                    .stream()
                    .filter(t ->
                        Objects.equals(
                            Isotope.LifeSpan.toLifeSpan((String) t.getUserData()),
                            getEditingIso().getLifeSpan())
                    ).findFirst();
    
                toggle.ifPresent(value -> toggleGrpLifeSpan.selectToggle(value));
            }
    
            if(lungAbsMatch.find()) {
                setLungAbsVisible(true);
                Optional<Toggle> toggle = toggleGrpLungAbs.getToggles()
                    .stream()
                    .filter(t ->
                        Objects.equals(
                            Isotope.LungAbsorption.toLungAbsorption((String) t.getUserData()),
                            getEditingIso().getLungAbsorption())
                    ).findFirst();
    
                toggle.ifPresent(value -> toggleGrpLungAbs.selectToggle(value));
            }
        } else {
            resetFirstPage();
        }
    }

    private void setSecondPageValues() {
        if(getEditingIso() != null) {
            datePicker.setValue(getEditingIso().getRefDate());
            txtFieldMass.setText(String.valueOf(getEditingIso().getMass()));
            comboBoxMassPrefix.getSelectionModel().select(getEditingIso().getMassPrefix().getVal());
            choiceBoxMassUnit.getSelectionModel().select(getEditingIso().getMassUnit().getVal());
            choiceBoxNature.getSelectionModel().select(getEditingIso().getNature().getVal());
            choiceBoxState.getSelectionModel().select(getEditingIso().getState().getVal());
            choiceBoxForm.getSelectionModel().select(getEditingIso().getForm().getVal());
            chckBoxSameMass.setSelected(false);
            chckBoxSameNSF.setSelected(false);

            btnFinish.setText("Edit");
            txtSecondPageStatus.setVisible(false);
            txtSecondPageStatus.setText(null);
        } else {
            resetSecondPage();
        }
    }

    protected boolean isAddInfoNotProvided() {
        boolean isLifeSpan = vBoxLifeSpan.isVisible();
        boolean isLungAbs = vBoxLungAbs.isVisible();
        boolean ret = false;

        if(isLifeSpan) {
            ret = toggleGrpLifeSpan.getSelectedToggle() == null;
        }

        if(isLungAbs) {
            ret = toggleGrpLungAbs.getSelectedToggle() == null;
        }
        return ret;
    }

    protected void setRadioBtnTxt() {
        radioBtnSlowLungAbs.setText(Isotope.LungAbsorption.SLOW.getVal());
        radioBtnMediumLungAbs.setText(Isotope.LungAbsorption.MEDIUM.getVal());
        radioBtnFastLungAbs.setText(Isotope.LungAbsorption.FAST.getVal());
        radioBtnShortLived.setText(Isotope.LifeSpan.SHORT.getVal());
        radioBtnLongLived.setText(Isotope.LifeSpan.LONG.getVal());
    }
    
    protected void setRadioBtnUserData() {
        radioBtnSlowLungAbs.setUserData(Isotope.LungAbsorption.SLOW.getAbbrVal());
        radioBtnMediumLungAbs.setUserData(Isotope.LungAbsorption.MEDIUM.getAbbrVal());
        radioBtnFastLungAbs.setUserData(Isotope.LungAbsorption.FAST.getAbbrVal());
        radioBtnShortLived.setUserData(Isotope.LifeSpan.SHORT.getAbbrVal());
        radioBtnLongLived.setUserData(Isotope.LifeSpan.LONG.getAbbrVal());
    }

    protected void radioBtnListener(Toggle newV) {
        if(newV != null) {
            Isotope iso = buildEditedIso();
            boolean isInTable = isIsoInTable(iso);
            String a0 = txtFieldA0.getText();
            btnNext.setDisable(iso == null || a0 == null ||
                a0.isBlank() || isInTable);
        } else {
            btnNext.setDisable(true);
        }
    }

    protected void initialActivityListener(String str) {
        if(str == null || str.isBlank()) {
            btnNext.setDisable(true);
        } else {
            // if user inputs any non-numerical characters, remove them
            String newTxt = str.replaceAll("\\D", "");
            Isotope iso = buildEditedIso();
            btnNext.setDisable(iso == null || newTxt.isBlank() ||
                isAddInfoNotProvided() || isIsoInTable(iso));
            txtFieldA0.setText(newTxt);
        }
    }

    protected void setupDropDownItems() {
        comboBoxA0Prefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxA0RadUnit.setItems(Conversions.RadUnit.getFxValues());
        comboBoxMassPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxMassUnit.setItems(Conversions.MassUnit.getFxValues());
        choiceBoxNature.setItems(Isotope.Nature.getFxValues());
        choiceBoxState.setItems(LimitsModelId.State.getFxValues());
        choiceBoxForm.setItems(LimitsModelId.Form.getFxValues());
    }

    protected void selectDefaultDropDownValues() {
        selectDefaultFirstPageDropDownValues();
        selectDefaultSecondPageDropDownValues();
    }

    private void selectDefaultSecondPageDropDownValues() {
        comboBoxMassPrefix.getSelectionModel().select(Conversions.SIPrefix.BASE.getVal());
        choiceBoxMassUnit.getSelectionModel().select(Conversions.MassUnit.GRAMS.getVal());
        choiceBoxNature.getSelectionModel().select(Isotope.Nature.REGULAR.getVal());
        choiceBoxState.getSelectionModel().select(LimitsModelId.State.SOLID.getVal());
        choiceBoxForm.getSelectionModel().select(LimitsModelId.Form.NORMAL.getVal());
    }

    private void selectDefaultFirstPageDropDownValues() {
        comboBoxA0Prefix.getSelectionModel().select(Conversions.SIPrefix.BASE.getVal());
        choiceBoxA0RadUnit.getSelectionModel().select(Conversions.RadUnit.CURIE.getVal());
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

    protected void setLifeSpanVisible(boolean isLifeSpan) {
        hBoxAddInfoTop.setVisible(isLifeSpan || vBoxLungAbs.isVisible());
        vBoxLifeSpan.setVisible(isLifeSpan);
        if(!isLifeSpan) {
            toggleGrpLifeSpan.selectToggle(null);
        }
        separatorAddInfo.setVisible(isLifeSpan || vBoxLungAbs.isVisible());
    }

    protected void setLungAbsVisible(boolean isLungAbs) {
        hBoxAddInfoTop.setVisible(isLungAbs || vBoxLifeSpan.isVisible());
        vBoxLungAbs.setVisible(isLungAbs);
        if(!isLungAbs) {
            toggleGrpLungAbs.selectToggle(null);
        }
        separatorAddInfo.setVisible(isLungAbs || vBoxLifeSpan.isVisible());
    }

    protected void nameListener(String newV) {
        if (newV == null || newV.isBlank()) {
            searchFilteredIsos.setPredicate(isotope -> false);
            filteredLifeSpanIsos.setPredicate(isotope -> false);
            filteredLungAbsIsos.setPredicate(isotope -> false);
            setLifeSpanVisible(false);
            setLungAbsVisible(false);
            btnNext.setDisable(true);
            setDuplicateIso(false);
        } else {
            searchFilteredIsos.setPredicate(validIsoFilteringPredicate(newV));
            filteredLifeSpanIsos.setPredicate(lifeSpanFilteringPredicate(newV));
            filteredLungAbsIsos.setPredicate(lungAbsFilteringPredicate(newV));
            Isotope iso = buildEditedIso();
            setLifeSpanVisible(!filteredLifeSpanIsos.isEmpty());
            setLungAbsVisible(!vBoxLifeSpan.isVisible() && !filteredLungAbsIsos.isEmpty());
            String a0 = txtFieldA0.getText();
            btnNext.setDisable(iso == null ||
                a0 == null || a0.isBlank() || isAddInfoNotProvided() || isIsoInTable(iso));
        }
    }

    private boolean isEditingIso(Isotope isotope) {
        if(getEditingIso() == null || isotope == null) {
            return false;
        }
        return getEditingIso().getAbbr().equals(isotope.getAbbr()) &&
            getEditingIso().getLifeSpan().equals(isotope.getLifeSpan()) &&
            getEditingIso().getLungAbsorption().equals(isotope.getLungAbsorption());
    }

    private boolean isIsoInTable(Isotope isotope) throws InvalidParameterException {
        boolean ret = !isEditingIso(isotope) &&
            getMain().getHomePaneController().isIsoInTable(isotope);
        setDuplicateIso(ret);
        return ret;
    }

    protected void setDuplicateIso(boolean isInvalid) {
        txtFieldIsoName.getStyleClass().removeAll("validRegion", "invalidRegion");
        txtFieldIsoName.getStyleClass().add(isInvalid ? "invalidRegion" : "validRegion");
        txtFirstPageStatus.setVisible(isInvalid);
        txtFirstPageStatus.setText("Isotope already in the table.");
    }

    protected void bindMassInputDisabledProp() {
        txtFieldMass.disableProperty().unbind();
        comboBoxMassPrefix.disableProperty().unbind();
        choiceBoxMassUnit.disableProperty().unbind();

        txtFieldMass.disableProperty().bind(chckBoxSameMass.selectedProperty());
        comboBoxMassPrefix.disableProperty().bind(chckBoxSameMass.selectedProperty());
        choiceBoxMassUnit.disableProperty().bind(chckBoxSameMass.selectedProperty());
    }

    protected void bindNSFInputDisableProp() {
        choiceBoxNature.disableProperty().unbind();
        choiceBoxState.disableProperty().unbind();
        choiceBoxForm.disableProperty().unbind();

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

    protected Predicate<IsotopeModel> lifeSpanFilteringPredicate(String str) {
        if (str == null || str.isBlank()) {
            return null;
        }
        return isotope -> isLifeSpanIso(isotope, str);
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

        return (name.contains(searchStr) || abbr.contains(searchStr)) && matchAbbr.find();
    }

    protected boolean isLifeSpanIso(IsotopeModel isotope, String str) {
        if (str == null || str.isBlank()) {
            return false;
        }

        String searchStr = str.toLowerCase();

        String abbr = isotope.getIsotopeId().getAbbr().toLowerCase();
        String name = isotope.getIsotopeId().getName().toLowerCase();
        Pattern pattern = Pattern.compile("\\)$");
        Matcher matchAbbr = pattern.matcher(abbr);

        return (name.contains(searchStr) || abbr.contains(searchStr)) && matchAbbr.find();
    }

    protected Isotope buildIsoFromFirstPage() {
        Isotope iso = null;
        if(!btnNext.isDisabled()) {
            iso = new Isotope(searchFilteredIsos.get(0));
            iso.setInitActivity(Float.parseFloat(txtFieldA0.getText()));
            iso.setInitActivityPrefix(Conversions.SIPrefix.toSIPrefix(comboBoxA0Prefix.getValue()));
            iso.setInitActivityUnit(Conversions.RadUnit.toRadUnit(choiceBoxA0RadUnit.getValue()));
            iso.setStrInitActivity();
            iso.setLungAbsorption(Isotope.LungAbsorption.NONE);
            iso.setLifeSpan(Isotope.LifeSpan.REGULAR);

            setIsoLungAbs(iso);
            setIsoLifeSpan(iso);
        }
        return iso;
    }

    protected Isotope buildEditedIso() {
        Isotope iso = null;
        if(searchFilteredIsos.size() == 1 && searchFilteredIsos.get(0) != null) {
            iso = new Isotope(searchFilteredIsos.get(0));
            setIsoLifeSpan(iso);
            setIsoLungAbs(iso);
        }
        return iso;
    }

    private void setIsoLifeSpan(Isotope iso) {
        if(toggleGrpLifeSpan.getSelectedToggle() != null) {
            RadioButton radioBtn = (RadioButton) toggleGrpLifeSpan.getSelectedToggle();
            iso.setAbbr(iso.getAbbr() + radioBtn.getUserData());
            iso.setLifeSpan(Isotope.LifeSpan.toLifeSpan((String) radioBtn.getUserData()));
        } else {
            iso.setLifeSpan(null);
        }
    }

    protected void setIsoLungAbs(Isotope iso) {
        if(toggleGrpLungAbs.getSelectedToggle() != null) {
            RadioButton radioBtn = (RadioButton) toggleGrpLungAbs.getSelectedToggle();
            iso.setAbbr(iso.getAbbr() + radioBtn.getUserData());
            iso.setLungAbsorption(Isotope.LungAbsorption.toLungAbsorption((String) radioBtn.getUserData()));
        } else {
            iso.setLungAbsorption(null);
        }
    }

    protected Isotope buildIso() {
        Isotope iso = buildIsoFromFirstPage();
        if(iso != null && !btnFinish.isDisabled()) {
            iso.setRefDate(datePicker.getValue());
            if(chckBoxSameMass.isDisabled()) {
                Shipment shipment = getMain().getHomePaneController().getShipment();
                iso.setMass(shipment.getMass());
                iso.setMassPrefix(shipment.getMassPrefix());
                iso.setMassUnit(shipment.getMassUnit());
            } else {
                iso.setMass(Float.parseFloat(txtFieldMass.getText()));
                iso.setMassPrefix(Conversions.SIPrefix.toSIPrefix(comboBoxMassPrefix.getValue()));
                iso.setMassUnit(Conversions.MassUnit.toMass(choiceBoxMassUnit.getValue()));
            }
            iso.setStrMass();

            if(chckBoxSameNSF.isDisabled()) {
                Shipment shipment = getMain().getHomePaneController().getShipment();
                iso.setNature(shipment.getNature());
                iso.setState(shipment.getState());
                iso.setForm(shipment.getForm());
            } else {
                iso.setNature(Isotope.Nature.toNature(choiceBoxNature.getValue()));
                iso.setState(LimitsModelId.State.toState(choiceBoxState.getValue()));
                iso.setForm(LimitsModelId.Form.toForm(choiceBoxForm.getValue()));
            }
            iso.getConstants().dbInit(iso.getIsoId(), iso.getLimitsId());
        }
        return iso;
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
    @FXML protected void nextBtnHandler() {
        logr.debug("User clicked the Next button on the {} pane", getPage());
        showPage(2);
    }

    /**
     * Helper function to handle the add button being pressed
     */
    @FXML protected void backBtnHandler() {
        logr.debug("User clicked the Back button on the {} pane", getPage());
        showPage(1);
    }

    /**
     * Helper function to handle the finish button being pressed
     */
    @FXML protected void finishBtnHandler() {
        logr.debug("User clicked the Finish button on the {} pane", getPage());
        if(Page.ADD.equals(getPage())) {
            Shipment shipment = getMain().getHomePaneController().getShipment();
            shipment.add(buildIso());
        }
        if(Page.EDIT.equals(getPage())) {
            getMain().getHomePaneController().updateIsotope(buildIso());
        }
        App.getStageHandler().closeSecondary();
    }
}
