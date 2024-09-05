package com.marcsllite.controller;

import com.marcsllite.util.handler.PropHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.InvalidParameterException;

public class SummaryPaneController extends BaseController {
    @FXML AnchorPane summaryPane;
    @FXML TextArea txtAreaSummary;
    @FXML Button btnSaveSummary;

    private static final Logger logr = LogManager.getLogger();

    public SummaryPaneController() throws IOException {
        this(null);
    }

    public SummaryPaneController(PropHandler propHandler) throws IOException {
        super(propHandler, Page.SUMMARY);
    }

    @Override
    @FXML public void initialize() {
        super.initialize();
        setInit(true);
    }

    @Override
    public void show() {
        summaryPane.setVisible(true);
        summaryPane.toFront();
    }

    @Override
    public void hide() {
        summaryPane.setVisible(false);
        summaryPane.toBack();
    }

    /*///////////////////////////////////////////// HOME PANE CONTROLLER /////////////////////////////////////////////*/

    /**
     * FXML function to handle any menu button pressed
     *
     * @param event the action performed
     */
    @FXML protected void summaryPaneHandler(ActionEvent event) throws InvalidParameterException {
        if(event == null) {
            var e = new InvalidParameterException("action event cannot be null");
            logr.throwing(e);
            throw e;
        } else if(event.getSource() == btnSaveSummary) {
            addBtnHandler();
        }
    }

    /*/////////////////////////////////////////////////// HELPERS ////////////////////////////////////////////////////*/

    /**
     * Helper function to handle the add button being pressed
     */
    @FXML protected void addBtnHandler(){
        logr.debug("User clicked the Add button on the summary pane");
    }
}
