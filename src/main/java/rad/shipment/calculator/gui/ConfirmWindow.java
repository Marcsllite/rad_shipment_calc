package rad.shipment.calculator.gui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConfirmWindow implements Window {
    // Declaring class variables
    private final static Logger LOGR = Logger.getLogger( ConfirmWindow.class.getName() );  // getting logger
    private final SimpleBooleanProperty ret = new SimpleBooleanProperty(false);  // boolean to know if user selected yes or no
    private List<Button> buttons = new ArrayList<>();

    private static Stage confirmWindow;

    /**
     * Function to display the Confirmation Window
     *
     * @param title the title of the window
     * @param message the message to be displayed inside the window
     * @return true if user selected a positive button otherwise false
     */
    public boolean display(String title, String message) {
        if(title == null || "".equals(title)) title = Main.getString("defaultConfirmTitle");

        if(message == null) message = Main.getString("defaultMessage");

        setStage(Window.createWindow(title));
        Window.setScene(confirmWindow, createScene(message));
        Window.show(confirmWindow);

        return getRet();
    }
    
    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Helper function to create a scene with given message and 2 buttons
     *
     * @param message the message to be displayed
     *
     * @return the created Scene
     */
     public Scene createScene(String message){
        Button btnYes = createButton("Yes", true);  // creating Yes button
        Button btnNo = createButton("No", false);  // creating No button

        // (StackPanes automatically centers and stack their children)
        StackPane line1 = new StackPane(Window.createLabel(message));  // creating a StackPane that contains the message
        HBox line2 = new HBox(50, btnYes, btnNo);  // creating a horizontal box to display the buttons
        line2.setAlignment(Pos.CENTER);  // making sure the buttons are centered in the horizontal box
        VBox layout = new VBox(10, line1, line2);
        layout.setAlignment(Pos.CENTER);  // making sure the lines in the vertical box are centered (centering everything vertically on the page)

        return new Scene(layout);  // setting the scene to show the layout just created
    }

    /**
     * Helper function to create a button
     *
     * @param buttonText the text ot be displayed on the button
     * @param positiveBtn whether or not clicking the button will set the return value to true
     *
     * @return the created button
     */
    public Button createButton(String buttonText, boolean positiveBtn){
        Button btn = Window.createButton(buttonText);

        // giving the button an id (for testing purposes)
        if(positiveBtn) btn.setId(Main.getString("confirmPositiveBtnID").replace("#", ""));
        else btn.setId(Main.getString("confirmNegativeBtnID").replace("#", ""));

        // closing the window when the user clicks the button
        btn.setOnAction(e -> {
            if(positiveBtn) setRet(true);
            else setRet(false);
            LOGR.info(btn.getText() + " was pressed");
            confirmWindow.close();
        });

        // adding button to array list
        buttons.add(btn);

        return btn;
    }

    /**
     * Helper function to find a button on the confirmWindow
     *
     * @param btnID the id of the button in the confirmWindow
     *
     * @return the button that corresponds to that button id
     */
    public Button findBtn(String btnID) {
        if(btnID == null || "".equals(btnID)) throw new InvalidParameterException("btnID cannot be null");

        for (Button btn: buttons) {
            if(btn.getId().equals(btnID)) return btn;
        }

        return null;
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/

    /**
     * Getter function to get the return value
     *
     * @return the return value of the confirmation window
     *          (if the user clicked the positive button or not)
     */
    public boolean getRet() { return ret.get(); }

    /**
     * Getter function to get stage
     *
     * @return the stage
     */
    public static Stage getStage() { return confirmWindow; }

    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/

    /**
     * Setter function to set the return value
     *
     * @param ret the new return value
     */
    public void setRet(boolean ret) { this.ret.set(ret); }

    /**
     * Setter function to set the stage
     *
     * @param stage the new stage
     */
    public static void setStage(Stage stage) {
        if(stage == null) throw new InvalidParameterException("stage cannot be null");

        confirmWindow = stage;

        // noting that the user closed the confirmation window
        confirmWindow.setOnCloseRequest(e -> {
            LOGR.info(Main.getString("confirmCloseMsg"));
            confirmWindow.close();
        });
    }
}
