package com.marcsllite.controller;

import com.marcsllite.util.handler.ImageHandler;
import com.marcsllite.util.handler.PropHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.InvalidParameterException;

public class MenuPaneController extends BaseController {
    // Declaring FXML objects
    @FXML protected GridPane menuPane;
    @FXML protected StackPane stackPaneLogo;
    @FXML protected ImageView imgViewColorLogo;
    @FXML protected ImageView imgViewGreyLogo;
    @FXML protected Button btnShipment;
    @FXML protected ImageView imgViewShipment;
    @FXML protected Button btnReference;
    @FXML protected ImageView imgViewReference;

    private static final Logger logr = LogManager.getLogger();
    // color to make the button corresponding to the current page
    private static final ImageHandler.AppColor CURRENT_COLOR = ImageHandler.AppColor.UML_BLUE;
    // color to make idle buttons
    private static final ImageHandler.AppColor IDLE_COLOR = ImageHandler.AppColor.DEFAULT_GREY;
    // color to make buttons when mouse is hovering over
    private static final ImageHandler.AppColor HOVER_COLOR = ImageHandler.AppColor.DEFAULT_WHITE;
    private Page currentPage;

    public enum Page {
        SHIPMENT("Shipment Page"),
        REFERENCE("Reference Page"),
        DETAILS("Shipment Details"),
        SUMMARY("Shipment Summary"),
        EMPTY("");

        private final String val;

