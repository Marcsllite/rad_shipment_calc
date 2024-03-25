package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.FXIds;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.ImageHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
    ImageHandler.Colors CURRENT_COLOR;
    ImageHandler.Colors IDLE_COLOR;
    ImageHandler.Colors HOVER_COLOR;

    public MenuPaneControllerGUITest() {
        super(FXMLView.MENU);
    }

    @Start
    public void start(Stage stage) throws IOException, TimeoutException {
        super.start(stage);
        controller = (MenuPaneController) getController();

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
        FxAssert.verifyThat(FXIds.GRIDPANE_MENU, NodeMatchers.isVisible());
    }

    @Test
    void testHideShow() {
        interact(() -> {
            GridPane menuPane = getNode(FXIds.GRIDPANE_MENU);

            controller.hide();

            FxAssert.verifyThat(menuPane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(menuPane, NodeMatchers.isVisible());
        });
    }

    @Test
    void testMenuPaneHandler_imgViewLogo() {
        clickOn(FXIds.IMGVIEW_COLOR_LOGO);
    }

    @Test
    void testMenuPaneHandler_btnShipment() {
        Button btnShip = getNode(FXIds.BTN_SHIPMENT);
        ImageView imgViewShip = getNode(FXIds.IMGVIEW_SHIPMENT);
        String imgUrl = ImageHandler.getShipmentImage(CURRENT_COLOR).getUrl();

        controller.unsetCurrentPage();

        clickOn(btnShip);

        assertEquals(MenuPaneController.Page.SHIPMENT, controller.getCurrentPage());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnShip.getTextFill());
        assertEquals(imgUrl, imgViewShip.getImage().getUrl());
    }

    @Test
    void testMenuPaneHandler_btnReference() {
        Button btnRef = getNode(FXIds.BTN_REFERENCE);
        ImageView imgViewRef = getNode(FXIds.IMGVIEW_REFERENCE);
        String imgUrl = ImageHandler.getReferenceImage(CURRENT_COLOR).getUrl();

        controller.unsetCurrentPage();
        clickOn(btnRef);

        assertEquals(MenuPaneController.Page.REFERENCE, controller.getCurrentPage());
        assertEquals(Paint.valueOf(CURRENT_COLOR.getVal()), btnRef.getTextFill());
        assertEquals(imgUrl, imgViewRef.getImage().getUrl());
    }
}
