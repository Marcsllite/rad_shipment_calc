package com.marcsllite.util.handler;

import com.marcsllite.util.factory.PropHandlerFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

public class FolderHandler {
    private static final Logger logr = LogManager.getLogger();
    private String appFolderPath;
    private String dataFolderPath;
    private static final String DATA_FOLDER_NAME = "Shipment Calculator";
    private PropHandler propHandler;

    public FolderHandler() throws IOException {
        this(null);
    }

    public FolderHandler(PropHandler propHandler) throws IOException {
        this.propHandler = propHandler == null? new PropHandlerFactory().getPropHandler(null) : propHandler;
        appFolderPath = this.propHandler.getString("appFolderName");
    }

    /**
     * Set the location of the default directory
     * 
     * @param appFolderName the name of the default directory
     */

    public void setAppFolderPath(String appFolderName) throws IOException {
        if (appFolderName == null || appFolderName.isBlank()) appFolderName = getPropHandler().getString("appFolderName");
        
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + appFolderName;
        var toBeCreated = createFolder(path);
        
        if (toBeCreated == null) {
            var e = new IOException("Failed to set up app folder");
            logr.throwing(Level.FATAL, e);
            throw e;
        }

        this.appFolderPath = path;
        setDataFolder();
    }

    /**
     * Creates the data folder (logging/db folder)
     */
    protected void setDataFolder() {
        String path = getAppFolderPath() + File.separator + DATA_FOLDER_NAME;
        var toBeCreated = createFolder(path);

        if (toBeCreated == null) {
            var e = new RuntimeException("Failed to set up data folder");
            logr.throwing(Level.FATAL, e);
            throw e;
        }

        this.dataFolderPath = path;
    }

    protected File createFolder(String path) {
        if(path == null) {
            return null;
        }

        var toBeCreated = new File(path);

        if (!toBeCreated.exists() && !toBeCreated.mkdirs()) {
            logr.debug("Failed to create {}", path);
            return null;
        } else {
            logr.info("{} was created", path);
            return toBeCreated;
        }
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/

    /**
     * Getter function to get the main folder where logging data resides
     *
     * @return data folder for further used by the Open logs Button
     */
    public String getDataFolderPath() {
        return dataFolderPath;
    }

    /**
     * Getter function to get the folder where this application saves its files
     *
     * @return the application's default folder
     */
    public String getAppFolderPath() {
        return appFolderPath;
    }

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        this.propHandler = propHandler;
    }
}
