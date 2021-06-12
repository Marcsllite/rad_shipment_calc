package rad.shipment.calculator.helpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class ConversionsTest {

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
    public void bqToCi() {
        // TODO: finish bqToCi test function
    }

    @Test
    public void ciToBq() {
        // TODO: finish ciToBq test function
    }

    @Test
    public void gyToRad() {
        // TODO: finish gyToRad test function
    }

    @Test
    public void radToGy() {
        // TODO: finish radToGy test function
    }

    @Test
    public void svToRem() {
        // TODO: finish svToRem test function
    }

    @Test
    public void remToSv() {
        // TODO: finish remToSv test function
    }

    @Test
    public void c_kgToR() {
        // TODO: finish c_kgToR test function
    }

    @Test
    public void RToC_kg() {
        // TODO: finish RToC_kg test function
    }

    @Test
    public void baseToYotta() {
        // TODO: finish baseToYotta test function
    }

    @Test
    public void baseToZetta() {
        // TODO: finish baseToZetta test function
    }

    @Test
    public void baseToExa() {
        // TODO: finish baseToExa test function
    }

    @Test
    public void baseToPeta() {
        // TODO: finish baseToPeta test function
    }

    @Test
    public void baseToTera() {
        // TODO: finish baseToTera test function
    }

    @Test
    public void baseToGiga() {
        // TODO: finish baseToGiga test function
    }

    @Test
    public void baseToMega() {
        // TODO: finish baseToMega test function
    }

    @Test
    public void baseToKilo() {
        // TODO: finish baseToKilo test function
    }

    @Test
    public void baseToHecto() {
        // TODO: finish baseToHecto test function
    }

    @Test
    public void baseToDeka() {
        // TODO: finish baseToDeka test function
    }

    @Test
    public void baseToDeci() {
        // TODO: finish baseToDeci test function
    }

    @Test
    public void baseToCenti() {
        // TODO: finish baseToCenti test function
    }

    @Test
    public void baseToMilli() {
        // TODO: finish baseToMilli test function
    }

    @Test
    public void baseToMicro() {
        // TODO: finish baseToMicro test function
    }

    @Test
    public void baseToNano() {
        // TODO: finish baseToNano test function
    }

    @Test
    public void baseToPico() {
        // TODO: finish baseToPico test function
    }

    @Test
    public void baseToFemto() {
        // TODO: finish baseToFemto test function
    }

    @Test
    public void baseToAtto() {
        // TODO: finish baseToAtto test function
    }

    @Test
    public void baseToZepto() {
        // TODO: finish baseToZepto test function
    }

    @Test
    public void baseToYocto() {
        // TODO: finish baseToYocto test function
    }

    @Test
    public void yottaToBase() {
        // TODO: finish yottaToBase test function
    }

    @Test
    public void zettaToBase() {
        // TODO: finish zettaToBase test function
    }

    @Test
    public void exaToBase() {
        // TODO: finish exaToBase test function
    }

    @Test
    public void petaToBase() {
        // TODO: finish petaToBase test function
    }

    @Test
    public void teraToBase() {
        // TODO: finish teraToBase test function
    }

    @Test
    public void gigaToBase() {
        // TODO: finish gigaToBase test function
    }

    @Test
    public void megaToBase() {
        // TODO: finish megaToBas test function
    }

    @Test
    public void kiloToBase() {
        // TODO: finish kiloToBase test function
    }

    @Test
    public void hectoToBase() {
        // TODO: finish hectoToBase test function
    }

    @Test
    public void dekaToBase() {
        // TODO: finish dekaToBase test function
    }

    @Test
    public void deciToBase() {
        // TODO: finish deciToBase test function
    }

    @Test
    public void centiToBase() {
        // TODO: finish centiToBase test function
    }

    @Test
    public void milliToBase() {
        // TODO: finish milliToBase test function
    }

    @Test
    public void microToBase() {
        // TODO: finish microToBase test function
    }

    @Test
    public void nanoToBase() {
        // TODO: finish nanoToBase test function
    }

    @Test
    public void picoToBase() {
        // TODO: finish picoToBase test function
    }

    @Test
    public void femtoToBase() {
        // TODO: finish femtoToBase test function
    }

    @Test
    public void attoToBase() {
        // TODO: finish attoToBase test function
    }

    @Test
    public void zeptoToBase() {
        // TODO: finish zeptoToBase test function
    }

    @Test
    public void yoctoToBase() {
        // TODO: finish yoctoToBase test function
    }
}