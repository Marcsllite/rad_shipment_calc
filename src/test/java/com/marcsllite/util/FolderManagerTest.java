package com.marcsllite.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class FolderManagerTest {
    FolderManager folderManager;
    PropManager propManager;
    final static String appFolder = "UMass Lowell Radiation Safety";
    final static String mainFolder = "Shipment Calculator";

    @BeforeEach
    public void setup() {
        propManager = PropManager.getInstance();
        folderManager = new FolderManager(propManager);
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
            RuntimeException.class, () -> folderManager.setDefaultDir(name)
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

        folderManager.setDefaultDir(name);
        assertEquals(path, folderManager.getDefaultDir());
    }
  
    @Test
    public void testSetDefaultDir_FolderAlreadyExists() {
        String name ="Default Dir";

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File toBeCreated = new File(path);

        assumeTrue(toBeCreated.mkdirs() || toBeCreated.exists());
        folderManager.setDefaultDir(name);
        assertEquals(path, folderManager.getDefaultDir());
    }

    @ParameterizedTest
    @MethodSource("testSetDefaultDirException_data")
    public void testSetDefaultDirExceptionChecker(String dirName, String expected) {
        folderManager.setDefaultDir(dirName);
        assertEquals(expected, folderManager.getDefaultDir());
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
            NullPointerException.class, () -> folderManager.setDataFolder(appFolder, null)
        );
    }

    @Test
    public void setDataFolder_InvalidName() {
        String expected = System.getProperty("user.home") + File.separator +
                            appFolder + File.separator +
                            "logs";
        folderManager.setDataFolder(null, OS.Unix);
        assertEquals(expected, folderManager.getDataFolder());

        folderManager.setDataFolder("", OS.Unix);
        assertEquals(expected, folderManager.getDataFolder());
    }

     @ParameterizedTest
     @MethodSource("setDataFolder_data")
     public void setDataFolderChecker(String name, String osVersion, String expected) {
        folderManager.setDataFolder(name, OS.valueOf(osVersion));
        assertEquals(expected, folderManager.getDataFolder());
     }

    private static Object[] setDataFolder_data() {
        String name = "FolderName";
        String winExp = System.getProperty("user.home") + File.separator +
                            "AppData" + File.separator +
                            "Local" + File.separator +
                            name + File.separator +
                            "logs";
        String otherExp = System.getProperty("user.home") + File.separator +
                            name + File.separator +
                            "logs";
        return new Object[] {
            new Object[] { name, OS.Windows.name(), winExp },
            new Object[] { name, OS.MAC.name(), otherExp },
            new Object[] { name, OS.Unix.name(), otherExp },
            new Object[] { name, OS.Solaris.name(), otherExp },
            new Object[] { name, OS.NOT_SUPPORTED.name(), null}
        };
    }
}
