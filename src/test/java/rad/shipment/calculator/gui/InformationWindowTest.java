package rad.shipment.calculator.gui;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class InformationWindowTest {
    private static Logger LOGR = Logger.getLogger(InformationWindow.class.getName()); // matches the logger in the affected class
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();  // expected exception
    private static final DateTimeFormatter fileNameForm = DateTimeFormatter.ofPattern(Main.getString("dateFileNameFormat"));
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;

    public String getTestCapturedLog(@NotNull OutputStream logCapturingStream, @NotNull StreamHandler customLogHandler) {
        customLogHandler.flush();
        return logCapturingStream.toString();
    }

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
    }

    @After
    public void afterEachTest() {
        // TODO: finish afterEachTest test function
    }

    @Test
    public void display_NullTitle_NullMessage() {
        // TODO: finish display_NullTitle_NullMessage test function
    }

    @Test
    public void display_NullTitle_EmptyStringMessage() {
        // TODO: finish display_NullTitle_EmptyStringMessage test function
    }

    @Test
    public void display_NullTitle_ProperMessage() {
        // TODO: finish display_NullTitle_ProperMessage test function
    }

    @Test
    public void display_EmptyStringTitle_NullMessage() {
        // TODO: finish display_EmptyStringTitle_NullMessage test function
    }

    @Test
    public void display_EmptyStringTitle_EmptyStringMessage() {
        // TODO: finish display_EmptyStringTitle_EmptyStringMessage test function
    }

    @Test
    public void display_EmptyStringTitle_ProperMessage() {
        // TODO: finish display_EmptyStringTitle_ProperMessage test function
    }

    @Test
    public void display_ProperTitle_NullMessage() {
        // TODO: finish display_ProperTitle_NullMessage test function
    }

    @Test
    public void display_ProperTitle_EmptyStringMessage() {
        // TODO: finish display_ProperTitle_EmptyStringMessage test function
    }

    @Test
    public void display_ProperTitle_ProperMessage() {
        // TODO: finish display_ProperTitle_ProperMessage test function
    }

    @Test
    public void createScene_NullMessage(){
        // TODO: finish createScene_NullMessage test function
    }

    @Test
    public void createScene_EmptyStringMessage(){
        // TODO: finish createScene_EmptyStringMessage test function
    }

    @Test
    public void createScene_ProperMessage(){
        // TODO: finish createScene_ProperMessage test function
    }

    @Test
    public void createButton_NullText_TruePosBtn(){
        // TODO: finish createButton_NullText_TruePosBtn test function
    }

    @Test
    public void createButton_NullText_FalsePosBtn(){
        // TODO: finish createButton_NullText_FalsePosBtn test function
    }

    @Test
    public void createButton_EmptyStringText_TruePosBtn(){
        // TODO: finish createButton_EmptyStringText_TruePosBtn test function
    }

    @Test
    public void createButton_EmptyStringText_FalsePosBtn(){
        // TODO: finish createButton_EmptyStringText_FalsePosBtn test function
    }

    @Test
    public void createButton_ProperText_TruePosBtn(){
        // TODO: finish createButton_ProperText_TruePosBtn test function
    }

    @Test
    public void createButton_ProperText_FalsePosBtn(){
        // TODO: finish createButton_ProperText_FalsePosBtn test function
    }
}