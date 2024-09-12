package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.TestUtils;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.NuclideConstants;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.RadBigDecimal;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

class ReferencePaneControllerGUITest extends GUITest {
    ReferencePaneController controller;
    GridPane gridPaneReference;
    TextField txtFieldSearch;
    TableView<Nuclide> tableViewSearch;
    TextField txtFieldA1;
    ComboBox<String> comboBoxRefA1Prefix;
    ChoiceBox<String> choiceBoxRefA1RadUnit;
    TextField txtFieldA2;
    ComboBox<String> comboBoxRefA2Prefix;
    ChoiceBox<String> choiceBoxRefA2RadUnit;
    TextField txtFieldDecayConst;
    TextField txtFieldExemptCon;
    ComboBox<String> comboBoxRefExemptConPrefix;
    ChoiceBox<String> choiceBoxRefExemptConRadUnit;
    TextField txtFieldExemptLim;
    ComboBox<String> comboBoxRefExemptLimPrefix;
    ChoiceBox<String> choiceBoxRefExemptLimRadUnit;
    TextField txtFieldHalfLife;
    TextField txtFieldReportQuan;
    ComboBox<String> comboBoxRefReportQuanPrefix;
    ChoiceBox<String> choiceBoxRefReportQuanRadUnit;

    public ReferencePaneControllerGUITest() {
        super(FXMLView.REFERENCE, BaseController.Page.REFERENCE);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (ReferencePaneController) getController();
        gridPaneReference = controller.referencePane;
        txtFieldSearch = controller.txtFieldSearch;
        tableViewSearch = controller.tableViewSearch;
        txtFieldA1 = controller.txtFieldA1;
        comboBoxRefA1Prefix = controller.comboBoxRefA1Prefix;
        choiceBoxRefA1RadUnit = controller.choiceBoxRefA1RadUnit;
        txtFieldA2 = controller.txtFieldA2;
        comboBoxRefA2Prefix = controller.comboBoxRefA2Prefix;
        choiceBoxRefA2RadUnit = controller.choiceBoxRefA2RadUnit;
        txtFieldDecayConst = controller.txtFieldDecayConst;
        txtFieldExemptCon = controller.txtFieldExemptCon;
        comboBoxRefExemptConPrefix = controller.comboBoxRefExemptConPrefix;
        choiceBoxRefExemptConRadUnit = controller.choiceBoxRefExemptConRadUnit;
        txtFieldExemptLim = controller.txtFieldExemptLim;
        comboBoxRefExemptLimPrefix = controller.comboBoxRefExemptLimPrefix;
        choiceBoxRefExemptLimRadUnit = controller.choiceBoxRefExemptLimRadUnit;
        txtFieldHalfLife = controller.txtFieldHalfLife;
        txtFieldReportQuan = controller.txtFieldReportQuan;
        comboBoxRefReportQuanPrefix = controller.comboBoxRefReportQuanPrefix;
        choiceBoxRefReportQuanRadUnit = controller.choiceBoxRefReportQuanRadUnit;
    }

    @BeforeEach
    public void setUp() {
        clearSelection(tableViewSearch);
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(gridPaneReference, NodeMatchers.isVisible());

        verifySetupDropDownItems();
        verifyInitTable();
    }

    protected void verifySetupDropDownItems() {
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefA1Prefix.getItems());
        assertEquals(Conversions.RadUnit.getFxValues(), choiceBoxRefA1RadUnit.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefA2Prefix.getItems());
        assertEquals(Conversions.RadUnit.getFxValues(), choiceBoxRefA2RadUnit.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefExemptConPrefix.getItems());
        assertEquals(Conversions.RadUnit.getFxValues(), choiceBoxRefExemptConRadUnit.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefExemptLimPrefix.getItems());
        assertEquals(Conversions.RadUnit.getFxValues(), choiceBoxRefExemptLimRadUnit.getItems());
        assertEquals(Conversions.SIPrefix.getFxValues(), comboBoxRefReportQuanPrefix.getItems());
        assertEquals(Conversions.RadUnit.getFxValues(), choiceBoxRefReportQuanRadUnit.getItems());

