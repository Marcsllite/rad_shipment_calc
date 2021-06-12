package rad.shipment.calculator.helpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class DatabaseEditorTest {

    private static Logger LOGR = Logger.getLogger(CommandExecutor.class.getName()); // matches the logger in the affected class
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
    }

    @After
    public void afterEachTest() throws Exception {
    }

    @Test
    public void startConnection() {
        // TODO: finish startConnection test function
    }

    @Test
    public void closeConnection() {
        // TODO: finish closeConnection test function
    }

    @Test
    public void getA1_NullName() {
        // TODO: finish getA1_NullName test function
    }

    @Test
    public void getA1_EmptyStringName() {
        // TODO: finish getA1_EmptyStringName test function
    }

    @Test
    public void getA1_ProperName() {
        // TODO: finish getA1_ProperName test function
    }

    @Test
    public void getA2_NullName() {
        // TODO: finish getA2_NullName test function
    }

    @Test
    public void getA2_EmptyStringName() {
        // TODO: finish getA2_EmptyStringName test function
    }

    @Test
    public void getA2_ProperName() {
        // TODO: finish getA2_ProperName test function
    }

    @Test
    public void getDecayConstant_NullName() {
        // TODO: finish getDecayConstant_NullName test function
    }

    @Test
    public void getDecayConstant_EmptyStringName() {
        // TODO: finish getDecayConstant_EmptyStringName test function
    }
    @Test
    public void getDecayConstant_ProperName() {
        // TODO: finish getDecayConstant_ProperName test function
    }

    @Test
    public void getExemptConcentration_NullName() {
        // TODO: finish getExemptConcentration_NullName test function
    }

    @Test
    public void getExemptConcentration_EmptyStringName() {
        // TODO: finish getExemptConcentration_EmptyStringName test function
    }

    @Test
    public void getExemptConcentration_ProperName() {
        // TODO: finish getExemptConcentration_ProperName test function
    }

    @Test
    public void getExemptLimit_NullName() {
        // TODO: finish getExemptLimit_NullName test function
    }

    @Test
    public void getExemptLimit_EmptyStringName() {
        // TODO: finish getExemptLimit_EmptyStringName test function
    }
    @Test
    public void getExemptLimit_ProperName() {
        // TODO: finish getExemptLimit_ProperName test function
    }

    @Test
    public void getHalfLife_NullName() {
        // TODO: finish getHalfLife_NullName test function
    }

    @Test
    public void getHalfLife_EmptyStringName() {
        // TODO: finish getHalfLife_EmptyStringName test function
    }
    @Test
    public void getHalfLife_ProperName() {
        // TODO: finish getHalfLife_ProperName test function
    }

    @Test
    public void getIALimitedMultiplier_NullName() {
        // TODO: finish getIALimitedMultiplier_NullName test function
    }

    @Test
    public void getIALimitedMultiplier_EmptyStringName() {
        // TODO: finish getIALimitedMultiplier_EmptyStringName test function
    }

    @Test
    public void getIALimitedMultiplier_ProperName() {
        // TODO: finish getIALimitedMultiplier_ProperName test function
    }

    @Test
    public void getIAPackageLimit_NullName() {
        // TODO: finish getIAPackageLimit_NullName test function
    }

    @Test
    public void getIAPackageLimit_EmptyStringName() {
        // TODO: finish getIAPackageLimit_EmptyStringName test function
    }

    @Test
    public void getIAPackageLimit_ProperName() {
        // TODO: finish getIAPackageLimit_ProperName test function
    }

    @Test
    public void getLicenseLimit_NullName() {
        // TODO: finish getLicenseLimit_NullName test function
    }

    @Test
    public void getLicenseLimit_EmptyStringName() {
        // TODO: finish getLicenseLimit_EmptyStringName test function
    }
    @Test
    public void getLicenseLimit_ProperName() {
        // TODO: finish getLicenseLimit_ProperName test function
    }

    @Test
    public void getLimitedLimit_NullName() {
        // TODO: finish getLimitedLimit_NullName test function
    }

    @Test
    public void getLimitedLimit_EmptyStringName() {
        // TODO: finish getLimitedLimit_EmptyStringName test function
    }

    @Test
    public void getLimitedLimit_ProperName() {
        // TODO: finish getLimitedLimit_ProperName test function
    }

    @Test
    public void getReportableQuantity_NullName() {
        // TODO: finish getReportableQuantity_NullName test function
    }

    @Test
    public void getReportableQuantity_EmptyStringName() {
        // TODO: finish getReportableQuantity_EmptyStringName test function
    }

    @Test
    public void getReportableQuantity_ProperName() {
        // TODO: finish getReportableQuantity_ProperName test function
    }

    @Test
    public void getDriver() {
        // TODO: finish getDriver test function
    }

    @Test
    public void getPath() {
        // TODO: finish getPath test function
    }

    @Test
    public void getSettings() {
        // TODO: finish getSettings test function
    }

    @Test
    public void getUser() {
        // TODO: finish getUser test function
    }

    @Test
    public void getPass() {
        // TODO: finish getPass test function
    }
}