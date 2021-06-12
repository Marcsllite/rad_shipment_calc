package rad.shipment.calculator.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import rad.shipment.calculator.panes.MenuPane;
import rad.shipment.calculator.panes.StartWithMenuPane;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class MenuPaneControllerTest extends ApplicationTest {

    private static Logger LOGR = Logger.getLogger(MenuPaneController.class.getName()); // matches the logger in the affected class
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;
    private MenuPaneController menuPaneController = new MenuPaneController();
    private String CURRENT_COLOR = menuPaneController.getCURRENT_COLOR();
    private String IDLE_COLOR = menuPaneController.getIDLE_COLOR();
    private String HOVER_COLOR = menuPaneController.getHOVER_COLOR();
    private MenuPane menuPane;
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start (@NotNull Stage stage) {
        // loading and showing the main parent node
        primaryStage = stage;
        stage.show();
    }

    /**
     * Override from parent in order to load the Menu Pane only
     * We only want to test the Menu Pane (not the entire app)
     */
    @Before
    public void beforeEachTest() throws Exception {
        /*
         * @Author Tommy Tynjä http://blog.diabol.se/?p=474
         *
         * Creating a custom log handler attached to desired logger
         * Then creating a SteamHandler attached to logger and an OutputStream.
         * Using the OutputStream to get the log contents
         */
        logCapturingStream = new ByteArrayOutputStream();
        Handler[] handlers = LOGR.getParent().getHandlers();
        customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());
        LOGR.addHandler(customLogHandler);

        FxToolkit.setupApplication(StartWithMenuPane.class);
        menuPane = new MenuPane(this);
    }

    @After
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();  // getting rid of current stage
        release(new KeyCode[]{});  // release key events (resource management)
        release(new MouseButton[]{});  // release mouse events (resource management)
    }

//    @Test
//    public void initialize(){
//        // Asserting that the menu pane was initialized correctly
//        Assert.assertTrue(menuPane.getImgViewColorLogo().isVisible());
//
//        // TODO: figure out how to check Reference Image color
//        Assert.assertEquals(Color.web(CURRENT_COLOR), menuPane.getBtnShipment().getTextFill());
//        Assert.assertEquals(ImageHandler.getShipmentImage(CURRENT_COLOR), menuPane.getImgViewShipment().getImage());
//
//        Assert.assertEquals(Color.web(IDLE_COLOR), menuPane.getBtnReference().getTextFill());
//        Assert.assertEquals(ImageHandler.getReferenceImage(IDLE_COLOR), menuPane.getImgViewReference().getImage());
//    }

    @Test
    public void injectMainController() {
        MainController testMainController = new MainController();

        menuPaneController.injectMainController(testMainController);

        Assert.assertEquals(testMainController, menuPaneController.getMainController());
    }

    @Test
    public void menuPaneHandler_NullActionEvent(){
        // TODO: finish menuPaneHandler_NullActionEvent test function
    }

    @Test
    public void menuPaneHandler_ShipmentActionEvent(){
        // TODO: finish menuPaneHandler_ShipmentActionEvent test function
    }

    @Test
    public void menuPaneHandler_ReferenceActionEvent(){
        // TODO: finish menuPaneHandler_ReferenceActionEvent test function
    }

    @Test
    public void shipmentBtnHandler(){
        // TODO: finish shipmentBtnHandler test function
    }

    @Test
    public void referenceBtnHandler(){
        // TODO: finish referenceBtnHandler test function
    }

    @Test
    public void setCurrentButton_NullParam(){
        // TODO: finish setCurrentButton_NullParam test function
    }

    @Test
    public void setCurrentButton_ProperParam(){
        // TODO: finish setCurrentButton_ProperParam test function
    }

    @Test
    public void unsetCurrentButton_NoCurrentBtn() {
        // TODO: finish unsetCurrentButton_NoCurrentBtn test function
    }

    @Test
    public void unsetCurrentButton_OneCurrentBtn() {
        // TODO: finish unsetCurrentButton_OneCurrentBtn test function
    }

    @Test
    public void setButtonColor_NullBtn_NullColor() {
        // TODO: finish setButtonColor_NullBtn_NullColor test function
    }

    @Test
    public void setButtonColor_NullBtn_ProperColor() {
        // TODO: finish setButtonColor_NullBtn_ProperColor test function
    }

    @Test
    public void setButtonColor_ProperBtn_NullColor() {
        // TODO: finish setButtonColor_ProperBtn_NullColor test function
    }

    @Test
    public void setButtonColor_ProperBtn_ProperColor() {
        // TODO: finish setButtonColor_ProperBtn_ProperColor test function
    }

    @Test
    public void mouseLogoEnter(){
        // TODO: finish mouseLogoEnter test function
    }

    @Test
    public void mouseLogoExit(){
        // TODO: finish mouseLogoExit test function
    }

