package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.NuclideUtils;
import com.marcsllite.util.controller.ModifyUtils;
import com.marcsllite.util.handler.PropHandler;
import javafx.collections.FXCollections;
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
import org.codehaus.plexus.util.StringUtils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class ModifyController extends BaseController {
    private static final Logger logr = LogManager.getLogger();
    @FXML StackPane modifyPane;
    // First Page
    @FXML VBox vBoxFirstPage;
    @FXML TextField txtFieldNuclideName;
    @FXML TextField txtFieldA0;
    @FXML ComboBox<String> comboBoxA0Prefix;
    @FXML ChoiceBox<String> choiceBoxA0RadUnit;
    @FXML VBox vBoxMoreInfo;
    @FXML HBox hBoxAddInfoTop;
    @FXML VBox vBoxLifeSpan;
    @FXML ToggleGroup toggleGrpLifeSpan;
    @FXML RadioButton radioBtnShortLived;
    @FXML RadioButton radioBtnLongLived;
    @FXML VBox vBoxLungAbs;
    @FXML ToggleGroup toggleGrpLungAbs;
    @FXML RadioButton radioBtnSlowLungAbs;
    @FXML RadioButton radioBtnMediumLungAbs;
    @FXML RadioButton radioBtnFastLungAbs;
    @FXML Separator separatorAddInfo;
    @FXML Text txtFirstPageStatus;
    @FXML Button btnNext;

    // Second Page
    @FXML VBox vBoxSecondPage;
    @FXML DatePicker datePicker;
    @FXML TextField txtFieldMass;
    @FXML ComboBox<String> comboBoxMassPrefix;
    @FXML ChoiceBox<String> choiceBoxMassUnit;
    @FXML ChoiceBox<String> choiceBoxNature;
    @FXML ChoiceBox<String> choiceBoxState;
    @FXML ChoiceBox<String> choiceBoxForm;
    @FXML CheckBox chckBoxSameMass;
    @FXML CheckBox chckBoxSameNSF;
    @FXML Button btnBack;
    @FXML Button btnFinish;
    @FXML Text txtSecondPageStatus;
    Nuclide editingNuclide;
    ObservableList<NuclideModel> nuclides;
    FilteredList<NuclideModel> searchFilteredNuclides;
    FilteredList<NuclideModel> filteredLifeSpanNuclides;
    FilteredList<NuclideModel> filteredLungAbsNuclides;

    public ModifyController(BaseController.Page page) throws IOException {
        this(page, null);
    }

    public ModifyController(BaseController.Page page, PropHandler propHandler) throws IOException {
        super(propHandler, page);
    }

    public Nuclide getEditingNuclide() {
        return editingNuclide;
    }

    public void setEditingNuclide(Nuclide editingNuclide) {
        this.editingNuclide = editingNuclide;
    }

    public ObservableList<NuclideModel> getNuclides() {
        return nuclides;
    }

    public void setNuclides(ObservableList<NuclideModel> nuclides) {
        this.nuclides = nuclides == null? FXCollections.observableArrayList() : nuclides;
        getNuclides().forEach(nuclide -> {
            String massNum = nuclide.getNuclideId().getMassNumber();
            nuclide.setLifeSpan(NuclideUtils.parseLifeSpanFromMassNumber(massNum));
            nuclide.setLungAbsorption(NuclideUtils.parseLungAbsFromMassNumber(massNum));
        });

        setSearchFilteredNuclides(new FilteredList<>(getNuclides(), null));

        ObservableList<NuclideModel> lifeSpanNuclides = getNuclides().stream()
            .filter(nuclide -> !Nuclide.LifeSpan.REGULAR.equals(nuclide.getLifeSpan()))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
        setFilteredLifeSpanNuclides(new FilteredList<>(lifeSpanNuclides, null));

        ObservableList<NuclideModel> lungAbsNuclides = getNuclides().stream()
            .filter(nuclide -> !Nuclide.LungAbsorption.NONE.equals(nuclide.getLungAbsorption()))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
        setFilteredLungAbsNuclides(new FilteredList<>(lungAbsNuclides, null));
    }

    public FilteredList<NuclideModel> getFilteredLifeSpanNuclides() {
        return filteredLifeSpanNuclides;
    }

    public void setFilteredLifeSpanNuclides(FilteredList<NuclideModel> filteredLifeSpanNuclides) {
        this.filteredLifeSpanNuclides = filteredLifeSpanNuclides == null?
            new FilteredList<>(FXCollections.observableArrayList(), null) :
            filteredLifeSpanNuclides;
    }

    public FilteredList<NuclideModel> getFilteredLungAbsNuclides() {
        return filteredLungAbsNuclides;
    }

    public void setFilteredLungAbsNuclides(FilteredList<NuclideModel> filteredLungAbsNuclides) {
        this.filteredLungAbsNuclides = filteredLungAbsNuclides == null?
            new FilteredList<>(FXCollections.observableArrayList(), null) :
            filteredLungAbsNuclides;
    }

    public FilteredList<NuclideModel> getSearchFilteredNuclides() {
        return searchFilteredNuclides;
    }

    public void setSearchFilteredNuclides(FilteredList<NuclideModel> searchFilteredNuclides) {
        this.searchFilteredNuclides = searchFilteredNuclides == null?
            new FilteredList<>(FXCollections.observableArrayList(), null) :
            searchFilteredNuclides;
    }

    @Override
    @FXML public void initialize() {
        if(!isInit()) {
            super.initialize();

            setNuclides(getDbService().getAllNuclideModels());

            reset();
            if(!setupDropDowns()) {
                RuntimeException rte = new RuntimeException("Failed to setup drop down items.");
                logr.throwing(rte);
                throw rte;
            }
            ModifyUtils.setRadioBtnData(radioBtnShortLived, Nuclide.LifeSpan.SHORT.getAbbrVal());
            ModifyUtils.setRadioBtnData(radioBtnLongLived, Nuclide.LifeSpan.LONG.getAbbrVal());
            ModifyUtils.setRadioBtnData(radioBtnSlowLungAbs, Nuclide.LungAbsorption.SLOW.getAbbrVal());
            ModifyUtils.setRadioBtnData(radioBtnMediumLungAbs, Nuclide.LungAbsorption.MEDIUM.getAbbrVal());
            ModifyUtils.setRadioBtnData(radioBtnFastLungAbs, Nuclide.LungAbsorption.FAST.getAbbrVal());
            ModifyUtils.setupTextListener(txtFieldNuclideName, ((obs, oldV, newV) -> nameListener(newV)));
            ModifyUtils.setupTextListener(txtFieldA0, ((obs, oldV, newV) -> initialActivityListener(newV)));
            ModifyUtils.setupTextListener(txtFieldMass, ((obs, oldV, newV) -> massListener(newV)));
            ModifyUtils.setupToggleListener(toggleGrpLifeSpan, ((obs, oldV, newV) -> radioBtnListener(newV)));
            ModifyUtils.setupToggleListener(toggleGrpLungAbs, ((obs, oldV, newV) -> radioBtnListener(newV)));
            ModifyUtils.bindDisabledPropToCheckBox(txtFieldMass, chckBoxSameMass);
            ModifyUtils.bindDisabledPropToCheckBox(comboBoxMassPrefix, chckBoxSameMass);
            ModifyUtils.bindDisabledPropToCheckBox(choiceBoxMassUnit, chckBoxSameMass);
            ModifyUtils.bindDisabledPropToCheckBox(choiceBoxNature, chckBoxSameNSF);
            ModifyUtils.bindDisabledPropToCheckBox(choiceBoxState, chckBoxSameNSF);
            ModifyUtils.bindDisabledPropToCheckBox(choiceBoxForm, chckBoxSameNSF);
            ModifyUtils.bindManagedPropToVisibility(hBoxAddInfoTop);
            ModifyUtils.bindManagedPropToVisibility(vBoxLifeSpan);
            ModifyUtils.bindManagedPropToVisibility(vBoxLungAbs);
            ModifyUtils.bindManagedPropToVisibility(separatorAddInfo);
            ModifyUtils.bindManagedPropToVisibility(txtFirstPageStatus);
            ModifyUtils.bindManagedPropToVisibility(txtSecondPageStatus);

            switch(getPage()){
                case ADD:
                    initAddPage();
                    break;
                case EDIT:
                    initEditPage();
                    break;
                default:
            }

            showPage(1);
            setInit(true);
        }
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

    public void showPage(int pageNum) {
        vBoxFirstPage.setVisible(pageNum == 1);
        vBoxSecondPage.setVisible(pageNum == 2);

        if(pageNum == 1) {
            vBoxSecondPage.toBack();
        } else {
            vBoxFirstPage.toBack();
        }
    }

    public void initAddPage() {
        setEditingNuclide(null);
        selectDefaultDropDownFirstPage();
        setLifeSpanVisible(false);
        setLungAbsVisible(false);
        txtFirstPageStatus.setVisible(false);
        btnNext.setDisable(true);

        datePicker.setValue(LocalDate.now());
        selectDefaultDropDownSecondPage();
        txtSecondPageStatus.setVisible(false);
        btnFinish.setText("Add");
        btnFinish.setDisable(true);
    }

    public void initEditPage() {
        setEditingNuclide(getMain().getHomePaneController().getSelectedNuclides().get(0));
        setFirstPageValues();
        setSecondPageValues();

        btnNext.setDisable(false);
        btnFinish.setDisable(false);
    }

    public void setFirstPageValues() {
        if (getEditingNuclide() == null) {
            resetFirstPage();
        } else {
            txtFieldNuclideName.setText(getEditingNuclide().getDisplayNameNotation());
            txtFieldA0.setText(getEditingNuclide().getInitActivityStr());
            comboBoxA0Prefix.getSelectionModel().select(getEditingNuclide().getInitActivityPrefix().getVal());
            choiceBoxA0RadUnit.getSelectionModel().select(getEditingNuclide().getInitActivityUnit().getVal());
            setLifeSpanVisible(ModifyUtils.setLifeSpanToggle(toggleGrpLifeSpan, getEditingNuclide().getLifeSpan()));
            setLungAbsVisible(ModifyUtils.setLungAbsToggle(toggleGrpLungAbs, getEditingNuclide().getLungAbsorption()));
            txtFirstPageStatus.setText(null);
            txtFirstPageStatus.setVisible(false);
        }
    }

    public void setSecondPageValues() {
        if (getEditingNuclide() == null) {
            resetSecondPage();
        } else {
            datePicker.setValue(getEditingNuclide().getRefDate());
            txtFieldMass.setText(getEditingNuclide().getMassStr());
            comboBoxMassPrefix.getSelectionModel().select(getEditingNuclide().getMassPrefix().getVal());
            choiceBoxMassUnit.getSelectionModel().select(getEditingNuclide().getMassUnit().getVal());
            choiceBoxNature.getSelectionModel().select(getEditingNuclide().getNature().getVal());
            choiceBoxState.getSelectionModel().select(getEditingNuclide().getLimitsId().getState().getVal());
            choiceBoxForm.getSelectionModel().select(getEditingNuclide().getLimitsId().getForm().getVal());
            chckBoxSameMass.setSelected(false);
            chckBoxSameNSF.setSelected(false);
            btnFinish.setText("Edit");
            txtSecondPageStatus.setVisible(false);
            txtSecondPageStatus.setText(null);
        }
    }

    public void reset() {
        resetFirstPage();
        resetSecondPage();
    }

    @SuppressWarnings("java:S112")
    public void resetFirstPage() throws RuntimeException {
        txtFieldNuclideName.setText(null);
        txtFieldA0.setText(null);
        setLifeSpanVisible(false);
        setLungAbsVisible(false);
        txtFirstPageStatus.setVisible(false);
        txtFirstPageStatus.setText(null);
        if(!selectDefaultDropDownFirstPage()) {
            RuntimeException rte = new RuntimeException("Failed to select default drop downs on first page.");
            logr.throwing(rte);
            throw rte;
        }
    }

    public void resetSecondPage() {
        datePicker.setValue(null);
        txtFieldMass.setText(null);
        chckBoxSameMass.setSelected(false);
        chckBoxSameNSF.setSelected(false);

        btnFinish.setText("Finish");
        txtSecondPageStatus.setVisible(false);
        txtSecondPageStatus.setText(null);
        if(!selectDefaultDropDownSecondPage()) {
            RuntimeException rte = new RuntimeException("Failed to select default drop downs on second page.");
            logr.throwing(rte);
            throw rte;
        }
    }

    public void setLifeSpanVisible(boolean isLifeSpan) {
        hBoxAddInfoTop.setVisible(isLifeSpan || vBoxLungAbs.isVisible());
        vBoxLifeSpan.setVisible(isLifeSpan);
        if(!isLifeSpan) {
            toggleGrpLifeSpan.selectToggle(null);
        }
        separatorAddInfo.setVisible(isLifeSpan || vBoxLungAbs.isVisible());
    }

    public void setLungAbsVisible(boolean isLungAbs) {
        hBoxAddInfoTop.setVisible(isLungAbs || vBoxLifeSpan.isVisible());
        vBoxLungAbs.setVisible(isLungAbs);
        if(!isLungAbs) {
            toggleGrpLungAbs.selectToggle(null);
        }
        separatorAddInfo.setVisible(isLungAbs || vBoxLifeSpan.isVisible());
    }

    private boolean setupDropDowns() {
        return ModifyUtils.setupDropDownItems(comboBoxA0Prefix, Conversions.SIPrefix.getFxValues()) &&
        ModifyUtils.setupDropDownItems(choiceBoxA0RadUnit, Conversions.RadUnit.getFxValues()) &&
        ModifyUtils.setupDropDownItems(comboBoxMassPrefix, Conversions.SIPrefix.getFxValues()) &&
        ModifyUtils.setupDropDownItems(choiceBoxMassUnit, Conversions.MassUnit.getFxValues()) &&
        ModifyUtils.setupDropDownItems(choiceBoxNature, Nuclide.Nature.getFxValues()) &&
        ModifyUtils.setupDropDownItems(choiceBoxState, LimitsModelId.State.getFxValues()) &&
        ModifyUtils.setupDropDownItems(choiceBoxForm, LimitsModelId.Form.getFxValues());
    }

    private boolean selectDefaultDropDownFirstPage() {
        return ModifyUtils.selectDropDownOption(comboBoxA0Prefix, Conversions.SIPrefix.BASE.getVal()) &&
        ModifyUtils.selectDropDownOption(choiceBoxA0RadUnit, Conversions.RadUnit.CURIE.getVal());
    }

    private boolean selectDefaultDropDownSecondPage() {
        return ModifyUtils.selectDropDownOption(comboBoxMassPrefix, Conversions.SIPrefix.BASE.getVal()) &&
        ModifyUtils.selectDropDownOption(choiceBoxMassUnit, Conversions.MassUnit.GRAMS.getVal()) &&
        ModifyUtils.selectDropDownOption(choiceBoxNature, Nuclide.Nature.REGULAR.getVal()) &&
        ModifyUtils.selectDropDownOption(choiceBoxState, LimitsModelId.State.SOLID.getVal()) &&
        ModifyUtils.selectDropDownOption(choiceBoxForm, LimitsModelId.Form.NORMAL.getVal());
    }

    /*///////////////////////////////////////////// MODIFY PANE CONTROLLER /////////////////////////////////////////////*/

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
            shipment.add(buildNuclide().initConstants());
        }
        if(Page.EDIT.equals(getPage())) {
            getMain().getHomePaneController().updateNuclide(buildNuclide());
        }
        App.getStageHandler().closeSecondary();
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    public void nameListener(String str) {
        ModifyUtils.setPredicate(getFilteredLifeSpanNuclides(), ModifyUtils.filteringPredicate(str));
        ModifyUtils.setPredicate(getFilteredLungAbsNuclides(), ModifyUtils.filteringPredicate(str));
        ModifyUtils.setPredicate(getSearchFilteredNuclides(), ModifyUtils.filteringPredicate(str));
        setLifeSpanVisible(!getFilteredLifeSpanNuclides().isEmpty());
        setLungAbsVisible(!vBoxLifeSpan.isVisible() && !getFilteredLungAbsNuclides().isEmpty());
        boolean isEditingNuclide = false;
        Nuclide nuclide = getNuclide();
        if(nuclide != null && getEditingNuclide() != null) {
            isEditingNuclide = nuclide.getNuclideId().equals(getEditingNuclide().getNuclideId()) &&
                nuclide.getLifeSpan().equals(getEditingNuclide().getLifeSpan()) &&
                nuclide.getLungAbsorption().equals(getEditingNuclide().getLungAbsorption());
        }
        setDuplicateNuclide(StringUtils.isNotBlank(str) && !isEditingNuclide && isNuclideInTable());
        btnNext.setDisable(
                getNuclide() == null ||
                StringUtils.isBlank(txtFieldA0.getText()) ||
                isAddInfoNotProvided() ||
                StringUtils.isNotBlank(txtFirstPageStatus.getText())
            );
    }

    public void initialActivityListener(String str) {
        // if user inputs any non-numerical characters, remove them
        String newTxt = StringUtils.isBlank(str)? null : str.replaceAll("[^\\d.]", "");
        txtFieldA0.setText(newTxt);
        btnNext.setDisable(
            StringUtils.isBlank(newTxt) ||
            getNuclide() == null ||
            isAddInfoNotProvided() ||
            StringUtils.isNotBlank(txtFirstPageStatus.getText())
        );
    }

    public void massListener(String str) {
        // if user inputs any non-numerical characters, remove them
        String newTxt = StringUtils.isBlank(str)? null : str.replaceAll("[^\\d.]", "");
        btnFinish.setDisable(
            StringUtils.isBlank(newTxt) ||
            datePicker.getValue() == null ||
            StringUtils.isNotBlank(txtFirstPageStatus.getText())
        );
        txtFieldMass.setText(newTxt);
    }

    public void radioBtnListener(Toggle newV) {
        btnNext.setDisable(
            getNuclide() == null ||
            StringUtils.isBlank(txtFieldA0.getText()) ||
            newV == null ||
            StringUtils.isNotBlank(txtFirstPageStatus.getText())
        );
    }

    public void setDuplicateNuclide(boolean isInvalid) {
        txtFieldNuclideName.getStyleClass().removeAll("validRegion", "invalidRegion");
        if(isInvalid) {
            txtFieldNuclideName.getStyleClass().add("invalidRegion");
            txtFirstPageStatus.setVisible(true);
            txtFirstPageStatus.setText("Nuclide already in the table.");
        } else {
            txtFieldNuclideName.getStyleClass().add("validRegion");
            txtFirstPageStatus.setVisible(false);
            txtFirstPageStatus.setText(null);
        }
    }

    public Nuclide getNuclide() {
        Nuclide nuclide = null;
        Nuclide searchFilteredNuclide = ModifyUtils.getFilteredNuclide(getSearchFilteredNuclides());

        switch (getPage()) {
            case EDIT:
                if(searchFilteredNuclide != null &&
                    getEditingNuclide().getNuclideId().minimumEquals(searchFilteredNuclide.getNuclideId())) {
                    nuclide = new Nuclide(getEditingNuclide());
                } else if(searchFilteredNuclide != null &&
                    !getEditingNuclide().getNuclideId().equals(searchFilteredNuclide.getNuclideId())) {
                    // user changed the name of the edited nuclide to a different nuclide
                    nuclide = searchFilteredNuclide;
                }
                break;
            case ADD:
                nuclide = searchFilteredNuclide;
                break;
            default:
        }
        return nuclide;
    }

    public Nuclide buildEditedNuclide() {
        Nuclide nuclide = null;
        if(getSearchFilteredNuclides().size() == 1 && getSearchFilteredNuclides().get(0) != null) {
            nuclide = new Nuclide(getSearchFilteredNuclides().get(0));
            ModifyUtils.setNuclideLifeSpan(nuclide, toggleGrpLifeSpan);
            ModifyUtils.setNuclideLungAbs(nuclide, toggleGrpLungAbs);
        }
        return nuclide;
    }

    public boolean isAddInfoNotProvided() {
        boolean isFilteredLifeSpanEmpty = getFilteredLifeSpanNuclides().isEmpty();
        boolean isFilteredLungAbsEmpty = getFilteredLungAbsNuclides().isEmpty();
        boolean isLifeSpanSelectionEmpty = toggleGrpLifeSpan.getSelectedToggle() == null;
        boolean isLungAbsSelectionEmpty = toggleGrpLungAbs.getSelectedToggle() == null;

        return (!isFilteredLifeSpanEmpty || !isFilteredLungAbsEmpty) &&
            (!isFilteredLifeSpanEmpty || isLungAbsSelectionEmpty) &&
            (isFilteredLifeSpanEmpty || isLifeSpanSelectionEmpty);
    }

    public boolean isNuclideInTable() throws InvalidParameterException {
        return getMain().getHomePaneController().isNuclideInTable(buildNuclideFromFirstPage());
    }

    public Nuclide buildNuclideFromFirstPage() {
        Nuclide nuclide = new Nuclide();
        nuclide.setInitActivityStr(txtFieldA0.getText());
        nuclide.setInitActivityPrefix(Conversions.SIPrefix.toSIPrefix(comboBoxA0Prefix.getValue()));
        nuclide.setInitActivityUnit(Conversions.RadUnit.toRadUnit(choiceBoxA0RadUnit.getValue()));
        ModifyUtils.setNuclideLifeSpan(nuclide, toggleGrpLifeSpan);
        ModifyUtils.setNuclideLungAbs(nuclide, toggleGrpLungAbs);
        return nuclide;
    }

    public Nuclide buildNuclide() {
        Nuclide nuclide = buildNuclideFromFirstPage();
        nuclide.setRefDate(datePicker.getValue());
        if(chckBoxSameMass.isSelected()) {
            Shipment shipment = getMain().getHomePaneController().getShipment();
            nuclide.setMassStr(shipment.getMassStr());
            nuclide.setMassPrefix(shipment.getMassPrefix());
            nuclide.setMassUnit(shipment.getMassUnit());
        } else {
            nuclide.setMassStr(txtFieldMass.getText());
            nuclide.setMassPrefix(Conversions.SIPrefix.toSIPrefix(comboBoxMassPrefix.getValue()));
            nuclide.setMassUnit(Conversions.MassUnit.toMass(choiceBoxMassUnit.getValue()));
        }

        if(chckBoxSameNSF.isSelected()) {
            Shipment shipment = getMain().getHomePaneController().getShipment();
            nuclide.setNature(shipment.getNature());
            nuclide.setLimitsId(shipment.getLimitsId());
        } else {
            nuclide.setNature(Nuclide.Nature.toNature(choiceBoxNature.getValue()));
            nuclide.getLimitsId().setState(LimitsModelId.State.toState(choiceBoxState.getValue()));
            nuclide.getLimitsId().setForm(LimitsModelId.Form.toForm(choiceBoxForm.getValue()));
        }
        nuclide.getConstants().dbInit(nuclide.getNuclideId(), nuclide.getLimitsId());
        return nuclide;
    }
}
