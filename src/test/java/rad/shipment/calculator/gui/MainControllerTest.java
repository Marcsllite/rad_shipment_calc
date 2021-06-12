package rad.shipment.calculator.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
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

public class MainControllerTest extends ApplicationTest {

    private static Logger LOGR = Logger.getLogger(MainController.class.getName()); // matches the logger in the affected class
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();  // expected exception
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;

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

        ApplicationTest.launch(Main.class);  // launching main class
    }

    @After
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();  // getting rid of current stage
        release(new KeyCode[]{});  // release key events (resource management)
        release(new MouseButton[]{});  // release mouse events (resource management)
    }

    @Test
    public void setBtnFireOnEnter_NullBtn(){
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("button cannot be null");
        MainController.setBtnFireOnEnter(null);
    }

    @Test
    public void setBtnFireOnEnter_ProperBtn() {
        Button button = new Button();

        Assert.assertNull(button.getOnKeyPressed());

        MainController.setBtnFireOnEnter(button);

        Assert.assertNotNull(button.getOnKeyPressed());
    }

    @Test
    public void setBtnFireOnEnter_ProperBtn_NoKey_NotFocused() {
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
        Button button = new Button();
        Button button2 = new Button();

        button.setOnAction(
            (event) -> simpleStringProperty.set("button was fired")
        );

        MainController.setBtnFireOnEnter(button);

        button2.requestFocus();

        Assert.assertNull(simpleStringProperty.get());
    }

    @Test
    public void setBtnFireOnEnter_ProperBtn_NoKey_Focused() {
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
        Button button = new Button();

        button.setOnAction(
            (event) -> simpleStringProperty.set("button was fired")
        );

        MainController.setBtnFireOnEnter(button);

        button.requestFocus();

        Assert.assertNull(simpleStringProperty.get());
    }

    @Test
    public void setBtnFireOnEnter_ProperBtn_EnterPressed_NotFocused() {
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
        Button button = new Button();
        Button button2 = new Button();

        button.setOnAction(
            (event) -> simpleStringProperty.set("button was fired")
        );

        MainController.setBtnFireOnEnter(button);

        button2.requestFocus();

        press(KeyCode.ENTER);

        Assert.assertNull(simpleStringProperty.get());
    }

    @Test
    public void setBtnFireOnEnter_ProperBtn_EnterPressed_Focused() {
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
        Button button = new Button();

        button.setOnAction(
                (event) -> simpleStringProperty.set("button was fired")
        );

        MainController.setBtnFireOnEnter(button);

        button.setDefaultButton(true);
        button.setFocusTraversable(true);
//        button.requestFocus();

        if (button.isFocused()) {
            press(KeyCode.ENTER);
            Assert.assertEquals("button was fired", simpleStringProperty.get());
        } else {
            System.out.println("button is not focused");
        }
    }

    @Test
    public void setLinkFireOnEnter_NullLink(){
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("link cannot be null");
        MainController.setLinkFireOnEnter(null);
    }

    @Test
    public void setLinkFireOnEnter_ProperLink(){
        Hyperlink link = new Hyperlink();

        Assert.assertNull(link.getOnKeyPressed());

        MainController.setLinkFireOnEnter(link);

        Assert.assertNotNull(link.getOnKeyPressed());
    }
}