package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.FXMLView;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.WindowMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

class HomePaneControllerGUITest extends GUITest {
    @Spy
    HomePaneController controller;
    GridPane gridPaneHome;
    Button btnAdd;
    Button btnEdit;
    Button btnRemove;
    TableView<Nuclide> tableViewHome;
    Button btnCalculate;
    StackPane stackPaneModify;
    Shipment shipment;

    public HomePaneControllerGUITest() {
        super(FXMLView.HOME, BaseController.Page.HOME);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);

        gridPaneHome = GUITest.getNode(FXIds.GRID_PANE_HOME);
        btnAdd = GUITest.getNode(FXIds.BTN_ADD);
        btnEdit = GUITest.getNode(FXIds.BTN_EDIT);
        btnRemove = GUITest.getNode(FXIds.BTN_REMOVE);
        tableViewHome = GUITest.getNode(FXIds.TABLE_VIEW_HOME);
        btnCalculate = GUITest.getNode(FXIds.BTN_CALCULATE);
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

    @Test
    void testAddBtnHandler_ShowHide() {
        assertFalse(btnAdd.isDisabled());

        clickOn(btnAdd);
        stackPaneModify = GUITest.getNode(FXIds.STACK_PANE_MODIFY);
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isShowing());
        assertTrue(getController() instanceof ModifyController);
        ModifyController c = (ModifyController) getController();
        assertEquals(BaseController.Page.ADD, c.getPage());

        interact(() -> window(stackPaneModify).hide());
        FxAssert.verifyThat(window(gridPaneHome), WindowMatchers.isShowing());
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isNotShowing());
    }

    @Test
    void testEditBtnHandler_ShowHide() {
        clearNuclideTable();
        assertTrue(btnEdit.isDisabled());

        addNuclideToTable();
        selectRow(tableViewHome, 0);
        assertFalse(btnEdit.isDisabled());

        clickOn(btnEdit);
        stackPaneModify = GUITest.getNode(FXIds.STACK_PANE_MODIFY);
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isShowing());
        assertTrue(getController() instanceof ModifyController);
        ModifyController c = (ModifyController) getController();
        assertEquals(BaseController.Page.EDIT, c.getPage());

        interact(() -> window(stackPaneModify).hide());
        FxAssert.verifyThat(window(gridPaneHome), WindowMatchers.isShowing());
        FxAssert.verifyThat(window(stackPaneModify), WindowMatchers.isNotShowing());
    }

    protected void addNuclideToTable() {
        controller = (HomePaneController) getController();
        shipment = controller.getShipment();
        Nuclide nuclide = new Nuclide("Name", new NuclideModelId());
        shipment.setNuclides(FXCollections.observableArrayList(nuclide));
        assertFalse(shipment.getNuclides().isEmpty());
        assertEquals(shipment.getNuclides().get(0), tableViewHome.getItems().get(0));
    }

    protected void clearNuclideTable() {
        controller = (HomePaneController) getController();
        shipment = controller.getShipment();
        shipment.setNuclides(FXCollections.observableArrayList());
        assertTrue(shipment.getNuclides().isEmpty());
        assertTrue(tableViewHome.getItems().isEmpty());
    }

    @Test
    void testRemoveBtnHandler() {
        clearNuclideTable();
        assertTrue(btnRemove.isDisabled());

        addNuclideToTable();
        selectRow(tableViewHome, 0);
        assertFalse(btnRemove.isDisabled());

        clickOn(btnRemove);
        assertTrue(tableViewHome.getItems().isEmpty());
    }

    @Test
    void testCalculateBtnHandler() {
        clearNuclideTable();
        assertTrue(btnCalculate.isDisabled());

        addNuclideToTable();
        assertFalse(btnCalculate.isDisabled());
        selectRow(tableViewHome, 0);

        clickOn(btnCalculate);

        assertNull(tableViewHome.getSelectionModel().getSelectedItem());
    }
}