        verifySelectBaseDropDownItems();
    }

    protected void verifySelectBaseDropDownItems() {
        assertEquals(Conversions.SIPrefix.TERA.getVal(), comboBoxRefA1Prefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.RadUnit.BECQUEREL.getVal(), choiceBoxRefA1RadUnit.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.TERA.getVal(), comboBoxRefA2Prefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.RadUnit.BECQUEREL.getVal(), choiceBoxRefA2RadUnit.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxRefExemptConPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.RadUnit.BECQUEREL.getVal(), choiceBoxRefExemptConRadUnit.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxRefExemptLimPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.RadUnit.BECQUEREL.getVal(), choiceBoxRefExemptLimRadUnit.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.SIPrefix.TERA.getVal(), comboBoxRefReportQuanPrefix.getSelectionModel().getSelectedItem());
        assertEquals(Conversions.RadUnit.BECQUEREL.getVal(), choiceBoxRefReportQuanRadUnit.getSelectionModel().getSelectedItem());
    }

    protected void verifyInitTable() {
        assertFalse(tableViewSearch.getItems().isEmpty());

        assertEquals(SelectionMode.SINGLE, tableViewSearch.getSelectionModel().getSelectionMode());
        assertNull(tableViewSearch.getSelectionModel().getSelectedItem());
    }

    @Test
    void testHideShow() {
        interact(() -> controller.hide());

        FxAssert.verifyThat(gridPaneReference, NodeMatchers.isInvisible());

        interact(() -> controller.show());

        FxAssert.verifyThat(gridPaneReference, NodeMatchers.isVisible());
    }

    @Test
    void testTableDataLinking() {
        validateRefTextFields(txtFieldA1, "A1");
        validateRefTextFields(txtFieldA2, "A2");
        validateRefTextFields(txtFieldDecayConst, "Decay Constant");
        validateRefTextFields(txtFieldExemptCon, "Exempt Concentration");
        validateRefTextFields(txtFieldExemptLim, "Exempt Limit");
        validateRefTextFields(txtFieldHalfLife, "Half Life");
        validateRefTextFields(txtFieldReportQuan, "Reportable Quantity");

        ObservableList<Nuclide> tableItems = tableViewSearch.getItems();
        assertEquals(TestUtils.getTestNuclideSize(), tableItems.size());

        for (int i = 0; i < tableItems.size(); i++) {
            selectRow(tableViewSearch, i);
            validateRefDataForSelectedRow(i);
        }
    }

    protected void validateRefDataForSelectedRow(int index) {
        NuclideConstants isoConstants = tableViewSearch.getItems().get(index).getConstants();

        assertEquals("Incorrect A1 for index " + index, isoConstants.getA1().toDisplayString(), txtFieldA1.getText());
        assertEquals("Incorrect A2 for index " + index, isoConstants.getA2().toDisplayString(), txtFieldA2.getText());
        assertEquals("Incorrect Decay Constant for index " + index, isoConstants.getDecayConstant().toDisplayString(), txtFieldDecayConst.getText());
        assertEquals("Incorrect Exempt Concentration for index " + index, isoConstants.getExemptConcentration().toDisplayString(), txtFieldExemptCon.getText());
        assertEquals("Incorrect Exempt Limit for index " + index, isoConstants.getExemptLimit().toDisplayString(), txtFieldExemptLim.getText());
        assertEquals("Incorrect Half LIfe for index " + index, isoConstants.getHalfLife().toDisplayString(), txtFieldHalfLife.getText());
        assertEquals("Incorrect Report Quantity (TBq) for index " + index, isoConstants.getTeraBqReportQuan().toDisplayString(), txtFieldReportQuan.getText());
    }

    protected void validateRefTextFields(TextField field, String promptTxt) {
        assertTrue(field.getText().isEmpty());
        assertFalse(field.isEditable());
        assertEquals(promptTxt, field.getPromptText());
    }

    @Test
    void testRadUnitListener() {
        int index = TestUtils.getRandomRow(tableViewSearch);
        selectRow(tableViewSearch, index);

        NuclideConstants isoConstants = tableViewSearch.getItems().get(index).getConstants();

        verifyRadUnitConversions(choiceBoxRefA1RadUnit, txtFieldA1, isoConstants.getA1());
        verifyRadUnitConversions(choiceBoxRefA2RadUnit, txtFieldA2, isoConstants.getA2());
        verifyRadUnitConversions(choiceBoxRefExemptConRadUnit, txtFieldExemptCon, isoConstants.getExemptConcentration());
        verifyRadUnitConversions(choiceBoxRefExemptLimRadUnit, txtFieldExemptLim, isoConstants.getExemptLimit());
        verifyRadUnitConversions(choiceBoxRefReportQuanRadUnit, txtFieldReportQuan, isoConstants.getTeraBqReportQuan());
    }

    protected void verifyRadUnitConversions(ChoiceBox<String> choiceBox, TextField field, RadBigDecimal original) {
        String exp = original.toDisplayString();
        assertEquals(exp, field.getText());

        interact(() -> choiceBox.getSelectionModel().select(Conversions.RadUnit.CURIE.getVal()));
        exp = Conversions.bqToCi(original).toDisplayString();
        assertEquals(exp, field.getText());

        interact(() -> choiceBox.getSelectionModel().select(Conversions.RadUnit.BECQUEREL.getVal()));
        exp = Conversions.ciToBq(new RadBigDecimal(exp)).toDisplayString();
        assertEquals(exp, field.getText());
    }

    @Test
    void testPrefixListener() {
        int index = TestUtils.getRandomRow(tableViewSearch);
        selectRow(tableViewSearch, index);

        NuclideConstants isoConstants = tableViewSearch.getItems().get(index).getConstants();

        verifyPrefixConversions(comboBoxRefA1Prefix, txtFieldA1, Conversions.SIPrefix.TERA, isoConstants.getA1());
        verifyPrefixConversions(comboBoxRefA2Prefix, txtFieldA2, Conversions.SIPrefix.TERA, isoConstants.getA2());
        verifyPrefixConversions(comboBoxRefExemptConPrefix, txtFieldExemptCon, Conversions.SIPrefix.BASE, isoConstants.getExemptConcentration());
        verifyPrefixConversions(comboBoxRefExemptLimPrefix, txtFieldExemptLim, Conversions.SIPrefix.BASE, isoConstants.getExemptLimit());
        verifyPrefixConversions(comboBoxRefReportQuanPrefix, txtFieldReportQuan, Conversions.SIPrefix.TERA, isoConstants.getTeraBqReportQuan());
    }

    protected void verifyPrefixConversions(ComboBox<String> comboBox, TextField field, Conversions.SIPrefix start, RadBigDecimal original) {
        String exp = original.toDisplayString();
        assertEquals(exp, field.getText());

        Optional<Conversions.SIPrefix> end = Arrays.stream(Conversions.SIPrefix.values()).findAny();
        assertTrue(end.isPresent());

        interact(() ->comboBox.getSelectionModel().select(end.get().getVal()));
        exp = Conversions.convertToPrefix(original,
                start,
                end.get()).toDisplayString();
        assertEquals(exp, field.getText());

        interact(() ->comboBox.getSelectionModel().select(start.getVal()));
        exp = Conversions.convertToPrefix(new RadBigDecimal(exp),
            end.get(),
            start).toDisplayString();
        assertEquals(exp, field.getText());
    }

    @Test
    void testUnselectRowOnSearch() {
        selectRow(tableViewSearch, TestUtils.getRandomRow(tableViewSearch));
        assertEquals(TestUtils.getTestNuclideSize(), tableViewSearch.getItems().size());

        setString("", txtFieldSearch);
        assertNotNull(tableViewSearch.getSelectionModel().getSelectedItem());
        assertEquals(TestUtils.getTestNuclideSize(), tableViewSearch.getItems().size());

        setString("z", txtFieldSearch);
        assertNull(tableViewSearch.getSelectionModel().getSelectedItem());
        assertEquals(TestUtils.subStringTestNuclide("z"), tableViewSearch.getItems().size());

        setString(null, txtFieldSearch);
        assertEquals(TestUtils.getTestNuclideSize(), tableViewSearch.getItems().size());
        selectRow(tableViewSearch, TestUtils.getRandomRow(tableViewSearch));

        setString(" ", txtFieldSearch);
        assertNull(tableViewSearch.getSelectionModel().getSelectedItem());
    }

    @Test
    void testSearchFiltering() {
        Nuclide nuclide = TestUtils.createNuclide();
        String symbolNotation = nuclide.getDisplaySymbolNotation();
        int notationSize = symbolNotation.length();

        setString("", txtFieldSearch);
        assertEquals(TestUtils.getTestNuclideSize(), tableViewSearch.getItems().size());

        setString("NAN", txtFieldSearch);
        assertTrue(tableViewSearch.getItems().isEmpty());

        for (int i = 1; i < notationSize; i++) {
            setString(symbolNotation.substring(0, i), txtFieldSearch);
            assertEquals("Incorrect search results for substring length "+i+", ("+symbolNotation.substring(0, i)+")",
                TestUtils.subStringTestNuclide(symbolNotation.substring(0, i)),
                tableViewSearch.getItems().size());
        }
    }
}
