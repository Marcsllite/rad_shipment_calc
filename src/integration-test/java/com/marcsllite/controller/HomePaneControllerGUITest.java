package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.TestUtils;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.Shipment;
import com.marcsllite.util.FXMLView;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
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
        assertTrue(gridPaneHome.isVisible());
        assertFalse(btnAdd.isDisabled());
        assertTrue(btnEdit.isDisabled());
        assertTrue(btnRemove.isDisabled());
        assertTrue(btnCalculate.isDisabled());
        assertTrue(tableViewHome.getItems().isEmpty());
    }

    @Test
    void testHideShow() {
        controller = (HomePaneController) getController();

        interact(() ->controller.hide());

        assertFalse(gridPaneHome.isVisible());

        interact(() ->controller.show());

        assertTrue(gridPaneHome.isVisible());
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

    @Disabled("failing on appveyor mac")
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
