package com.marcsllite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.swing.filechooser.FileSystemView;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.Util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.stage.Stage;

class AppTest {
    @BeforeAll
        public static void init() {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }

    @Nested
    @DisplayName("App Class UI Tests")
    @ExtendWith(ApplicationExtension.class)
    class AppTestUI {
        Stage stage;
        App app;

        @Start
        public void start(Stage stage) throws Exception {
            this.stage = stage;
        }

        @BeforeEach
        public void setUp() {
            app = new App();
        }

        @AfterEach
        public void tearDown() throws TimeoutException {
            FxToolkit.hideStage();
            app = null;
        }

        @Test
        void testStart() {
            Platform.runLater(() -> {
                app.start(stage);
                WaitForAsyncUtils.waitForFxEvents();
                assertEquals(FXMLView.MAIN, app.getStageManager().getCurrentView());
            });
        }
    }

    private boolean deleteDirectory(File directoryToBeDeleted) throws IOException {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null)
            for (File file : allContents)
            deleteDirectory(file);  
        return directoryToBeDeleted.delete();
    }
  
    @Test
    void testSetDefaultDir_InvalidName() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();

        // linux and Mac have no bad names
        assumeTrue(os.contains("win"));

        String name ="?.\"*.*.?";

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> App.setDefaultDir(name)
        );
        
        assertTrue(exception.getMessage().contains("Failed to set up default directory"));
    }

    @Test
    void testSetDefaultDir_MakeNewFolder() throws IOException {
        String name ="Default Dir";

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File directoryToBeDeleted = new File(path);
        
        assumeTrue(deleteDirectory(directoryToBeDeleted));
        App.setDefaultDir(name);
        assertEquals(path, App.getDefaultDir());
    }
  
    @Test
    void testSetDefaultDir_FolderAlreadyExists() throws IOException {
        String name ="Default Dir";

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File toBeCreated = new File(path);

        assumeTrue(toBeCreated.mkdirs() || toBeCreated.exists());
        App.setDefaultDir(name);
        assertEquals(path, App.getDefaultDir());
    }

    @ParameterizedTest
    @MethodSource("testSetDefaultDirException_data")
    void testSetDefaultDirExceptionChecker(String dirName, String expected) {
        String name = Util.getString("appMainFolder");

        assumeFalse(name == null);
        assumeFalse(name.isBlank());

        App.setDefaultDir(dirName);
        assertEquals(expected, App.getDefaultDir());
    }
  
    private static Object[] testSetDefaultDirException_data() {
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + Util.getString("appMainFolder");
        return new Object[] {
            new Object[] { null, path },
            new Object[] { "", path }
        };
    }

    @ParameterizedTest
    @MethodSource("setDataFolderException_data")
    void setDataFolderExceptionChecker(String osVersion, String expected) {
        App.setDataFolder(osVersion);
        assertEquals(expected, App.getDataFolder());
    }

    private static Object[] setDataFolderException_data() {
        return new Object[] {
            new Object[] { null, null },
            new Object[] { "", null }
        };
    }

    @ParameterizedTest
    @MethodSource("setDataFolder_data")
    void setDataFolderChecker(String osVersion, String expected) {
        App.setDataFolder(osVersion);
        assertEquals(expected, App.getDataFolder());
    }

    private static Object[] setDataFolder_data() {
        String folderName = Util.getString("appFolderName");
        String winExp = System.getProperty("user.home") + File.separator +
                            "AppData" + File.separator +
                            "Local" + File.separator +
                            folderName + File.separator +
                            "logs";
        String otherExp = System.getProperty("user.home") + File.separator +
                            folderName + File.separator +
                            "logs";
        return new Object[] {
            new Object[] { "Windows", winExp },
            new Object[] { "MAC", otherExp },
            new Object[] { "Unix", otherExp },
            new Object[] { "Solaris", otherExp },
            new Object[] { "Not Supported", null}
        };
    }
}
