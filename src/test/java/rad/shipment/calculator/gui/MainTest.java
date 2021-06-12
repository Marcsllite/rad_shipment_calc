package rad.shipment.calculator.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import rad.shipment.calculator.view.FXMLView;

import javax.swing.filechooser.FileSystemView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import static org.junit.Assert.fail;

public class MainTest extends ApplicationTest {

    private static Logger LOGR = Logger.getLogger(Main.class.getName()); // matches the logger in the affected class
    @Rule public final ExpectedException expectedException = ExpectedException.none();  // expected exception
    private ResourceBundle bundle = ResourceBundle.getBundle("gradle");  // getting resource bundle
    private static OutputStream logCapturingStream;
    private static StreamHandler customLogHandler;
    private Stage primaryStage;

    public String getTestCapturedLog(@NotNull OutputStream logCapturingStream, @NotNull StreamHandler customLogHandler) {
        customLogHandler.flush();
        return logCapturingStream.toString();
    }

    /**
     * @author Baeldung https://www.baeldung.com/java-delete-directory
     */
    private boolean deleteDirectory(@NotNull File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start (@NotNull Stage stage) {
        // loading and showing the main parent node
        primaryStage = stage;
        stage.show();
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

        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }

        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    @After
    public void afterEachTest() throws Exception {
        FxToolkit.hideStage();  // getting rid of current stage
        release(new KeyCode[]{});  // release key events (resource management)
        release(new MouseButton[]{});  // release mouse events (resource management)
    }

    @Test
    public void findCurrentOS() {
        // Windows
        // Assert.assertEquals(bundle.getString("windows"), Main.findCurrentOS());

        // Mac OS
        // Assert.assertEquals(bundle.getString("mac"), Main.findCurrentOS());

        // Unix
        // Assert.assertEquals(bundle.getString("unix"), Main.findCurrentOS());

        // Solaris
        // Assert.assertEquals(bundle.getString("solaris"), Main.findCurrentOS());
    }

    @Test
    public void setUpDefaultDirectory_MakeNewFolder() {
        File directoryToBeDeleted = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + Main.getString("appMainFolder"));
        Assert.assertTrue(deleteDirectory(directoryToBeDeleted));

        Main.setUpDefaultDirectory();

        // FIXME: fix setUpDefaultDirectory_MakeNewFolder test function
        Assert.assertTrue(getTestCapturedLog(logCapturingStream, customLogHandler).contains(
                FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + Main.getString("appMainFolder") + " directory was created"));
    }

    @Test
    public void setUpDefaultDirectory_FolderAlreadyExists() {
        File directoryToBeDeleted = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + Main.getString("appMainFolder"));
        Assert.assertTrue(deleteDirectory(directoryToBeDeleted));

