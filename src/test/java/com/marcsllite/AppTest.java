package com.marcsllite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import com.marcsllite.util.Util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AppTest {
    final static String folderName = Util.getString("appFolderName");
    
    private boolean deleteDirectory(File directoryToBeDeleted) throws IOException {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null)
            for (File file : allContents)
            deleteDirectory(file);  
        return directoryToBeDeleted.delete();
    }
  
    @Test
    public void setDefaultDir_InvalidName() throws IOException {
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

    @ParameterizedTest
    @MethodSource("setDefaultDirException_data")
    public void setDefaultDirExceptionChecker(String dirName, String expected) {
        String name = Util.getString("appMainFolder");

        assumeFalse(name == null);
        assumeFalse(name.isBlank());

        App.setDefaultDir(dirName);
        assertEquals(expected, App.getDefaultDir());
    }
  
    private static Object[] setDefaultDirException_data() {
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + Util.getString("appMainFolder");
        return new Object[] {
            new Object[] { null, path },
            new Object[] { "", path }
        };
    }

    @ParameterizedTest
    @MethodSource("setDataFolderException_data")
    public void setDataFolderExceptionChecker(String osVersion, String expected) {
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
    public void setDataFolderChecker(String osVersion, String expected) {
        assumeFalse(folderName == null);
        assumeFalse(folderName.isBlank());

        App.setDataFolder(osVersion);
        assertEquals(expected, App.getDataFolder());
    }

    private static Object[] setDataFolder_data() {
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
