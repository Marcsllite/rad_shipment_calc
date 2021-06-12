package rad.shipment.calculator.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.commons.validator.UrlValidator;
import rad.shipment.calculator.helpers.CommandExecutor;
import rad.shipment.calculator.helpers.DatabaseEditor;
import rad.shipment.calculator.view.FXMLView;
import rad.shipment.calculator.view.StageManager;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    // Declaring variables
    private final static Logger LOGR = Logger.getLogger(Main.class.getName());  // getting logger
    private static String dataFolder;
    private static String defaultDir;  // Documents folder where any created files are saved
    private static final ResourceBundle bundle = ResourceBundle.getBundle("gradle");  // getting resource bundle
    private final InformationWindow informationWindow = new InformationWindow();
    protected static StageManager stageManager;  // creating stage manager object to switch fxml views
    private static DatabaseEditor H2DBEditor = new DatabaseEditor(getString("h2DB_Driver"),
                                                                getString("h2DB_Path"),
                                                                getString("h2DB_Settings"),
                                                                getString("DB_User"),
                                                                getString("DB_Pass"));
//    private static DatabaseEditor SQLiteDBEditor = new DatabaseEditor(getString("h2DB_Driver"),
//                                                                    getString("h2DB_Path"),
//                                                                    getString("h2DB_Settings"),
//                                                                    getString("DB_User"),
//                                                                    getString("DB_Pass"));

    /**
     * Main function to run application
     */
    public static void main(String[] args) { launch(args); }

    /**
     * Function to run at the start of application
     */
    @Override public void start(Stage primaryStage) {
        // checking if successfully connected to the database
        if(H2DBEditor.startConnection()) {
            try {
                setUpDefaultDirectory(); // Setting up default Directory in documents folder

                setUpDataFolder(findCurrentOS());  // Setting up data folder

//            setupLogger();  // setting up the logging

                stageManager = new StageManager(primaryStage);  // using StageManager to setup stage
                displayInitialScene();  // displaying the initialScene (durationMain.fxml)
            } catch (RuntimeException e) {
                LOGR.log(Level.SEVERE, "Failed to start application. Error: ", e);
                informationWindow.display("An Error Occurred", "Failed to start application. Error: " + e);
                Platform.exit();  // closing application
            }
        } else Platform.exit();  // terminating application if database connection failed
    }

    /**
     * Function to run before application closes
     */
    @Override public void stop() {
        H2DBEditor.closeConnection(); // Closing main database connection
        LOGR.info("Closing application.");  // logging that application is closing
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * @author Mkyong.com https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
     * Helper function to figure out the current operating system
     *
     * @return the current operating system
     */
    public static String findCurrentOS(){
        String OS = System.getProperty("os.name").toLowerCase();

        if (OS.contains("win")) return bundle.getString("windows");
        else if (OS.contains("mac")) return bundle.getString("mac");
        else if (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0) return bundle.getString("unix");
        else if (OS.contains("sunos")) return bundle.getString("solaris");
        else return bundle.getString("noSupport");
    }

    /**
     * Helper function to set the location of the default directory
     */
    protected static void setUpDefaultDirectory() throws RuntimeException {
        // logging that the main folder was created
        if ((new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + getString("appMainFolder"))).mkdirs()) {
            LOGR.info(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + getString("appMainFolder") + " directory was created");
        }

        // checking to see if default directory was created
        if((new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + getString("appMainFolder"))).exists())
            defaultDir = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + getString("appMainFolder");
        else throw new RuntimeException("Failed to set up default directory");
    }

    /**
     * Helper function to set the location of the data folder
     *
     * @param currentOS the operating system of the computer
     */
    protected static void setUpDataFolder(String currentOS) {
        // sets the dataFolder (folder where the logging is kept) to
        //      Windows: C:/Users/[username]/AppData/Local/[appFolderName]/logs
        //      MacOS/Unix/Solaris: ~/[appFolderName]/logs

        String dirLoc = null;

        if(currentOS == null) LOGR.warning("Data Folder was not created");

        else if(currentOS.equals(bundle.getString("windows"))) {
            dirLoc = System.getProperty("user.home") + File.separator +
                    "AppData" + File.separator +
                    "Local" + File.separator +
                    getString("appFolderName") + File.separator +
                    "logs";
        }

        else if(currentOS.equals(bundle.getString("mac")) ||
                currentOS.equals(bundle.getString("unix")) ||
                currentOS.equals(bundle.getString("solaris"))) {
            dirLoc = System.getProperty("user.home") + File.separator +
                    getString("appFolderName") + File.separator +
                    "logs";
        }

        if(dirLoc == null) LOGR.warning("Data Folder was not created");
        else if((new File(dirLoc)).mkdirs()) LOGR.info( dirLoc + " directory was created");

        dataFolder = dirLoc;
    }

    /**
     * Helper function to set up the logging
     */
    private static void setupLogger() {
        // reset all the settings for logging
        LogManager.getLogManager().reset();
        LOGR.setLevel(Level.ALL);

        // Only Logging marked as Severe will be printed out
        // on the Console
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        LOGR.addHandler(ch);

        try {
            FileHandler fh;
            setUpDataFolder(findCurrentOS());
            File dir = new File(getDataFolder());

            // If dataFolder exists
            if (dir.exists()) {
                // saves the logs directly to dataFolder
                fh = new FileHandler(dataFolder + File.separator +getString("logFileName"), true);
                fh.setLevel(Level.FINE);
                fh.setFormatter(new SimpleFormatter());
                LOGR.addHandler(fh);
            } else {
                // logging warning that normal data folder creation failed
                LOGR.warning( "Failed to create Log directory in AppData directory. Creating log folder in default directory (" + defaultDir + ")");
                dataFolder = defaultDir;  // setting the data folder to be the default folder located in the documents folder

                // creating first log file
                fh = new FileHandler(dataFolder + File.separator + getString("logFileName"), true);

                // setting logging level to fine
                fh.setLevel(Level.FINE);
                fh.setFormatter(new SimpleFormatter());
                LOGR.addHandler(fh);
            }

            LOGR.info("Logs started");  // noting that logging is successful
        } catch (IOException e) {
            LOGR.log(Level.SEVERE, "File logger not working.", e);
        }

        /*
         * Different Levels in order. OFF SEVERE WARNING INFO CONFIG FINE FINER FINEST ALL
         */
    }

    /**
     * Helper function to display first screen
     *
     * Useful to override this method by sub-classes wishing to change the first
     * Scene to be displayed on startup. Example: Functional tests on add window.
     */
    protected void displayInitialScene() { stageManager.switchScene(FXMLView.MAIN); }

    /**
     * Helper function to help make resource bundle string dynamic
     * replaces {0}, {1}, {3}, .. strings with given parameter values
     *
     * @param bundleKey the name of the key from the resource bundle
     * @param newValues the newValues to change the braced values in the bundle
     */
    public static String replaceBundleString(String bundleKey, String... newValues) {
        String bundleString;
        List<String> replacements;

        // checking if the key exists in the bundle
        try {
            if(bundleKey == null) throw new InvalidParameterException("bundleKey cannot be null");
            if("".equals(bundleKey)) throw new InvalidParameterException("bundleKey is empty string");
            bundleString = getString(bundleKey);
            replacements = parseStringsToReplace(bundleString);
        } catch (MissingResourceException | InvalidParameterException e) {
            return "";  // if key does not exist return empty string
        }

        // if newValues is null or was omitted, return value from ResourceBundle
        if(newValues == null || newValues.length == 0 || newValues[0] == null) return bundleString;
        else {
            String ret = bundleString;
            if(replacements.isEmpty()) return ret;
            else {
                int loopCount = (newValues.length > replacements.size())? replacements.size() : newValues.length;
                for(int i = 0; i < loopCount; i++){
                    ret = bundleString.replace(replacements.get(i), newValues[i]);
                    bundleString = ret;
                }
                return ret;
            }
        }
    }

    /**
     * Helper function to create a list of the substrings to be changed
     * finds strings that match the regular expression: \\{\\d+}
     *
     * @param searchString the string in which to search for the substring
     * @return a List of substrings to replace in the order they were found
     */
    protected static List<String> parseStringsToReplace(String searchString) {
        List<String> ret = new ArrayList<>();

        // making sure searchString is not null
        if(searchString == null || "".equals(searchString)) return ret;
        else {
            Pattern pattern = Pattern.compile(getString("replaceBundleStringRegex"));
            Matcher matcher = pattern.matcher(searchString);
            // adding \\{\\d+} regex to list
//            while(matcher.find()){ ret.add("\\" + matcher.group()); }
            while(matcher.find()){ ret.add(matcher.group()); }
        }

        return ret;
    }

    /**
     * Helper Function to navigate to the desired link
     * @ author Mkyong https://www.mkyong.com/java/open-browser-in-java-windows-or-linux/
     *          edited to use CommandExecutor instead of Runtime
     * @param link the link to navigate to
     */
    public static void navigateToLink(String link) throws RuntimeException{
        if(link == null) throw new InvalidParameterException("link is null");
        if("".equals(link)) throw new InvalidParameterException("link is empty string");
//
        String currentOS = findCurrentOS();  // getting the current operating system
        CommandExecutor commandExecutor = new CommandExecutor();
        UrlValidator urlValidator = new UrlValidator();

        if(!urlValidator.isValid(link)) {
            LOGR.warning("Failed to navigate to " + link + ". Invalid URL");
            throw new InvalidParameterException("link is malformed");
        }

        if(currentOS.equals(getString("noSupport"))) {
            LOGR.warning("Operating system is not supported! Failed to open " + link);  // logging errors
            throw new RuntimeException("Operating system is not supported");
        }
        else if(currentOS.equals(getString("windows"))) {
            commandExecutor.setCommands("rundll32", "url.dll,FileProtocolHandler", link);
            commandExecutor.runCommands();
        }
        else if(currentOS.equals(getString("mac"))) {
            commandExecutor.setCommands("open", link);
            commandExecutor.runCommands();
        }
        else if(currentOS.equals(getString("unix")) || currentOS.equals(getString("solaris"))) {
//            // Do a best guess on unix until we get a platform independent way
//            // Build a list of browsers to try, in this order.
//            String[] browsers = {"chromium-browser", "epiphany", "firefox", "mozilla", "konqueror",
//                    "netscape","opera","links","lynx"};
//
//            // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
//            StringBuilder cmd = new StringBuilder();
//            for (int i=0; i<browsers.length; i++)
//                cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(link).append("\" ");

            commandExecutor.setCommands("xdg-open", link, "&");
            commandExecutor.runCommands();
        }
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/

    /**
     * Getter function to get the value at the specified key in the project's ResourceBundle file
     *
     * @param key a key in the projects bundle file
     * @return the value at that key
     */
    public static String getString(String key){
        if(key == null || "".equals(key)) return "";

        // checking if the key exists in the bundle
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "";  // if key does not exist return empty string
        }
    }

    /**
     * Getter function to get the integer value at the specified key in the project's ResourceBundle file
     *
     * @param key a key in the projects bundle file
     * @return the integer value at that key
     */
    public static int getInt(String key) throws RuntimeException {
        if(key == null) throw new InvalidParameterException("key is null");
        if("".equals(key)) throw new InvalidParameterException("key is empty string");

        // checking if the key exists in the bundle and parsing value if it exists
        try {
            String bundleString = bundle.getString(key);
            return Integer.parseInt(bundleString);
        } catch (MissingResourceException e) {
            throw new InvalidParameterException("Key does not exist");  // if key does not exist return empty string
        } catch (NumberFormatException ee) {
            throw new RuntimeException("Value is not a number");
        }
    }

    /**
     * Getter function to get the resource bundle
     *
     * @return the resource bundle
     */
    public static ResourceBundle getBundle() {return bundle; }

    /**
     * Getter function to get the database editor for the embedded database
     *
     * @return the database editor for the embedded database
     */
    public static DatabaseEditor getDBEditor(){ return H2DBEditor; }

    /**
     * Getter function to get the main folder where logging data resides
     *
     * @return data folder for further used by the Open logs Button
     */
    static String getDataFolder() { return dataFolder; }

    /**
     * Getter function to get the folder where this application saves its files
     *
     * @return the application's default folder
     */
    public static String getDefaultFolder() { return defaultDir; }
}
