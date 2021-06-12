package rad.shipment.calculator.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rad.shipment.calculator.view.FXMLView;
import java.security.InvalidParameterException;
import java.util.logging.Logger;

public interface Window {
    // Declaring class variables
    Logger LOGR = Logger.getLogger( Window.class.getName() );  // getting logger
    SimpleStringProperty title = new SimpleStringProperty(Main.getString("defaultWindowTitle"));
    SimpleStringProperty message = new SimpleStringProperty(Main.getString("defaultMessage"));

    /**
     * Abstract function to display the window
     *
     * @param title the title of the window
     * @param message the message to be displayed inside the window
     */
    boolean display(String title, String message);
    
    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Abstract helper function to create a scene with given message
     *
     * @param message the message to be displayed
     *
     * @return the created Scene
     */
    Scene createScene(String message);

    /**
     * Helper function to create a new scene with the given title
     *
     * @param title the title of the scene to be created
     */
    static Stage createWindow(String title){
        if(title == null || "".equals(title)) title = Main.getString("defaultWindowTitle");

        Stage window = new Stage();
        Window.title.set(title);  // setting the title simple string value

        // Making sure user can only interact with this window
        // must close this window to interact with previous window
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);  // setting title of window
        window.getIcons().add(FXMLView.MAIN.getIconImage());  // setting the window icon to the same icon as the home pane
        window.setMinWidth(Main.getInt("windowMinWidth"));  // setting the minimum size (horizontally) for the window
        window.centerOnScreen();  // centering the new window in the middle of the screen

        // noting that the user closed the window
        window.setOnCloseRequest(e -> {
            LOGR.info(Main.getString("windowCloseMsg"));
            window.close();
        });

        LOGR.info("Created Window titled " + title);
        return window;
    }

    /**
     * Helper function to setup a scene in this window
     *
     * @param window the stage where the scene will be set
     * @param scene the scene to be set for this window
     */
    static void setScene(Stage window, Scene scene) throws InvalidParameterException {
        if(window == null) throw new InvalidParameterException("window cannot be null");
        if(scene == null) throw new InvalidParameterException("scene cannot be null");

        window.setScene(scene);  // setting the window to show the scene
        window.setResizable(false);  // stopping the user from resizing the window
    }

    /**
     * Helper function to show the window
     *
     * @param window the stage to be shown
     */
    static void show(Stage window) {
        if(window == null) throw new InvalidParameterException("window cannot be null");
        window.showAndWait();  // showing window and waiting for user input
    }

    /**
     * Helper function to create label to hold the message to be shown on the window
     *
     * @param message the message to be displayed
     *
     * @return the created label
     */
    static Label createLabel(String message){
        if(message == null) message = Main.getString("defaultMessage");
        
        Window.message.set(message);  // setting the SimpleStringProperty to the message
        
        Label labelMessage = new Label(message);  // creating a label that contains the message
        labelMessage.setAlignment(Pos.CENTER);  // making sure the label is center aligned
        labelMessage.setTextAlignment(TextAlignment.CENTER);  // making sure the text is center aligned in the label
        labelMessage.setMaxWidth(Main.getInt("windowMaxWidth"));  // setting maximum size for the label
        labelMessage.setWrapText(true);  // making sure that any text longer than the maximum size gets wrapped to new line

        return labelMessage;
    }

    /**
     * Helper function to create a button
     *
     * @param buttonText the text ot be displayed on the button
     *
     * @return the created button
     */
    static Button createButton(String buttonText){
        if(buttonText == null || "".equals(buttonText)) buttonText = Main.getString("defaultBtn");

        Button btn = new Button(buttonText);

        // Allowing the user to press enter to select a button if focused on it
        MainController.setBtnFireOnEnter(btn);

        return btn;
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/
    
    /**
     * Getter function to get the title of this window
     * 
     * @return the title of this window
     */
    static String getTitle() { return title.get(); }

    /**
     * Getter function to get the message of this window
     *
     * @return the message of this window
     */
    static String getMessage() { return message.get(); }

    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/

    /**
     * Setter function to set the title of this window
     *
     * @param title the title to be set
     */
    static void setTitle(String title) { Window.title.set(title); }

    /**
     * Setter function to set the message of this window
     *
     * @param message the message to be set
     */
    static void setMessage(String message) { Window.message.set(message); }
}
