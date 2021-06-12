package rad.shipment.calculator.helpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import rad.shipment.calculator.gui.MenuPaneController;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class EncryptionTest {

    private static Logger LOGR = Logger.getLogger(MenuPaneController.class.getName()); // matches the logger in the affected class
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
    public void generateKey() {
        // TODO: finish generateKey test function
    }

    @Test
    public void encodeByteArrayToString_NullByteArray() {
        // TODO: finish encodeByteArrayToString_NullByteArray test function
    }

    @Test
    public void encodeByteArrayToString_ProperByteArray() {
        // TODO: finish encodeByteArrayToString_ProperByteArray test function
    }

    @Test
    public void decodeStringToByteArray_NullHexString() {
        // TODO: finish decodeStringToByteArray_NullHexString test function
    }

    @Test
    public void decodeStringToByteArray_GarbageHexString() {
        // TODO: finish decodeStringToByteArray_GarbageHexString test function
    }

    @Test
    public void decodeStringToByteArray_ProperHexString() {
        // TODO: finish decodeStringToByteArray_ProperHexString test function
    }

    @Test
    public void encrypt_NullPlainText() {
        // TODO: finish encrypt_NullPlainText test function
    }

    @Test
    public void encrypt_ProperPlainText() {
        // TODO: finish encrypt_ProperPlainText test function
    }

    @Test
    public void decrypt_NullCipherMessage() {
        // TODO: finish decrypt_NullCipherMessage test function
    }

    @Test
    public void decrypt_InvalidCipherMessage() {
        // TODO: finish decrypt_InvalidCipherMessage test function
    }

    @Test
    public void decrypt_ProperCipherMessage() {
        // TODO: finish decrypt_ProperCipherMessage test function
    }

    @Test
    public void byteToHex(){
        // TODO: finish byteToHex test function
    }

    @Test
    public void hexToByte_NullHexString(){
        // TODO: finish hexToByte_NullHexString test function
    }

    @Test
    public void hexToByte_EmptyStringHexString(){
        // TODO: finish hexToByte_EmptyStringHexString test function
    }

    @Test
    public void hexToByte_ProperHexString(){
        // TODO: finish hexToByte_ProperHexString test function
    }

    @Test
    public void charToInt(){
        // TODO: finish charToInt test function
    }

    @Test
    public void prepareMAC_NullIV(){
        // TODO: finish prepareMAC_NullIV test function
    }

    @Test
    public void prepareMAC_ProperIV(){
        // TODO: finish prepareMAC_ProperIV test function
    }

    @Test
    public void prepareMAC_NullCipherText(){
        // TODO: finish prepareMAC_NullCipherText test function
    }

    @Test
    public void prepareMAC_ProperCipherText(){
        // TODO: finish prepareMAC_ProperCipherText test function
    }

    @Test
    public void prepareMAC_NullAuthKey(){
        // TODO: finish prepareMAC_NullAuthKey test function
    }

    @Test
    public void prepareMAC_ProperAuthKey(){
        // TODO: finish prepareMAC_ProperAuthKey test function
    }
}