package rad.shipment.calculator.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import rad.shipment.calculator.helpers.ImageHandler;

import java.security.InvalidParameterException;
import java.util.logging.Logger;

public class MenuPaneController {
    // Declaring FXML objects
    @FXML private ImageView imgViewColorLogo;
    @FXML private ImageView imgViewGreyLogo;
    @FXML private Button btnShipment;
    @FXML private ImageView imgViewShipment;
    @FXML private Button btnReference;
    @FXML private ImageView imgViewReference;

    // Declaring variables
    private static final Logger LOGR = Logger.getLogger(MenuPaneController.class.getName());  // getting logger
    private MainController mainController;
    private final String CURRENT_COLOR = Main.getString("umlBlue");  // color to make the button corresponding to the current page
    private final String IDLE_COLOR = Main.getString("defaultGrey");  // color to make idle buttons
    private final String HOVER_COLOR = Main.getString("defaultWhite");  // color to make buttons when mouse is hovering over
    private int btnClicks;  // variable to keep track of the number of times user clicked
                            // a button even though they were already on the page associated with that button
    private int currentBtn;  // variable to know what page the user is currently on
                              // 0 = Shipment, 1 = Reference

    /*///////////////////////////////////////////////// START/SETUP //////////////////////////////////////////////////*/

    /**
     * FXML Function to initialize GUI (run when the edit.fxml file is loaded by the FXMLLoader)
     */
    @FXML private void initialize(){
        mouseLogoExit(); // Making sure logo is grey

        // Making sure menu button text is white
        // mouseHomeExit();
        mouseShipmentExit();
        mouseReferenceExit();

        // Making the Home button turn blue to show that it's selected
        setCurrentButton(btnShipment);
        btnClicks = 0;
    }

    /**
     * Function to connect the Menu Pane Controller with the current MainController
     * (Allows the Menu Pane Controller to have access to the other controllers in the MainController through this link)
     *
     * @param mainController the instance of the current MainController
     */
    void injectMainController(MainController mainController){ setMainController(mainController); }

    /*///////////////////////////////////////////// MENU PANE CONTROLLER /////////////////////////////////////////////*/