        // creating folders
        Assert.assertTrue((new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + Main.getString("appMainFolder"))).mkdirs());

        Main.setUpDefaultDirectory();

        // FIXME: fix setupDefaultDirectory_FolderAlreadyExists test function
        Assert.assertFalse(getTestCapturedLog(logCapturingStream, customLogHandler).contains(
                FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + Main.getString("appMainFolder") + " directory was created"));
    }

    @Test
    public void setUpDataFolder_NullParam(){
        Main.setUpDataFolder(null);

        Assert.assertNull(Main.getDataFolder());
    }

    @Test
    public void setUpDataFolder_EmptyString(){
        Main.setUpDataFolder("");

        Assert.assertNull(Main.getDataFolder());
    }

    @Test
    public void setUpDataFolder_Windows(){
        String expected = System.getProperty("user.home") + File.separator +
                "AppData" + File.separator +
                "Local"+ File.separator +
                bundle.getString("appFolderName") + File.separator +
                "logs";

        Main.setUpDataFolder(bundle.getString("windows"));

        Assert.assertEquals(expected, Main.getDataFolder());
    }

    @Test
    public void setUpDataFolder_Mac(){
        String expected = System.getProperty("user.home") + File.separator +
                bundle.getString("appFolderName") + File.separator +
                "logs";

        Main.setUpDataFolder(bundle.getString("mac"));

        Assert.assertEquals(expected, Main.getDataFolder());
    }

    @Test
    public void setUpDataFolder_Unix(){
        String expected = System.getProperty("user.home") + File.separator +
                bundle.getString("appFolderName") + File.separator +
                "logs";

        Main.setUpDataFolder(bundle.getString("unix"));

        Assert.assertEquals(expected, Main.getDataFolder());
    }

    @Test
    public void setUpDataFolder_Solaris(){
        String expected = System.getProperty("user.home") + File.separator +
                bundle.getString("appFolderName") + File.separator +
                "logs";

        Main.setUpDataFolder(bundle.getString("solaris"));

        Assert.assertEquals(expected, Main.getDataFolder());
    }

    @Test
    public void displayInitialScreen() {
        FXMLView view = FXMLView.MAIN;
        Parent rootNode = null;

        try { rootNode = (new FXMLLoader(getClass().getResource(view.getFxmlLoc()), bundle)).load(); }
        catch (IOException e) { fail("Failed to load fxml file"); }

        Scene actual = Main.stageManager.getPrimaryStage().getScene();
        Scene expected = new Scene(rootNode, view.getWidth(), view.getHeight());

        Assert.assertEquals(rootNode.toString(), actual.getRoot().toString().trim());
        Assert.assertEquals(expected.getWidth(), actual.getWidth(), 0);
        Assert.assertEquals(expected.getHeight(), actual.getHeight(), 0);
    }

    @Test
    public void replaceBundleString_NullKey() {
        // saving value from replaceBundleString function
        String nullKey1 = Main.replaceBundleString(null, (String[]) null);
        String nullKey2 = Main.replaceBundleString(null, (String) null);

        // making sure values are what they should be
        Assert.assertEquals("", nullKey1);
        Assert.assertEquals("", nullKey2);
    }

    @Test
    public void replaceBundleString_EmptyStringKey() {
        // saving value from replaceBundleString function
        String emptyStringKey1 = Main.replaceBundleString("", (String[]) null);
        String emptyStringKey2 = Main.replaceBundleString("", (String) null);

        // making sure values are what they should be
        Assert.assertEquals("", emptyStringKey1);
        Assert.assertEquals("", emptyStringKey2);
    }

    @Test
    public void replaceBundleString_FakeKey_NullParam() {
        // saving value from replaceBundleString function
        String fakeKey_nullReplacement1 = Main.replaceBundleString("fakeKey", (String[]) null);
        String fakeKey_nullReplacement2 = Main.replaceBundleString("fakeKey", (String) null);

        // making sure values are what they should be
        Assert.assertEquals("", fakeKey_nullReplacement1);
        Assert.assertEquals("", fakeKey_nullReplacement2);
    }

    @Test
    public void replaceBundleString_FakeKey_EmptyStringParam() {
        // saving value from replaceBundleString function
        String fakeKey_nullReplacement1 = Main.replaceBundleString("", (String[]) null);
        String fakeKey_nullReplacement2 = Main.replaceBundleString("", (String) null);

        // making sure values are what they should be
        Assert.assertEquals("", fakeKey_nullReplacement1);
        Assert.assertEquals("", fakeKey_nullReplacement2);
    }

    @Test
    public void replaceBundleString_FakeKey_NoParam() {
        // saving value from replaceBundleString function
        String fakeKey_noReplacement = Main.replaceBundleString("fakeKey");

        // making sure values are what they should be
        Assert.assertEquals("", fakeKey_noReplacement);
    }

    @Test
    public void replaceBundleString_FakeKey_OneParam() {
        // saving value from replaceBundleString function
        String fakeKey_replacementGivenButNotRequired = Main.replaceBundleString("fakeKey", "ONE");

        // making sure values are what they should be
        Assert.assertEquals("", fakeKey_replacementGivenButNotRequired);
    }

    @Test
    public void replaceBundleString_NoTextInResource_NullParam() {
        // Getting ResourceBundle strings
        String noText = bundle.getString("replaceBundleString_noText");

        // saving value from replaceBundleString function
        String noText_nullReplacement1 = Main.replaceBundleString("replaceBundleString_noText", (String[]) null);
        String noText_nullReplacement2 = Main.replaceBundleString("replaceBundleString_noText", (String) null);

        // making sure values are what they should be
        Assert.assertEquals(noText, noText_nullReplacement1);
        Assert.assertEquals(noText, noText_nullReplacement2);
    }

    @Test
    public void replaceBundleString_NoTextInResource_NoParam() {
        // Getting ResourceBundle strings
        String noText = bundle.getString("replaceBundleString_noText");

        // saving value from replaceBundleString function
        String noText_noReplacement = Main.replaceBundleString("replaceBundleString_noText");

        // making sure values are what they should be
        Assert.assertEquals(noText, noText_noReplacement);
    }

    @Test
    public void replaceBundleString_NoTextInResource_OneParam() {
        // Getting ResourceBundle strings
        String noText = bundle.getString("replaceBundleString_noText");

        // saving value from replaceBundleString function
        String noText_replacementGivenButNotRequired = Main.replaceBundleString("replaceBundleString_noText", "ONE");

        // making sure values are what they should be
        Assert.assertEquals(noText, noText_replacementGivenButNotRequired);
    }

    @Test
    public void replaceBundleString_NoReplacementsInResource_NullParam() {
        // Getting ResourceBundle strings
        String noReplacements = bundle.getString("replaceBundleString_noReplacements");

        // saving value from replaceBundleString function
        String noReplacements_nullReplacement1 = Main.replaceBundleString("replaceBundleString_noReplacements", (String[]) null);
        String noReplacements_nullReplacement2 = Main.replaceBundleString("replaceBundleString_noReplacements", (String) null);

        // making sure values are what they should be
        Assert.assertEquals(noReplacements, noReplacements_nullReplacement1);
        Assert.assertEquals(noReplacements, noReplacements_nullReplacement2);
    }

    @Test
    public void replaceBundleString_NoReplacementsInResource_NoParam() {
        // Getting ResourceBundle strings
        String noReplacements = bundle.getString("replaceBundleString_noReplacements");

        // saving value from replaceBundleString function
        String noReplacements_noReplacement = Main.replaceBundleString("replaceBundleString_noReplacements");

        // making sure values are what they should be
        Assert.assertEquals(noReplacements, noReplacements_noReplacement);
    }

    @Test
    public void replaceBundleString_NoReplacementsInResource_OneParam() {
        // Getting ResourceBundle strings
        String noReplacements = bundle.getString("replaceBundleString_noReplacements");

        // saving value from replaceBundleString function
        String noReplacements_replacementGivenButNotRequired = Main.replaceBundleString("replaceBundleString_noReplacements", "ONE");

        // making sure values are what they should be
        Assert.assertEquals(noReplacements, noReplacements_replacementGivenButNotRequired);
    }

    @Test
    public void replaceBundleString_OneReplacementInResource_NullParam() {
        // Getting ResourceBundle strings
        String oneReplacement = bundle.getString("replaceBundleString_oneReplacement");

        // saving value from replaceBundleString function
        String oneReplacement_nullReplacement1 = Main.replaceBundleString("replaceBundleString_oneReplacement", (String[]) null);
        String oneReplacement_nullReplacement2 = Main.replaceBundleString("replaceBundleString_oneReplacement", (String) null);

        // making sure values are what they should be
        Assert.assertEquals(oneReplacement, oneReplacement_nullReplacement1);
        Assert.assertEquals(oneReplacement, oneReplacement_nullReplacement2);
    }

    @Test
    public void replaceBundleString_OneReplacementInResource_NoParam() {
        // Getting ResourceBundle strings
        String oneReplacement = bundle.getString("replaceBundleString_oneReplacement");

        // saving value from replaceBundleString function
        String oneReplacement_noReplacement = Main.replaceBundleString("replaceBundleString_oneReplacement");

        // making sure values are what they should be
        Assert.assertEquals(oneReplacement, oneReplacement_noReplacement);
    }

    @Test
    public void replaceBundleString_OneReplacementInResource_OneParam() {
        // saving value from replaceBundleString function
        String oneReplacement_oneReplacementGiven = Main.replaceBundleString("replaceBundleString_oneReplacement", "ONE");

        // making sure values are what they should be
        Assert.assertEquals("This string contains ONE replacements", oneReplacement_oneReplacementGiven);
    }

    @Test
    public void replaceBundleString_OneReplacementInResource_TwoParams() {
        // saving value from replaceBundleString function
        String oneReplacement_twoReplacementGiven = Main.replaceBundleString("replaceBundleString_oneReplacement", "ONE", "TWO");

        // making sure values are what they should be
        Assert.assertEquals("This string contains ONE replacements", oneReplacement_twoReplacementGiven);
    }

    @Test
    public void replaceBundleString_WrongStartNumberInResource_NullParam() {
        // Getting ResourceBundle strings
        String wrongNumber = bundle.getString("replaceBundleString_wrongNumber");

        // saving value from replaceBundleString function
        String wrongNumber_nullReplacement1 = Main.replaceBundleString("replaceBundleString_wrongNumber", (String[]) null);
        String wrongNumber_nullReplacement2 = Main.replaceBundleString("replaceBundleString_wrongNumber", (String) null);

        // making sure values are what they should be
        Assert.assertEquals(wrongNumber, wrongNumber_nullReplacement1);
        Assert.assertEquals(wrongNumber, wrongNumber_nullReplacement2);
    }

    @Test
    public void replaceBundleString_WrongStartNumberInResource_NoParam() {
        // Getting ResourceBundle strings
        String wrongNumber = bundle.getString("replaceBundleString_wrongNumber");

        // saving value from replaceBundleString function
        String wrongNumber_noReplacement = Main.replaceBundleString("replaceBundleString_wrongNumber");

        // making sure values are what they should be
        Assert.assertEquals(wrongNumber, wrongNumber_noReplacement);
    }

    @Test
    public void replaceBundleString_WrongStartNumberInResource_OneParam() {
        // saving value from replaceBundleString function
        String wrongNumber_oneReplacement = Main.replaceBundleString("replaceBundleString_wrongNumber", "ONE");

        // making sure values are what they should be
        Assert.assertEquals("This string contains the incorrect ONE for replacement", wrongNumber_oneReplacement);
    }

    @Test
    public void replaceBundleString_WrongStartNumberInResource_TwoParams() {
        // saving value from replaceBundleString function
        String wrongNumber_twoReplacements = Main.replaceBundleString("replaceBundleString_wrongNumber", "ONE", "TWO");

        // making sure values are what they should be
        Assert.assertEquals("This string contains the incorrect ONE for replacement", wrongNumber_twoReplacements);
    }

    @Test
    public void replaceBundleString_TwoReplacementsInResource_NullParam() {
        // Getting ResourceBundle strings
        String twoReplacements = bundle.getString("replaceBundleString_twoReplacements");

        // saving value from replaceBundleString function
        String twoReplacements_nullReplacement1 = Main.replaceBundleString("replaceBundleString_twoReplacements", (String[]) null);
        String twoReplacements_nullReplacement2 = Main.replaceBundleString("replaceBundleString_twoReplacements", (String) null);

        // making sure values are what they should be
        Assert.assertEquals(twoReplacements, twoReplacements_nullReplacement1);
        Assert.assertEquals(twoReplacements, twoReplacements_nullReplacement2);
    }

    @Test
    public void replaceBundleString_TwoReplacementsInResource_NoParam() {
        // Getting ResourceBundle strings
        String twoReplacements = bundle.getString("replaceBundleString_twoReplacements");

        // saving value from replaceBundleString function
        String twoReplacements_noReplacement = Main.replaceBundleString("replaceBundleString_twoReplacements");

        // making sure values are what they should be
        Assert.assertEquals(twoReplacements, twoReplacements_noReplacement);
    }

    @Test
    public void replaceBundleString_TwoReplacementsInResource_OneParam() {
        // saving value from replaceBundleString function
        String twoReplacements_oneReplacement = Main.replaceBundleString("replaceBundleString_twoReplacements", "ONE");

        // making sure values are what they should be
        Assert.assertEquals("This string contains ONE, {1} replacements", twoReplacements_oneReplacement);
    }

    @Test
    public void replaceBundleString_TwoReplacementsInResource_TwoParams() {
        // saving value from replaceBundleString function
        String twoReplacements_twoReplacements = Main.replaceBundleString("replaceBundleString_twoReplacements", "ONE", "TWO");

        // making sure values are what they should be
        Assert.assertEquals("This string contains ONE, TWO replacements", twoReplacements_twoReplacements);
    }

    @Test
    public void replaceBundleString_TwoReplacementsInResource_ThreeParams() {
        // saving value from replaceBundleString function
        String twoReplacements_threeReplacements = Main.replaceBundleString("replaceBundleString_twoReplacements", "ONE", "TWO", "THREE");

        // making sure values are what they should be
        Assert.assertEquals("This string contains ONE, TWO replacements", twoReplacements_threeReplacements);
    }

    @Test
    public void replaceBundleString_ThreeReplacementsInResource_NullParam() {
        // Getting ResourceBundle strings
        String threeReplacements = bundle.getString("replaceBundleString_threeReplacements");

        // saving value from replaceBundleString function
        String threeReplacements_nullReplacement1 = Main.replaceBundleString("replaceBundleString_threeReplacements", (String[]) null);
        String threeReplacements_nullReplacement2 = Main.replaceBundleString("replaceBundleString_threeReplacements", (String) null);

        // making sure values are what they should be
        Assert.assertEquals(threeReplacements, threeReplacements_nullReplacement1);
        Assert.assertEquals(threeReplacements, threeReplacements_nullReplacement2);
    }

    @Test
    public void replaceBundleString_ThreeReplacementsInResource_NoParam() {
        // Getting ResourceBundle strings
        String threeReplacements = bundle.getString("replaceBundleString_threeReplacements");

        // saving value from replaceBundleString function
        String threeReplacements_noReplacement = Main.replaceBundleString("replaceBundleString_threeReplacements");

        // making sure values are what they should be
        Assert.assertEquals(threeReplacements, threeReplacements_noReplacement);
    }

    @Test
    public void replaceBundleString_ThreeReplacementsInResource_OneParam() {
        // saving value from replaceBundleString function
        String threeReplacements_oneReplacement = Main.replaceBundleString("replaceBundleString_threeReplacements", "ONE");

        // making sure values are what they should be
        Assert.assertEquals("This string contains ONE, {1}, {2} replacements", threeReplacements_oneReplacement);
    }

    @Test
    public void replaceBundleString_ThreeReplacementsInResource_TwoParams() {
        // saving value from replaceBundleString function
        String threeReplacements_twoReplacements = Main.replaceBundleString("replaceBundleString_threeReplacements", "ONE", "TWO");

        // making sure values are what they should be
        Assert.assertEquals("This string contains ONE, TWO, {2} replacements", threeReplacements_twoReplacements);
    }

    @Test
    public void replaceBundleString_ThreeReplacementsInResource_ThreeParams() {
        // saving value from replaceBundleString function
        String threeReplacements_threeReplacements = Main.replaceBundleString("replaceBundleString_threeReplacements", "ONE", "TWO", "THREE");

        // making sure values are what they should be
        Assert.assertEquals("This string contains ONE, TWO, THREE replacements", threeReplacements_threeReplacements);
    }

    @Test
    public void replaceBundleString_ThreeReplacementsInResource_FourParams() {
        // saving value from replaceBundleString function
        String threeReplacements_fourReplacements = Main.replaceBundleString("replaceBundleString_threeReplacements", "ONE", "TWO", "THREE", "FOUR");

        // making sure values are what they should be
        Assert.assertEquals("This string contains ONE, TWO, THREE replacements", threeReplacements_fourReplacements);
    }

    @Test
    public void parseStringsToReplace_NullParam() {
        Assert.assertEquals(new ArrayList<String>(),
                            Main.parseStringsToReplace(null));
    }

    @Test
    public void parseStringsToReplace_EmptyStringParam() {
        Assert.assertEquals(new ArrayList<String>(),
                Main.parseStringsToReplace(""));
    }

    @Test
    public void parseStringsToReplace_NoReplacements() {
        Assert.assertEquals(new ArrayList<String>(),
                            Main.parseStringsToReplace(Main.getString("replaceBundleString_noReplacements")));
    }

    @Test
    public void parseStringsToReplace_OneReplacement() {
        List<String> expected = new ArrayList<>();
        expected.add("{0}");

        Assert.assertEquals(expected,
                            Main.parseStringsToReplace(Main.getString("replaceBundleString_oneReplacement")));
    }

    @Test
    public void parseStringsToReplace_OneReplacement_WrongNumber() {
        List<String> expected = new ArrayList<>();
        expected.add("{1}");

        Assert.assertEquals(expected,
                Main.parseStringsToReplace(Main.getString("replaceBundleString_wrongNumber")));
    }

    @Test
    public void parseStringsToReplace_TwoReplacements() {
        List<String> expected = new ArrayList<>();
        expected.add("{0}");
        expected.add("{1}");

        Assert.assertEquals(expected,
                Main.parseStringsToReplace(Main.getString("replaceBundleString_twoReplacements")));

    }

    @Test
    public void parseStringsToReplace_ThreeReplacements() {
        List<String> expected = new ArrayList<>();
        expected.add("{0}");
        expected.add("{1}");
        expected.add("{2}");

        Assert.assertEquals(expected,
                Main.parseStringsToReplace(Main.getString("replaceBundleString_threeReplacements")));

    }

    @Test
    public void navigateToLink_NullParam() {
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("link is null");
        Main.navigateToLink(null);
    }

    @Test
    public void navigateToLink_EmptyStringParam() {
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("link is empty string");
        Main.navigateToLink("");
    }

    @Test
    public void navigateToLink_ImproperURL() {
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("link is malformed");

        Main.navigateToLink("thisUrlIsInvalid");
    }

    @Test
    public void navigateToLink_ProperURL() {  // unsopported on travic ci
        //NOTE: "www.google.com" is valid on Windows but invalid on macOS
        Main.navigateToLink("https://www.google.com");
    }

    @Test
    public void getString_NullParam() { Assert.assertEquals("", Main.getString(null)); }

    @Test
    public void getString_EmptyStringParam() { Assert.assertEquals("", Main.getString("")); }

    @Test
    public void getString_FakeKey() { Assert.assertEquals("", Main.getString("fakeKey")); }

    @Test
    public void getString_NoTextInResource() { Assert.assertEquals("", Main.getString("replaceBundleString_noText")); }

    @Test
    public void getString_ProperKey() {
        Assert.assertEquals(bundle.getString("mainPane"), Main.getString("mainPane"));
    }

    @Test
    public void getInt_NullParam() {
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("key is null");
        Main.getInt(null);
    }

    @Test
    public void getInt_EmptyStringParam() {
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("key is empty string");
        Main.getInt("");
    }

    @Test
    public void getInt_FakeKey() {
        expectedException.expect(InvalidParameterException.class);
        expectedException.expectMessage("Key does not exist");
        Main.getInt("fakeKey");
    }

    @Test
    public void getInt_NoTextInResource() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Value is not a number");
        Main.getInt("replaceBundleString_noText");
    }

    @Test
    public void getInt_StringKey() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Value is not a number");
        Main.getInt("defaultStr");
    }

    @Test
    public void getInt_IntKey() { Assert.assertEquals(Integer.parseInt(bundle.getString("defaultInt")), Main.getInt("defaultInt")); }

    @Test
    public void getBundle() {
        Assert.assertNotNull(Main.getBundle());
        Assert.assertEquals(bundle.getBaseBundleName(), Main.getBundle().getBaseBundleName());
    }

    @Test
    public void getDBEditor() {
        Assert.assertNotNull(Main.getDBEditor());
    }

    @Test
    public void getDataFolder() {
        Assert.assertNotNull(Main.getDataFolder());
    }

    @Test
    public void getDefaultFolder() { Assert.assertNotNull(Main.getDefaultFolder()); }
}