package rad.shipment.calculator.panes;

import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.Contract;
import org.loadui.testfx.GuiTest;
import rad.shipment.calculator.gui.HomePaneControllerTest;
import rad.shipment.calculator.gui.Main;
import rad.shipment.calculator.helpers.Isotope;

import java.security.InvalidParameterException;
import java.util.logging.Logger;

import static rad.shipment.calculator.FXIds.*;

public class HomePane {
    private static Logger LOGR = Logger.getLogger(HomePane.class.getName()); // matches the logger in the affected class
    private final HomePaneControllerTest driver;
    private final String DEFAULT_BTN = BTN_ADD;

    @Contract("null -> fail") public HomePane(HomePaneControllerTest driver) {
        if(driver == null) throw new InvalidParameterException("HomePaneControllerTest driver cannot be null");
        this.driver = driver;
        if (!driver.getPrimaryStage().getTitle().equals(Main.getString("homePane"))) {
            throw new IllegalArgumentException("Did not receive the Home Pane to test Home");
        }
    }

    /*////////////////////////////////////////////////// MENU PANE ///////////////////////////////////////////////////*/
    

    /*///////////////////////////////////////////////// CONVENIENCE //////////////////////////////////////////////////*/

    /**
     * Convenience method that clicks on the default button
     *
     * @return HomePane.this (for method chaining)
     */
    public HomePane clickDefaultBtn() {
        driver.clickOn(DEFAULT_BTN);
        return this;
    }

    /**
     * Convenience method that clicks on the Add button
     *
     * @return HomePane.this (for method chaining)
     */
    public HomePane clickAddBtn() {
        driver.clickOn(BTN_ADD);
        return this;
    }

    /**
     * Convenience method that clicks on the Edit button
     *
     * @return HomePane.this (for method chaining)
     */
    public HomePane clickEditBtn() {
        driver.clickOn(BTN_EDIT);
        return this;
    }

    /**
     * Convenience method that clicks on the Remove button
     *
     * @return HomePane.this (for method chaining)
     */
    public HomePane clickRemoveBtn() {
        driver.clickOn(BTN_REMOVE);
        return this;
    }

    /**
     * Convenience method that clicks on the Calculate button
     *
     * @return HomePane.this (for method chaining)
     */
    public HomePane clickCalculateBtn() {
        driver.clickOn(BTN_CALCULATE);
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
     * Getter function to get the Add button
     *
     * @return the Add button
     */
    public Button getBtnAdd(){ return GuiTest.find(BTN_ADD); }

    /**
     * Getter function to get the Edit button
     *
     * @return the Edit button
     */
    public Button getBtnEdit(){ return GuiTest.find(BTN_EDIT); }

    /**
     * Getter function to get the Remove button
     *
     * @return the Remove button
     */
    public Button getBtnRemove(){ return GuiTest.find(BTN_REMOVE); }

    /**
     * Getter function to get the tableView
     *
     * @return the tableView
     */
    public TableView<Isotope> getTableView(){ return GuiTest.find(TABLEVIEW); }

    /**
     * Getter function to get the Calculate button
     *
     * @return the Calculate button
     */
    public Button getBtnCalculate(){ return GuiTest.find(BTN_CALCULATE); }
}
