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
import rad.shipment.calculator.panes.HomePane;
import rad.shipment.calculator.panes.StartWithHomePane;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class HomePaneControllerTest extends ApplicationTest {

    private static Logger LOGR = Logger.getLogger(HomePaneController.class.getName()); // matches the logger in the affected class
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;
    private HomePaneController homePaneController = new HomePaneController();
    private HomePane homePane;
    private Stage primaryStage;

    public Stage getPrimaryStage() { return primaryStage; }

    @Override
    public void start (@NotNull Stage stage) {
        // loading and showing the main parent node
        primaryStage = stage;
        stage.show();
    }

    /**
     * Override from parent in order to load the Home Pane only
     * We only want to test the Home Pane (not the entire app)
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

        FxToolkit.setupApplication(StartWithHomePane.class);
        homePane = new HomePane(this);
    }

    @After
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();  // getting rid of current stage
        release(new KeyCode[]{});  // release key events (resource management)
        release(new MouseButton[]{});  // release mouse events (resource management)
    }

//    @Test
//    public void initialize(){
//        // making sure buttons have an OnKeyPressed event handler
//        Assert.assertNotNull(homePane.getBtnAdd().getOnKeyPressed());
//        Assert.assertNotNull(homePane.getBtnEdit().getOnKeyPressed());
//        Assert.assertNotNull(homePane.getBtnRemove().getOnKeyPressed());
//        Assert.assertNotNull(homePane.getBtnCalculate().getOnKeyPressed());
//
//        // making sure edit button's disable property is bound
//        Assert.assertTrue(homePane.getBtnEdit().disableProperty().isBound());
//
//        // making sure remove button's disable property is bound
//        Assert.assertTrue(homePane.getBtnRemove().disableProperty().isBound());
//    }

    @Test
    public void injectMainController() {
        MainController testMainController = new MainController();

        homePaneController.injectMainController(testMainController);

        Assert.assertEquals(testMainController, homePaneController.getMainController());
    }

//    @Test
//    public void initTable(){
//        homePaneController.initTable();
//        TableView<Isotope> tableView =  homePane.getTableView();
//
//        // Making sure the selection model is in multiple mode
//        Assert.assertEquals(SelectionMode.MULTIPLE,tableView.getSelectionModel().getSelectionMode());
//
//        // Making sure that the tableView has a OnKeyPressed event handler
//        Assert.assertNotNull(tableView.getOnKeyPressed());
//    }

    @Test
    public void homePaneHandler_NullActionEvent(){
        // TODO: finish homePaneHandler_NullActionEvent test function
    }

    @Test
    public void homePaneHandler_AddActionEvent(){
        // TODO: finish homePaneHandler_AddActionEvent test function
    }

    @Test
    public void homePaneHandler_EditActionEvent(){
        // TODO: finish homePaneHandler_EditActionEvent test function
    }

    @Test
    public void homePaneHandler_RemoveActionEvent(){
        // TODO: finish homePaneHandler_RemoveActionEvent test function
    }

    @Test
    public void homePaneHandler_CalculateActionEvent(){
        // TODO: finish homePaneHandler_CalculateActionEvent test function
    }

    @Test
    public void addBtnHandler(){
        // TODO: finish addBtnHandler test function
    }

    @Test
    public void editBtnHandler(){
        // TODO: finish editBtnHandler test function
    }

    @Test
    public void removeBtnHandler(){
        // TODO: finish removeBtnHandler test function
    }

    @Test
    public void calculateBtnHandler(){
        // TODO: finish calculateBtnHandler test function
    }

    @Test
    public void setMainController_Null_Controller(){
        // TODO: finish setMainController_Null_Controller test function
    }

    @Test
    public void setMainController_Proper_Controller(){
        // TODO: finish setMainController_Proper_Controller test function
    }

    @Test
    public void getMainController_Null_Controller(){
        // TODO: finish getMainController_Null_Controller test function
    }

    @Test
    public void getMainController_Proper_Controller(){
        // TODO: finish getMainController_Proper_Controller test function
    }
}