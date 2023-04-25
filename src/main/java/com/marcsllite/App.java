package com.marcsllite;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.OS;
import com.marcsllite.util.PropManager;
import com.marcsllite.util.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final Logger logr = LogManager.getLogger();
    private String dataFolder;
    private String defaultDir;
    private StageManager stageManager;
    private PropManager propManager = PropManager.getInstance();

    public App() {
        setDataFolder(propManager.parseOS(propManager.getOS()));
        setDefaultDir(propManager.getString("appMainFolder"));
    }

    /**
     * Main function to run application
     */
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        setStageManager(stage);
        stageManager.show(FXMLView.MAIN);
    }

    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/

    /**
     * Create a new StageManager with root at given stage
     * 
     * @param stage the root stage
     */
    private void setStageManager(Stage stage) {
        stageManager = new StageManager(stage);
    }

    /**
     * Set the location of the default directory
     * 
     * @param dirName the name of the default directory
     */
    @SuppressWarnings("java:S112")
    public void setDefaultDir(String dirName) throws RuntimeException {
        if (dirName == null || dirName.isEmpty()) dirName = propManager.getString("appMainFolder"); 
        
        String name = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + dirName;
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
    protected void setDataFolder(OS currentOS) {
        String dirLoc = null;

        switch(currentOS){
            case Windows:
                dirLoc = System.getProperty("user.home") + File.separator +
                        "AppData" + File.separator +
                        "Local" + File.separator +
                        propManager.getString("appFolderName") + File.separator +
                        "logs";
                break;
            case MAC: case Unix: case Solaris:
                dirLoc = System.getProperty("user.home") + File.separator +
                        propManager.getString("appFolderName") + File.separator +
                        "logs";
                break;
            default:
                logr.warn("Data Folder was not created");
        }

        if(dirLoc != null && (new File(dirLoc)).mkdirs()) {
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
    public StageManager getStageManager() { return stageManager; }

    /**
     * Getter function to get the main folder where logging data resides
     *
     * @return data folder for further used by the Open logs Button
     */
    protected String getDataFolder() { 
        return dataFolder; 
    }

    /**
     * Getter function to get the folder where this application saves its files
     *
     * @return the application's default folder
     */
    public String getDefaultDir() {
        return defaultDir;
    }
}