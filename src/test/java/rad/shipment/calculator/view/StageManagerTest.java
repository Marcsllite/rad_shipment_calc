package rad.shipment.calculator.view;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import rad.shipment.calculator.gui.Main;
import rad.shipment.calculator.gui.MainController;
import rad.shipment.calculator.gui.MenuPaneController;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class StageManagerTest extends ApplicationTest {

    private static Logger LOGR = Logger.getLogger(Main.class.getName()); // matches the logger in the affected class
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();  // expected exception
    Exception properException = new Exception(Main.getString("properException"));
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;

    public String getTestCapturedLog(@NotNull OutputStream logCapturingStream, @NotNull StreamHandler customLogHandler) {
        customLogHandler.flush();
        return logCapturingStream.toString();
    }

    @Before
    public void beforeEachTest() throws Exception {
        /*
          @Author Tommy Tynjä http://blog.diabol.se/?p=474
         *
         * Creating a custom log handler attached to desired logger
         * Then creating a SteamHandler attached to logger and an OutputStream.
         * Using the OutputStream to get the log contents
         */
        logCapturingStream = new ByteArrayOutputStream();
        Handler[] handlers = LOGR.getParent().getHandlers();
        customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());
        LOGR.addHandler(customLogHandler);

        ApplicationTest.launch(Main.class);  // launching main class
    }

    @After
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();  // getting rid of current stage
        release(new KeyCode[]{});  // release key events (resource management)
        release(new MouseButton[]{});  // release mouse events (resource management)
    }

    @Test
    public void getPrimaryStage(){
        // TODO: finish getPrimaryStage test function
    }

    @Test
    public void switchScene_NullView() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage("FXML View is null");
                stageManager.switchScene(null);
            }
        );
    }

    @Test
    public void switchScene_MainView() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                stageManager.switchScene(FXMLView.MAIN);
            }
        );
    }

    @Test
    public void switchScene_MenuView() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                stageManager.switchScene(FXMLView.MENU);
            }
        );
    }

    @Test
    public void getController_MainFxml() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                stageManager.switchScene(FXMLView.MAIN);

                Assert.assertEquals(MainController.class, stageManager.getController().getClass());
            }
        );
    }

    @Test
    public void getController_MenuFxml() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                stageManager.switchScene(FXMLView.MENU);
                Assert.assertEquals(MenuPaneController.class, stageManager.getController().getClass());
            }
        );
    }

    @Test
    public void show_NullView() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage("FXML View is null");
                stageManager.show(null);
            }
        );
    }

    @Test
    public void show_MainView() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                stageManager.show(FXMLView.MAIN);
            }
        );
    }

    @Test
    public void show_MenuView() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                stageManager.show(FXMLView.MENU);
            }
        );
    }

    @Test
    public void loadViewNodeHierarchy_NullView() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage("FXML View is null");
                stageManager.loadViewNodeHierarchy(null);
            }
        );
    }

    @Test
    public void loadViewNodeHierarchy_MainView() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                stageManager.loadViewNodeHierarchy(FXMLView.MAIN);
            }
        );
    }

    @Test
    public void loadViewNodeHierarchy_MenuView() {
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());

                stageManager.loadViewNodeHierarchy(FXMLView.MENU);
            }
        );
    }

    @Test
    public void logAndThrowException_NullMsg_NullException(){
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());
                String givenMsg = null;
                String expectedMsg = Main.getString("defaultMessage");

                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage(expectedMsg);
                stageManager.logAndThrowException(givenMsg, null);
            }
        );
    }

    @Test
    public void logAndThrowException_EmptyStringMsg_NullException(){
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());
                String givenMsg = "";
                String expectedMsg = Main.getString("defaultMessage");

                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage(expectedMsg);
                stageManager.logAndThrowException(givenMsg, null);

                Assert.assertTrue(
                        getTestCapturedLog(logCapturingStream, customLogHandler).
                                contains("SEVERE: " + expectedMsg)
                );
            }
        );
    }

    @Test
    public void logAndThrowException_ProperMsg_NullException(){
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());
                String givenMsg = Main.getString("properMessage");

                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage(givenMsg);
                stageManager.logAndThrowException(givenMsg, null);

                Assert.assertTrue(
                        getTestCapturedLog(logCapturingStream, customLogHandler).
                                contains("SEVERE: " + givenMsg)
                );
            }
        );
    }

    @Test
    public void logAndThrowException_NullMsg_ProperException(){
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());
                String givenMsg = null;
                String expectedMsg = Main.getString("defaultMessage");

                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage(expectedMsg);
                stageManager.logAndThrowException(givenMsg, properException);

                Assert.assertTrue(
                        getTestCapturedLog(logCapturingStream, customLogHandler).
                                contains("SEVERE: " + expectedMsg)
                );

                Assert.assertTrue(
                        getTestCapturedLog(logCapturingStream, customLogHandler).
                                contains(properException.toString())
                );
            }
        );
    }

    @Test
    public void logAndThrowException_EmptyStringMsg_ProperException(){
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());
                String givenMsg = "";
                String expectedMsg = Main.getString("defaultMessage");

                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage(expectedMsg);
                stageManager.logAndThrowException(givenMsg, properException);

                Assert.assertTrue(
                        getTestCapturedLog(logCapturingStream, customLogHandler).
                                contains("SEVERE: " + expectedMsg)
                );

                Assert.assertTrue(
                        getTestCapturedLog(logCapturingStream, customLogHandler).
                                contains(properException.toString())
                );
            }
        );
    }

    @Test
    public void logAndThrowException_ProperMsg_ProperException(){
        Platform.runLater(
            () -> {
                StageManager stageManager = new StageManager(new Stage());
                String givenMsg = Main.getString("properMessage");

                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage(givenMsg);
                stageManager.logAndThrowException(givenMsg, properException);

                Assert.assertTrue(
                        getTestCapturedLog(logCapturingStream, customLogHandler).
                                contains("SEVERE: " + givenMsg)
                );

                Assert.assertTrue(
                        getTestCapturedLog(logCapturingStream, customLogHandler).
                                contains(properException.toString())
                );
            }
        );
    }
}