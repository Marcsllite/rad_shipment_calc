package com.marcsllite.util.handler;

import com.marcsllite.App;
import com.marcsllite.util.OS;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

public class PropHandler extends ResourceBundle {
    private static final Logger logr = LogManager.getLogger();
    public static final String PROP_NAME = "properties";
    private String os;
    private Properties prop;

    public PropHandler() {
        this(null);
    }

    public PropHandler(InputStream stream) {
        setOs(System.getProperty("os.name").toLowerCase());
        if(stream != null) {
            setProp(stream);
        }
    }

    /**
     * Get the value at the specified key in the project's property file
     *
     * @param key a key in the project's property file
     * @return the value at that key or empty string
     */
    @Override
    protected Object handleGetObject(String key) {
        if(key == null || key.isBlank()) return "";

        String ret = Objects.requireNonNull(prop, "Properties has not been initialized").getProperty(key);
        return (ret == null)? "" : ret;
    }

    @Override
    public Enumeration<String> getKeys() {
        Set<String> handleKeys = Objects.requireNonNull(prop, "Properties has not been initialized").stringPropertyNames();
        return Collections.enumeration(handleKeys);
    }

    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/
    /**
     * Set the properties using Properties object
     * 
     * @param properties the properties to be set
     */
    public void setProp(Properties properties)  {
        prop = properties;
    }

    /**
     * Set the properties using a stream
     *
     * @param stream the InputStream to the properties file
     */
    public void setProp(InputStream stream) throws InvalidParameterException{
        try {
            prop = new Properties();
            prop.loadFromXML(stream);
        } catch (IOException | NullPointerException e) {
            logr.catching(Level.FATAL, e);
            var ee = new InvalidParameterException("Failed to set properties from stream: " + stream.toString());
            logr.throwing(Level.FATAL, ee);
            throw ee;
        }
    }

    /**
     * Set the current operating system
     * 
     * @param os the current operating system
     */
    public void setOs(String os) { this.os = os; }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/
    /**
     * Getter function to get the properties
     *
     * @return properties object
     */
    public Properties getProp() { return prop; }

    /**
     * Getter function to get the current operating system
     *
     * @return the current operating system
     */
    public String getOS() { return os; }

    /**
     * @author Mkyong.com <a href="https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/">...</a>
     * Convenience function to figure out the current operating system
     *
     * @return the current operating system
     */
    public OS parseOS(String os) {
        if(os == null) {
            return OS.NOT_SUPPORTED;
        }

        if (os.contains("win")) return OS.WINDOWS;
        else if (os.contains("mac")) return OS.MAC;
        else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) return OS.UNIX;
        else if (os.contains("sunos")) return OS.SOLARIS;
        else return OS.NOT_SUPPORTED;
    }

    /**
     * @author Alvin Alexander <a href="https://alvinalexander.com/blog/post/java/read-text-file-from-jar-file">...</a>
     * Convenience function to open the given file and return its
     * contents to a string
     *
     * @param resourceFilePath the relative path of the resource file to get the text of
     * @return the contents of the file or the empty string if errors occurred
     */
    public String getFileText(String resourceFilePath) throws InvalidParameterException {
        try {
            if (resourceFilePath == null || resourceFilePath.isBlank()) {
                var e = new InvalidParameterException("resourceFilepath (" + resourceFilePath + ") is invalid");
                logr.throwing(e);
                throw e;
            } 

            InputStream is = App.class.getResourceAsStream(resourceFilePath);  // getResourceAsStream() may throw NullPointerException
            var isr = new InputStreamReader(Objects.requireNonNull(is));
            var br = new BufferedReader(isr);
            var sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) { sb.append(line).append("\n"); } // readLine() may throw IOException
            br.close();  // close() may throw IOException
            isr.close();  // close() may throw IOException
            is.close();  // close() may throw IOException
            return sb.toString();
        } catch (IOException | NullPointerException | InvalidParameterException e) {
            logr.atWarn().withThrowable(e).log("Failed to read file ({}) and turn to String.", resourceFilePath);
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
    public String replacePropString(String propKey, Object[] newValues) throws InvalidParameterException {
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

            String propString = getString(propKey);
            return newValues == null? propString : MessageFormat.format(propString, newValues);
        } catch (InvalidParameterException e) {
            logr.catching(Level.DEBUG, e);
            return "";  // if key does not exist return empty string
        }
    }


    /**
     * Convenience function to get the double value at the specified key in the project's property file
     *
     * @param key a key in the project's property file
     * @return the double value at that key
     */
    public double getDouble(String key) {
        double ret = Double.MIN_VALUE;

        if(key == null || key.isBlank()) return ret;

        String value = getString(key);

        // checking if the key exists in the property file and parsing value if it exists
        try {
            ret = Double.parseDouble(value);
        } catch (NumberFormatException ee) {
            logr.catching(Level.DEBUG, ee);
            var e = new InvalidParameterException(value + " is not a number");
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
    public List<String> getList(String listName) {
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
}
