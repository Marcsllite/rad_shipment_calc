package com.marcsllite.controller;

import com.marcsllite.GUITest;
import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.handler.ImageHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
public class MenuPaneControllerGUITest extends GUITest {
    MainController mainController;
    @InjectMocks
    MenuPaneController controller;
    ImageHandler.Colors CURRENT_COLOR;
    ImageHandler.Colors IDLE_COLOR;
    ImageHandler.Colors HOVER_COLOR;
    @Start
    public void start(Stage stage) {
        super.start(stage, FXMLView.MENU);
        controller = (MenuPaneController) getController(MenuPaneController.class);

        mainController = mock(MainController.class);

        CURRENT_COLOR = controller.getCURRENT_COLOR();
        IDLE_COLOR = controller.getIDLE_COLOR();
        HOVER_COLOR = controller.getHOVER_COLOR();

        doNothing().when(mainController).showHomePane();
        doNothing().when(mainController).showReferencePane();
    }

    @Test
    public void testInit() {
        FxAssert.verifyThat(FXIds.GRIDPANE_MENU, NodeMatchers.isVisible());
    }

    @Test
    public void testHideShow() {
        interact(() -> {
            GridPane menuPane = getNode(FXIds.GRIDPANE_MENU);

            controller.hide();

            FxAssert.verifyThat(menuPane, NodeMatchers.isInvisible());

            controller.show();

            FxAssert.verifyThat(menuPane, NodeMatchers.isVisible());
        });
    }

    @Test
    public void testMenuPaneHandler_imgViewLogo() {
        clickOn(FXIds.IMGVIEW_COLOR_LOGO);
    }

    @Test
    public void testMenuPaneHandler_btnShipment() {
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
    public void testMenuPaneHandler_btnReference() {
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
