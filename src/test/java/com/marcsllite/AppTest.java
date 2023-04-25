package com.marcsllite;

import org.junit.jupiter.api.BeforeAll;
// import com.marcsllite.util.FXMLView;
// import javafx.application.Platform;
// import javafx.stage.Stage;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.api.FxToolkit;
// import org.testfx.framework.junit5.ApplicationExtension;
// import org.testfx.util.WaitForAsyncUtils;
import org.testfx.framework.junit5.Start;

import com.marcsllite.util.OS;

import javafx.application.Platform;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class AppTest {
    // @Nested
    // @DisplayName("App Class UI Tests")
    // public class AppTestUI extends GUITest {
    //     App app;

    //     @Override
    //     @Start
    //     public void start(Stage stage) {
    //         app = new App();
    //         app.start(stage);
    //     }

    //     @Test
    //     public void testStart() {
    
    //     }
    // }
    final static String appFolder = "UMass Lowell Radiation Safety";
    final static String mainFolder = "Shipment Calculator";
    static App app;

    @BeforeAll
    public static void setup() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        app = (App) FxToolkit.setupApplication(App.class);
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    @Test
    public void testSetDefaultDir_InvalidName() {
        String os = System.getProperty("os.name").toLowerCase();

        // linux and Mac have no bad names
        assumeTrue(os.contains("win"));

        String name ="?.\"*.*.?";

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> app.setDefaultDir(name)
        );
        
        assertTrue(exception.getMessage().contains("Failed to set up default directory"));
    }

    @Test
   public void testSetDefaultDir_MakeNewFolder() {
        String name ="Default Dir";

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File dir = new File(path);
        
        if(dir.exists()) {
            assumeTrue(deleteDirectory(dir));
        }

        app.setDefaultDir(name);
        assertEquals(path, app.getDefaultDir());
    }
  
    @Test
   public void testSetDefaultDir_FolderAlreadyExists() {
        String name ="Default Dir";

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File toBeCreated = new File(path);

        assumeTrue(toBeCreated.mkdirs() || toBeCreated.exists());
        app.setDefaultDir(name);
        assertEquals(path, app.getDefaultDir());
    }

    @ParameterizedTest
    @MethodSource("testSetDefaultDirException_data")
   public void testSetDefaultDirExceptionChecker(String dirName, String expected) {
        app.setDefaultDir(dirName);
        assertEquals(expected, app.getDefaultDir());
    }
  
    private static Object[] testSetDefaultDirException_data() {
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + mainFolder;
        return new Object[] {
            new Object[] { null, path },
            new Object[] { "", path }
        };
    }

    @Test
    public void setDataFolderExceptionChecker() {
        assertThrows(
            NullPointerException.class, () -> app.setDataFolder(null)
        );
    }

     @ParameterizedTest
     @MethodSource("setDataFolder_data")
     public void setDataFolderChecker(String osVersion, String expected) {
        app.setDataFolder(OS.valueOf(osVersion));
        assertEquals(expected, app.getDataFolder());
     }

    private static Object[] setDataFolder_data() {
        String winExp = System.getProperty("user.home") + File.separator +
                            "AppData" + File.separator +
                            "Local" + File.separator +
                            appFolder + File.separator +
                            "logs";
        String otherExp = System.getProperty("user.home") + File.separator +
                            appFolder + File.separator +
                            "logs";
        return new Object[] {
            new Object[] { OS.Windows.name(), winExp },
            new Object[] { OS.MAC.name(), otherExp },
            new Object[] { OS.Unix.name(), otherExp },
            new Object[] { OS.Solaris.name(), otherExp },
            new Object[] { OS.NOT_SUPPORTED.name(), null}
        };
    }
}
