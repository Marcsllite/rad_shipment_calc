package com.marcsllite.controller;

import com.marcsllite.FXIds;
import com.marcsllite.GUITest;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.ImageHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

class MenuPaneControllerGUITest extends GUITest {
    MainController mainController;
    MenuPaneController controller;
    GridPane gridPaneMenu;
    StackPane stackPaneLogo;
    ImageView imgViewColorLogo;
    ImageView imgViewGreyLogo;
    Button btnShipment;
    ImageView imgViewShipment;
    Button btnReference;
    ImageView imgViewReference;
    ImageHandler.Colors CURRENT_COLOR;
    ImageHandler.Colors IDLE_COLOR;
    ImageHandler.Colors HOVER_COLOR;

    public MenuPaneControllerGUITest() {
        super(FXMLView.MENU, BaseController.Page.MENU);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (MenuPaneController) getController();
        gridPaneMenu = GUITest.getNode(FXIds.GRID_PANE_MENU);
        stackPaneLogo = GUITest.getNode(FXIds.STACK_PANE_LOGO);
        imgViewColorLogo = GUITest.getNode(FXIds.IMG_VIEW_COLOR_LOGO);
        imgViewGreyLogo = GUITest.getNode(FXIds.IMG_VIEW_GREY_LOGO);
        btnShipment = GUITest.getNode(FXIds.BTN_SHIPMENT);
        imgViewShipment = GUITest.getNode(FXIds.IMG_VIEW_SHIPMENT);
        btnReference = GUITest.getNode(FXIds.BTN_REFERENCE);
        imgViewReference = GUITest.getNode(FXIds.IMG_VIEW_REFERENCE);

        mainController = spy(controller.getMain());
        controller.setMain(mainController);

        CURRENT_COLOR = controller.getCurrentColor();
        IDLE_COLOR = controller.getIdleColor();
        HOVER_COLOR = controller.getHoverColor();

        doNothing().when(mainController).showHomePane();
        doNothing().when(mainController).showReferencePane();
    }

    @Test
    void testInit() {
        FxAssert.verifyThat(gridPaneMenu, NodeMatchers.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() ->controller.hide());

        FxAssert.verifyThat(gridPaneMenu, NodeMatchers.isInvisible());

        interact(() ->controller.show());

        FxAssert.verifyThat(gridPaneMenu, NodeMatchers.isVisible());
    }

    @Test
    void testMenuPaneHandler_btnShipment() {
        String imgUrl = ImageHandler.getShipmentImage(CURRENT_COLOR).getUrl();

        controller.unsetCurrentPage();

        clickOn(btnShipment);

        assertEquals(MenuPaneController.Page.SHIPMENT, controller.getCurrentPage());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnShipment.getTextFill());
        assertEquals(imgUrl, imgViewShipment.getImage().getUrl());
    }

    @Test
    void testMenuPaneHandler_btnReference() {
        String imgUrl = ImageHandler.getReferenceImage(CURRENT_COLOR).getUrl();

        controller.unsetCurrentPage();
        clickOn(btnReference);

        assertEquals(MenuPaneController.Page.REFERENCE, controller.getCurrentPage());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnReference.getTextFill());
        assertEquals(imgUrl, imgViewReference.getImage().getUrl());
    }
}
