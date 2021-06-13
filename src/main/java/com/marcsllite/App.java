package com.marcsllite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileSystemView;

import com.marcsllite.util.FXMLView;
import com.marcsllite.util.StageManager;

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
    private static String os;
    private static Properties prop;

    public App() {
        setDataFolder(getCurrentOS());
        setDefaultDir(getString("appMainFolder"));
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

    /*///////////////////////////////////////////////// CONVENIENCE //////////////////////////////////////////////////*/

    /**
     * @author Mkyong.com https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
     * Convenience function to figure out the current operating system
     *
     * @return the current operating system
     */
    public static String getCurrentOS(){
        String os = getOs();

        if (os.contains("win")) return getString("windows");
        else if (os.contains("mac")) return getString("mac");
        else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) return getString("unix");
        else if (os.contains("sunos")) return getString("solaris");
        else return getString("noSupport");
    }

    /**
     * @author Alvin Alexander https://alvinalexander.com/blog/post/java/read-text-file-from-jar-file
     * Convenience function to open the given file and return its
     * contents to a string
     *
     * @param resourceFilePath the relative path of the resource file to get the text of
     * @return the contents of the file or teh empty string if errors occurred
     */
    public static String getFileText(String resourceFilePath) throws InvalidParameterException {
        try {
            if (resourceFilePath == null || resourceFilePath.isBlank()) {
                var e = new InvalidParameterException("resourceFilepath (" + resourceFilePath + ") is invalid");
                logr.throwing(e);
                throw e;
            } 

            InputStream is = App.class.getResourceAsStream(resourceFilePath);  // getResourceAsStream() may throw NullPointerException
            var isr = new InputStreamReader(is);
            var br = new BufferedReader(isr);
            var sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) { sb.append(line).append("\n"); } // readLine() may throw IOException
            br.close();  // close() may throw IOException
            isr.close();  // close() may throw IOException
            is.close();  // close() may throw IOException
            return sb.toString();
        } catch (IOException | NullPointerException | InvalidParameterException e) {
            logr.atWarn().withThrowable(e).log("Failed to read file (%s) and turn to String.", resourceFilePath);
        }
        return "";
    }

    /**
     * Convenience function to replace any {0}, {1}, {3}, .. strings
     * in the property file with given parameter values
     *
     * @param propKey the name of the key from the property file
     * @param newValues the newValues to change the braced values in the string
     */
    public static String replacePropString(String propKey, String... newValues) throws InvalidParameterException {
        String propString;
        List<String> replacements;

        // checking if the key exists in the properties file
        try {
            InvalidParameterException e = null;
            if (propKey == null) {
                e = new InvalidParameterException("propKey cannot be null");
            } else if (propKey.isBlank()) {
                e = new InvalidParameterException("propKey is empty string");
            } 
            if (e != null) {
                logr.throwing(e);
                throw e;
            }

            propString = getString(propKey);
            replacements = parseStringsToReplace(propString);
        } catch (MissingResourceException | InvalidParameterException e) {
            logr.catching(Level.DEBUG, e);
            return "";  // if key does not exist return empty string
        }

        // if newValues is null or was omitted, return value from properties file
        if (newValues != null && newValues.length != 0 && newValues[0] != null && !replacements.isEmpty()) {
            int loopCount = (newValues.length > replacements.size()) ? replacements.size() : newValues.length;
            for (var i = 0; i < loopCount; i++) {
                propString = propString.replace(replacements.get(i), newValues[i]); 
            }
        } 
        
        return propString;
    }

    /**
     * Convenience function to create a list of the substrings to be changed
     * finds strings that match the regular expression: \\{\\d+}
     *
     * @param searchString the string in which to search for the substring
     * @return a List of substrings to replace in the order they were found
     */
    protected static List<String> parseStringsToReplace(String searchString) {
        List<String> ret = new ArrayList<>();

        // making sure searchString is not null
        if(searchString == null || searchString.isBlank()) return ret;
        
        var pattern = Pattern.compile(getString("replacePropStringRegex"));
        var matcher = pattern.matcher(searchString);
        // adding \\{\\d+} regex to list
        while(matcher.find()){ ret.add(matcher.group()); }

        return ret;
    }

    /**
     * Convenience function to get the value at the specified key in the project's property file
     *
     * @param key a key in the projects property file
     * @return the value at that key
     */
    public static String getString(String key){
        if(key == null || key.isBlank()) return "";

        String ret = App.getProp().getProperty(key);
        return (ret == null)? "" : ret;
    }

    /**
     * Convenience function to get the integer value at the specified key in the project's property file
     *
     * @param key a key in the projects property file
     * @return the integer value at that key
     */
    public static int getInt(String key) {
        int ret = Integer.MIN_VALUE;

        if(key == null || key.isBlank()) return ret;

        // checking if the key exists in the property file and parsing value if it exists
        try {
            ret = Integer.parseInt(getString(key));
        } catch (NumberFormatException ee) {
            logr.catching(Level.DEBUG, ee);
            var e = new InvalidParameterException("Value is not a number");
            logr.throwing(e);
            throw e;
        }
        return ret;
    }

    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/

    /**
     * Set the properties
     * 
     * @param path the path to the properties file
     */
    protected static void setProp(String path) throws InvalidParameterException{
        try {
            prop = new Properties();
            prop.loadFromXML(new FileInputStream(path));
        } catch (IOException | NullPointerException e) {
            logr.catching(Level.FATAL, e);
            var ee = new InvalidParameterException("Failed to set properties from path: " + path);
            logr.throwing(Level.FATAL, ee);
            throw ee;
        } 
    }

    /**
     * Set the current operating system
     * 
     * @param path the current operating system
     */
    protected static void setOs(String os) { App.os = os; }

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
    protected static void setDefaultDir(String dirName) throws RuntimeException {
        if (dirName == null || dirName.isEmpty()) dirName = getString("appMainFolder"); 
        
        String name = String.valueOf(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()) + File.separator + dirName;
        var toBeCreated = new File(name);
        
        if (toBeCreated.exists() || toBeCreated.mkdirs()) {
            logr.info("%s directory was created", name);
            defaultDir = name;
        } else {
            var e = new RuntimeException("Failed to set up default directory");
            logr.throwing(Level.FATAL, e);
            throw e;
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

        if(getString("windows").equals(currentOS)) {
            dirLoc = System.getProperty("user.home") + File.separator +
                    "AppData" + File.separator +
                    "Local" + File.separator +
                    getString("appFolderName") + File.separator +
                    "logs";
        } else if(getString("mac").equals(currentOS) ||
                    getString("unix").equals(currentOS) ||
                    getString("solaris").equals(currentOS)) {
            dirLoc = System.getProperty("user.home") + File.separator +
                    getString("appFolderName") + File.separator +
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
     * Getter function to get the properties
     *
     * @return properties object
     */
    public static Properties getProp() {
        if(prop == null) {
            setProp("src/main/resources/properties.xml");
        }

        return prop;
    }

    /**
     * Getter function to get the current operating system
     *
     * @return the current operating system
     */
    public static String getOs() {
        if (os == null) {
            setOs(System.getProperty("os.name").toLowerCase());
        }

        return os;
    }
    
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