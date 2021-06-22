package com.marcsllite;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AppTest extends ApplicationTest {
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/primary.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }
  
    @Test
    @Parameters(method = "setPropException_data")
    public void setPropExceptionChecker(String path) {
        InvalidParameterException exception = Assert.assertThrows(
            InvalidParameterException.class, () -> App.setProp(path)
        );
        
        Assert.assertTrue(exception.getMessage().contains("Failed to set properties from path: " + path));
    }
  
    private Object[] setPropException_data() {
        return new Object[] { 
            new Object[] { null },
            new Object[] { "" },
            new Object[] { "invalid/path" }
        };
    }
  
    @Test
    public void getOs_NullOS() {
        String expected = System.getProperty("os.name").toLowerCase();
        App.setOs(null);
        Assert.assertEquals(expected, App.getOs());
    }
  
    @Test
    @Parameters({
        "win, windows",
        "mac, mac",
        "nix, unix",
        "nux, unix",
        "aix, unix",
        "sunos, solaris",
        "none, noSupport"
    })
    public void getCurrentOSChecker(String osName, String expected) {
        App.setOs(osName);
        Assert.assertEquals(App.getString(expected), App.getCurrentOS());
    }
  
    @Test
    public void parseStringToReplace() {
        Assert.assertEquals(new ArrayList<String>(), 
        App.parseStringsToReplace(null));
    }
  
    @Test
    @Parameters(method = "replacePropString_data")
    public void replacePropStringChecker(String key, String expected, String[] replacement) {
        if (replacement != null) {
            switch (replacement.length) {
                case 1:
                    Assert.assertEquals(expected, App.replacePropString(key, replacement[0]));
                    break;
                case 2:
                    Assert.assertEquals(expected, App.replacePropString(key, replacement[0], replacement[1]));
                    break;
                case 3:
                    Assert.assertEquals(expected, App.replacePropString(key, replacement[0], replacement[1], replacement[2]));
                    break;
                case 4:
                    Assert.assertEquals(expected, App.replacePropString(key, replacement[0], replacement[1], replacement[2], replacement[3]));
                    break;
            } 
        } else {
            Assert.assertEquals(expected, App.replacePropString(key));
            Assert.assertEquals(expected, App.replacePropString(key, (String[]) null));
        } 
    }
  
    private Object[] replacePropString_data() {
        return new Object[] { 
            new Object[] { null, "", new String[] { null } },
            new Object[] { "", "", new String[] { "" } },
            new Object[] { "", "", new String[] { null } },
            new Object[] { "fakeKey", "", new String[] { null } },
            new Object[] { "fakeKey", "", null },
            new Object[] { "fakeKey", "", new String[] { "ONE" } },
            new Object[] { "replacePropString_noText", "", new String[] { null } },
            new Object[] { "replacePropString_noText", "", null },
            new Object[] { "replacePropString_noText", "", new String[] { "ONE" } },
            new Object[] { "replacePropString_noReplacements", App.getString("replacePropString_noReplacements"), new String[] { null } },
            new Object[] { "replacePropString_noReplacements", App.getString("replacePropString_noReplacements"), null },
            new Object[] { "replacePropString_noReplacements", App.getString("replacePropString_noReplacements"), new String[] { "ONE" } },
            new Object[] { "replacePropString_oneReplacement", App.getString("replacePropString_oneReplacement"), new String[] { null } },
            new Object[] { "replacePropString_oneReplacement", App.getString("replacePropString_oneReplacement"), null },
            new Object[] { "replacePropString_oneReplacement", "This string contains ONE replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_oneReplacement", "This string contains ONE replacements", new String[] { "ONE", "TWO" } },
            new Object[] { "replacePropString_wrongNumber", App.getString("replacePropString_wrongNumber"), new String[] { null } },
            new Object[] { "replacePropString_wrongNumber", App.getString("replacePropString_wrongNumber"), null },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect ONE for replacement", new String[] { "ONE" } },
            new Object[] { "replacePropString_wrongNumber", "This string contains the incorrect ONE for replacement", new String[] { "ONE", "TWO" } }, 
            new Object[] { "replacePropString_oneSameReplacements", "This string contains a replacement here: ONE, and the same replacement here: ONE", new String[] { "ONE" } },
            new Object[] { "replacePropString_twoReplacements", App.getString("replacePropString_twoReplacements"), new String[] { null } },
            new Object[] { "replacePropString_twoReplacements", App.getString("replacePropString_twoReplacements"), null },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, {1} replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, TWO replacements", new String[] { "ONE", "TWO" } },
            new Object[] { "replacePropString_twoReplacements", "This string contains ONE, TWO replacements", new String[] { "ONE", "TWO", "THREE" } },
            new Object[] { "replacePropString_threeReplacements", App.getString("replacePropString_threeReplacements"), new String[] { null } },
            new Object[] { "replacePropString_threeReplacements", App.getString("replacePropString_threeReplacements"), null },
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, {1}, {2} replacements", new String[] { "ONE" } },
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, TWO, {2} replacements", new String[] { "ONE", "TWO" } }, 
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, TWO, THREE replacements", new String[] { "ONE", "TWO", "THREE" } },
            new Object[] { "replacePropString_threeReplacements", "This string contains ONE, TWO, THREE replacements", new String[] { "ONE", "TWO", "THREE", "FOUR" } },
            new Object[] { "replacePropString_twoSameReplacements", "First: ONE, Second: TWO, Third: ONE, Fourth: TWO", new String[] { "ONE", "TWO" } }
        };
    }
  
    @Test
    @Parameters(method = "getString_data")
    public void getStringChecker(String propName, String expected) {
        Assert.assertEquals(expected, App.getString(propName));
    }
  
    private Object[] getString_data() {
        return new Object[] { 
            new Object[] { null, "" },
            new Object[] { "", "" },
            new Object[] { "fakeKey", "" },
            new Object[] { "replacePropString_noText", "" },
            new Object[] { "mainPane", "UMass Lowell - Rad Shipment Calculator" }
        };
    }

    @Test
    @Parameters(method = "getList_data")
    public void getListChecker(String listName, List<String> expected) {
        Assert.assertEquals(expected, App.getList(listName));
    }

    private Object[] getList_data() {
        return new Object[] { 
            new Object[] { null, new ArrayList<String>()},
            new Object[] { "", new ArrayList<String>()},
            new Object[] { "fakeKey", new ArrayList<String>()},
            new Object[] { "replacePropString_noText", new ArrayList<String>()},
            new Object[] { "mainPane", new ArrayList<String>() {{
                                            add("UMass Lowell - Rad Shipment Calculator"); 
                                        }}},
            new Object[] { "getList_TrailingDelimiters", new ArrayList<String>() {{
                add("This");
                add("List"); 
                add("has"); 
                add("trailing"); 
                add("delimiters");
            }}},
            new Object[] { "getList_ProperList", new ArrayList<String>() {{
                add("This");
                add("is"); 
                add("a"); 
                add("proper"); 
                add("list");
            }}},
            new Object[] { "getList_SpacesWithinElements", new ArrayList<String>() {{
                add("element 1 has spaces");
                add("element 2 does too");
            }}},
            new Object[] { "getList_SpacesAroundElements", new ArrayList<String>() {{
                add("there are spaces around this element");
                add("spaces around here too");
            }}}
        };
    }
  
    @Test
    @Parameters({"fakeKey", "replacePropString_noReplacements"})
    public void getIntExceptionChecker(String propName) {
        InvalidParameterException exception = Assert.assertThrows(
            InvalidParameterException.class, () -> App.getInt(propName)
        );
        Assert.assertTrue(exception.getMessage().contains("Value is not a number"));
    }
  
    @Test
    @Parameters(method = "getInt_data")
    public void getIntChecker(String propName, int expected) {
        Assert.assertEquals(expected, App.getInt(propName));
    }
  
    private Object[] getInt_data() {
        return new Object[] { 
            new Object[] { null, Integer.MIN_VALUE },
            new Object[] { "", Integer.MIN_VALUE },
            new Object[] { "mainWidth", 600 }
        };
    }
  
    @Test
    @Parameters(method = "getFileText_data")
    public void getFileTextChecker(String file, String expected) {
        Assert.assertEquals(expected, App.getFileText(file));
    }
  
    private Object[] getFileText_data() {
        return new Object[] {
            new Object[] { null, "" },
            new Object[] { "", "" },
            new Object[] { "invalid/path", "" },
            new Object[] { "/text/test.txt", "This File is For Testing A Main Function\n" }
        };
    }
  
    private boolean deleteDirectory(File directoryToBeDeleted) throws IOException {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null)
            for (File file : allContents)
            deleteDirectory(file);  
        return directoryToBeDeleted.delete();
    }
  
    @Test
    public void setUpDefaultDirectory_MakeNewFolder() throws IOException {
        String name = App.getString("appMainFolder");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File directoryToBeDeleted = new File(path);
        
        Assert.assertTrue(deleteDirectory(directoryToBeDeleted));
        App.setDefaultDir(name);
        Assert.assertEquals(path, App.getDefaultDir());
    }
  
    @Test
    public void setUpDefaultDirectory_FolderAlreadyExists() throws IOException {
        String name = App.getString("appMainFolder");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File toBeCreated = new File(path);

        Assert.assertTrue(toBeCreated.mkdirs() || toBeCreated.exists());
        App.setDefaultDir(name);
        Assert.assertEquals(path, App.getDefaultDir());
    }
  
    @Test
    @Parameters(method = "setDefaultDir_data")
    public void setDefaultDirChecker(String dirName, String expected) {
        App.setDefaultDir(dirName);
        Assert.assertEquals(expected, App.getDefaultDir());
    }
  
    private Object[] setDefaultDir_data() {
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + App.getString("appMainFolder");
        return new Object[] {
            new Object[] { null, path },
            new Object[] { "", path }
        };
    }
  
    @Test
    @Parameters(method = "setUpDataFolder_data")
    public void setUpDataFolderChecker(String osVersion, String expected) {
        App.setDataFolder(App.getString(osVersion));
        Assert.assertEquals(expected, App.getDataFolder());
    }

    private Object[] setUpDataFolder_data() {
        String winExp = System.getProperty("user.home") + File.separator +
                            "AppData" + File.separator +
                            "Local" + File.separator +
                            App.getString("appFolderName") + File.separator +
                            "logs";
        String otherExp = System.getProperty("user.home") + File.separator +
                            App.getString("appFolderName") + File.separator +
                            "logs";
        return new Object[] {
            new Object[] { "windows", winExp },
            new Object[] { "mac", otherExp },
            new Object[] { "unix", otherExp },
            new Object[] { "solaris", otherExp },
            new Object[] { null, null },
            new Object[] { "", null }
        };
    }
}
