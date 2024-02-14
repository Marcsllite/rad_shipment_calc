package com.marcsllite.util.handler;

import com.marcsllite.util.OS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class FolderManagerTest {
    FolderHandler folderHandler;
    PropHandler propHandler =  new PropHandler() {
        @Override
        protected Object handleGetObject(String key) {
            switch (key) {
                case "appFolderName":
                    return appFolder;
                case "appMainFolder":
                    return mainFolder;
                default:
                    return "";
            }
        }

        @Override
        protected Set<String> handleKeySet() {
            return Set.of("appFolderName", "appMainFolder");
        }
    };
    final static String appFolder = "UMass Lowell Radiation Safety";
    final static String mainFolder = "Shipment Calculator";

    @BeforeEach
    public void setup() {
        folderHandler = new FolderHandler(propHandler);
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
    @EnabledOnOs(org.junit.jupiter.api.condition.OS.WINDOWS)
    public void testSetDefaultDir_InvalidName() {
        String os = System.getProperty("os.name").toLowerCase();

        String name ="?.\"*.*.?";

        RuntimeException exception = assertThrows(
            RuntimeException.class, () -> folderHandler.setDefaultDir(name)
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

        folderHandler.setDefaultDir(name);
        assertEquals(path, folderHandler.getDefaultDir());
    }
  
    @Test
    public void testSetDefaultDir_FolderAlreadyExists() {
        String name ="Default Dir";

        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + name;
        File toBeCreated = new File(path);

        assumeTrue(toBeCreated.mkdirs() || toBeCreated.exists());
        folderHandler.setDefaultDir(name);
        assertEquals(path, folderHandler.getDefaultDir());
    }

    @ParameterizedTest
    @MethodSource("setDefaultDirException_data")
    public void testSetDefaultDir_Exceptions(String dirName, String expected) {
        folderHandler.setDefaultDir(dirName);
        assertEquals(expected, folderHandler.getDefaultDir());
    }
  
    private static Object[] setDefaultDirException_data() {
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + mainFolder;
        return new Object[] {
            new Object[] { null, path },
            new Object[] { "", path }
        };
    }

    @Test
    public void testSetDataFolder_Exceptions() {
        assertThrows(
            NullPointerException.class, () -> folderHandler.setDataFolder(appFolder, null)
        );
    }

    @Test
    public void testSetDataFolder_InvalidName() {
        String expected = System.getProperty("user.home") + File.separator +
                            appFolder + File.separator +
                            "logs";
        folderHandler.setDataFolder(null, OS.Unix);
        assertEquals(expected, folderHandler.getDataFolder());

        folderHandler.setDataFolder("", OS.Unix);
        assertEquals(expected, folderHandler.getDataFolder());
    }

     @ParameterizedTest
     @MethodSource("setDataFolder_data")
     public void testSetDataFolder(String name, String osVersion, String expected) {
        folderHandler.setDataFolder(name, OS.valueOf(osVersion));
        assertEquals(expected, folderHandler.getDataFolder());
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
