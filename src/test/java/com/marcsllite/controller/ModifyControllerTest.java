package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.Shipment;
import com.marcsllite.util.handler.StageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.InvalidParameterException;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModifyControllerTest {
    ModifyController controller;

    @BeforeEach
    public void setUp() throws IOException {
        controller = new ModifyController(BaseController.Page.NONE, new PropHandlerTestObj());
        assertEquals(BaseController.Page.NONE, controller.getPage());
    }

    @Test
    void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.modifyHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }

    @Test
    void testFinish_Add() throws IOException {
        controller = spy(new ModifyController(BaseController.Page.ADD, new PropHandlerTestObj()));
        MainController mainController = mock(MainController.class);
        HomePaneController homePaneController = mock(HomePaneController.class);
        Shipment shipment = mock(Shipment.class);
        Nuclide nuclide = mock(Nuclide.class);
        StageHandler stageHandler = mock(StageHandler.class);
        controller.setMain(mainController);

        when(mainController.getHomePaneController()).thenReturn(homePaneController);
        when(homePaneController.getShipment()).thenReturn(shipment);
        doReturn(nuclide).when(controller).buildNuclide();
        doReturn(nuclide).when(nuclide).initConstants();
        doNothing().when(shipment).add(nuclide);
        doNothing().when(stageHandler).closeSecondary();

        try(MockedStatic<App> staticApp = mockStatic(App.class)) {
            staticApp.when(App::getStageHandler).thenReturn(stageHandler);
            controller.finishBtnHandler();
        }

        verify(shipment).add(nuclide);
        verify(stageHandler).closeSecondary();
    }

    @Test
    void testFinish_Edit() throws IOException {
        controller = spy(new ModifyController(BaseController.Page.EDIT, new PropHandlerTestObj()));
        MainController mainController = mock(MainController.class);
        HomePaneController homePaneController = mock(HomePaneController.class);
        Nuclide nuclide = mock(Nuclide.class);
        StageHandler stageHandler = mock(StageHandler.class);
        controller.setMain(mainController);

        when(mainController.getHomePaneController()).thenReturn(homePaneController);
        doReturn(nuclide).when(controller).buildNuclide();
        doNothing().when(homePaneController).updateNuclide(nuclide);
        doNothing().when(stageHandler).closeSecondary();

        try(MockedStatic<App> staticApp = mockStatic(App.class)) {
            staticApp.when(App::getStageHandler).thenReturn(stageHandler);
            controller.finishBtnHandler();
        }

        verify(homePaneController).updateNuclide(nuclide);
        verify(stageHandler).closeSecondary();
    }
}