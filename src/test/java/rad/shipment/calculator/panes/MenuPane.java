package rad.shipment.calculator.panes;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.loadui.testfx.GuiTest;
import rad.shipment.calculator.gui.Main;
import rad.shipment.calculator.gui.MenuPaneControllerTest;

import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static rad.shipment.calculator.FXIds.*;

public class MenuPane {
    private static Logger LOGR = Logger.getLogger(MenuPane.class.getName()); // matches the logger in the affected class
    private final MenuPaneControllerTest driver;
    final String DEFAULT_BTN = BTN_SHIPMENT;

    public MenuPane(MenuPaneControllerTest driver) {
        this.driver = driver;
        if (!driver.getPrimaryStage().getTitle().equals(Main.getString("menuPane"))) {
            throw new IllegalArgumentException("Did not receive the Menu Pane to test Menu");
        }
    }

    /*////////////////////////////////////////////////// MENU PANE ///////////////////////////////////////////////////*/

    /**
     * Function to check that the given Node changes color when
     * the mouse hovers over it
     *
     * @param node the Node to be checked
     * @return true if the hover worked as expected
     *         false if something went wrong
     */
    public boolean checkHover(Node node) throws InvalidParameterException {
        // checking to see if the given node is a menu pane node
        if(node == null) throw new InvalidParameterException("node cannot be null");

        if(node == getImgViewColorLogo()) {
            mouseOverReferenceBtn();
            ImageView before = getImgViewColorLogo();

            mouseOverLogo();

            return !before.isVisible() || !getImgViewColorLogo().isVisible();
        }

        else if(node == getImgViewGreyLogo()) {
            mouseOverReferenceBtn();
            ImageView before = getImgViewGreyLogo();

            mouseOverLogo();

            return !before.isVisible() || !getImgViewGreyLogo().isVisible();
        }

        else if(node == getBtnShipment()) {
            mouseOverReferenceBtn();
            Button before = getBtnShipment();

            mouseOverShipmentBtn();

            return before.getTextFill() != getBtnShipment().getTextFill();
        }

        else if(node == getImgViewShipment()) {
            mouseOverReferenceBtn();
            ImageView before = getImgViewShipment();

            mouseOverShipmentBtn();

            return before.getImage() != getImgViewShipment().getImage();
        }

        else if(node == getBtnReference()) {
            mouseOverShipmentBtn();
            Button before = getBtnReference();

            mouseOverReferenceBtn();

            return before.getTextFill() != getBtnReference().getTextFill();
        }

        else if(node == getImgViewReference()) {
            mouseOverShipmentBtn();
            ImageView before = getImgViewReference();

            mouseOverReferenceBtn();

            return before.getImage() != getImgViewReference().getImage();
        }

        else { throw new InvalidParameterException("node is not a menu pane node"); }
    }

    /*///////////////////////////////////////////////// CONVENIENCE //////////////////////////////////////////////////*/

    /**
     * Convenience method that clicks on the default button
     *
     * @return MenuPane.this (for method chaining)
     */
    public MenuPane clickDefaultBtn() {
        driver.clickOn(DEFAULT_BTN);
        return this;
    }

    /**
     * Convenience method that clicks on the UMass Lowell logo
     *
     * @return MenuPane.this (for method chaining)
     */
    public MenuPane clickLogo() {
        try { driver.clickOn(IMGVIEW_COLOR_LOGO); }
        catch (RuntimeException e) { LOGR.log(Level.WARNING, "Failed to open browser to link. Error: ", e); }
        return this;
    }

    /**
     * Convenience method that clicks on the Shipment button
     *
     * @return MenuPane.this (for method chaining)
     */
    public MenuPane clickShipmentBtn() {
        driver.clickOn(BTN_SHIPMENT);
        return this;
    }

    /**
     * Convenience method that clicks on the Reference button
     *
     * @return MenuPane.this (for method chaining)
     */
    public MenuPane clickReferenceBtn() {
        driver.clickOn(BTN_REFERENCE);
        return this;
    }

    /**
     * Convenience method that moves the mouse over the default button
     *
     * @return MenuPane.this (for method chaining)
     */
    public MenuPane mouseOverDefaultBtn() {
        driver.moveTo(DEFAULT_BTN);
        return this;
    }

    /**
     * Convenience method that moves the mouse over the UMass Lowell logo
     *
     * @return MenuPane.this (for method chaining)
     */
    public MenuPane mouseOverLogo() {
        driver.moveTo(IMGVIEW_GREY_LOGO);
        return this;
    }
    
    /**
     * Convenience method that moves the mouse over the Shipment button
     *
     * @return MenuPane.this (for method chaining)
     */
    public MenuPane mouseOverShipmentBtn() {
        driver.moveTo(BTN_SHIPMENT);
        return this;
    }

    /**
     * Convenience method that moves the mouse over the Reference button
     *
     * @return MenuPane.this (for method chaining)
     */
    public MenuPane mouseOverReferenceBtn() {
        driver.moveTo(BTN_REFERENCE);
        return this;
    }

    /*/////////////////////////////////////////////////// GETTERS ////////////////////////////////////////////////////*/

    /**
     * Getter function to get the fx:id of the default button
     * 
     * @return the fx:id of the default button
     */
    public String getDefaultBtn() { return DEFAULT_BTN; }

    /**
     * Getter function to get the image view containing the
     * UMass Lowell logo (colored)
     *
     * @return the the image view containing the colored UMass Lowell logo
     */
    public ImageView getImgViewColorLogo(){
        return GuiTest.find(IMGVIEW_COLOR_LOGO); }

    /**
     * Getter function to get the image view containing the
     * UMass Lowell logo (grey)
     *
     * @return the the image view containing the grey UMass Lowell logo
     */
    public ImageView getImgViewGreyLogo(){ return GuiTest.find(IMGVIEW_GREY_LOGO); }

    /**
     * Getter function to get the Shipment button
     *
     * @return the Shipment button
     */
    public Button getBtnShipment(){ return GuiTest.find(BTN_SHIPMENT); }

    /**
     * Getter function to get the image view containing the
     * Shipment button image
     *
     * @return the the image view containing the Shipment button image
     */
    public ImageView getImgViewShipment(){ return GuiTest.find(IMGVIEW_SHIPMENT); }

    /**
     * Getter function to get the Reference button
     *
     * @return the Reference button
     */
    public Button getBtnReference(){ return GuiTest.find(BTN_REFERENCE); }

    /**
     * Getter function to get the image view containing the
     * Reference button image
     *
     * @return the the image view containing the Reference button image
     */
    public ImageView getImgViewReference(){ return GuiTest.find(IMGVIEW_REFERENCE); }
}