        Page(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    public MenuPaneController() throws IOException {
        this(null);
    }

    public MenuPaneController(PropHandler propHandler) throws IOException {
        super(propHandler, BaseController.Page.MENU);
    }

    /**
     * FXML Function to initialize GUI (run when the menuPane.fxml file is loaded by the FXMLLoader)
     */
    @Override
    @FXML public void initialize() {
        super.initialize();
        mouseLogoExit(); 
        mouseShipmentExit();
        mouseReferenceExit();

        setCurrentButton(btnShipment);
        setInit(true);
    }

    @Override
    public void show() {
        menuPane.setVisible(true);
        menuPane.toFront();
    }

    @Override
    public void hide() {
        menuPane.setVisible(false);
        menuPane.toBack();
    }

    /*///////////////////////////////////////////// MENU PANE CONTROLLER /////////////////////////////////////////////*/

    /**
     * FXML function to handle any menu button pressed
     *
     * @param event the action performed
     */
    @FXML protected void menuPaneHandler(ActionEvent event) throws InvalidParameterException {
        if(event == null) {
            var e = new InvalidParameterException("action event cannot be null");
            logr.throwing(e);
            throw e;
        }

        else if(event.getSource() == btnShipment) {
            shipmentBtnHandler();
        } else if(event.getSource() == btnReference) {
            referenceBtnHandler();
        }
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Helper function to handle the shipment button being pressed
     */
    protected void shipmentBtnHandler(){
        // checking if user is already on the Shipment page
        if(getCurrentPage() != Page.SHIPMENT) {
            setCurrentButton(btnShipment);  // setting the current button
            getMain().showHomePane();
        }
    }

    /**
     * Helper function to handle the reference button being pressed
     */
    protected void referenceBtnHandler(){
        // checking if user is already on the Shipment page
        if(getCurrentPage() != Page.REFERENCE) {
            setCurrentButton(btnReference);  // setting the current button
            getMain().showReferencePane();
        }
    }

    /**
     * Helper function to mark the button that corresponds to the page the user is currently viewing
     *
     * @param btnCurrent the menu button that corresponds with the current page the user is on
     */
    protected void setCurrentButton(Button btnCurrent) throws InvalidParameterException {
        // marking the given button
        if(btnCurrent == null) {
            var e = new InvalidParameterException("The current button cannot be null"); 
            logr.throwing(e);
            throw e;
        }

        // un-marking previous button
        unsetCurrentPage();

        if(btnCurrent.equals(btnShipment)) {
            setButtonColor(btnShipment, getCurrentColor());  // setting the single button and icon to be the current color
            currentPage = Page.SHIPMENT;
        } else if(btnCurrent.equals(btnReference)) {
            setButtonColor(btnReference, getCurrentColor());  // setting the multiple button and icon to be the current color
            currentPage = Page.REFERENCE;
        }
    }

    /**
     * Helper function to un-mark the previously marked button
     */
    protected void unsetCurrentPage() {
        currentPage = Page.EMPTY;

        // changing the color of the buttons to the idle color
        setButtonColor(btnShipment, getIdleColor());  // setting the single button and icon to be the current color
        setButtonColor(btnReference, getIdleColor());  // setting the multiple button and icon to be the current color
    }

    /**
     * Helper function to change the color of the given menu button text and icon
     *
     * @param btnMenu the menu button to set the color for
     * @param color the color to set the menu button and icon
     */
    protected void setButtonColor(Button btnMenu, ImageHandler.AppColor color) throws InvalidParameterException {
        if(btnMenu == null) {
            var e = new InvalidParameterException("The menu button cannot be null");
            logr.throwing(e);
            throw e;
        } else if(color == null) {
            var e = new InvalidParameterException("The color cannot be null");
            logr.throwing(e);
            throw e;
        }

        if(btnShipment.equals(btnMenu)) {
            btnShipment.setTextFill(Color.web(color.getVal()));  // changing Shipment button text color
            imgViewShipment.setImage(ImageHandler.getShipmentImage(color));  // changing icon color
        } else if(btnReference.equals(btnMenu)) {
            btnReference.setTextFill(Color.web(color.getVal()));  // changing Reference button text color
            imgViewReference.setImage(ImageHandler.getReferenceImage(color));  // changing icon color
        }
    }

    /**
     * FXML function to handle mouse hovering over UMass Lowell logo
     */
    @FXML protected void mouseLogoEnter(){
        imgViewColorLogo.setVisible(false);  // hiding colored logo
        imgViewGreyLogo.toFront();  // bringing grey logo to front
        imgViewGreyLogo.setVisible(true);  // showing grey logo
    }

    /**
     * FXML function to handle mouse leaving UMass Lowell logo
     */
    @FXML protected void mouseLogoExit(){
        imgViewGreyLogo.setVisible(false);  // hiding grey logo
        imgViewColorLogo.toFront();  // bringing colored logo to front
        imgViewColorLogo.setVisible(true);  // showing colored logo
    }

    /**
     * FXML function to handle mouse hovering over single button
     */
    @FXML protected void mouseShipmentEnter(){ setButtonColor(btnShipment, getHoverColor()); }

    /**
     * FXML function to handle mouse leaving single button
     */
    @FXML protected void mouseShipmentExit(){
        if(getCurrentPage() == Page.SHIPMENT) {
            setButtonColor(btnShipment, getCurrentColor());
        } else {
            setButtonColor(btnShipment, getIdleColor());
        }
    }

    /**
     * FXML function to handle mouse hovering over multiple button
     */
    @FXML protected void mouseReferenceEnter(){ setButtonColor(btnReference, getHoverColor()); }

    /**
     * FXML function to handle mouse leaving multiple button
     */
    @FXML protected void mouseReferenceExit(){
        if(getCurrentPage() == Page.REFERENCE) {
            setButtonColor(btnReference, getCurrentColor());
        } else {
            setButtonColor(btnReference, getIdleColor());
        }
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/

    /**
     * Getter function to get the current menu button
     *
     * @return the current button in integer form
     */
    public Page getCurrentPage() { return currentPage; }

    public ImageHandler.AppColor getCurrentColor() {
        return CURRENT_COLOR;
    }

    public ImageHandler.AppColor getIdleColor() {
        return IDLE_COLOR;
    }

    public ImageHandler.AppColor getHoverColor() {
        return HOVER_COLOR;
    }
}
