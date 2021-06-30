package com.marcsllite;

import static org.junit.Assert.assertEquals;
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
    public void start(Stage stage) throws Exception { }
  
    private boolean deleteDirectory(File directoryToBeDeleted) throws IOException {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null)
            for (File file : allContents)
            deleteDirectory(file);  
        return directoryToBeDeleted.delete();
    }
  
    @Test
    public void setDefaultDir_MakeNewFolder() throws IOException {
        String name = Util.getString("appMainFolder");

        assumeNotNull(name);
        assumeFalse(name.isBlank());

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File directoryToBeDeleted = new File(path);
        
        assertTrue(deleteDirectory(directoryToBeDeleted));
        App.setDefaultDir(name);
        assertEquals(path, App.getDefaultDir());
    }
  
    @Test
    public void setDefaultDir_FolderAlreadyExists() throws IOException {
        String name = Util.getString("appMainFolder");

        assumeNotNull(name);
        assumeFalse(name.isBlank());

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
        String name = Util.getString("appMainFolder");

        assumeNotNull(name);
        assumeFalse(name.isBlank());

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        return new Object[] {
            new Object[] { null, path },
            new Object[] { "", path }
        };
    }
  
    @Test
    @Parameters(method = "setDataFolder_data")
    public void setDataFolderChecker(String osVersion, String expected) {
        String os = Util.getString(osVersion);
        
        if(osVersion != null && !osVersion.isBlank()) {
            assumeNotNull(os);
            assumeFalse(os.isBlank());
        }

        App.setDataFolder(os);
        assertEquals(expected, App.getDataFolder());
    }

    private Object[] setDataFolder_data() {
        String name = Util.getString("appFolderName");

        assumeNotNull(name);
        assumeFalse(name.isBlank());

        String winExp = System.getProperty("user.home") + File.separator +
                            "AppData" + File.separator +
                            "Local" + File.separator +
                            name + File.separator +
                            "logs";
        String otherExp = System.getProperty("user.home") + File.separator +
                            name + File.separator +
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
