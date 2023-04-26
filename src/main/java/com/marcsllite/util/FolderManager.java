package com.marcsllite.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class FolderManager {
    private static final Logger logr = LogManager.getLogger();
    private String dataFolder;
    private String defaultDir;
    private PropManager propManager = PropManager.getInstance();

    public FolderManager(){
        setDataFolder(propManager.parseOS(propManager.getOS()));
        setDefaultDir(propManager.getString("appMainFolder"));
    }

    public FolderManager(PropManager propManager) {
        this.propManager = propManager;
        setDataFolder(propManager.parseOS(propManager.getOS()));
        setDefaultDir(propManager.getString("appMainFolder"));
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
