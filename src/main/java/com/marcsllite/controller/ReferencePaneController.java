package com.marcsllite.controller;

import com.marcsllite.model.Nuclide;
import com.marcsllite.model.PTableColumn;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.RadBigDecimal;
import com.marcsllite.util.handler.PropHandler;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ReferencePaneController extends BaseController {
    @FXML GridPane referencePane;
    @FXML TextField txtFieldSearch;
    @FXML TableView<Nuclide> tableViewSearch;
    @FXML PTableColumn<Nuclide, String> tableColRefFullName;
    @FXML PTableColumn<Nuclide, String> tableColRefMassNumber;
    @FXML TextField txtFieldA1;
    @FXML ComboBox<String> comboBoxRefA1Prefix;
    @FXML ChoiceBox<String> choiceBoxRefA1RadUnit;
    @FXML TextField txtFieldA2;
    @FXML ComboBox<String> comboBoxRefA2Prefix;
    @FXML ChoiceBox<String> choiceBoxRefA2RadUnit;
    @FXML TextField txtFieldDecayConst;
    @FXML TextField txtFieldExemptCon;
    @FXML ComboBox<String> comboBoxRefExemptConPrefix;
    @FXML ChoiceBox<String> choiceBoxRefExemptConRadUnit;
    @FXML TextField txtFieldExemptLim;
    @FXML ComboBox<String> comboBoxRefExemptLimPrefix;
    @FXML ChoiceBox<String> choiceBoxRefExemptLimRadUnit;
    @FXML TextField txtFieldHalfLife;
    @FXML TextField txtFieldReportQuan;
    @FXML ComboBox<String> comboBoxRefReportQuanPrefix;
    @FXML ChoiceBox<String> choiceBoxRefReportQuanRadUnit;
    ObservableList<Nuclide> refNuclides;

    public ReferencePaneController() throws IOException {
        this(null);
    }

    public ReferencePaneController(PropHandler propHandler) throws IOException {
        super(propHandler, Page.REFERENCE);
    }

    @Override
    @FXML public void initialize() {
        super.initialize();
        setupDropDownItems();
        initTable();
        setInit(true);
    }

    @Override
    public void show() {
        referencePane.setVisible(true);
        referencePane.toFront();
    }

    @Override
    public void hide() {
        referencePane.setVisible(false);
        referencePane.toBack();
    }

    public ObservableList<Nuclide> getRefNuclides() {
        return refNuclides;
    }

    public void setRefNuclides(ObservableList<Nuclide> refNuclides) {
        this.refNuclides = refNuclides;
    }

    protected void initTable() {
        tableColRefFullName.setCellValueFactory(new PropertyValueFactory<>("nameSymbol"));
        tableColRefMassNumber.setCellValueFactory(new PropertyValueFactory<>("massNumber"));

        tableViewSearch.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableViewSearch.getSelectionModel().clearSelection();

        setupSearchFiltering(getDbService().getAllNuclides());
    }

    protected void setupDropDownItems() {
        comboBoxRefA1Prefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefA1RadUnit.setItems(Conversions.RadUnit.getFxValues());
        comboBoxRefA2Prefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefA2RadUnit.setItems(Conversions.RadUnit.getFxValues());
        comboBoxRefExemptConPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefExemptConRadUnit.setItems(Conversions.RadUnit.getFxValues());
        comboBoxRefExemptLimPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefExemptLimRadUnit.setItems(Conversions.RadUnit.getFxValues());
        comboBoxRefReportQuanPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefReportQuanRadUnit.setItems(Conversions.RadUnit.getFxValues());

        selectBaseDropDownItems();

        comboBoxRefA1Prefix.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            prefixListener(txtFieldA1, oldV, newV)
        ));
        choiceBoxRefA1RadUnit.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            radUnitListener(txtFieldA1, oldV, newV)
        ));
        comboBoxRefA2Prefix.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            prefixListener(txtFieldA2, oldV, newV)
        ));
        choiceBoxRefA2RadUnit.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            radUnitListener(txtFieldA2, oldV, newV)
        ));
        comboBoxRefExemptConPrefix.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            prefixListener(txtFieldExemptCon, oldV, newV)
        ));
        choiceBoxRefExemptConRadUnit.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            radUnitListener(txtFieldExemptCon, oldV, newV)
        ));
        comboBoxRefExemptLimPrefix.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            prefixListener(txtFieldExemptLim, oldV, newV)
        ));
        choiceBoxRefExemptLimRadUnit.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            radUnitListener(txtFieldExemptLim, oldV, newV)
        ));
        comboBoxRefReportQuanPrefix.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            prefixListener(txtFieldReportQuan, oldV, newV)
        ));
        choiceBoxRefReportQuanRadUnit.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            radUnitListener(txtFieldReportQuan, oldV, newV)
        ));
    }

    protected void selectBaseDropDownItems() {
        selectDropDownItem(comboBoxRefA1Prefix, Conversions.SIPrefix.TERA.getVal());
        selectDropDownItem(choiceBoxRefA1RadUnit, Conversions.RadUnit.BECQUEREL.getVal());
        selectDropDownItem(comboBoxRefA2Prefix, Conversions.SIPrefix.TERA.getVal());
        selectDropDownItem(choiceBoxRefA2RadUnit, Conversions.RadUnit.BECQUEREL.getVal());
        selectDropDownItem(comboBoxRefExemptConPrefix, Conversions.SIPrefix.BASE.getVal());
        selectDropDownItem(choiceBoxRefExemptConRadUnit, Conversions.RadUnit.BECQUEREL.getVal());
        selectDropDownItem(comboBoxRefExemptLimPrefix, Conversions.SIPrefix.BASE.getVal());
        selectDropDownItem(choiceBoxRefExemptLimRadUnit, Conversions.RadUnit.BECQUEREL.getVal());
        selectDropDownItem(comboBoxRefReportQuanPrefix, Conversions.SIPrefix.TERA.getVal());
        selectDropDownItem(choiceBoxRefReportQuanRadUnit, Conversions.RadUnit.BECQUEREL.getVal());
    }

    @SuppressWarnings("unchecked")
    protected void selectDropDownItem(Control list, String selection) {
        if (list instanceof ChoiceBox) {
            ((ChoiceBox<String>) list).getSelectionModel().select(selection);
        }
        if (list instanceof ComboBox) {
            ((ComboBox<String>) list).getSelectionModel().select(selection);
        }
    }

    protected void radUnitListener(TextField field, String oldV, String newV) {
        try {
            RadBigDecimal prev = new RadBigDecimal(field.getText());
            unbindControl(field);

            if(Conversions.RadUnit.CURIE.getVal().equals(oldV) &&
                Conversions.RadUnit.BECQUEREL.getVal().equals(newV)) {
                field.setText(Conversions.ciToBq(prev).toDisplayString());
            } else if(Conversions.RadUnit.BECQUEREL.getVal().equals(oldV) &&
                Conversions.RadUnit.CURIE.getVal().equals(newV)) {
                field.setText(Conversions.bqToCi(prev).toDisplayString());
            }
        } catch (NumberFormatException ignored) {
            // ignored
        }
    }

    protected void prefixListener(TextField field, String oldV, String newV) {
        try {
            RadBigDecimal converted = Conversions.convertToPrefix(
                new RadBigDecimal(field.getText()),
                Conversions.SIPrefix.toSIPrefix(oldV),
                Conversions.SIPrefix.toSIPrefix(newV));

            unbindControl(field);
            field.setText(converted.toDisplayString());
        } catch (NumberFormatException ignored) {
            // ignored
        }
    }

    protected void setupSearchFiltering(ObservableList<Nuclide> list) {
        FilteredList<Nuclide> filteredData =  new FilteredList<>(list, null);

        txtFieldSearch.textProperty().addListener(
            (observable, oldV, newV) -> searchFilteringListener(filteredData, newV)
        );

        SortedList<Nuclide> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewSearch.comparatorProperty());
        tableViewSearch.setItems(sortedData);
        tableViewSearch.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldV, newV) -> setupTableDataLinking(newV)
        );
    }

    protected void searchFilteringListener(FilteredList<Nuclide> filteredData, String newV) {
        if (newV != null && !newV.isBlank()) {
            tableViewSearch.getSelectionModel().clearSelection();
        }

        filteredData.setPredicate(nuclide -> doesNuclideNameMatch(nuclide, newV));
    }

    protected boolean doesNuclideNameMatch(Nuclide nuclide, String str) {
        if(nuclide == null) {
            return false;
        }
        if (str == null || str.isBlank()) {
            return true;
        }
        String searchStr = str.toLowerCase();

        return nuclide.getDisplayNameNotation().toLowerCase().contains(searchStr) ||
            nuclide.getDisplaySymbolNotation().toLowerCase().contains(searchStr);
    }

    protected void setupTableDataLinking(Nuclide nuclide) {
        unbindNuclideConstants();
        if(nuclide != null) {
            nuclide.getConstants().dbInit(nuclide.getNuclideId(), nuclide.getLimitsId());
            bindNuclideConstants(nuclide);
            selectBaseDropDownItems();
        }
    }

    protected void bindNuclideConstants(Nuclide nuclide) {
        if(nuclide != null) {
            txtFieldA1.textProperty().bind(nuclide.getConstants().a1Property());
            txtFieldA2.textProperty().bind(nuclide.getConstants().a2Property());
            txtFieldDecayConst.textProperty().bind(nuclide.getConstants().decayConstantProperty());
            txtFieldExemptCon.textProperty().bind(nuclide.getConstants().exemptConcentrationProperty());
            txtFieldExemptLim.textProperty().bind(nuclide.getConstants().exemptLimitProperty());
            txtFieldHalfLife.textProperty().bind(nuclide.getConstants().halfLifeProperty());
            txtFieldReportQuan.textProperty().bind(nuclide.getConstants().teraBqReportQuanProperty());
        }
    }
    protected void unbindNuclideConstants() {
        unbindControl(txtFieldA1);
        unbindControl(txtFieldA2);
        unbindControl(txtFieldDecayConst);
        unbindControl(txtFieldExemptCon);
        unbindControl(txtFieldExemptLim);
        unbindControl(txtFieldHalfLife);
        unbindControl(txtFieldReportQuan);
    }

    protected void unbindControl(Control control) {
        if (control instanceof TextField) {
            TextField field = (TextField) control;
            field.textProperty().unbind();
            field.textProperty().set("");
        }
        if (control instanceof Label) {
            Label lbl = (Label) control;
            lbl.textProperty().unbind();
            lbl.textProperty().set("");
        }
    }
}
