package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.TestUtils;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.Shipment;
import com.marcsllite.model.db.NuclideModelId;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.StageHandler;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HomePaneControllerTest {
    HomePaneController controller;

    @BeforeEach
    public void setUp() throws IOException {
        controller = spy(new HomePaneController(new PropHandlerTestObj()));
        assertEquals(BaseController.Page.HOME, controller.getPage());
    }

    @Test
    void testAddBtnHandler_ShowHide()  {
        try(MockedStatic<App> appStaticMock = mockStatic(App.class)) {
            doNothing().when(controller).clearTable();
            StageHandler stageHandler = mock(StageHandler.class);
            appStaticMock.when(App::getStageHandler).thenReturn(stageHandler);
            doNothing().when(stageHandler).showModal(FXMLView.MODIFY, BaseController.Page.ADD);
            controller.addBtnHandler();
            verify(stageHandler).showModal(FXMLView.MODIFY, BaseController.Page.ADD);
        }
    }

    @Test
    void testEditBtnHandler_ShowHide()  {
        try(MockedStatic<App> appStaticMock = mockStatic(App.class)) {
            StageHandler stageHandler = mock(StageHandler.class);
            appStaticMock.when(App::getStageHandler).thenReturn(stageHandler);
            doNothing().when(stageHandler).showModal(FXMLView.MODIFY, BaseController.Page.EDIT);
            controller.editBtnHandler();
            verify(stageHandler).showModal(FXMLView.MODIFY, BaseController.Page.EDIT);
        }
    }

    @Test
    void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.homePaneHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }

    @Test
    void testIsIsoInTable_NullNuclide() {
        assertFalse(controller.isNuclideInTable(null));
    }

    @Test
    void testIsIsoInTable_NullShipment() {
        controller.setShipment(null);
        assertFalse(controller.isNuclideInTable(new Nuclide()));
    }

    @Test
    void testIsIsoInTable_EmptyTable() {
        Shipment shipment = spy(Shipment.class);
        shipment.setNuclides(FXCollections.observableArrayList());
        controller.setShipment(shipment);

        assertFalse(controller.isNuclideInTable(new Nuclide()));
    }

    @Test
    void testIsIsoInTable() {
        Shipment shipment = spy(Shipment.class);
        Nuclide nuclide = new Nuclide();
        shipment.setNuclides(FXCollections.observableArrayList(nuclide));
        controller.setShipment(shipment);

        Nuclide nuclide2 = new Nuclide();
        assertTrue(controller.isNuclideInTable(nuclide2));

        nuclide2.setLungAbsorption(Nuclide.LungAbsorption.FAST);
        assertFalse(controller.isNuclideInTable(nuclide2));

        nuclide2.setLifeSpan(Nuclide.LifeSpan.LONG);
        assertFalse(controller.isNuclideInTable(nuclide2));

        NuclideModelId nuclideId = new NuclideModelId("test", "s");
        nuclide2.setNuclideId(nuclideId);
        assertFalse(controller.isNuclideInTable(nuclide2));
    }

    @Test
    void testUpdateNuclide_NullNuclide() {
        controller.updateNuclide(null);

        verify(controller, times(0)).getShipment();
    }

    @Test
    void testUpdateNuclide_NotSelected() {
        controller.updateNuclide(TestUtils.createNuclide());
        
        verify(controller, times(0)).getShipment();
    }
}