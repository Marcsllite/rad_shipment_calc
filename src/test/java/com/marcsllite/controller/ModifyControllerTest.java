package com.marcsllite.controller;

import com.marcsllite.App;
import com.marcsllite.PropHandlerTestObj;
import com.marcsllite.model.Nuclide;
import com.marcsllite.model.Shipment;
import com.marcsllite.util.handler.StageHandler;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    void testSetSearchFilteredNuclides_NullParam() {
        controller.setSearchFilteredNuclides(null);
        assertEquals(0, controller.getSearchFilteredNuclides().size());
    }

    @Test
    void testSetFilteredLifeSpanNuclides_NullParam() {
        controller.setFilteredLifeSpanNuclides(null);
        assertEquals(0, controller.getFilteredLifeSpanNuclides().size());
    }

    @Test
    void testSetFilteredLungAbsNuclides_NullParam() {
        controller.setFilteredLungAbsNuclides(null);
        assertEquals(0, controller.getFilteredLungAbsNuclides().size());
    }

    @Test
    void testSetFirstPageValues_NullEditingNuclide() {
        ModifyController controllerSpy = spy(controller);
        when(controllerSpy.getEditingNuclide()).thenReturn(null);
        doNothing().when(controllerSpy).resetFirstPage();
        controllerSpy.setFirstPageValues();
        verify(controllerSpy).resetFirstPage();
    }

    @Test
    void testSetSecondPageValues_NullEditingNuclide() {
        ModifyController controllerSpy = spy(controller);
        when(controllerSpy.getEditingNuclide()).thenReturn(null);
        doNothing().when(controllerSpy).resetSecondPage();
        controllerSpy.setSecondPageValues();
        verify(controllerSpy).resetSecondPage();
    }

    @Test
    void testSetNuclides_Null() {
        controller.setNuclides(null);
        assertEquals(0, controller.getSearchFilteredNuclides().size());
        assertEquals(0, controller.getFilteredLifeSpanNuclides().size());
        assertEquals(0, controller.getFilteredLungAbsNuclides().size());
    }

    @Test
    void testBuildEditedNuclide_Null() {
        ModifyController controllerSpy = spy(controller);
        when(controllerSpy.getSearchFilteredNuclides()).thenReturn(new FilteredList<>(FXCollections.observableArrayList()));
        assertNull(controllerSpy.buildEditedNuclide());
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
            doNothing().when(controller).updateNuclideName(nuclide);
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
            doNothing().when(controller).updateNuclideName(nuclide);
            controller.finishBtnHandler();
        }

        verify(homePaneController).updateNuclide(nuclide);
        verify(stageHandler).closeSecondary();
    }
}