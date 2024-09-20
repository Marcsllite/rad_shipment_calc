package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.ImageHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    ImageHandler.AppColor CURRENT_COLOR;
    ImageHandler.AppColor IDLE_COLOR;
    ImageHandler.AppColor HOVER_COLOR;

    public MenuPaneControllerGUITest() {
        super(FXMLView.MENU, BaseController.Page.MENU);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (MenuPaneController) getController();
        gridPaneMenu = controller.menuPane;
        stackPaneLogo = controller.stackPaneLogo;
        imgViewColorLogo = controller.imgViewColorLogo;
        imgViewGreyLogo = controller.imgViewGreyLogo;
        btnShipment = controller.btnShipment;
        imgViewShipment = controller.imgViewShipment;
        btnReference = controller.btnReference;
        imgViewReference = controller.imgViewReference;

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
        interact(() -> controller.initialize());
        assertTrue(controller.isInit());
        assertTrue(gridPaneMenu.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() ->controller.hide());

        assertFalse(gridPaneMenu.isVisible());

        interact(() ->controller.show());

        assertTrue(gridPaneMenu.isVisible());
    }

    @Test
    void testSetButtonColor_ProperBtn_NullColor() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.setButtonColor(btnReference, null)
        );
        assertTrue(ex.getMessage().contains("The color cannot be null"));
    }

    @Disabled("failing on appveyor mac")
    void testMouseHover() {
        clickOn(btnShipment);
        assertEquals(MenuPaneController.Page.SHIPMENT, controller.getCurrentPage());

        moveTo(stackPaneLogo);
        assertFalse(imgViewColorLogo.isVisible());
        assertTrue(imgViewGreyLogo.isVisible());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnShipment.getTextFill());
        assertEquals(Paint.valueOf(IDLE_COLOR.getVal()), btnReference.getTextFill());

        moveTo(btnReference);
        assertTrue(imgViewColorLogo.isVisible());
        assertFalse(imgViewGreyLogo.isVisible());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnShipment.getTextFill());
        assertEquals(Paint.valueOf(HOVER_COLOR.getVal()), btnReference.getTextFill());

        clickOn(btnReference);
        assertEquals(MenuPaneController.Page.REFERENCE, controller.getCurrentPage());
        assertEquals(Paint.valueOf(IDLE_COLOR.getVal()), btnShipment.getTextFill());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnReference.getTextFill());

        moveTo(btnShipment);
        assertTrue(imgViewColorLogo.isVisible());
        assertFalse(imgViewGreyLogo.isVisible());
        assertEquals(Paint.valueOf(HOVER_COLOR.getVal()), btnShipment.getTextFill());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnReference.getTextFill());
    }

    private void assertButtonClick(Button btn, MenuPaneController.Page page, ImageView view, String imgUrl) {
        clickOn(btn);
        moveTo(stackPaneLogo);
        assertEquals(page, controller.getCurrentPage());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btn.getTextFill());
        assertEquals(imgUrl, view.getImage().getUrl());
    }

    @Disabled("failing on appveyor mac")
    void testMenuPaneHandler_btnShipment() {
        String imgUrl = ImageHandler.getShipmentImage(CURRENT_COLOR).getUrl();

        controller.unsetCurrentPage();

        assertButtonClick(btnShipment, MenuPaneController.Page.SHIPMENT, imgViewShipment, imgUrl);
        assertButtonClick(btnShipment, MenuPaneController.Page.SHIPMENT, imgViewShipment, imgUrl);
    }

    @Disabled("failing on appveyor mac")
    void testMenuPaneHandler_btnReference() {
        String imgUrl = ImageHandler.getReferenceImage(CURRENT_COLOR).getUrl();

        controller.unsetCurrentPage();

        assertButtonClick(btnReference, MenuPaneController.Page.REFERENCE, imgViewReference, imgUrl);
        assertButtonClick(btnReference, MenuPaneController.Page.REFERENCE, imgViewReference, imgUrl);
    }
}
