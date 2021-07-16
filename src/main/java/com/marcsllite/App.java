package com.marcsllite;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.StageManager;
import com.marcsllite.util.Util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    private static final Logger logr = LogManager.getLogger();
    private static String dataFolder;
    private static String defaultDir;
    protected static StageManager stageManager;

    static {
        setDataFolder(Util.getCurrentOS());
        setDefaultDir(Util.getString("appMainFolder"));
    }

    /**
     * Main function to run application
     */
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        setStageManager(stage);
        stageManager.switchScene(FXMLView.PRIMARY);
    }

    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/

    /**
     * Create a new StageManager with root at given stage
     * 
     * @param stage the root stage
     */
    static void setStageManager(Stage stage) {
        stageManager = new StageManager(stage);
    }

    /**
     * Set the location of the default directory
     * 
     * @param dirName the name of the default directory
     */
    @SuppressWarnings("java:S112")
    public static void setDefaultDir(String dirName) throws RuntimeException {
        if (dirName == null || dirName.isEmpty()) dirName = Util.getString("appMainFolder"); 
        
        String name = String.valueOf(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()) + File.separator + dirName;
        var toBeCreated = new File(name);
        
        if (!toBeCreated.exists() && !toBeCreated.mkdirs()) {
            var e = new RuntimeException("Failed to set up default directory");
            logr.throwing(Level.FATAL, e);
            throw e;
        } else {
            logr.info("%s directory was created", name);
            defaultDir = name;
        }
    }

    /**
     * Set the location of the data folder (logging folder)
     * 
     * Windows: C:/Users/[username]/AppData/Local/[appFolderName]/logs
     * MacOS/Unix/Solaris: ~/[appFolderName]/logs
     * 
     * @param currentOS the operating system of the computer
     */
    protected static void setDataFolder(String currentOS) {
        String dirLoc = null;

        if(currentOS == null ||  currentOS.isBlank()) {
            dataFolder = null;
            return;
        } else if(Util.getString("windows").equals(currentOS)) {
            dirLoc = System.getProperty("user.home") + File.separator +
                    "AppData" + File.separator +
                    "Local" + File.separator +
                    Util.getString("appFolderName") + File.separator +
                    "logs";
        } else if(Util.getString("mac").equals(currentOS) ||
                    Util.getString("unix").equals(currentOS) ||
                    Util.getString("solaris").equals(currentOS)) {
            dirLoc = System.getProperty("user.home") + File.separator +
                    Util.getString("appFolderName") + File.separator +
                    "logs";
        }

        if(dirLoc == null) {
            logr.warn("Data Folder was not created");
        } else if((new File(dirLoc)).mkdirs()) {
            logr.info("%s directory was created", dirLoc);
        }

        dataFolder = dirLoc;
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/
    
    /**
     * Getter function to get the StageManager
     *
     * @return the StageManager
     */
    public static StageManager getStageManager() { return stageManager; }

    /**
     * Getter function to get the main folder where logging data resides
     *
     * @return data folder for further used by the Open logs Button
     */
    static String getDataFolder() { 
        return dataFolder; 
    }

    /**
     * Getter function to get the folder where this application saves its files
     *
     * @return the application's default folder
     */
    public static String getDefaultDir() {
        return defaultDir;
    }
}