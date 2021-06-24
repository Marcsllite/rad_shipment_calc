package com.marcsllite;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import com.marcsllite.util.Util;

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
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File directoryToBeDeleted = new File(path);
        
        Assert.assertTrue(deleteDirectory(directoryToBeDeleted));
        App.setDefaultDir(name);
        Assert.assertEquals(path, App.getDefaultDir());
    }
  
    @Test
    public void setDefaultDir_FolderAlreadyExists() throws IOException {
        String name = Util.getString("appMainFolder");
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
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + Util.getString("appMainFolder");
        return new Object[] {
            new Object[] { null, path },
            new Object[] { "", path }
        };
    }
  
    @Test
    @Parameters(method = "setUpDataFolder_data")
    public void setUpDataFolderChecker(String osVersion, String expected) {
        App.setDataFolder(Util.getString(osVersion));
        Assert.assertEquals(expected, App.getDataFolder());
    }

    private Object[] setUpDataFolder_data() {
        String winExp = System.getProperty("user.home") + File.separator +
                            "AppData" + File.separator +
                            "Local" + File.separator +
                            Util.getString("appFolderName") + File.separator +
                            "logs";
        String otherExp = System.getProperty("user.home") + File.separator +
                            Util.getString("appFolderName") + File.separator +
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
