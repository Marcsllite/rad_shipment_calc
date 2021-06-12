package rad.shipment.calculator.gui;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class ConfirmWindowTest extends ApplicationTest {

    private static Logger LOGR = Logger.getLogger(ConfirmWindow.class.getName()); // matches the logger in the affected class
    @Rule public final ExpectedException expectedException = ExpectedException.none();  // expected exception
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;
    //private Stage primaryStage;

    public String getTestCapturedLog(@NotNull OutputStream logCapturingStream, @NotNull StreamHandler customLogHandler) {
        customLogHandler.flush();
        return logCapturingStream.toString();
    }

    @Before
    public void beforeEachTest() {
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
    }

    @After
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();  // getting rid of current stage
        release(new KeyCode[]{});  // release key events (resource management)
        release(new MouseButton[]{});  // release mouse events (resource management)
    }

    @Test
    public void display_NullTitle_NullMessage() {
        Platform.runLater(
            () -> {
                ConfirmWindow confirmWindow = new ConfirmWindow();

                confirmWindow.display(null, null);

                Assert.assertEquals(Main.getString("defaultConfirmTitle"), ConfirmWindow.title.get());
                Assert.assertEquals(Main.getString("defaultMessage"), ConfirmWindow.message.get());
            }
        );
    }

    @Test
    public void display_NullTitle_EmptyStringMessage() {
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.display(null, "");

                    Assert.assertEquals(Main.getString("defaultConfirmTitle"), ConfirmWindow.title.get());
                    Assert.assertEquals("", ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void display_NullTitle_ProperMessage() {
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.display(null, Main.getString("properMessage"));

                    Assert.assertEquals(Main.getString("defaultConfirmTitle"), ConfirmWindow.title.get());
                    Assert.assertEquals(Main.getString("properMessage"), ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void display_EmptyStringTitle_NullMessage() {
       Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.display("", null);

                    Assert.assertEquals(Main.getString("defaultConfirmTitle"), ConfirmWindow.title.get());
                    Assert.assertEquals(Main.getString("defaultMessage"), ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void display_EmptyStringTitle_EmptyStringMessage() {
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.display("", "");

                    Assert.assertEquals(Main.getString("defaultConfirmTitle"), ConfirmWindow.title.get());
                    Assert.assertEquals("", ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void display_EmptyStringTitle_ProperMessage() {
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.display("", Main.getString("properMessage"));

                    Assert.assertEquals(Main.getString("defaultConfirmTitle"), ConfirmWindow.title.get());
                    Assert.assertEquals(Main.getString("properMessage"), ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void display_ProperTitle_NullMessage() {
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.display(Main.getString("properTitle"), null);

                    Assert.assertEquals(Main.getString("properTitle"), ConfirmWindow.title.get());
                    Assert.assertEquals(Main.getString("defaultMessage"), ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void display_ProperTitle_EmptyStringMessage() {
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.display(Main.getString("properTitle"), "");

                    Assert.assertEquals(Main.getString("properTitle"), ConfirmWindow.title.get());
                    Assert.assertEquals("", ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void display_ProperTitle_ProperMessage() {
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.display(Main.getString("properTitle"), Main.getString("properMessage"));

                    Assert.assertEquals(Main.getString("properTitle"), ConfirmWindow.title.get());
                    Assert.assertEquals(Main.getString("properMessage"), ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void createScene_NullMessage(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.createScene(null);

                    Assert.assertEquals(Main.getString("defaultMessage"), ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void createScene_EmptyStringMessage(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.createScene("");

                    Assert.assertEquals("", ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void createScene_ProperMessage(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.createScene(Main.getString("properMessage"));

                    Assert.assertEquals(Main.getString("properMessage"), ConfirmWindow.message.get());
                }
        );
    }

    @Test
    public void createButton_NullText_TruePosBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    Button button = confirmWindow.createButton(null, true);

                    Assert.assertEquals(Main.getString("defaultBtn"), button.getText());
                    Assert.assertEquals(Main.getString("confirmPositiveBtnID"), button.getId());
                }
        );
    }

    @Test
    public void createButton_NullText_FalsePosBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    Button button = confirmWindow.createButton(null, false);

                    Assert.assertEquals(Main.getString("defaultBtn"), button.getText());
                    Assert.assertEquals(Main.getString("confirmNegativeBtnID"), button.getId());
                }
        );
    }

    @Test
    public void createButton_EmptyStringText_TruePosBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    Button button = confirmWindow.createButton("", true);

                    Assert.assertEquals(Main.getString("defaultBtn"), button.getText());
                    Assert.assertEquals(Main.getString("confirmPositiveBtnID"), button.getId());
                }
        );
    }

    @Test
    public void createButton_EmptyStringText_FalsePosBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    Button button = confirmWindow.createButton("", false);

                    Assert.assertEquals(Main.getString("defaultBtn"), button.getText());
                    Assert.assertEquals(Main.getString("confirmNegativeBtnID"), button.getId());
                }
        );
    }

    @Test
    public void createButton_ProperText_TruePosBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    Button button = confirmWindow.createButton(Main.getString("properBtnText"), true);

                    Assert.assertEquals(Main.getString("properBtnText"), button.getText());
                    Assert.assertEquals(Main.getString("confirmPositiveBtnID"), button.getId());
                }
        );
    }

    @Test
    public void createButton_ProperText_FalsePosBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    Button button = confirmWindow.createButton(Main.getString("properBtnText"), false);

                    Assert.assertEquals(Main.getString("properBtnText"), button.getText());
                    Assert.assertEquals(Main.getString("confirmNegativeBtnID"), button.getId());
                }
        );
    }

    @Test
    public void findBtn_NullID(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    expectedException.expect(RuntimeException.class);
                    expectedException.expectMessage("btnID cannot be null");
                    confirmWindow.findBtn(null);
                }
        );
    }

    @Test
    public void findBtn_EmptyStringID(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    expectedException.expect(RuntimeException.class);
                    expectedException.expectMessage("btnID cannot be null");
                    confirmWindow.findBtn("");
                }
        );
    }

    @Test
    public void findBtn_ProperID_PositiveBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    Button positiveBtn = confirmWindow.createButton(Main.getString("properBtnText"), true);

                    Button button = confirmWindow.findBtn(Main.getString("confirmPositiveBtnID"));

                    Assert.assertEquals(positiveBtn, button);
                }
        );
    }

    @Test
    public void findBtn_ProperID_NegativeBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    Button negativeBtn = confirmWindow.createButton(Main.getString("properBtnText"), false);

                    Button button = confirmWindow.findBtn(Main.getString("confirmNegativeBtnID"));

                    Assert.assertEquals(negativeBtn, button);
                }
        );
    }

    @Test
    public void findBtn_ProperID_InvalidBtnID(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    Button button = confirmWindow.findBtn(Main.getString("defaultBtn"));

                    Assert.assertEquals(null, button);
                }
        );
    }

    @Test
    public void setRetAndGetRet() {
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.setRet(true);
                    Assert.assertTrue(confirmWindow.getRet());

                    confirmWindow.setRet(false);
                    Assert.assertFalse(confirmWindow.getRet());
                }
        );
    }

    @Test
    public void setConfirmWindow_NullStage(){
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("stage cannot be null");
        ConfirmWindow.setStage(null);
    }

    @Test
    public void setAndGetConfirmWindow_ProperStage(){
        Platform.runLater(
            () -> {
                Stage properStage = Window.createWindow("This is a proper stage");

                ConfirmWindow.setStage(properStage);

                Assert.assertEquals(properStage, ConfirmWindow.getStage());
            }
        );
    }

    @Test
    public void closeConfirmWindow_PositiveBtn(){
        Platform.runLater(
            () -> {
                ConfirmWindow confirmWindow = new ConfirmWindow();

                boolean ret = confirmWindow.display(Main.getString("properTitle"), Main.getString("properMessage"));

                Button positiveBtn = confirmWindow.findBtn(Main.getString("confirmPositiveBtnID"));
                clickOn(positiveBtn);

                Assert.assertFalse(ConfirmWindow.getStage().isShowing());
            }
        );
    }

    @Test
    public void closeConfirmWindow_NegativeBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    boolean ret = confirmWindow.display(Main.getString("properTitle"), Main.getString("properMessage"));

                    Button negativeBtn = confirmWindow.findBtn(Main.getString("confirmNegativeBtnID"));
                    clickOn(negativeBtn);

                    Assert.assertFalse(ConfirmWindow.getStage().isShowing());
                }
        );
    }

    @Test
    public void closeConfirmWindow_CloseBtn(){
        Platform.runLater(
                () -> {
                    ConfirmWindow confirmWindow = new ConfirmWindow();

                    confirmWindow.display(Main.getString("properTitle"), Main.getString("properMessage"));
                    Stage stage = ConfirmWindow.getStage();

                    stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

                    Assert.assertTrue(
                            getTestCapturedLog(logCapturingStream, customLogHandler).
                                    contains(Main.getString("confirmCloseMsg"))
                    );
                }
        );
    }
}