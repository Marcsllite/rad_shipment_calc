package rad.shipment.calculator.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import rad.shipment.calculator.panes.StartWithSummaryPane;
import rad.shipment.calculator.panes.SummaryPane;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class SummaryPaneControllerTest extends ApplicationTest {

    private static Logger LOGR = Logger.getLogger(SummaryPaneController.class.getName()); // matches the logger in the affected class
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;
    private SummaryPaneController summaryPaneController = new SummaryPaneController();
    private SummaryPane summaryPane;
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
     * Override from parent in order to load the Summary Pane only
     * We only want to test the Summary Pane (not the entire app)
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

        FxToolkit.setupApplication(StartWithSummaryPane.class);
        summaryPane = new SummaryPane(this);
    }

    @After
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();  // getting rid of current stage
        release(new KeyCode[]{});  // release key events (resource management)
        release(new MouseButton[]{});  // release mouse events (resource management)
    }

    @Test
    public void summaryPaneHandler_NullActionEvent(){
        // TODO: finish summaryPaneHandler_NullActionEvent test function
    }

    @Test
    public void summaryPaneHandler_SaveActionEvent(){
        // TODO: finish summaryPaneHandler_SaveActionEvent test function
    }

    @Test
    public void saveBtnHandler() {
        // TODO: finish saveBtnHandler test function
    }

    @Test
    public void getTxtAreaSummary() {
        // TODO: finish getTxtAreaSummary test function
    }
}