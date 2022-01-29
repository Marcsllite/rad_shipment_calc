package com.marcsllite.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.regex.Pattern;

import com.marcsllite.App;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.ButtonBase;
import javafx.scene.input.KeyCode;

public final class Util {
    private static final Logger logr = LogManager.getLogger();
    private static final String PROP_NAME = "properties.xml";
    private static String os;
    private static Properties prop;

    static {
        Util.setOs(Util.getCurrentOS());
        Util.setProp(PROP_NAME);
    }

    private Util() {}

    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/
    /**
     * Set the properties
     * 
     * @param path the path to the properties file
     */
    public static void setProp(String path) throws InvalidParameterException{
        try {
            Util.prop = new Properties();
            var loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream(path);
            Util.prop.loadFromXML(stream);
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
    public static void setOs(String os) { Util.os = os; }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/
    /**
     * Getter function to get the properties
     *
     * @return properties object
     */
    public static Properties getProp() {
        if(Util.prop == null) {
            Util.setProp(PROP_NAME);
        }

        return Util.prop;
    }

    /**
     * Getter function to get the current operating system
     *
     * @return the current operating system
     */
    public static String getOs() {
        if (Util.os == null) {
            setOs(System.getProperty("os.name").toLowerCase());
        }

        return Util.os;
    }

    /**
     * @author Mkyong.com https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
     * Convenience function to figure out the current operating system
     *
     * @return the current operating system
     */
    public static String getCurrentOS(){
        String os = Util.getOs();

        if (os.contains("win")) return Util.getString("windows");
        else if (os.contains("mac")) return Util.getString("mac");
        else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) return Util.getString("unix");
        else if (os.contains("sunos")) return Util.getString("solaris");
        else return getString("noSupport");
    }

    /**
     * @author Alvin Alexander https://alvinalexander.com/blog/post/java/read-text-file-from-jar-file
     * Convenience function to open the given file and return its
     * contents to a string
     *
     * @param resourceFilePath the relative path of the resource file to get the text of
     * @return the contents of the file or the empty string if errors occurred
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
    public static List<String> parseStringsToReplace(String searchString) {
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
    public static String getString(String key) {
        if(key == null || key.isBlank()) return "";

        String ret = Util.getProp().getProperty(key);
        return (ret == null)? "" : ret;
    }

    /**
     * Convenience function to get the double value at the specified key in the project's property file
     *
     * @param key a key in the projects property file
     * @return the double value at that key
     */
    public static double getDouble(String key) {
        double ret = Double.MIN_VALUE;

        if(key == null || key.isBlank()) return ret;

        String value = getString(key);

        // checking if the key exists in the property file and parsing value if it exists
        try {
            ret = Double.parseDouble(value);
        } catch (NumberFormatException ee) {
            logr.catching(Level.DEBUG, ee);
            var e = new InvalidParameterException(value + "is not a number");
            logr.throwing(e);
            throw e;
        }
        return ret;
    }

    /**
     * Convenience function to get multiple Strings from the properties file
     * that all start with the given listName
     * 
     * NOTE: all lists must be in the following format in the properties file
     *          <entry key="{listName}">element1|element2|...</entry>
     * where {listName} is the name for the list
     * and a pipe (|) character separates the different elements from one another
     * trailing pipe character will be ignored
     *      
     * Example: listName = "si", index = 0    
     *      <entry key="siPrefixes">
     *          Yotta (Y)|
     *          Zetta (Z)|
     *          ...
     *      </entry>
     *
     * @param listName the properties key that all the list elements contain
     * @return a list of all the values from the property entry
     */
    public static List<String> getList(String listName) {
        List<String> ret = new ArrayList<>();

        var str = getString(listName);
        if(!str.isBlank()) {
            // removing new line character, tab characters
            // and spaces before and after 
            str = str.trim().replaceAll("\\r\\n|\\r|\\n|\\t|", "");
            ret = Arrays.asList(str.split("\\|"));
            // removing white spaces around element
            for(var i = 0; i < ret.size(); i++) {
                ret.set(i, ret.get(i).trim());
            }
        }

        return ret;
    }

    /**
     * Helper function to allow the given buttonBase to be fired
     * when the enter/space key is pressed and the buttonBase is in focus
     * 
     * Valid ButtonBases:
     *      MenuBarButton
     *      IndicatorButton
     *      Button
     *      CheckBox
     *      Hyperlink
     *      MenuButton
     *      RadioButton
     *      SplitMenuButton
     *      ToggleButton
     *
     * @param btnBase the btnBase to add the listener to
     */
    public static void fireBtnOnEnter(ButtonBase btnBase) throws InvalidParameterException {
        if(btnBase == null) throw new InvalidParameterException("button base cannot be null");

        btnBase.setOnKeyPressed(
            event -> {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    btnBase.fire();
                    event.consume();
                }
            }
        );
    }
}
