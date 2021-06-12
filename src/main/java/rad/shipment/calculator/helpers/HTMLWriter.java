package rad.shipment.calculator.helpers;

import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.*;
import javafx.collections.ObservableList;
import rad.shipment.calculator.gui.Main;

import java.io.*;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTMLWriter {

    private static final Logger LOGR = Logger.getLogger(HTMLWriter.class.getName());
    private final DateTimeFormatter normalForm = DateTimeFormatter.ofPattern(Main.getString("dateNormalFormat"));
    private ObservableList<Isotope> isotopes;
    
    /**
     * Constructs a HTMLWriter object with the given list of Isotopes
     */
    public HTMLWriter(ObservableList<Isotope> isotopes){ this.isotopes = isotopes; }

    /**
     * Constructs a HTMLWriter object with the given list of DeviceInfos
     */
    public HTMLWriter(){}

    /**
     *  Function to create a save a html document to the given file path
     *
     * @param filePath the location to save the html document
     * @return true if the save was successful, false if anything went wrong
     */
    public boolean saveHTML(String filePath) {
        try {
            if(filePath == null || "".equals(filePath)) throw new InvalidParameterException("filePath (" + filePath + ") is invalid");

            Table table = createTable(); // creating table
            if(table == null) throw new RuntimeException("Failed to create table");

            // creating document
            Document statusReport = createDocument(table);
            if(statusReport == null) throw new RuntimeException("Failed to create html document");

            File file = new File(filePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(statusReport.write());
            writer.close();
            return true;
        } catch (IOException | RuntimeException e) {
            LOGR.log(Level.SEVERE, "Failed to create/save html document. Error ", e);
            return false;
        }
    }

    /**
     * Helper function to create the timestamp of the report
     *
     * @return the string representation of the time stamp of the report
     */
    private String getTimeStamp(){ return "Generated on " + normalForm.format(LocalDateTime.now()); }

    /**
     * @Author Alvin Alexander https://alvinalexander.com/blog/post/java/read-text-file-from-jar-file
     * Helper function to open the given file and return its
     * contents to a string
     *
     * @param resourceFilePath the relative path of the resource file to get the text of
     * @return the contents of the file or teh empty string if errors occurred
     */
    private String getFileText(String resourceFilePath){
        try {
            if(resourceFilePath == null || "".equals(resourceFilePath))
                throw new InvalidParameterException("resourceFilepath (" + resourceFilePath + ") is invalid");

            InputStream is = getClass().getResourceAsStream(resourceFilePath);  // getResourceAsStream() may throw NullPointerException
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) { sb.append(line); } // readLine() may throw IOException
            br.close();  // close() may throw IOException
            isr.close();  // close() may throw IOException
            is.close();  // close() may throw IOException
            return sb.toString();
        } catch (IOException | NullPointerException | InvalidParameterException e) {
            LOGR.log(Level.WARNING, "Failed to read file (" + resourceFilePath + ") and turn to String. Error: ", e);
        }
        return "";
    }

    /**
     * Helper function to get the correct data from the given isotope and column header
     *
     * @param isotope the isotope to get the data from
     * @param header the header of the current column
     *
     * @return the isotope's String value for the given column
     */
    private String getColumnData(Isotope isotope, String header) {
        if(header == null || "".equals(header) || isotope == null) return null;

        // TODO: update getColumnData helper function to work with Shipment Isotopes
//        if (Main.getString("colNumber").equals(header)) return Integer.toString(isotope.getNumber());
//        else if (Main.getString("colDeviceID").equals(header)) return isotope.getDeviceId();
//        else if (Main.getString("colDeviceType").equals(header)) return isotope.getDeviceType();
//        else if (Main.getString("colUpTime").equals(header)) return isotope.getUpTime().getHTMLUptime();
//        else if (Main.getString("colHostnameIP").equals(header)) return isotope.getHostnameIP();
//        else if (Main.getString("colServerType").equals(header)) return isotope.getServerType();
//        else if (Main.getString("colServerHostname").equals(header)) return isotope.getServerHostname();
//        else if (Main.getString("colStatus").equals(header)) return isotope.getTooltipStatus();
//        else if (Main.getString("colFirmware").equals(header)) return isotope.getFirmware();
//        else if (Main.getString("colTimezone").equals(header)) return isotope.getTimezone();
//        else if (Main.getString("colBiometrics").equals(header)) return isotope.getBiometrics();
//        else if (Main.getString("colPunchUploadFail").equals(header)) return Integer.toString(isotope.getPunchUploadFailure());
        return null;
    }
    
    /**
    * Helper function to create the status report table
    *
    * @return the newly generated table or null if errors occurred
    */
    private Table createTable(){
        Table table = new Table().setId("table");
        List<Tr> tableRows = new ArrayList<>();  // list to hold the table rows

        String[] headerText = new String[]{  // getting the names for the columns
                // TODO: update headerText to work with Shipment isotopes
//            Main.getString("colNumber"),
//            Main.getString("colDeviceID"),
//            Main.getString("colDeviceType"),
//            Main.getString("colUpTime"),
//            Main.getString("colHostnameIP"),
//            Main.getString("colServerType"),
//            Main.getString("colServerHostname"),
//            Main.getString("colStatus"),
//            Main.getString("colFirmware"),
//            Main.getString("colTimezone"),
//            Main.getString("colBiometrics"),
//            Main.getString("colPunchUploadFail")
        };

        Thead tableHead = new Thead();  // creating THead group
        Tbody tableBody = new Tbody();  // creating TBody group

        Tr headerRow = new Tr();  // creating table header row
        tableHead.appendChild(headerRow);  // adding header row to THead group

        // adding bolded text from headerText array to Table Header
        for (int i = 0; i < headerText.length; i++) {
            Th colHead = new Th();  // creating <th><\th> element
            // setting javascript sorting function to be called when header is clicked
            colHead.setAttribute("onclick", "sortTable(" + i + ", '" + headerText[i] + "')");
            colHead.appendChild(new B().appendText(headerText[i]));  // adding bold header text
            headerRow.appendChild(colHead);
        }

        // adding rows to table with Numbers
        for(int i = 0; i < isotopes.size(); i++){
            tableRows.add(new Tr().appendChild(new Td().appendText(Integer.toString(i + 1))));
        }

        // adding device's info to rows (skipping number)
        for(int i = 1; i < headerText.length; i++){  // looping through all the header texts
            for (int j = 0; j < isotopes.size(); j++) {
                if(getColumnData(isotopes.get(j), headerText[i]) == null) return null;
                tableRows.get(j).appendChild(new Td().appendText(getColumnData(isotopes.get(j), headerText[i])));
            }
        }

        // adding rows to table body
        for (Tr row: tableRows) { tableBody.appendChild(row); }

        // adding THead and TBody to table
        table.appendChild(tableHead).appendChild(tableBody);

        return table;
    }

    /**
     * Helper function to create the html document
     *
     * @return the created document or null if anything went wrong
     */
    private Document createDocument(Table table) {
        String cssFile = getFileText("/css/statusReport.css"),
                jsFile = getFileText("/scripts/script.js"),
                title = "",
                timeStamp = getTimeStamp();
        try {
            if("".equals(cssFile)) throw new RuntimeException("Failed to get css file");
            if("".equals(jsFile)) throw new RuntimeException("Failed to get js file");
            if(title == null) throw new RuntimeException("Failed to get page title");
        } catch (RuntimeException e) {  // logging errors
            LOGR.log(Level.SEVERE, "Failed to create new document. Error: ", e);
            return null;
        }

        // creating new html 4.01 transitional document
        Document statusRprt = new Document(DocumentType.HTMLTransitional);

        // setting up the head tag of the document
        /*<head>
                <meta name = "viewport" content="width=device-width, initial-scale=1.0">
                <link rel="shortcut icon" href=" + (path of icon image) type="image/x-icon" />
                <style> *see /resources/css/statusReport.css* <style>
                <title> + ConfirmWindow.getFileName() + </title>
          </head>
         */
        statusRprt.head.appendChild(new Meta("width=device-width, initial-scale=1.0").setName("viewport"));
        statusRprt.head.appendChild(new Link().setRel("shortcut icon").setHref(ImageHandler.getColorLogoBkgPath()).setType("image/x-icon"));
        statusRprt.head.appendChild(new Style("text/css").appendText(cssFile));
        statusRprt.head.appendChild(new Title().appendText(title));

        // setting up wrapper
        // if table is not null
        /*<div class = "wrapper">
                <h1>Rad Shipment Calculator - Summary Report</h1>
                <hr>
                <div class = "description">
                    <p id = "reportTime"> getTimeStamp() </p>
                    <p id = "numDevices"><strong>Number of Devices in Report:</strong> Integer.toString(devices.size()) </p>
                </div>
                table (from parameter)
          </div>
        */
        // if table is null
        /*<div class = "wrapper">
                <h1>Rad Shipment Calculator - Summary Report</h1>
                <hr>
                <div class = "description">
                    <p id = "reportTime"> getTimeStamp() </p>
                </div>
          </div>
        */
        Div wrapper = new Div().setCSSClass("wrapper");  // creating wrapper division

        wrapper.appendChild(new H1().appendText(Main.getString("summaryReport")));
        wrapper.appendChild(new Hr());

        Div description = new Div().setCSSClass("description");  // creating description division
        description.appendChild(new P().setId("reportTime").appendText(timeStamp));
        if(table != null) description.appendChild(new P().setId("numIsotopes").appendChild(new Strong().appendText("Number of Isotopes in Shipment: ")).appendText(Integer.toString(isotopes.size())));

        if(table != null) wrapper.appendChild(description).appendChild(table);  // adding description division and table to wrapper division

        // setting up javascript to allow sorting by column
        // <script type="application/javascript"> *see /resources/scripts/scripts.js* </script>
        Script script = new Script("text/javascript").appendText(jsFile);

        // adding wrapper to body
        /*<body>
                <div class = "wrapper">
                    <div class="description">
                        <h1>Duration Test Tracker - Status Report</h1>
                        <hr>
                        <p id="reportTime"> getTimeStamp() </p>
                        <p id="numDevices"><strong>Number of Devices in Report:</strong> Integer.toString(devices.size()) </p>
                    </div>
                    table (from parameter)
                </div>
                <script type="application/javascript"> *see /resources/scripts/scripts.js* </script>
          </body>
        */
        statusRprt.body.appendChild(wrapper).appendChild(script);  // adding wrapper and javascript to body

        return statusRprt;
    }
}
