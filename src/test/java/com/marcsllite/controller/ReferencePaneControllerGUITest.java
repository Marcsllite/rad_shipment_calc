package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.model.Isotope;
import com.marcsllite.model.IsotopeConstants;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.FXMLView;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

class ReferencePaneControllerGUITest extends GUITest {
    ReferencePaneController controller;
    GridPane gridPaneReference;
    TextField txtFieldSearch;
    TableView<Isotope> tableViewSearch;
    TextField txtFieldA1;
    ComboBox<String> comboBoxRefA1Prefix;
    ChoiceBox<String> choiceBoxRefA1RadUnit;
    Label labelA2;
    ComboBox<String> comboBoxRefA2Prefix;
    ChoiceBox<String> choiceBoxRefA2RadUnit;
    Label labelDecayConst;
    Label labelExemptCon;
    ComboBox<String> comboBoxRefExemptConPrefix;
    ChoiceBox<String> choiceBoxRefExemptConRadUnit;
    Label labelExemptLim;
    ComboBox<String> comboBoxRefExemptLimPrefix;
    ChoiceBox<String> choiceBoxRefExemptLimRadUnit;
    Label labelHalfLife;
    Label labelReportQuan;
    ComboBox<String> comboBoxRefReportQuanPrefix;
    ChoiceBox<String> choiceBoxRefReportQuanRadUnit;

    public ReferencePaneControllerGUITest() {
        super(FXMLView.REFERENCE);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (ReferencePaneController) getController();
        gridPaneReference = getNode(FXIds.GRIDPANE_REFERENCE);
        txtFieldSearch = getNode(FXIds.TXTFIELD_SEARCH);
        tableViewSearch = getNode(FXIds.TABLEVIEW_SEARCH);
        txtFieldA1 = getNode(FXIds.TXTFIELD_A1);
        comboBoxRefA1Prefix = getNode(FXIds.COMBOBOX_REF_A1_PREFIX);
        choiceBoxRefA1RadUnit = getNode(FXIds.CHOICEBOX_REF_A1_RAD_UNIT);
        labelA2 = getNode(FXIds.LABEL_A2);
        comboBoxRefA2Prefix = getNode(FXIds.COMBOBOX_REF_A2_PREFIX);
        choiceBoxRefA2RadUnit = getNode(FXIds.CHOICEBOX_REF_A2_RAD_UNIT);
        labelDecayConst = getNode(FXIds.LABEL_DECAY_CONSTANT);
        labelExemptCon = getNode(FXIds.LABEL_EXEMPT_CONCENTRATION);
        comboBoxRefExemptConPrefix = getNode(FXIds.COMBOBOX_REF_EXEMPT_CON_PREFIX);
        choiceBoxRefExemptConRadUnit = getNode(FXIds.CHOICEBOX_REF_EXEMPT_CON_RAD_UNIT);
        labelExemptLim = getNode(FXIds.LABEL_EXEMPT_LIMIT);
        comboBoxRefExemptLimPrefix = getNode(FXIds.COMBOBOX_REF_EXEMPT_LIM_PREFIX);
        choiceBoxRefExemptLimRadUnit = getNode(FXIds.CHOICEBOX_REF_EXEMPT_LIM_RAD_UNIT);
        labelHalfLife = getNode(FXIds.LABEL_HALF_LIFE);
        labelReportQuan = getNode(FXIds.LABEL_REPORTABLE_QUANTITY);
        comboBoxRefReportQuanPrefix = getNode(FXIds.COMBOBOX_REF_REPORT_QUAN_PREFIX);
        choiceBoxRefReportQuanRadUnit = getNode(FXIds.CHOICEBOX_REF_REPORT_QUAN_RAD_UNIT);
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(gridPaneReference, NodeMatchers.isVisible());

        interact(() -> {
            verifySetupDropDownItems();
            verifyInitTable();
        });
    }

