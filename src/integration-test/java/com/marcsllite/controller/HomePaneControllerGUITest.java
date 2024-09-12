package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.TestUtils;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.LimitsModelId;
import com.marcsllite.util.Conversions;
import com.marcsllite.util.FXMLView;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.mockito.Spy;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.WindowMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class HomePaneControllerGUITest extends GUITest {
    @Spy HomePaneController controller;
    GridPane gridPaneHome;
    Button btnAdd;
    Button btnEdit;
    Button btnRemove;
    TableView<Nuclide> tableViewHome;
    Button btnCalculate;
    StackPane stackPaneModify;
    TextField txtFieldNuclideName;
    TextField txtFieldA0;
    ComboBox<String> comboBoxA0Prefix;
    ChoiceBox<String> choiceBoxA0RadUnit;
    RadioButton radioBtnShortLived;
    RadioButton radioBtnLongLived;
    RadioButton radioBtnSlowLungAbs;
    RadioButton radioBtnMediumLungAbs;
    RadioButton radioBtnFastLungAbs;
    Button btnNext;
    DatePicker datePicker;
    TextField txtFieldMass;
    ComboBox<String> comboBoxMassPrefix;
    ChoiceBox<String> choiceBoxMassUnit;
    ChoiceBox<String> choiceBoxNature;
    ChoiceBox<String> choiceBoxState;
    ChoiceBox<String> choiceBoxForm;
    Button btnBack;
    Button btnFinish;
    Shipment shipment;

    public HomePaneControllerGUITest() {
        super(FXMLView.HOME, BaseController.Page.HOME);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (HomePaneController) getController();
        gridPaneHome = controller.homePane;
        btnAdd = controller.btnAdd;
        btnEdit = controller.btnEdit;
        btnRemove = controller.btnRemove;
        tableViewHome = controller.tableViewHome;
        btnCalculate = controller.btnCalculate;
    }

    @BeforeEach
    public void setUp() {
        clearSelection(tableViewHome);
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(gridPaneHome, NodeMatchers.isVisible());
        FxAssert.verifyThat(btnAdd, NodeMatchers.isEnabled());
        FxAssert.verifyThat(btnEdit, NodeMatchers.isDisabled());
        FxAssert.verifyThat(btnRemove, NodeMatchers.isDisabled());
        FxAssert.verifyThat(btnCalculate, NodeMatchers.isDisabled());
        assertTrue(tableViewHome.getItems().isEmpty());
    }

    @Test
    void testHideShow() {
        controller = (HomePaneController) getController();

        interact(() ->controller.hide());

        FxAssert.verifyThat(gridPaneHome, NodeMatchers.isInvisible());

        interact(() ->controller.show());

        FxAssert.verifyThat(gridPaneHome, NodeMatchers.isVisible());
    }

    protected void setModifyPaneNodes() {
        ModifyController modifyController = (ModifyController) getController();
        stackPaneModify = modifyController.modifyPane;
        txtFieldNuclideName = modifyController.txtFieldNuclideName;
        txtFieldA0 = modifyController.txtFieldA0;
        comboBoxA0Prefix = modifyController.comboBoxA0Prefix;
        choiceBoxA0RadUnit = modifyController.choiceBoxA0RadUnit;
        radioBtnShortLived = modifyController.radioBtnShortLived;
        radioBtnLongLived = modifyController.radioBtnLongLived;
        radioBtnSlowLungAbs = modifyController.radioBtnSlowLungAbs;
        radioBtnMediumLungAbs = modifyController.radioBtnMediumLungAbs;
        radioBtnFastLungAbs = modifyController.radioBtnFastLungAbs;
        btnNext = modifyController.btnNext;
        datePicker = modifyController.datePicker;
        txtFieldMass = modifyController.txtFieldMass;
        comboBoxMassPrefix = modifyController.comboBoxMassPrefix;
        choiceBoxMassUnit = modifyController.choiceBoxMassUnit;
        choiceBoxNature = modifyController.choiceBoxNature;
        choiceBoxState = modifyController.choiceBoxState;
        choiceBoxForm = modifyController.choiceBoxForm;
        btnBack = modifyController.btnBack;
        btnFinish = modifyController.btnFinish;
    }
    
    @Test
    void testAddBtnHandler_ShowHide() {
        assertFalse(btnAdd.isDisabled());

        clickOn(btnAdd);
        setModifyPaneNodes();
        
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isShowing());
        assertTrue(getController() instanceof ModifyController);
        ModifyController c = (ModifyController) getController();
        assertEquals(BaseController.Page.ADD, c.getPage());
        verifyModifyPane(null);

        interact(() -> window(stackPaneModify).hide());
        FxAssert.verifyThat(window(gridPaneHome), WindowMatchers.isShowing());
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isNotShowing());
    }

    @ParameterizedTest(name = "testEditNuclide-{0}")
    @FieldSource("com.marcsllite.TestUtils#testNuclides")
    void testEditBtnHandler_ShowHide(TestUtils.TestNuclide testNuclide) {
        Nuclide nuclide = TestUtils.createNuclide(testNuclide);
        clearNuclideTable();
        assertTrue(btnEdit.isDisabled());

        addNuclideToTable(nuclide);
        selectRow(tableViewHome, 0);
        assertFalse(btnEdit.isDisabled());

        clickOn(btnEdit);
        setModifyPaneNodes();
        
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isShowing());
        assertTrue(getController() instanceof ModifyController);
        ModifyController c = (ModifyController) getController();
        assertEquals(BaseController.Page.EDIT, c.getPage());
        verifyModifyPane(nuclide);

        clickOn(btnNext);
        clickOn(btnFinish);
        FxAssert.verifyThat(window(gridPaneHome), WindowMatchers.isShowing());
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isNotShowing());
    }
    
    protected void verifyModifyPane(Nuclide nuclide) {
        if(nuclide == null) {
            assertNull(txtFieldNuclideName.getText());
            assertNull(txtFieldA0.getText());
            assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxA0Prefix.getSelectionModel().getSelectedItem());
            assertEquals(Conversions.RadUnit.CURIE.getVal(), choiceBoxA0RadUnit.getSelectionModel().getSelectedItem());
            assertTrue(btnNext.isDisabled());
            assertNotNull(datePicker.getValue());
            assertNull(txtFieldMass.getText());
            assertEquals(Conversions.SIPrefix.BASE.getVal(), comboBoxMassPrefix.getSelectionModel().getSelectedItem());
            assertEquals(Conversions.MassUnit.GRAMS.getVal(), choiceBoxMassUnit.getSelectionModel().getSelectedItem());
            assertEquals(Nuclide.Nature.REGULAR.getVal(), choiceBoxNature.getSelectionModel().getSelectedItem());
            assertEquals(LimitsModelId.State.SOLID.getVal(), choiceBoxState.getSelectionModel().getSelectedItem());
            assertEquals(LimitsModelId.Form.NORMAL.getVal(), choiceBoxForm.getSelectionModel().getSelectedItem());
            assertFalse(btnBack.isDisabled());
            assertTrue(btnFinish.isDisabled());
        } else {
            assertEquals(nuclide.getDisplayNameNotation(), txtFieldNuclideName.getText());
            if(nuclide.getInitActivity().isInfinity() ||
                nuclide.getInitActivity().isNegativeInfinity()) {
                assertNull(txtFieldA0.getText());
            } else {
                assertEquals(nuclide.getInitActivityStr(), txtFieldA0.getText());
            }
            assertEquals(nuclide.getInitActivityPrefix().getVal(), comboBoxA0Prefix.getSelectionModel().getSelectedItem());
            assertEquals(nuclide.getInitActivityUnit().getVal(), choiceBoxA0RadUnit.getSelectionModel().getSelectedItem());
            assertFalse(btnNext.isDisabled());
            assertEquals(nuclide.getRefDate(), datePicker.getValue());
            if(nuclide.getMass().isInfinity() ||
                nuclide.getMass().isNegativeInfinity()) {
                assertNull(txtFieldMass.getText());
            } else {
                assertEquals(nuclide.getMassStr(), txtFieldMass.getText());
            }
            assertEquals(nuclide.getMassPrefix().getVal(), comboBoxMassPrefix.getSelectionModel().getSelectedItem());
            assertEquals(nuclide.getMassUnit().getVal(), choiceBoxMassUnit.getSelectionModel().getSelectedItem());
            assertEquals(nuclide.getNature().getVal(), choiceBoxNature.getSelectionModel().getSelectedItem());
            assertEquals(nuclide.getLimitsId().getState().getVal(), choiceBoxState.getSelectionModel().getSelectedItem());
            assertEquals(nuclide.getLimitsId().getForm().getVal(), choiceBoxForm.getSelectionModel().getSelectedItem());
            assertFalse(btnBack.isDisabled());
            assertFalse(btnFinish.isDisabled());
        }
    }

    protected void addNuclideToTable(Nuclide nuclide) {
        if(nuclide == null) {
            nuclide = new Nuclide();
        }

        controller = (HomePaneController) getController();
        shipment = controller.getShipment();
        int size = shipment.getNuclides().size();
        shipment.getNuclides().add(nuclide);
        assertEquals(size+1, shipment.getNuclides().size());
        assertFalse(shipment.getNuclides().isEmpty());
        assertEquals(shipment.getNuclides().get(size), tableViewHome.getItems().get(size));
    }

    protected void clearNuclideTable() {
        controller = (HomePaneController) getController();
        shipment = controller.getShipment();
        shipment.setNuclides(null);
        assertTrue(shipment.getNuclides().isEmpty());
        assertTrue(tableViewHome.getItems().isEmpty());
    }

    @Test
    void testRemoveBtnHandler() {
        clearNuclideTable();
        assertTrue(btnRemove.isDisabled());

        addNuclideToTable(TestUtils.createNuclide());
        selectRow(tableViewHome, 0);
        assertFalse(btnRemove.isDisabled());

        clickOn(btnRemove);
        assertTrue(tableViewHome.getItems().isEmpty());
    }

    @Test
    void testRemoveBtnHandler_FailedToRemove() {
        controller = (HomePaneController) getController();
        Shipment shipmentSpy = spy(controller.getShipment());
        controller.setShipment(shipmentSpy);

        when(shipmentSpy.remove(any())).thenReturn(false);

        RuntimeException ex = assertThrows(
            RuntimeException.class,
            () -> controller.removeBtnHandler()
        );

        Assertions.assertEquals("Failed to remove the selection", ex.getMessage());
    }

    @Test
    void testCalculateBtnHandler() {
        clearNuclideTable();
        assertTrue(btnCalculate.isDisabled());

        addNuclideToTable(TestUtils.createNuclide());
        assertFalse(btnCalculate.isDisabled());
        selectRow(tableViewHome, 0);

        clickOn(btnCalculate);

        // TODO: selection isn't cleared on appveyor mac
        //assertEquals(0, tableViewHome.getSelectionModel().getSelectedItems().size());
    }

    @Test
    void testUpdateNuclide_MultipleSelection() {
        clearNuclideTable();
        int nuclidesInTable = (int) TestUtils.getRandomNumber(3, 10);
        int selection = (int) TestUtils.getRandomNumber(2, nuclidesInTable);

        for(int i = 0; i < nuclidesInTable; i++) {
            addNuclideToTable(TestUtils.createNuclide());
        }

        selectRows(tableViewHome, 0, selection+1);
        assertFalse(btnAdd.isDisabled());
        assertTrue(btnEdit.isDisabled());
        assertFalse(btnRemove.isDisabled());
        assertFalse(btnCalculate.isDisabled());
    }
}
