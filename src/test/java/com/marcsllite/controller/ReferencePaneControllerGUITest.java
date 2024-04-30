package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
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
        gridPaneReference = GUITest.getNode(FXIds.GRID_PANE_REFERENCE);
        txtFieldSearch = GUITest.getNode(FXIds.TXT_FIELD_SEARCH);
        tableViewSearch = GUITest.getNode(FXIds.TABLE_VIEW_SEARCH);
        txtFieldA1 = GUITest.getNode(FXIds.TXT_FIELD_A1);
        comboBoxRefA1Prefix = GUITest.getNode(FXIds.COMBO_BOX_REF_A1_PREFIX);
        choiceBoxRefA1RadUnit = GUITest.getNode(FXIds.CHOICE_BOX_REF_A1_RAD_UNIT);
        txtFieldA2 = GUITest.getNode(FXIds.TXT_FIELD_A2);
        comboBoxRefA2Prefix = GUITest.getNode(FXIds.COMBO_BOX_REF_A2_PREFIX);
        choiceBoxRefA2RadUnit = GUITest.getNode(FXIds.CHOICE_BOX_REF_A2_RAD_UNIT);
        txtFieldDecayConst = GUITest.getNode(FXIds.TXT_FIELD_DECAY_CONSTANT);
        txtFieldExemptCon = GUITest.getNode(FXIds.TXT_FIELD_EXEMPT_CON);
        comboBoxRefExemptConPrefix = GUITest.getNode(FXIds.COMBO_BOX_REF_EXEMPT_CON_PREFIX);
        choiceBoxRefExemptConRadUnit = GUITest.getNode(FXIds.CHOICE_BOX_REF_EXEMPT_CON_RAD_UNIT);
        txtFieldExemptLim = GUITest.getNode(FXIds.TXT_FIELD_EXEMPT_LIMIT);
        comboBoxRefExemptLimPrefix = GUITest.getNode(FXIds.COMBO_BOX_REF_EXEMPT_LIM_PREFIX);
        choiceBoxRefExemptLimRadUnit = GUITest.getNode(FXIds.CHOICE_BOX_REF_EXEMPT_LIM_RAD_UNIT);
        txtFieldHalfLife = GUITest.getNode(FXIds.TXT_FIELD_HALF_LIFE);
        txtFieldReportQuan = GUITest.getNode(FXIds.TXT_FIELD_REPORTABLE_QUANTITY);
        comboBoxRefReportQuanPrefix = GUITest.getNode(FXIds.COMBO_BOX_REF_REPORT_QUAN_PREFIX);
        choiceBoxRefReportQuanRadUnit = GUITest.getNode(FXIds.CHOICE_BOX_REF_REPORT_QUAN_RAD_UNIT);
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
        assertNull(tableViewSearch.getSelectionModel().getSelectedItem());
        validateRefTextFields(txtFieldA1, "A1");
        validateRefTextFields(txtFieldA2, "A2");
        validateRefTextFields(txtFieldDecayConst, "Decay Constant");
        validateRefTextFields(txtFieldExemptCon, "Exempt Concentration");
        validateRefTextFields(txtFieldExemptLim, "Exempt Limit");
        validateRefTextFields(txtFieldHalfLife, "Half Life");
        validateRefTextFields(txtFieldReportQuan, "Reportable Quantity");

        ObservableList<Nuclide> tableItems = tableViewSearch.getItems();
        assertEquals(4, tableItems.size());

        selectRow(tableViewSearch, 0);
        validateRefDataForSelectedRow(0);

        selectRow(tableViewSearch, 1);
        validateRefDataForSelectedRow(1);

        selectRow(tableViewSearch, 2);
        validateRefDataForSelectedRow(2);

        selectRow(tableViewSearch, 3);
        validateRefDataForSelectedRow(3);
    }

    protected void validateRefDataForSelectedRow(int index) {
        NuclideConstants isoConstants = tableViewSearch.getItems().get(index).getConstants();

        assertEquals(isoConstants.getA1().toDisplayString(), txtFieldA1.getText());
        assertEquals(isoConstants.getA2().toDisplayString(), txtFieldA2.getText());
        assertEquals(isoConstants.getDecayConstant().toDisplayString(), txtFieldDecayConst.getText());
        assertEquals(isoConstants.getExemptConcentration().toDisplayString(), txtFieldExemptCon.getText());
        assertEquals(isoConstants.getExemptLimit().toDisplayString(), txtFieldExemptLim.getText());
        assertEquals(isoConstants.getHalfLife().toDisplayString(), txtFieldHalfLife.getText());
        assertEquals(isoConstants.getTeraBqReportQuan().toDisplayString(), txtFieldReportQuan.getText());
    }

    protected void validateRefTextFields(TextField field, String promptTxt) {
        assertTrue(field.getText().isEmpty());
        assertFalse(field.isEditable());
        assertEquals(promptTxt, field.getPromptText());
    }

    @Test
    void testRadUnitListener() {
        selectRow(tableViewSearch, 0);

        NuclideConstants isoConstants = tableViewSearch.getItems().get(0).getConstants();

        verifyRadUnitConversions(choiceBoxRefA1RadUnit, txtFieldA1, isoConstants.getA1());
        verifyRadUnitConversions(choiceBoxRefA2RadUnit, txtFieldA2, isoConstants.getA2());
        verifyRadUnitConversions(choiceBoxRefExemptConRadUnit, txtFieldExemptCon, isoConstants.getExemptConcentration());
        verifyRadUnitConversions(choiceBoxRefExemptLimRadUnit, txtFieldExemptLim, isoConstants.getExemptLimit());
        verifyRadUnitConversions(choiceBoxRefReportQuanRadUnit, txtFieldReportQuan, isoConstants.getTeraBqReportQuan());
        interact(() -> clearSelection(tableViewSearch));
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
        selectRow(tableViewSearch, 0);

        NuclideConstants isoConstants = tableViewSearch.getItems().get(0).getConstants();

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
        selectRow(tableViewSearch, 0);

        interact(() ->txtFieldSearch.setText(null));
        assertNotNull(tableViewSearch.getSelectionModel().getSelectedItem());

        interact(() ->txtFieldSearch.setText(""));
        assertNotNull(tableViewSearch.getSelectionModel().getSelectedItem());

        interact(() ->txtFieldSearch.setText(" "));
        assertNotNull(tableViewSearch.getSelectionModel().getSelectedItem());

        interact(() ->txtFieldSearch.setText("z"));
        assertNull(tableViewSearch.getSelectionModel().getSelectedItem());

        interact(() ->txtFieldSearch.setText(null));
        assertNull(tableViewSearch.getSelectionModel().getSelectedItem());
    }

    @Test
    void testSearchFiltering() {
        interact(() -> txtFieldSearch.setText(null));
        assertEquals(4, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText(""));
        assertEquals(4, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText("A"));
        assertEquals(3, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText("An"));
        assertEquals(1, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText("An-"));
        assertEquals(1, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText("An-z"));
        assertTrue(tableViewSearch.getItems().isEmpty());

        interact(() -> txtFieldSearch.setText("An-1"));
        assertEquals(1, tableViewSearch.getItems().size());
    }
}
