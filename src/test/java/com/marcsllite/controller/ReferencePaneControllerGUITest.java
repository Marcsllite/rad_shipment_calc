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
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

class ReferencePaneControllerGUITest extends GUITest {
    ReferencePaneController controller;
    GridPane gridPaneReference;
    TextField txtFieldSearch;
    TableView<Isotope> tableViewSearch;
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

    @Test
    void testInit() {
        FxAssert.verifyThat(gridPaneReference, NodeMatchers.isVisible());

        verifySetupDropDownItems();
        verifyInitTable();
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

        ObservableList<Isotope> tableItems = tableViewSearch.getItems();
        assertEquals(2, tableItems.size());

        selectRow(0);
        validateSelectedRow(0);

        selectRow(1);
        validateSelectedRow(1);
        clearSelection();
    }

    protected void validateSelectedRow(int index) {
        IsotopeConstants isoConstants = tableViewSearch.getItems().get(index).getConstants();

        assertEquals(String.valueOf(isoConstants.getA1()), txtFieldA1.getText());
        assertEquals(String.valueOf(isoConstants.getA2()), txtFieldA2.getText());
        assertEquals(String.valueOf(isoConstants.getDecayConstant()), txtFieldDecayConst.getText());
        assertEquals(String.valueOf(isoConstants.getExemptConcentration()), txtFieldExemptCon.getText());
        assertEquals(String.valueOf(isoConstants.getExemptLimit()), txtFieldExemptLim.getText());
        assertEquals(String.valueOf(isoConstants.getHalfLife()), txtFieldHalfLife.getText());
        assertEquals(String.valueOf(isoConstants.getTeraBqReportQuan()), txtFieldReportQuan.getText());
    }

    protected void validateRefTextFields(TextField field, String promptTxt) {
        assertTrue(field.getText().isEmpty());
        assertFalse(field.isEditable());
        assertEquals(promptTxt, field.getPromptText());
    }

    @Test
    void testRadUnitListener() {
        selectRow(0);

        IsotopeConstants isoConstants = tableViewSearch.getItems().get(0).getConstants();

        verifyRadUnitConversions(choiceBoxRefA1RadUnit, txtFieldA1, isoConstants.getA1());
        verifyRadUnitConversions(choiceBoxRefA2RadUnit, txtFieldA2, isoConstants.getA2());
        verifyRadUnitConversions(choiceBoxRefExemptConRadUnit, txtFieldExemptCon, isoConstants.getExemptConcentration());
        verifyRadUnitConversions(choiceBoxRefExemptLimRadUnit, txtFieldExemptLim, isoConstants.getExemptLimit());
        verifyRadUnitConversions(choiceBoxRefReportQuanRadUnit, txtFieldReportQuan, isoConstants.getTeraBqReportQuan());
        interact(this::clearSelection);
    }

    protected void verifyRadUnitConversions(ChoiceBox<String> choiceBox, TextField field, float original) {
        String exp = String.valueOf(original);
        assertEquals(exp, field.getText());

        interact(() -> choiceBox.getSelectionModel().select(Isotope.RadUnit.CURIE.getVal()));
        exp = Conversions.bqToCi(BigDecimal.valueOf(original)).toString();
        assertEquals(exp, field.getText());

        interact(() -> choiceBox.getSelectionModel().select(Isotope.RadUnit.BECQUEREL.getVal()));
        exp = String.valueOf(original);
        assertEquals(exp, field.getText());
    }

    @Test
    void testPrefixListener() {
        selectRow(0);

        IsotopeConstants isoConstants = tableViewSearch.getItems().get(0).getConstants();

        verifyPrefixConversions(comboBoxRefA1Prefix, txtFieldA1, Conversions.SIPrefix.TERA, isoConstants.getA1());
        verifyPrefixConversions(comboBoxRefA2Prefix, txtFieldA2, Conversions.SIPrefix.TERA, isoConstants.getA2());
        verifyPrefixConversions(comboBoxRefExemptConPrefix, txtFieldExemptCon, Conversions.SIPrefix.BASE, isoConstants.getExemptConcentration());
        verifyPrefixConversions(comboBoxRefExemptLimPrefix, txtFieldExemptLim, Conversions.SIPrefix.BASE, isoConstants.getExemptLimit());
        verifyPrefixConversions(comboBoxRefReportQuanPrefix, txtFieldReportQuan, Conversions.SIPrefix.TERA, isoConstants.getTeraBqReportQuan());

        clearSelection();
    }

    protected void verifyPrefixConversions(ComboBox<String> comboBox, TextField field, Conversions.SIPrefix start, float original) {
        String exp = String.valueOf(original);
        assertEquals(exp, field.getText());

        Optional<Conversions.SIPrefix> end = Arrays.stream(Conversions.SIPrefix.values()).findAny();
        assertTrue(end.isPresent());

        interact(() ->comboBox.getSelectionModel().select(end.get().getVal()));
        exp = Conversions.convertToPrefix(BigDecimal.valueOf(original),
                start,
                end.get()).toString();
        assertEquals(exp, field.getText());

        interact(() ->comboBox.getSelectionModel().select(start.getVal()));
        exp = String.valueOf(original);
        assertEquals(exp, field.getText());
    }

    @Test
    void testUnselectRowOnSearch() {
        selectRow(0);

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

    protected void selectRow(int index) {
        if(tableViewSearch.getItems().isEmpty()) {
            fail("Table has no items to select from");
        }

        interact(() ->tableViewSearch.getSelectionModel().select(index));
        assertNotNull(tableViewSearch.getSelectionModel().getSelectedItem());
    }

    protected void clearSelection() {
        interact(() -> tableViewSearch.getSelectionModel().clearSelection());
        assertNull(tableViewSearch.getSelectionModel().getSelectedItem());
    }

    @Test
    void testSearchFiltering() {
        interact(() -> txtFieldSearch.setText(null));
        assertEquals(2, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText(""));
        assertEquals(2, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText("A"));
        assertEquals(2, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText("An"));
        assertEquals(1, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText("Ann"));
        assertEquals(1, tableViewSearch.getItems().size());

        interact(() -> txtFieldSearch.setText("Annz"));
        assertTrue(tableViewSearch.getItems().isEmpty());

        interact(() -> txtFieldSearch.setText("Anny"));
        assertEquals(1, tableViewSearch.getItems().size());
    }
}
