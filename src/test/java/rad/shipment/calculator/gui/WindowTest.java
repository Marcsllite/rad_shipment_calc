package rad.shipment.calculator.gui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.testfx.framework.junit.ApplicationTest;

import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class WindowTest extends ApplicationTest {

    private static Logger LOGR = Logger.getLogger(Window.class.getName()); // matches the logger in the affected class
    @Rule public final ExpectedException expectedException = ExpectedException.none();  // expected exception
    private static final DateTimeFormatter fileNameForm = DateTimeFormatter.ofPattern(Main.getString("dateFileNameFormat"));
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;

    public String getTestCapturedLog(@NotNull OutputStream logCapturingStream, @NotNull StreamHandler customLogHandler) {
        customLogHandler.flush();
        return logCapturingStream.toString();
    }

    @Before
    public void beforeEachTest() throws Exception {
    }

    @After
    public void afterEachTest() throws Exception {
    }

    @Test
    public void createWindow_NullTitle() {
        // FIXME: fix createWindow_NullTitle test function
        Platform.runLater(
            () -> {
                Window.createWindow(null);

                Assert.assertEquals(Main.getString("defaultWindowTitle"), Window.getTitle());
            }
        );
    }

    @Test
    public void createWindow_EmptyStringTitle() {
        // FIXME: fix createWindow_EmptyStringTitle test function
        Platform.runLater(
            () -> {
                Window.createWindow("");

                Assert.assertEquals(Main.getString("defaultWindowTitle"), Window.getTitle());
            }
        );
    }

    @Test
    public void createWindow_ProperTitle() {
        // FIXME: fix createWindow_ProperTitle test function
        Platform.runLater(
            () -> {
                Window.createWindow(Main.getString("properTitle"));

                Assert.assertEquals(Main.getString("properTitle"), Window.getTitle());
            }
        );
    }

    @Test
    public void setScene_Null_Window_NullScene() {
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("window cannot be null");
        Window.setScene(null, null);
    }

    @Test
    public void setScene_Null_Window_ProperScene() {
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("window cannot be null");
        Scene properScene = new Scene(new Label("This is a proper scene"));
        Window.setScene(null, properScene);
    }

    @Test
    public void setScene_ProperWindow_NullScene() {
        Platform.runLater(
            () -> {
                expectedException.expect(RuntimeException.class);
                expectedException.expectMessage("scene cannot be null");
                Stage properWindow = Window.createWindow("This is a proper window");
                Window.setScene(properWindow, null);
            }
        );
    }

    @Test
    public void setScene_ProperWindow_ProperScene() {
        Platform.runLater(
            () -> {
                Stage properWindow = Window.createWindow("This is a proper window");
                Scene properScene = new Scene(new Label("This is a proper scene"));

                Window.setScene(properWindow, properScene);

                Assert.assertEquals(properScene, properWindow.getScene());
                Assert.assertFalse(properWindow.isResizable());
            }
        );
    }

    @Test
    public void createLabel_NullMessage() {
        Platform.runLater(
            () -> {
                Label label = Window.createLabel(null);

                Assert.assertEquals(Main.getString("defaultMessage"), label.getText());
            }
        );
    }

    @Test
    public void createLabel_EmptyStringMessage() {
        Platform.runLater(
                () -> {
                    Label label = Window.createLabel("");

                    Assert.assertEquals("", label.getText());
                }
        );
    }

    @Test
    public void createLabel_ProperMessage() {
        Platform.runLater(
                () -> {
                    Label label = Window.createLabel(Main.getString("properMessage"));

                    Assert.assertEquals(Main.getString("properMessage"), label.getText());
                }
        );
    }

    @Test
    public void createButton_NullBtnText() {
        Platform.runLater(
            () -> {
                Button button = Window.createButton(null);

                Assert.assertEquals(Main.getString("defaultBtn"), button.getText());
            }
        );
    }

    @Test
    public void createButton_EmptyStringBtnText() {
        Platform.runLater(
            () -> {
                Button button = Window.createButton("");

                Assert.assertEquals(Main.getString("defaultBtn"), button.getText());
            }
        );
    }

    @Test
    public void createButton_ProperBtnText() {
        // TODO: finish createButton_ProperBtnText test function
        Platform.runLater(
            () -> {
                Button button = Window.createButton(Main.getString("properBtnText"));

                Assert.assertEquals(Main.getString("properBtnText"), button.getText());
            }
        );
    }
}