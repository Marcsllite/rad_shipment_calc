package rad.shipment.calculator.helpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class CommandExecutorTest {

    private static Logger LOGR = Logger.getLogger(CommandExecutor.class.getName()); // matches the logger in the affected class
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
    }

    @After
    public void afterEachTest() throws Exception {
    }


    @Test
    public void setAndRunCommands_NullCommand() {
        // TODO: finish setAndRunCommands_Command test function
    }

    @Test
    public void setAndRunCommands_EmptyStringCommand() {
        // TODO: finish setAndRunCommands_EmptyStringCommand test function
    }

    @Test
    public void setAndRunCommands_ProperCommand() {
        // TODO: finish setAndRunCommands_ProperCommand test function
    }

    @Test
    public void getAndSetExecDir_NullPath() {
        // TODO: finish getAndSetExecDir_NullPath test function
    }

    @Test
    public void getAndSetExecDir_EmptyStringPath() {
        // TODO: finish getAndSetExecDir_EmptyStringPath test function
    }

    @Test
    public void getAndSetExecDir_ProperPath() {
        // TODO: finish getAndSetExecDir_ProperPath test function
    }

    @Test
    public void getAndSetExecDir_DirectoryPath() {
        // TODO: finish getAndSetExecDir_DirectoryPath test function
    }

    @Test
    public void getAndSetExecDir_FakePath() {
        // TODO: finish getAndSetExecDir_FakePath test function
    }

    @Test
    public void getAndSetProcessBuilder_NullParam() {
        // TODO: finish getAndSetProcessBuilder_NullParam test function
    }

    @Test
    public void getAndSetProcessBuilder_ProperParam() {
        // TODO: finish getAndSet ProcessBuilder_ProperParam test function
    }
}