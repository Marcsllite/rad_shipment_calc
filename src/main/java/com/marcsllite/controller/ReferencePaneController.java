package com.marcsllite.controller;

import com.marcsllite.model.Isotope;
import com.marcsllite.model.PTableColumn;
import com.marcsllite.util.Conversions;
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
import java.math.BigDecimal;
import java.util.function.Predicate;

public class ReferencePaneController extends BaseController {
    @FXML GridPane referencePane;
    @FXML TextField txtFieldSearch;
    @FXML TableView<Isotope> tableViewSearch;
    @FXML PTableColumn<Isotope, String> tableColRefAbbr;
    @FXML PTableColumn<Isotope, String> tableColRefName;
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

    public ReferencePaneController() throws IOException {
        this(null);
    }

    public ReferencePaneController(PropHandler propHandler) throws IOException {
        super(propHandler);
        setPage(Page.REFERENCE);
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

    protected void initTable() {
        tableColRefAbbr.setCellValueFactory(new PropertyValueFactory<>("abbr"));
        tableColRefName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableViewSearch.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableViewSearch.getSelectionModel().clearSelection();

        setupSearchFiltering(getDbService().getAllIsotopes());
    }

    protected void setupDropDownItems() {
        comboBoxRefA1Prefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefA1RadUnit.setItems(Isotope.RadUnit.getFxValues());
        comboBoxRefA2Prefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefA2RadUnit.setItems(Isotope.RadUnit.getFxValues());
        comboBoxRefExemptConPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefExemptConRadUnit.setItems(Isotope.RadUnit.getFxValues());
        comboBoxRefExemptLimPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefExemptLimRadUnit.setItems(Isotope.RadUnit.getFxValues());
        comboBoxRefReportQuanPrefix.setItems(Conversions.SIPrefix.getFxValues());
        choiceBoxRefReportQuanRadUnit.setItems(Isotope.RadUnit.getFxValues());

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
        selectDropDownItem(choiceBoxRefA1RadUnit, Isotope.RadUnit.BECQUEREL.getVal());
        selectDropDownItem(comboBoxRefA2Prefix, Conversions.SIPrefix.TERA.getVal());
        selectDropDownItem(choiceBoxRefA2RadUnit, Isotope.RadUnit.BECQUEREL.getVal());
        selectDropDownItem(comboBoxRefExemptConPrefix, Conversions.SIPrefix.BASE.getVal());
        selectDropDownItem(choiceBoxRefExemptConRadUnit, Isotope.RadUnit.BECQUEREL.getVal());
        selectDropDownItem(comboBoxRefExemptLimPrefix, Conversions.SIPrefix.BASE.getVal());
        selectDropDownItem(choiceBoxRefExemptLimRadUnit, Isotope.RadUnit.BECQUEREL.getVal());
        selectDropDownItem(comboBoxRefReportQuanPrefix, Conversions.SIPrefix.TERA.getVal());
        selectDropDownItem(choiceBoxRefReportQuanRadUnit, Isotope.RadUnit.BECQUEREL.getVal());
    }

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
            BigDecimal prev = new BigDecimal(field.getText());
            unbindControl(field);

            if(Isotope.RadUnit.CURIE.getVal().equals(oldV) &&
                Isotope.RadUnit.BECQUEREL.getVal().equals(newV)) {
                field.setText(Conversions.ciToBq(prev).toString());
            } else if(Isotope.RadUnit.BECQUEREL.getVal().equals(oldV) &&
                Isotope.RadUnit.CURIE.getVal().equals(newV)) {
                field.setText(Conversions.bqToCi(prev).toString());
            }
        } catch (NumberFormatException ignored) {
            // ignored
        }
    }

    protected void prefixListener(TextField field, String oldV, String newV) {
        try {
            BigDecimal converted = Conversions.convertToPrefix(
                new BigDecimal(field.getText()),
                Conversions.SIPrefix.toSIPrefix(oldV),
                Conversions.SIPrefix.toSIPrefix(newV));

            unbindControl(field);
            field.setText(converted.toString());
        } catch (NumberFormatException ignored) {
            // ignored
        }
    }

    protected void setupSearchFiltering(ObservableList<Isotope> list) {
        FilteredList<Isotope> filteredData =  new FilteredList<>(list, null);

        txtFieldSearch.textProperty().addListener(
            (observable, oldV, newV) -> {
                if (newV != null && !newV.isBlank()) {
                    tableViewSearch.getSelectionModel().clearSelection();
                }

                filteredData.setPredicate(searchFilteringPredicate(newV));
            }
        );

        SortedList<Isotope> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewSearch.comparatorProperty());
        tableViewSearch.setItems(sortedData);
        tableViewSearch.getSelectionModel().selectedItemProperty().addListener(((observable, oldV, newV) ->
            setupTableDataLinking(newV)
        ));
    }

    protected Predicate<Isotope> searchFilteringPredicate(String str) {
        return isotope -> doesIsotopeNameMatch(isotope, str);
    }

    protected boolean doesIsotopeNameMatch(Isotope isotope, String str) {
        if(isotope == null) {
            return false;
        }
        if (str == null || str.isBlank()) {
            return true;
        }
        String searchStr = str.toLowerCase();

        return isotope.getIsoId().getAbbr().toLowerCase().contains(searchStr) ||
            isotope.getIsoId().getName().toLowerCase().contains(searchStr);
    }

    protected void setupTableDataLinking(Isotope isotope) {
        unbindIsotopeConstants();
        if(isotope != null) {
            bindIsotopeConstants(isotope);
            selectBaseDropDownItems();
        }
    }

    protected void bindIsotopeConstants(Isotope isotope) {
        if(isotope != null) {
            txtFieldA1.textProperty().bind(isotope.getConstants().a1Property().asString());
            txtFieldA2.textProperty().bind(isotope.getConstants().a2Property().asString());
            txtFieldDecayConst.textProperty().bind(isotope.getConstants().decayConstantProperty().asString());
            txtFieldExemptCon.textProperty().bind(isotope.getConstants().exemptConcentrationProperty().asString());
            txtFieldExemptLim.textProperty().bind(isotope.getConstants().exemptLimitProperty().asString());
            txtFieldHalfLife.textProperty().bind(isotope.getConstants().halfLifeProperty().asString());
            txtFieldReportQuan.textProperty().bind(isotope.getConstants().teraBqReportQuanProperty().asString());
        }
    }
    protected void unbindIsotopeConstants() {
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