//    @Test
//    public void mouseShipmentEnter(){
//        // FIXME: fix mouseShipmentEnter test function
//        Assert.assertTrue(menuPane.checkHover(menuPane.getBtnShipment()));
//        Assert.assertTrue(menuPane.checkHover(menuPane.getImgViewShipment()));
//    }

//    @Test
//    public void mouseShipmentExit_CurrentBtn(){
//        Assert.assertEquals(Color.web(CURRENT_COLOR), menuPane.getBtnShipment().getTextFill());
//        // TODO: figure out how to check Shipment Image color
//        Assert.assertEquals(ImageHandler.getShipmentImage(CURRENT_COLOR), menuPane.getImgViewShipment().getImage());
//    }

//    @Test
//    public void mouseShipmentExit_IdleBtn(){
//        // FIXME: fix color assertion
//        Assert.assertEquals(Color.web(IDLE_COLOR), menuPane.getBtnShipment().getTextFill());
//        // TODO: figure out how to check Shipment Image color
//        Assert.assertEquals(ImageHandler.getShipmentImage(IDLE_COLOR), menuPane.getImgViewShipment().getImage());
//    }

//    @Test
//    public void mouseReferenceEnter(){
//        // FIXME: fix mouseReferenceEnter test function
//        Assert.assertTrue(menuPane.checkHover(menuPane.getBtnReference()));
//        Assert.assertTrue(menuPane.checkHover(menuPane.getImgViewReference()));
//    }

//    @Test
//    public void mouseReferenceExit_CurrentBtn(){
//        // FIXME: fix color assertion
//        Assert.assertEquals(Color.web(CURRENT_COLOR), menuPane.getBtnReference().getTextFill());
//        // TODO: figure out how to check Reference Image color
//        Assert.assertEquals(ImageHandler.getReferenceImage(CURRENT_COLOR), menuPane.getImgViewReference().getImage());
//    }

//    @Test
//    public void mouseReferenceExit_IdleBtn(){
//        Assert.assertEquals(Color.web(IDLE_COLOR), menuPane.getBtnReference().getTextFill());
//        // TODO: figure out how to check Reference Image color
//        Assert.assertEquals(ImageHandler.getReferenceImage(IDLE_COLOR), menuPane.getImgViewReference().getImage());
//    }

    @Test
    public void getAndSetMainController() {
        MainController testMainController = new MainController();

        Assert.assertNull(menuPaneController.getMainController());

        menuPaneController.setMainController(testMainController);

        Assert.assertEquals(testMainController, menuPaneController.getMainController());
    }

    @Test
    public void getCURRENT_COLOR() {
        // TODO: finish getCURRENT_COLOR test function
    }

    @Test
    public void getIDLE_COLOR() {
        // TODO: finish getIDLE_COLOR test function
    }

    @Test
    public void getHOVER_COLOR() {
        // TODO: finish getHOVER_COLOR test function
    }
}