package rad.shipment.calculator.helpers;

import rad.shipment.calculator.gui.InformationWindow;
import rad.shipment.calculator.gui.Main;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseEditor {

    // Declaring variables
    private static final Logger LOGR = Logger.getLogger(DatabaseEditor.class.getName());  // getting logger
    private final String DRIVER;  // database driver
    private final String PATH;  // the path to the database file
    private final String SETTINGS;  // any settings for the database
    private final String USER;  // username for database
    private final String PASS;  // password for database
    private static Connection con;  // Connection to the database to be edited

    /*/////////////////////////////////////////////// DATABASE EDITOR ////////////////////////////////////////////////*/

    /**
     * Constructs a database editor object that can manipulate and view entries in a database
     *
     * @param driver the driver for the database
     * @param path the path to create/open the database (default path in bundle properties)
     * @param settings any additional settings to add to the end of the database path
     * @param user the username of the database
     * @param pass the password of the database
     */
    public DatabaseEditor(String driver, String path, String settings, String user, String pass){
        // making sure values are valid
        if(driver == null) throw new InvalidParameterException("Database driver cannot be null");
        if(path == null) path = Main.replaceBundleString("defaultDBPath");
        if(settings == null) settings = "";
        if(user == null) user = "";
        if(pass == null) pass = "";

        // saving values
        DRIVER = driver;
        PATH = path;
        SETTINGS = settings;
        USER = user;
        PASS = pass;
    }

    /**
     * Function to start connection to main database
     *
     * @return true if a connection was started
     *          false if an something went wrong
     */
    public boolean startConnection() {
        con = null;
        try {
            Class.forName(DRIVER);  // registering database driver
            String conURL;

            if("".equals(getSettings())) conURL = PATH;
            else conURL = PATH + SETTINGS;

            if("".equals(getUser()) && "".equals(getUser()))
                con = DriverManager.getConnection(conURL);
            else con = DriverManager.getConnection(conURL, USER, PASS);

            LOGR.info("Successfully connected to the database");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            LOGR.log(Level.SEVERE, "Failed to setup/connect to database. Error: ", e);
            new InformationWindow().display("Error", "Connection to database failed. Error: " + e);
            return false;
        }
    }

    /**
     * Function to close the database connection
     */
    public void closeConnection(){
        try {
            if(con == null) throw new RuntimeException("Cannot close connection, connection is null");
            con.close();
            LOGR.info("closed database connection");
        } catch (SQLException | RuntimeException e) {
            LOGR.log(Level.SEVERE, "Failed to close database connection. Error: ", e);
        }
    }

    /**
     * Function to populate the data in the database with the proper information if there is no data
     */
    public void setupEmbeddedDB(){
        try {
            if (con == null) throw new RuntimeException("Cannot connect to database, connection is null");

            Statement db = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  // creating statement to execute SQL commands

            // creating tables
            db.executeUpdate(Main.getString("createReferenceTable"));
            db.executeUpdate(Main.getString("createInfoTable"));

            // checking if tables are empty
            ResultSet rsInfo = db.executeQuery(Main.getString("referenceTableQuery"));
            ResultSet rsReference = db.executeQuery(Main.getString("infoTableQuery"));

            // adding data to databases if needed
            if(getResultSetSize(rsReference) == 0) setupReferenceTable();
            if(getResultSetSize(rsInfo) == 0) setupInfoTable();

            db.close();  // closing statement
        } catch (SQLException | RuntimeException e) {
            LOGR.log(Level.SEVERE, "Failed to create tables in database. Error: ", e);  // logging any errors
        }
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Helper function to get the size of a ResultSet
     *
     * @param result the ResultSet to find the size of
     * @return the size of the ResultSet
     */
    private int getResultSetSize(ResultSet result){
        int size = 0;
        if (result != null) {
            try {
                result.last();    // moves cursor to the last row
                size = result.getRow(); // get row id
                result.beforeFirst();
            } catch (SQLException e) {
                LOGR.log(Level.SEVERE, "Failed to get size of result set. Error: ", e);
            }
        }
        return size;
    }
    
    /**
     * Helper function to populate Reference table 
     */
    private void setupReferenceTable(){
        // TODO: setup reference table
    }

    /**
     * Helper function to populate Info table 
     */
    private void setupInfoTable(){
        // TODO: setup info table
    }
    
    /**
     * Helper function to figure out if given isotope name is valid for this program
     * This function searches isotope name in Reference table of database
     * 
     * @param name the name to be checked
     * @return true if the isotope name was found, else false
     */
    private boolean isValidIso(String name) {
        if(name == null) throw new InvalidParameterException("Isotope name cannot be null");
        
        // TODO: finish isValidIso function to check if given isotope is valid
        return false;
    }
    
    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/

    /**
     * Getter function to get the given isotope's A1 value 
     * from the Info table in the database
     * 
     * @param name the name of the isotope
     * @return the A1 value of that isotope
     */
    public float getA1(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get A1 function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's A2 value 
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the A2 value of that isotope
     */
    public float getA2(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get A2 function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's Decay Constant value 
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the Decay Constant value of that isotope
     */
    public float getDecayConstant(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get DecayConstant function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's Exempt Concentration value 
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the Exempt Concentration value of that isotope
     */
    public float getExemptConcentration(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get Exempt Concentration function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's Exempt Limit value 
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the Exempt Limit value of that isotope
     */
    public float getExemptLimit(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get Exempt Limit function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's Half Life value 
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the Half Life value of that isotope
     */
    public float getHalfLife(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get Half Life function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's Instruments/Articles Limited Multiplier value 
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the Instruments/Articles Limited Multiplier value of that isotope
     */
    public float getIALimitedMultiplier(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get Instruments/Articles Limited Multiplier function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's Instruments/Articles Package Limit value 
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the Instruments/Articles Package Limit value of that isotope
     */
    public float getIAPackageLimit(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get Instruments/Articles Package Limit function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's License Limit value 
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the License Limit value of that isotope
     */
    public float getLicenseLimit(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get License Limit function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's Limited Limit value 
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the Limited Limit value of that isotope
     */
    public float getLimitedLimit(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get Limited Limit function to get value from database
        return (float)Main.getInt("defaultInt");
    }

    /**
     * Getter function to get the given isotope's Reportable Quantity value
     * from the Info table in the database
     *
     * @param name the name of the isotope
     * @return the Reportable Quantity value of that isotope
     */
    public float getReportableQuantity(String name) {
        if(!isValidIso(name)) throw new InvalidParameterException("Isotope is invalid");

        // TODO: finish get Reportable Quantity function to get value from database
        return (float)Main.getInt("defaultInt");
    }
    
    /**
     *  Getter function to get the driver for the database
     *
     * @return the driver for the database
     */
    public String getDriver() { return DRIVER; }

    /**
     *  Getter function to get the path for the database
     *
     * @return the path for the database
     */
    public String getPath() { return PATH; }

    /**
     *  Getter function to get the settings for the database
     *
     * @return the settings for the database
     */
    public String getSettings() { return SETTINGS; }

    /**
     *  Getter function to get the username for the database
     *
     * @return the username for the database
     */
    public String getUser() { return USER; }

    /**
     *  Getter function to get the password for the database
     *
     * @return the password for the database
     */
    public String getPass() { return PASS; }
}