    protected void verifySetupDropDownItems() {
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefA1Prefix.getItems());
        assertEquals(Isotope.RadUnit.getFxValues(), choiceBoxRefA1RadUnit.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefA2Prefix.getItems());
        assertEquals(Isotope.RadUnit.getFxValues(), choiceBoxRefA2RadUnit.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefExemptConPrefix.getItems());
        assertEquals(Isotope.RadUnit.getFxValues(), choiceBoxRefExemptConRadUnit.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefExemptLimPrefix.getItems());
        assertEquals(Isotope.RadUnit.getFxValues(), choiceBoxRefExemptLimRadUnit.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefReportQuanPrefix.getItems());
        assertEquals(Isotope.RadUnit.getFxValues(), choiceBoxRefReportQuanRadUnit.getItems());

        verifySelectBaseDropDownItems();
    }

    protected void verifySelectBaseDropDownItems() {
        assertEquals(Conversions.SIPrefix.TERA.getVal(), comboBoxRefA1Prefix.getSelectionModel().getSelectedItem());
        assertEquals(Isotope.RadUnit.BECQUEREL.getVal(), choiceBoxRefA1RadUnit.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.TERA.getVal(), comboBoxRefA2Prefix.getSelectionModel().getSelectedItem());
        assertEquals(Isotope.RadUnit.BECQUEREL.getVal(), choiceBoxRefA2RadUnit.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxRefExemptConPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Isotope.RadUnit.BECQUEREL.getVal(), choiceBoxRefExemptConRadUnit.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxRefExemptLimPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Isotope.RadUnit.BECQUEREL.getVal(), choiceBoxRefExemptLimRadUnit.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.TERA.getVal(), comboBoxRefReportQuanPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Isotope.RadUnit.BECQUEREL.getVal(), choiceBoxRefReportQuanRadUnit.getSelectionModel().getSelectedItem());
    }

    protected void verifyInitTable() {
        assertFalse(tableViewSearch.getItems().isEmpty());
        assertEquals(SelectionMode.SINGLE, tableViewSearch.getSelectionModel().getSelectionMode());
        assertNull(tableViewSearch.getSelectionModel().getSelectedItem());
    }

    @Test
    void testHideShow() {
        interact(() -> {
            controller.hide();

            FxAssert.verifyThat(gridPaneReference, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(gridPaneReference, NodeMatchers.isVisible());
        });
    }

    @Test
    void testTableDataLinking() {
        interact(() -> {
            ObservableList<Isotope> tableItems = tableViewSearch.getItems();
            assertEquals(1, tableItems.size());

            IsotopeConstants isoConstants = tableItems.get(0).getConstants();

            assertNull(tableViewSearch.getSelectionModel().getSelectedItem());
            assertTrue(txtFieldA1.getText().isEmpty());
            assertFalse(labelA2.getText().isEmpty());
            assertFalse(labelDecayConst.getText().isEmpty());
            assertFalse(labelExemptCon.getText().isEmpty());
            assertFalse(labelExemptLim.getText().isEmpty());
            assertFalse(labelHalfLife.getText().isEmpty());
            assertFalse(labelReportQuan.getText().isEmpty());

            tableViewSearch.getSelectionModel().select(0);

            assertEquals(String.valueOf(isoConstants.getA1()), txtFieldA1.getText());
            assertEquals(String.valueOf(isoConstants.getA2()), labelA2.getText());
            assertEquals(String.valueOf(isoConstants.getDecayConstant()), labelDecayConst.getText());
            assertEquals(String.valueOf(isoConstants.getExemptConcentration()), labelExemptCon.getText());
            assertEquals(String.valueOf(isoConstants.getExemptLimit()), labelExemptLim.getText());
            assertEquals(String.valueOf(isoConstants.getHalfLife()), labelHalfLife.getText());
            assertEquals(String.valueOf(isoConstants.getTeraBqReportQuan()), labelReportQuan.getText());
        });
    }

    @Test
    void testRadUnitListener() {
        interact(() -> {
            tableViewSearch.getSelectionModel().select(0);

            IsotopeConstants isoConstants = tableViewSearch.getItems().get(0).getConstants();
            BigDecimal a1 = BigDecimal.valueOf(isoConstants.getA1());
            BigDecimal a2 = BigDecimal.valueOf(isoConstants.getA2());
            BigDecimal exemptCon = BigDecimal.valueOf(isoConstants.getExemptConcentration());
            BigDecimal exemptLim = BigDecimal.valueOf(isoConstants.getExemptLimit());
            BigDecimal reportQuan = BigDecimal.valueOf(isoConstants.getTeraBqReportQuan());

            // A1
            String exp = String.valueOf(isoConstants.getA1());
            assertEquals(exp, txtFieldA1.getText());

            choiceBoxRefA1RadUnit.getSelectionModel().select(Isotope.RadUnit.CURIE.getVal());
            exp = Conversions.bqToCi(a1).toString();
            assertEquals(exp, txtFieldA1.getText());

            choiceBoxRefA1RadUnit.getSelectionModel().select(Isotope.RadUnit.BECQUEREL.getVal());
            exp = String.valueOf(isoConstants.getA1());
            assertEquals(exp, txtFieldA1.getText());

            // A2
            exp = String.valueOf(isoConstants.getA2());
            assertEquals(exp, labelA2.getText());

            choiceBoxRefA2RadUnit.getSelectionModel().select(Isotope.RadUnit.CURIE.getVal());
            exp = Conversions.bqToCi(a2).toString();
            assertEquals(exp, labelA2.getText());

            choiceBoxRefA2RadUnit.getSelectionModel().select(Isotope.RadUnit.BECQUEREL.getVal());
            exp = String.valueOf(isoConstants.getA2());
            assertEquals(exp, labelA2.getText());

            // Exempt Concentration
            exp = String.valueOf(isoConstants.getExemptConcentration());
            assertEquals(exp, labelExemptCon.getText());

            choiceBoxRefExemptConRadUnit.getSelectionModel().select(Isotope.RadUnit.CURIE.getVal());
            exp = Conversions.bqToCi(exemptCon).toString();
            assertEquals(exp, labelExemptCon.getText());

            choiceBoxRefExemptConRadUnit.getSelectionModel().select(Isotope.RadUnit.BECQUEREL.getVal());
            exp = String.valueOf(isoConstants.getExemptConcentration());
            assertEquals(exp, labelExemptCon.getText());

            // Exempt Limit
            exp = String.valueOf(isoConstants.getExemptLimit());
            assertEquals(exp, labelExemptLim.getText());

            choiceBoxRefExemptLimRadUnit.getSelectionModel().select(Isotope.RadUnit.CURIE.getVal());
            exp = Conversions.bqToCi(exemptLim).toString();
            assertEquals(exp, labelExemptLim.getText());

            choiceBoxRefExemptLimRadUnit.getSelectionModel().select(Isotope.RadUnit.BECQUEREL.getVal());
            exp = String.valueOf(isoConstants.getExemptLimit());
            assertEquals(exp, labelExemptLim.getText());

            //  Reportable Quantity
            exp = String.valueOf(isoConstants.getTeraBqReportQuan());
            assertEquals(exp, labelReportQuan.getText());

            choiceBoxRefReportQuanRadUnit.getSelectionModel().select(Isotope.RadUnit.CURIE.getVal());
            exp = Conversions.bqToCi(reportQuan).toString();
            assertEquals(exp, labelReportQuan.getText());

            choiceBoxRefReportQuanRadUnit.getSelectionModel().select(Isotope.RadUnit.BECQUEREL.getVal());
            exp = String.valueOf(isoConstants.getTeraBqReportQuan());
            assertEquals(exp, labelReportQuan.getText());
        });
    }

    @Test
    void testPrefixListener() {
        interact(() -> {
            tableViewSearch.getSelectionModel().select(0);

            IsotopeConstants isoConstants = tableViewSearch.getItems().get(0).getConstants();
            BigDecimal a1 = BigDecimal.valueOf(isoConstants.getA1());
            BigDecimal a2 = BigDecimal.valueOf(isoConstants.getA2());
            BigDecimal exemptCon = BigDecimal.valueOf(isoConstants.getExemptConcentration());
            BigDecimal exemptLim = BigDecimal.valueOf(isoConstants.getExemptLimit());
            BigDecimal reportQuan = BigDecimal.valueOf(isoConstants.getTeraBqReportQuan());

            // A1
            String exp = String.valueOf(isoConstants.getA1());
            assertEquals(exp, txtFieldA1.getText());

            comboBoxRefA1Prefix.getSelectionModel().select(Conversions.SIPrefix.PICO.getVal());
            exp = Conversions.convertToPrefix(a1, Conversions.SIPrefix.TERA, Conversions.SIPrefix.PICO).toString();
            assertEquals(exp, txtFieldA1.getText());

            comboBoxRefA1Prefix.getSelectionModel().select(Conversions.SIPrefix.TERA.getVal());
            exp = String.valueOf(isoConstants.getA1());
            assertEquals(exp, txtFieldA1.getText());

            // A2
            exp = String.valueOf(isoConstants.getA2());
            assertEquals(exp, labelA2.getText());

            comboBoxRefA2Prefix.getSelectionModel().select(Conversions.SIPrefix.YOTTA.getVal());
            exp = Conversions.convertToPrefix(a2, Conversions.SIPrefix.TERA, Conversions.SIPrefix.YOTTA).toString();
            assertEquals(exp, labelA2.getText());

            comboBoxRefA2Prefix.getSelectionModel().select(Conversions.SIPrefix.TERA.getVal());
            exp = String.valueOf(isoConstants.getA2());
            assertEquals(exp, labelA2.getText());

            // Exempt Concentration
            exp = String.valueOf(isoConstants.getExemptConcentration());
            assertEquals(exp, labelExemptCon.getText());

            comboBoxRefExemptConPrefix.getSelectionModel().select(Conversions.SIPrefix.MEGA.getVal());
            exp = Conversions.convertToPrefix(exemptCon, Conversions.SIPrefix.BASE, Conversions.SIPrefix.MEGA).toString();
            assertEquals(exp, labelExemptCon.getText());

            comboBoxRefExemptConPrefix.getSelectionModel().select(Conversions.SIPrefix.BASE.getVal());
            exp = String.valueOf(isoConstants.getExemptConcentration());
            assertEquals(exp, labelExemptCon.getText());

            // Exempt Limit
            exp = String.valueOf(isoConstants.getExemptLimit());
            assertEquals(exp, labelExemptLim.getText());

            comboBoxRefExemptLimPrefix.getSelectionModel().select(Conversions.SIPrefix.EXA.getVal());
            exp = Conversions.convertToPrefix(exemptLim, Conversions.SIPrefix.BASE, Conversions.SIPrefix.EXA).toString();
            assertEquals(exp, labelExemptLim.getText());

            comboBoxRefExemptLimPrefix.getSelectionModel().select(Conversions.SIPrefix.BASE.getVal());
            exp = String.valueOf(isoConstants.getExemptLimit());
            assertEquals(exp, labelExemptLim.getText());

            //  Reportable Quantity
            exp = String.valueOf(isoConstants.getTeraBqReportQuan());
            assertEquals(exp, labelReportQuan.getText());

            comboBoxRefReportQuanPrefix.getSelectionModel().select(Conversions.SIPrefix.MICRO.getVal());
            exp = Conversions.convertToPrefix(reportQuan, Conversions.SIPrefix.TERA, Conversions.SIPrefix.MICRO).toString();
            assertEquals(exp, labelReportQuan.getText());

            comboBoxRefReportQuanPrefix.getSelectionModel().select(Conversions.SIPrefix.TERA.getVal());
            exp = String.valueOf(isoConstants.getTeraBqReportQuan());
            assertEquals(exp, labelReportQuan.getText());
        });
    }

    @Test
    void testUnselectRowOnSearch() {
        interact(() -> {
            assertEquals(1, tableViewSearch.getItems().size());
            tableViewSearch.getSelectionModel().select(0);
            txtFieldSearch.setText("z");

            assertNull(tableViewSearch.getSelectionModel().getSelectedItem());
        });
    }

    @Test
    void testSearchFiltering() {
        interact(() -> {
            txtFieldSearch.setText(null);
            assertEquals(1, tableViewSearch.getItems().size());

            txtFieldSearch.setText("");
            assertEquals(1, tableViewSearch.getItems().size());

            txtFieldSearch.setText("A");
            assertEquals(1, tableViewSearch.getItems().size());

            txtFieldSearch.setText("Ab");
            assertEquals(1, tableViewSearch.getItems().size());

            txtFieldSearch.setText("Abb");
            assertEquals(1, tableViewSearch.getItems().size());

            txtFieldSearch.setText("Abbz");
            assertTrue(tableViewSearch.getItems().isEmpty());

            txtFieldSearch.setText("Abbr");
            assertEquals(1, tableViewSearch.getItems().size());
        });
    }
}
