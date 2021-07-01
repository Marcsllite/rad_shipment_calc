package com.marcsllite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import com.marcsllite.util.Util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AppTest extends ApplicationTest {
    App app = new App();

    public void start(Stage stage) throws Exception { app.start(stage); }
  
    private boolean deleteDirectory(File directoryToBeDeleted) throws IOException {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null)
            for (File file : allContents)
            deleteDirectory(file);  
        return directoryToBeDeleted.delete();
    }
  
    @Test
    public void setDefaultDir_InvalidName() throws IOException {
        String name ="?.\"*.*.?";

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> App.setDefaultDir(name)
        );
        
        assertTrue(exception.getMessage().contains("Failed to set up default directory"));
    }

    @Test
    public void setDefaultDir_MakeNewFolder() throws IOException {
        String name ="Default Dir";

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File directoryToBeDeleted = new File(path);
        
        assertTrue(deleteDirectory(directoryToBeDeleted));
        App.setDefaultDir(name);
        assertEquals(path, App.getDefaultDir());
    }
  
    @Test
    public void setDefaultDir_FolderAlreadyExists() throws IOException {
        String name ="Default Dir";

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File toBeCreated = new File(path);

        assertTrue(toBeCreated.mkdirs() || toBeCreated.exists());
        App.setDefaultDir(name);
        assertEquals(path, App.getDefaultDir());
    }

    @Test
    @Parameters(method = "setDefaultDirException_data")
    public void setDefaultDirExceptionChecker(String dirName, String expected) {
        App.setDefaultDir(dirName);
        assertEquals(expected, App.getDefaultDir());
    }
  
    private Object[] setDefaultDirException_data() {
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + Util.getString("appMainFolder");
        return new Object[] {
            new Object[] { null, path },
            new Object[] { "", path }
        };
    }

    String folderName = Util.getString("appFolderName");
  
    @Test
    @Parameters(method = "setDataFolderException_data")
    public void setDataFolderExceptionChecker(String osVersion, String expected) {
        App.setDataFolder(osVersion);
        assertEquals(expected, App.getDataFolder());
    }

    private Object[] setDataFolderException_data() {
        return new Object[] {
            new Object[] { null, null },
            new Object[] { "", null }
        };
    }

    @Test
    @Parameters(method = "setDataFolder_data")
    public void setDataFolderChecker(String osVersion, String expected) {
        assumeNotNull(folderName);
        assumeFalse(folderName.isBlank());

        App.setDataFolder(osVersion);
        assertEquals(expected, App.getDataFolder());
    }

    private Object[] setDataFolder_data() {
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
            new Object[] { "Solaris", otherExp }
        };
    }
}
