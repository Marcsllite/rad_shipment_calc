package com.marcsllite;

import com.marcsllite.util.FXIds;
import com.marcsllite.util.FXMLView;
import com.marcsllite.util.StageManager;
import com.marcsllite.util.Util;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.security.InvalidParameterException;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuPaneControllerTest {
    StageManager stageManager;
    MenuPaneController c;

    @Nested
    @DisplayName("Menu Pane Controller UI Tests")
    @ExtendWith(MockitoExtension.class)
    @ExtendWith(ApplicationExtension.class)
    class MenuPaneControllerTestUI {
        GridPane menuPane;
        ImageView imgViewColor;
        ImageView imgViewGrey;
        Button btnShip;
        ImageView imgViewShip;
        Button btnRef;
        ImageView imgViewRef;

        @Start
        public void start(Stage stage) {
            stageManager = new StageManager(stage);
            stageManager.show(FXMLView.MENU);
        }

        @BeforeEach
        public void setUp(FxRobot robot) {
            c = spy(MenuPaneController.class);
            
            menuPane = robot.lookup(FXIds.MENU_PANE).queryAs(GridPane.class);
            imgViewColor = robot.lookup(FXIds.IMGVIEW_COLOR_LOGO).queryAs(ImageView.class);
            imgViewGrey = robot.lookup(FXIds.IMGVIEW_GREY_LOGO).queryAs(ImageView.class);
            btnShip = robot.lookup(FXIds.BTN_SHIPMENT).queryButton();
            imgViewShip = robot.lookup(FXIds.IMGVIEW_SHIPMENT).queryAs(ImageView.class);
            btnRef = robot.lookup(FXIds.BTN_REFERENCE).queryButton();
            imgViewRef = robot.lookup(FXIds.IMGVIEW_REFERENCE).queryAs(ImageView.class);
            
            c.menuPane = menuPane;
            c.imgViewColorLogo = imgViewColor;
            c.imgViewGreyLogo = imgViewGrey;
            c.btnShipment = btnShip;
            c.imgViewShipment = imgViewShip;
            c.btnReference = btnRef;
            c.imgViewReference = imgViewRef;

            MainController.getInstance().registerController(c);
        }

        @AfterEach
        public void tearDown(FxRobot robot) throws TimeoutException {
            FxToolkit.hideStage();
            robot.release(new KeyCode[0]);
            robot.release(new MouseButton[0]);

            c = null;
            stageManager = null;
            menuPane = null;
            imgViewColor = null;
            imgViewGrey = null;
            btnShip = null;
            imgViewShip = null;
            btnRef = null;
            imgViewRef = null;
        }

         @Test
         void testMenuPaneHandler_NullEventSource() {
             ActionEvent event = mock(ActionEvent.class);

             when(event.getSource()).thenReturn(null);

             c.menuPaneHandler(event);

             verify(c, times(0)).shipmentBtnHandler();
             verify(c, times(0)).referenceBtnHandler();
         }

         @Test
         void testMenuPaneHandler_ShipmentEvent() {
             ActionEvent event = mock(ActionEvent.class);
            
             when(event.getSource()).thenReturn(btnShip);
             doNothing().when(c).shipmentBtnHandler();
            
             c.menuPaneHandler(event);

             verify(c, times(1)).shipmentBtnHandler();
         }

         @Test
         void testMenuPaneHandler_ReferenceEvent() {
             ActionEvent event = mock(ActionEvent.class);

             when(event.getSource()).thenReturn(btnRef);
             doNothing().when(c).referenceBtnHandler();
            
             c.menuPaneHandler(event);
             verify(c, times(1)).referenceBtnHandler();
         }

         @Test
         void testMouseShipmentEnter(FxRobot robot) {
             robot.moveTo(FXIds.BTN_SHIPMENT);

             Paint exp = Color.web(c.getHoverColor());

             assertEquals(exp, btnShip.getTextFill());
         }

         @Test
         void testMouseShipmentExit_Current() {
             when(c.getCurrentBtn()).thenReturn(0);

             c.mouseShipmentExit();

             Paint exp = Color.web(c.getCurrentColor());

             assertEquals(exp, btnShip.getTextFill());
         }

         @Test
         void testMouseShipmentExit_NotCurrent() {
             when(c.getCurrentBtn()).thenReturn(1);

             c.mouseShipmentExit();

             Paint exp = Color.web(c.getIdleColor());

             assertEquals(exp, btnShip.getTextFill());
         }

         @Test
         void testMouseReferenceEnter(FxRobot robot) {
             robot.moveTo(FXIds.BTN_REFERENCE);

             Paint exp = Color.web(c.getHoverColor());

             assertEquals(exp, btnRef.getTextFill());
         }

         @Test
         void testMouseReferenceExit_Current() {
             when(c.getCurrentBtn()).thenReturn(1);

             c.mouseReferenceExit();

             Paint exp = Color.web(c.getCurrentColor());

             assertEquals(exp, btnRef.getTextFill());
         }

         @Test
         void testMouseReferenceExit_NotCurrent(FxRobot robot) {
             when(c.getCurrentBtn()).thenReturn(0);

             c.mouseReferenceExit();

             Paint exp = Color.web(c.getIdleColor());

             assertEquals(exp, btnRef.getTextFill());
         }

         @Test
         void testSetCurrentButton_Ship() {
             c.setCurrentButton(btnShip);

             String curColor = c.getIdleColor();

             assumeFalse(curColor == null);
             assumeFalse(curColor.isBlank());

             verify(c, times(1)).setButtonColor(btnShip, curColor);
             assertEquals(0, c.getCurrentBtn());
         }

         @Test
         void testSetCurrentButton_Ref() {
             c.setCurrentButton(btnRef);

             String curColor = c.getIdleColor();

             assumeFalse(curColor == null);
             assumeFalse(curColor.isBlank());

             verify(c, times(1)).setButtonColor(btnRef, curColor);
             assertEquals(1, c.getCurrentBtn());
         }

         @Test
         void testShipmentBtnHandler_NotCurrent() {
             when(c.getCurrentBtn()).thenReturn(1);

             c.shipmentBtnHandler();

             verify(c, times(1)).setCurrentButton(btnShip);
         }

         @Test
         void testReferenceBtnHandler_NotCurrent() {
             when(c.getCurrentBtn()).thenReturn(0);

             c.referenceBtnHandler();

             verify(c, times(1)).setCurrentButton(btnRef);
         }

         @Test
         void testUnsetCurrentButton() {
             c.unsetCurrentButton();

             String curColor = c.getIdleColor();

             assumeFalse(curColor == null);
             assumeFalse(curColor.isBlank());

             verify(c, times(1)).setButtonColor(btnShip, curColor);
             verify(c, times(1)).setButtonColor(btnRef, curColor);
             assertEquals(-2, c.getCurrentBtn());
         }

         @Test
         void testSetButtonColor_BtnShip_NullColor() {
             InvalidParameterException ex = assertThrows(
                 InvalidParameterException.class,
                 () -> c.setButtonColor(btnShip, null)
             );
             assertTrue(ex.getMessage().contains("The color cannot be null or blank"));
         }

         @Test
         void testSetButtonColor_BtnShip_ProperColor() {
             String color = c.getCurrentColor();

             c.setButtonColor(btnShip, color);

             Paint expPaint = Color.web(color);

             assertEquals(expPaint, btnShip.getTextFill());
         }

         @Test
         void testSetButtonColor_BtnRef_ProperColor() {
             String color = c.getCurrentColor();

             c.setButtonColor(btnRef, color);

             Paint expPaint = Color.web(color);

             assertEquals(expPaint, btnRef.getTextFill());
         }

         @Test
         void testMouseLogoEnter(FxRobot robot) {
             c.imgViewColorLogo.setVisible(true);
             c.imgViewGreyLogo.setVisible(false);
            
             robot.moveTo(imgViewColor);

             assertFalse(imgViewColor.isVisible());
             assertTrue(imgViewGrey.isVisible());
         }

         @Test
         void testMouseLogoExit() {
             c.imgViewColorLogo.setVisible(false);
             c.imgViewGreyLogo.setVisible(true);
            
             c.mouseLogoExit();

             assertTrue(imgViewColor.isVisible());
             assertFalse(imgViewGrey.isVisible());
         }

         @Test
         void testHide(FxRobot robot) {
             GridPane pane = robot.lookup(FXIds.MENU_PANE).queryAs(GridPane.class);
             c.menuPane = pane;

             assertTrue(pane.isVisible());

             c.hide();

             assertFalse(pane.isVisible());
         }

         @Test
         void testShow(FxRobot robot) {
             GridPane pane = robot.lookup(FXIds.MENU_PANE).queryAs(GridPane.class);
             c.menuPane = pane;
             c.menuPane.setVisible(false);

             c.show();
             assertTrue(pane.isVisible());
         }
    }

    @BeforeEach
    public void setUp() {
        c = new MenuPaneController();
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void testSetButtonColor_NullBtn_InvalidColor(String color) {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> c.setButtonColor(null, color)
        );
        assertTrue(ex.getMessage().contains("The menu button cannot be null"));
        assertTrue(ex.getMessage().contains("The color cannot be null or blank"));
    }

    @Test
    void testSetButtonColor_NullBtn_ProperColor() {
        String color = c.getCurrentColor();
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> c.setButtonColor(null, color)
        );
        assertTrue(ex.getMessage().contains("The menu button cannot be null"));
    }

    @Test
    void testSetCurrentButton_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> c.setCurrentButton(null)
        );
        assertTrue(ex.getMessage().contains("The current button cannot be null"));
    }

    @Test
    void testMenuPaneHandler_NullParam() {
        InvalidParameterException ex = assertThrows(
            InvalidParameterException.class, 
            () -> c.menuPaneHandler(null)
        );

        assertTrue(ex.getMessage().contains("action event cannot be null"));
    }

    @Test
    void testGetCurrentColor() {
        String exp = Util.getString("umlBlue");

        assumeFalse(exp.isBlank());

        assertEquals(exp, c.getCurrentColor());
    }

    @Test
    void testGetHoverColor() {
        String exp = Util.getString("defaultWhite");

        assumeFalse(exp.isBlank());

        assertEquals(exp, c.getHoverColor());
    }

    @Test
    void testGetIdleColor() {
        String exp = Util.getString("de faultGrey");

        assumeFalse(exp.isBlank());

        assertEquals(exp, c.getIdleColor());
    }
}
