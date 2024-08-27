package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.model.db.NuclideModel;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.NuclideUtils;
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

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.marcsllite.util.NuclideUtils.LIFE_SPAN_PATTERN;
import static com.marcsllite.util.NuclideUtils.LUNG_ABS_PATTERN;

public class ModifyController extends BaseController {
    private static final Logger logr = LogManager.getLogger();
    @FXML private StackPane modifyPane;
    // First Page
    @FXML private VBox vBoxFirstPage;
    @FXML private TextField txtFieldNuclideName;
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
            String newTxt = str.replaceAll("[^\\d.]", "");
            btnFinish.setDisable(Page.ADD.equals(getPage()) && newTxt.isBlank());
            txtFieldMass.setText(newTxt);
        }
    }

    public Nuclide getEditingNuclide() {
        return editingNuclide;
    }

    public void setEditingNuclide(Nuclide editingNuclide) {
        this.editingNuclide = editingNuclide;
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
        txtFieldNuclideName.setText(null);
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
        txtFieldNuclideName.textProperty().removeListener((observable, oldV, newV) -> nameListener(newV));
        txtFieldA0.textProperty().removeListener((observable, oldV, newV) -> initialActivityListener(newV));
        txtFieldMass.textProperty().removeListener(((observable, oldV, newV) -> massListener(newV)));
        toggleGrpLifeSpan.selectedToggleProperty().removeListener((observable, oldV, newV) -> radioBtnListener(newV));
        toggleGrpLungAbs.selectedToggleProperty().removeListener((observable, oldV, newV) -> radioBtnListener(newV));

        txtFieldNuclideName.textProperty().addListener((observable, oldV, newV) -> nameListener(newV));
        txtFieldA0.textProperty().addListener((observable, oldV, newV) -> initialActivityListener(newV));
        txtFieldMass.textProperty().addListener(((observable, oldV, newV) -> massListener(newV)));
        toggleGrpLifeSpan.selectedToggleProperty().addListener((observable, oldV, newV) -> radioBtnListener(newV));
        toggleGrpLungAbs.selectedToggleProperty().addListener((observable, oldV, newV) -> radioBtnListener(newV));
    }
    
    protected void initAddPage() {
        setEditingNuclide(null);
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
        setEditingNuclide(getMain().getHomePaneController().getSelectedNuclides().get(0));
        btnNext.setDisable(false);
        btnFinish.setDisable(false);
        
        setFirstPageValues();
        setSecondPageValues();
    }

    private void setFirstPageValues() {
        if(getEditingNuclide() != null) {
            txtFieldNuclideName.setText(getEditingNuclide().getDisplayNameNotation());
            txtFieldA0.setText(getEditingNuclide().getDisplayInitActivity());
            if(txtFieldA0.getText().isBlank()) {
                txtFieldA0.setText("0");
            }
            comboBoxA0Prefix.getSelectionModel().select(getEditingNuclide().getInitActivityPrefix().getVal());
            choiceBoxA0RadUnit.getSelectionModel().select(getEditingNuclide().getInitActivityUnit().getVal());
    
            txtFirstPageStatus.setVisible(false);
            txtFirstPageStatus.setText(null);
    
            String massNumber = getEditingNuclide().getNuclideId().getMassNumber().toLowerCase();
            Pattern lungAbsPattern = Pattern.compile(LUNG_ABS_PATTERN);
            Matcher lungAbsMatch = lungAbsPattern.matcher(massNumber);
            Pattern lifeSpanPattern = Pattern.compile(LIFE_SPAN_PATTERN);
            Matcher lifeSpanMatch = lifeSpanPattern.matcher(massNumber);
    
            if(lifeSpanMatch.find()) {
                setLifeSpanVisible(true);
                Optional<Toggle> toggle = toggleGrpLifeSpan.getToggles()
                    .stream()
                    .filter(t ->
                        Objects.equals(
                            Nuclide.LifeSpan.toLifeSpan((String) t.getUserData()),
                            Nuclide.LifeSpan.toLifeSpan(lifeSpanMatch.group(1)))
                    ).findFirst();
    
                toggle.ifPresent(value -> toggleGrpLifeSpan.selectToggle(value));
            }
    
            if(lungAbsMatch.find()) {
                setLungAbsVisible(true);
                Optional<Toggle> toggle = toggleGrpLungAbs.getToggles()
                    .stream()
                    .filter(t ->
                        Objects.equals(
                            Nuclide.LungAbsorption.toLungAbsorption((String) t.getUserData()),
                            Nuclide.LungAbsorption.toLungAbsorption(lungAbsMatch.group(0)))
                    ).findFirst();
    
                toggle.ifPresent(value -> toggleGrpLungAbs.selectToggle(value));
            }
        } else {
            resetFirstPage();
        }
    }

    private void setSecondPageValues() {
        if(getEditingNuclide() != null) {
            datePicker.setValue(getEditingNuclide().getRefDate());
            txtFieldMass.setText(getEditingNuclide().getDisplayMass());
            if(txtFieldMass.getText().isBlank()) {
                txtFieldMass.setText("0");
            }
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
        radioBtnSlowLungAbs.setText(Nuclide.LungAbsorption.SLOW.getVal());
        radioBtnMediumLungAbs.setText(Nuclide.LungAbsorption.MEDIUM.getVal());
        radioBtnFastLungAbs.setText(Nuclide.LungAbsorption.FAST.getVal());
        radioBtnShortLived.setText(Nuclide.LifeSpan.SHORT.getVal());
        radioBtnLongLived.setText(Nuclide.LifeSpan.LONG.getVal());
    }
    
    protected void setRadioBtnUserData() {
        radioBtnSlowLungAbs.setUserData(Nuclide.LungAbsorption.SLOW.getAbbrVal());
        radioBtnMediumLungAbs.setUserData(Nuclide.LungAbsorption.MEDIUM.getAbbrVal());
        radioBtnFastLungAbs.setUserData(Nuclide.LungAbsorption.FAST.getAbbrVal());
        radioBtnShortLived.setUserData(Nuclide.LifeSpan.SHORT.getAbbrVal());
        radioBtnLongLived.setUserData(Nuclide.LifeSpan.LONG.getAbbrVal());
    }

    protected void radioBtnListener(Toggle newV) {
        if(newV != null) {
            Nuclide nuclide = null;
            if(Page.EDIT.equals(getPage())) {
                nuclide = buildEditedNuclide();
            } else if(getSearchFilteredNuclides().size() == 1) {
                nuclide = new Nuclide(getSearchFilteredNuclides().get(0));
            }
            boolean isInTable = isNuclideInTable(nuclide);
            String a0 = txtFieldA0.getText();
            btnNext.setDisable(!Page.EDIT.equals(getPage()) &&
                nuclide == null ||
                a0 == null || a0.isBlank() ||
                isInTable);
        } else {
            btnNext.setDisable(!Page.EDIT.equals(getPage()));
        }
    }

    protected void initialActivityListener(String str) {
        getFilteredLifeSpanNuclides().setPredicate(filteringPredicate(txtFieldNuclideName.getText()));
        getFilteredLungAbsNuclides().setPredicate(filteringPredicate(txtFieldNuclideName.getText()));
        getSearchFilteredNuclides().setPredicate(filteringPredicate(txtFieldNuclideName.getText()));

        if(str == null || str.isBlank()) {
            btnNext.setDisable(!Page.EDIT.equals(getPage()));
        } else {
            // if user inputs any non-numerical characters, remove them
            String newTxt = str.replaceAll("[^\\d.]", "");
            Nuclide nuclide = null;
            if(Page.EDIT.equals(getPage())) {
                nuclide = buildEditedNuclide();
            } else if(getSearchFilteredNuclides().size() == 1) {
                nuclide = new Nuclide(getSearchFilteredNuclides().get(0));
            }
            btnNext.setDisable(!Page.EDIT.equals(getPage()) &&
                nuclide == null ||
                newTxt.isBlank() ||
                isAddInfoNotProvided() ||
                isNuclideInTable(nuclide));
            txtFieldA0.setText(newTxt);
        }
    }

    protected void setupDropDownItems() {
        comboBoxA0Prefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxA0RadUnit.setItems(Conversions.RadUnit.getFxValues());
        comboBoxMassPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxMassUnit.setItems(Conversions.MassUnit.getFxValues());
        choiceBoxNature.setItems(Nuclide.Nature.getFxValues());
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
        choiceBoxNature.getSelectionModel().select(Nuclide.Nature.REGULAR.getVal());
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
            getSearchFilteredNuclides().setPredicate(nuclide -> false);
            getFilteredLifeSpanNuclides().setPredicate(nuclide -> false);
            getFilteredLungAbsNuclides().setPredicate(nuclide -> false);
            setLifeSpanVisible(false);
            setLungAbsVisible(false);
            btnNext.setDisable(!Page.EDIT.equals(getPage()));
            setDuplicateNuclide(false);
        } else {
            getFilteredLifeSpanNuclides().setPredicate(filteringPredicate(newV));
            getFilteredLungAbsNuclides().setPredicate(filteringPredicate(newV));
            getSearchFilteredNuclides().setPredicate(filteringPredicate(newV));
            Nuclide nuclide = null;
            if(Page.EDIT.equals(getPage())) {
                nuclide = buildEditedNuclide();
            } else if(getSearchFilteredNuclides().size() == 1) {
                nuclide = new Nuclide(getSearchFilteredNuclides().get(0));
            }
            setLifeSpanVisible(!getFilteredLifeSpanNuclides().isEmpty());
            setLungAbsVisible(!vBoxLifeSpan.isVisible() && !getFilteredLungAbsNuclides().isEmpty());
            String a0 = txtFieldA0.getText();
            btnNext.setDisable(!Page.EDIT.equals(getPage()) &&
                nuclide == null ||
                a0 == null || a0.isBlank() ||
                isAddInfoNotProvided() ||
                isNuclideInTable(nuclide));
        }
    }

    private boolean isEditingNuclide(Nuclide nuclide) {
        if(getEditingNuclide() == null || nuclide == null) {
            return false;
        }
        return getEditingNuclide().getNuclideId().equals(nuclide.getNuclideId()) &&
            getEditingNuclide().getLifeSpan().equals(nuclide.getLifeSpan()) &&
            getEditingNuclide().getLungAbsorption().equals(nuclide.getLungAbsorption());
    }

    private boolean isNuclideInTable(Nuclide nuclide) throws InvalidParameterException {
        boolean ret = !isEditingNuclide(nuclide) &&
            getMain().getHomePaneController().isNuclideInTable(nuclide);
        setDuplicateNuclide(ret);
        return ret;
    }

    protected void setDuplicateNuclide(boolean isInvalid) {
        txtFieldNuclideName.getStyleClass().removeAll("validRegion", "invalidRegion");
        txtFieldNuclideName.getStyleClass().add(isInvalid ? "invalidRegion" : "validRegion");
        txtFirstPageStatus.setVisible(isInvalid);
        if(isInvalid){
            txtFirstPageStatus.setText("Nuclide already in the table.");
        }
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

    protected Predicate<NuclideModel> filteringPredicate(String str) {
        if (str == null || str.isBlank()) {
            return nuclide -> false;
        }
        return nuclide -> doesNuclideMatchSearch(nuclide, str);
    }

    protected boolean doesNuclideMatchSearch(NuclideModel nuclide, String str) {
        if(nuclide == null || str == null || str.isBlank()) {
            return false;
        }
        String searchStr = str.trim()
            .replaceAll(LIFE_SPAN_PATTERN, "")
            .replaceAll(LUNG_ABS_PATTERN, "");
        String symbol = nuclide.getSymbolNotation();
        String name = nuclide.getNameNotation();

        return symbol.equalsIgnoreCase(searchStr) || name.equalsIgnoreCase(searchStr);
    }

    protected Nuclide buildNuclideFromFirstPage() {
        Nuclide nuclide = null;
        if(getSearchFilteredNuclides().size() == 1 && !btnNext.isDisabled()) {
            nuclide = new Nuclide(getSearchFilteredNuclides().get(0));
            nuclide.setInitActivityStr(txtFieldA0.getText());
            nuclide.setInitActivityPrefix(Conversions.SIPrefix.toSIPrefix(comboBoxA0Prefix.getValue()));
            nuclide.setInitActivityUnit(Conversions.RadUnit.toRadUnit(choiceBoxA0RadUnit.getValue()));
            setNuclideLifeSpan(nuclide);
            setNuclideLungAbs(nuclide);
        }
        return nuclide;
    }

    protected Nuclide buildEditedNuclide() {
        Nuclide nuclide = null;
        if(getSearchFilteredNuclides().size() == 1 && getSearchFilteredNuclides().get(0) != null) {
            nuclide = new Nuclide(getSearchFilteredNuclides().get(0));
            setNuclideLifeSpan(nuclide);
            setNuclideLungAbs(nuclide);
        }
        return nuclide;
    }

    private void setNuclideLifeSpan(Nuclide nuclide) {
        if(toggleGrpLifeSpan.getSelectedToggle() != null) {
            RadioButton radioBtn = (RadioButton) toggleGrpLifeSpan.getSelectedToggle();
            nuclide.getNuclideId().setMassNumber(nuclide.getNuclideId().getMassNumber() + radioBtn.getUserData());
            nuclide.setLifeSpan(Nuclide.LifeSpan.toLifeSpan((String) radioBtn.getUserData()));
        } else {
            nuclide.setLifeSpan(Nuclide.LifeSpan.REGULAR);
        }
    }

    protected void setNuclideLungAbs(Nuclide nuclide) {
        if(toggleGrpLungAbs.getSelectedToggle() != null) {
            RadioButton radioBtn = (RadioButton) toggleGrpLungAbs.getSelectedToggle();
            nuclide.getNuclideId().setMassNumber(nuclide.getNuclideId().getMassNumber() + radioBtn.getUserData());
            nuclide.setLungAbsorption(Nuclide.LungAbsorption.toLungAbsorption((String) radioBtn.getUserData()));
        } else {
            nuclide.setLungAbsorption(Nuclide.LungAbsorption.NONE);
        }
    }

    protected Nuclide buildNuclide() {
        Nuclide nuclide = buildNuclideFromFirstPage();
        if(nuclide != null && !btnFinish.isDisabled()) {
            nuclide.setRefDate(datePicker.getValue());
            if(chckBoxSameMass.isDisabled()) {
                Shipment shipment = getMain().getHomePaneController().getShipment();
                nuclide.setMassStr(shipment.getMassStr());
                nuclide.setMassPrefix(shipment.getMassPrefix());
                nuclide.setMassUnit(shipment.getMassUnit());
            } else {
                nuclide.setMassStr(txtFieldMass.getText());
                nuclide.setMassPrefix(Conversions.SIPrefix.toSIPrefix(comboBoxMassPrefix.getValue()));
                nuclide.setMassUnit(Conversions.MassUnit.toMass(choiceBoxMassUnit.getValue()));
            }

            if(chckBoxSameNSF.isDisabled()) {
                Shipment shipment = getMain().getHomePaneController().getShipment();
                nuclide.setNature(shipment.getNature());
                nuclide.setLimitsId(shipment.getLimitsId());
            } else {
                nuclide.setNature(Nuclide.Nature.toNature(choiceBoxNature.getValue()));
                nuclide.getLimitsId().setState(LimitsModelId.State.toState(choiceBoxState.getValue()));
                nuclide.getLimitsId().setForm(LimitsModelId.Form.toForm(choiceBoxForm.getValue()));
            }
            nuclide.getConstants().dbInit(nuclide.getNuclideId(), nuclide.getLimitsId());
        }
        return nuclide;
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
            shipment.add(buildNuclide().initConstants());
        }
        if(Page.EDIT.equals(getPage())) {
            getMain().getHomePaneController().updateNuclide(buildNuclide());
        }
        App.getStageHandler().closeSecondary();
    }
}
