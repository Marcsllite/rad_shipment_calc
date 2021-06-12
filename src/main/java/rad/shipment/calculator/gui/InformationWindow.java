package rad.shipment.calculator.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class InformationWindow implements Window {

    private final static Logger LOGR = Logger.getLogger( InformationWindow.class.getName() );  // getting logger
    private static Stage informationWindow;

    /**
     * Function to display the informational Window
     *
     * @param title the title of the window
     * @param message the message to be displayed inside the window
     * @return true if user selected a positive button otherwise false
     */
    public boolean display(String title, String message) {
        if(title == null || "".equals(title)) title = Main.getString("defaultInformationTitle");

        if(message == null) message = Main.getString("defaultMessage");

        informationWindow = Window.createWindow(title);
        Window.setScene(informationWindow, createScene(message));
        Window.show(informationWindow);

        return true;
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
        Button btnOkay = createButton("Okay", true);  // creating okay btn

        // Allowing the user to press enter to select a button if focused on it
        MainController.setBtnFireOnEnter(btnOkay);

        // (StackPanes automatically centers and stack their children)
        StackPane line1 = new StackPane(Window.createLabel(message));  // creating a StackPane that contains the message
        StackPane line2 = new StackPane(btnOkay);  // creating a StackPane that contains the Okay button
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
    protected static Button createButton(String buttonText, boolean positiveBtn){
        Button btn = Window.createButton(buttonText);  // creating a button for the user to click

        // closing the window when the user clicks the button
        btn.setOnAction(e -> {
            LOGR.info(btn.getText() + " was pressed");
            informationWindow.close();
        });

        return btn;
    }
}
