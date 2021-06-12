package rad.shipment.calculator.view;

import javafx.application.Platform;
import rad.shipment.calculator.gui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages switching Scenes on the Primary Stage
 * useful for testing specific scenes without
 * having to go through the flow of the application
 */
public class StageManager {

    // Declaring variables
    private static final Logger LOGR = Logger.getLogger(StageManager.class.getName());  // getting logger
    private final Stage primaryStage;
    private FXMLLoader fxmlLoader;

    public StageManager(Stage stage) { primaryStage = stage; }

    public Stage getPrimaryStage() { return primaryStage; }

    /**
     * Function to switch the current scene to another scene
     *
     * @param view teh FXMLView of the new scene to switch to
     */
    public void switchScene(final FXMLView view) {
        if(view == null) throw new InvalidParameterException("FXML View is null");
        Parent root = loadViewNodeHierarchy(view);
        primaryStage.setScene(new Scene(root, view.getWidth(), view.getHeight()));
        primaryStage.setMinWidth(view.getWidth());
        primaryStage.setMinHeight(view.getHeight());
        primaryStage.setMaxWidth(view.getMaxWidth());
        primaryStage.setMaxHeight(view.getMaxHeight());
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(false);
        show(view);
    }

    /**
     * Getter function to get the controller of the current loaded fxml page
     *
     * @return the controller of the loaded fxml file
     *          (MainController, MenuPaneController)
     */
    public <T> T getController(){ return fxmlLoader.getController();}

    /**
     * Function to show the window
     *
     * @param view the FXMLView of the window
     */
    protected void show(final FXMLView view) {
        if(view == null) throw new InvalidParameterException("FXML View is null");
        primaryStage.setTitle(view.getTitle());
        primaryStage.getIcons().add(view.getIconImage());
        primaryStage.centerOnScreen();
        
        try {
            primaryStage.show();
        } catch (Exception exception) {
            Platform.exit();
            logAndThrowException("Unable to show scene titled " + view.getTitle() + " Error: ",  exception);
        }
    }

    /**
     * Function to load the object hierarchy from a FXML document and returns the root node
     * of that hierarchy.
     *
     * @param view the FXMLView of the stage
     * @return Parent root node of the FXML document hierarchy
     */
    protected Parent loadViewNodeHierarchy(final FXMLView view) {
        if(view == null) throw new InvalidParameterException("FXML View is null");
        Parent rootNode = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource(view.getFxmlLoc()), Main.getBundle());
            rootNode = fxmlLoader.load();
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            Platform.exit();
            logAndThrowException("Unable to load FXML view " + view.getFxmlName() + " Error: ", exception);
        }
        return rootNode;
    }

    /**
     * Helper function to log any errors in setting up the stage and close the stage
     *
     * @param errorMsg the error message to log
     * @param exception the exception that occurred
     */
    protected void logAndThrowException(String errorMsg, Exception exception) throws RuntimeException {
        if(errorMsg == null || "".equals(errorMsg)) errorMsg = Main.getString("defaultMessage");

        if(exception == null) LOGR.log(Level.SEVERE, errorMsg);
        else LOGR.log(Level.SEVERE, errorMsg, exception);

        throw new RuntimeException(errorMsg);
    }
}
