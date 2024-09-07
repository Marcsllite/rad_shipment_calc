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
import java.security.InvalidParameterException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
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
        interact(() -> controller.initialize());
        assertTrue(controller.isInit());
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
    void testSetButtonColor_ProperBtn_NullColor() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class,
            () -> controller.setButtonColor(btnReference, null)
        );
        assertTrue(ex.getMessage().contains("The color cannot be null"));
    }

    @Test
    void testMouseHover() {
        moveTo(stackPaneLogo);
        FxAssert.verifyThat(imgViewColorLogo, NodeMatchers.isInvisible());
        FxAssert.verifyThat(imgViewGreyLogo, NodeMatchers.isVisible());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnShipment.getTextFill());
        assertEquals(Paint.valueOf(IDLE_COLOR.getVal()), btnReference.getTextFill());

        moveTo(btnReference);
        FxAssert.verifyThat(imgViewColorLogo, NodeMatchers.isVisible());
        FxAssert.verifyThat(imgViewGreyLogo, NodeMatchers.isInvisible());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnShipment.getTextFill());
        assertEquals(Paint.valueOf(HOVER_COLOR.getVal()), btnReference.getTextFill());

        clickOn(btnReference);
        assertEquals(Paint.valueOf(IDLE_COLOR.getVal()), btnShipment.getTextFill());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnReference.getTextFill());

        moveTo(btnShipment);
        FxAssert.verifyThat(imgViewColorLogo, NodeMatchers.isVisible());
        FxAssert.verifyThat(imgViewGreyLogo, NodeMatchers.isInvisible());
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

    @Test
    void testMenuPaneHandler_btnShipment() {
        String imgUrl = ImageHandler.getShipmentImage(CURRENT_COLOR).getUrl();

        controller.unsetCurrentPage();

        assertButtonClick(btnShipment, MenuPaneController.Page.SHIPMENT, imgViewShipment, imgUrl);
        assertButtonClick(btnShipment, MenuPaneController.Page.SHIPMENT, imgViewShipment, imgUrl);
    }

    @Test
    void testMenuPaneHandler_btnReference() {
        String imgUrl = ImageHandler.getReferenceImage(CURRENT_COLOR).getUrl();

        controller.unsetCurrentPage();

        assertButtonClick(btnReference, MenuPaneController.Page.REFERENCE, imgViewReference, imgUrl);
        assertButtonClick(btnReference, MenuPaneController.Page.REFERENCE, imgViewReference, imgUrl);
    }
}
