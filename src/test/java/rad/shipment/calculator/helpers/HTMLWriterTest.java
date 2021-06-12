package rad.shipment.calculator.helpers;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import rad.shipment.calculator.gui.ConfirmWindow;
import rad.shipment.calculator.gui.Main;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class HTMLWriterTest {

    private static Logger LOGR = Logger.getLogger(ConfirmWindow.class.getName()); // matches the logger in the affected class
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
    public void afterEachTest() throws Exception {
    }

    @Test
    public void saveHTML_NullFilePath() {
    }

    @Test
    public void saveHTML_EmptyStringFilePath() {
    }

    @Test
    public void saveHTML_ProperFilePath() {
    }

    @Test
    public void getTimeStamp(){
    }

    @Test
    public void getFileText_NullResourceFilePath(){
    }

    @Test
    public void getFileText_EmptyStringResourceFilePath(){
    }

    @Test
    public void getFileText_ProperResourceFilePath(){
    }

    @Test
    public void getColumnData_NullIsotope(){
    }

    @Test
    public void getColumnData_ProperIsotope(){
    }

    @Test
    public void getColumnData_NullHeader(){
    }

    @Test
    public void getColumnData_EmptyStringHeader(){
    }

    @Test
    public void getColumnData_ProperHeader(){
    }

    @Test
    public void createDocument(){
    }
}