    /**
     * FXML function to handle any menu button pressed
     *
     * @param event the action performed
     */
    @FXML private void menuPaneHandler(ActionEvent event) {
        if(event == null) throw new InvalidParameterException("action event cannot be null");

        else if(event.getSource() == btnShipment) shipmentBtnHandler();
        else if(event.getSource() == btnReference) referenceBtnHandler();
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Helper function to handle the UMass Lowell logo being pressed
     */
    @FXML private void LogoImgViewHandler(){
        LOGR.info("User clicked the UMass logo in the menu pane");

        Main.navigateToLink("https://www.uml.edu/");  // navigating to UMass Lowell website
    }

    /**
     * Helper function to handle the shipment button being pressed
     */
    private void shipmentBtnHandler(){
        // checking if user is already on the Shipment page
        if(currentBtn == 0) LOGR.info("User clicked the "  + btnShipment.getText() + " menu button (" + ++btnClicks + ")");
        else {
            setCurrentButton(btnShipment);  // setting the current button
        }
    }

    /**
     * Helper function to handle the reference button being pressed
     */
    private void referenceBtnHandler(){
        // checking if user is already on the Shipment page
        if(currentBtn == 1) LOGR.info("User clicked the "  + btnReference.getText() + " menu button (" + ++btnClicks + ")");
        else {
            setCurrentButton(btnReference);  // setting the current button
        }
    }

    /**
     * Helper function to mark the button that corresponds to the page the user is currently viewing
     *
     * @param btnCurrent the menu button that corresponds with the current page the user is on
     */
    private void setCurrentButton(Button btnCurrent) {
        // un-marking previous button
        unsetCurrentButton();

        // marking the given button
        if(btnCurrent == null) throw new InvalidParameterException("The current button cannot be null");

        else if(btnCurrent.equals(btnShipment)) {
            setButtonColor(btnShipment, CURRENT_COLOR);  // setting the single button and icon to be the current color
            currentBtn = 0;  // noting that the Shipment button is marked
            LOGR.info("Navigated to " + btnShipment.getText() + " page.");
        } else if(btnCurrent.equals(btnReference)) {
            setButtonColor(btnReference, CURRENT_COLOR);  // setting the multiple button and icon to be the current color
            currentBtn = 1;  // noting that the Reference button is marked
            LOGR.info("Navigated to " + btnReference.getText() + " page.");
        } else throw new InvalidParameterException("The button is not a menu button");
    }

    /**
     * Helper function to un-mark the previously marked button
     */
    private void unsetCurrentButton() {
        currentBtn = Main.getInt("defaultInt");

        // changing the color of the buttons to the idle color
        setButtonColor(btnShipment, IDLE_COLOR);  // setting the single button and icon to be the current color
        setButtonColor(btnReference, IDLE_COLOR);  // setting the multiple button and icon to be the current color
    }

    /**
     * Helper function to change the color of the given menu button text and icon
     *
     * @param btnMenu the menu button to set the color for
     * @param color the color to set the menu button and icon
     */
    private void setButtonColor(Button btnMenu, String color) {
        // making sure user is giving valid values
        if(btnMenu == null) throw new InvalidParameterException("The menu button cannot be null");
        if(color == null) throw new InvalidParameterException("The color cannot be null");
        if(!color.equals(Main.getString("umlBlue")) &&
                !color.equals(Main.getString("defaultWhite")) &&
                !color.equals(Main.getString("defaultGrey"))) throw new InvalidParameterException("The color is invalid");

        else if(btnMenu.equals(btnShipment)) {
            btnShipment.setTextFill(Color.web(color));  // changing Shipment button text color
            imgViewShipment.setImage(ImageHandler.getShipmentImage(color));  // changing icon color
        } else if(btnMenu.equals(btnReference)) {
            btnReference.setTextFill(Color.web(color));  // changing Reference button text color
            imgViewReference.setImage(ImageHandler.getReferenceImage(color));  // changing icon color
        } else throw new InvalidParameterException("The button is not a menu button");
    }

    /**
     * FXML function to handle mouse hovering over UMass Lowell logo
     */
    @FXML private void mouseLogoEnter(){
        imgViewColorLogo.setVisible(false);  // hiding colored logo
        imgViewGreyLogo.toFront();  // bringing grey logo to front
        imgViewGreyLogo.setVisible(true);  // showing grey logo
    }

    /**
     * FXML function to handle mouse leaving UMass Lowell logo
     */
    @FXML private void mouseLogoExit(){
        imgViewGreyLogo.setVisible(false);  // hiding grey logo
        imgViewColorLogo.toFront();  // bringing colored logo to front
        imgViewColorLogo.setVisible(true);  // showing colored logo
    }

    /**
     * FXML function to handle mouse hovering over single button
     */
    @FXML private void mouseShipmentEnter(){ setButtonColor(btnShipment, HOVER_COLOR); }

    /**
     * FXML function to handle mouse leaving single button
     */
    @FXML private void mouseShipmentExit(){
        if(currentBtn == 0) setButtonColor(btnShipment, CURRENT_COLOR);
        else setButtonColor(btnShipment, IDLE_COLOR);
    }

    /**
     * FXML function to handle mouse hovering over multiple button
     */
    @FXML private void mouseReferenceEnter(){ setButtonColor(btnReference, HOVER_COLOR); }

    /**
     * FXML function to handle mouse leaving multiple button
     */
    @FXML private void mouseReferenceExit(){
        if(currentBtn == 1) setButtonColor(btnReference, CURRENT_COLOR);
        else setButtonColor(btnReference, IDLE_COLOR);
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/
    /**
     * Getter function to get the mainController instance
     *
     * @return the main controller instance
     */
    protected MainController getMainController() { return mainController; }

    /**
     * Getter function to get the color that the current menu button should be
     *
     * @return the color of the current menu button
     */
    public String getCURRENT_COLOR() { return CURRENT_COLOR; }

    /**
     * Getter function to get the color that idle menu buttons should be
     *
     * @return the color of idle menu buttons
     */
    public String getIDLE_COLOR() { return IDLE_COLOR; }

    /**
     * Getter function to get the color of the menu buttons when the mouse is hovering over them
     *
     * @return the color of the menu buttons when the mouse is hovering over them
     */
    public String getHOVER_COLOR() { return HOVER_COLOR; }

    /*/////////////////////////////////////////////////// SETTERS ////////////////////////////////////////////////////*/
    /**
     * Setter function to set the mainController instance
     *
     * @param mainController the new mainController instance
     */
    protected void setMainController(MainController mainController) { this.mainController = mainController; }
}
