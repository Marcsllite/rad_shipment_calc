package com.marcsllite.controller;

import com.marcsllite.service.DBService;
import com.marcsllite.service.DBServiceImpl;
import com.marcsllite.util.factory.PropHandlerFactory;
import com.marcsllite.util.handler.PropHandler;
import javafx.fxml.FXML;

import java.io.IOException;

public abstract class BaseController {
    private MainController main;
    private PropHandler propHandler;
    private DBService dbService;
    private Page page;
    private boolean init = false;
    public enum Page {NONE, MAIN, MENU, HOME, REFERENCE, ADD, EDIT, DETAILS, SUMMARY}

    protected BaseController() throws IOException {
        this(null);
    }

    protected BaseController(PropHandler propHandler) throws IOException {
        setPropHandler(propHandler == null? new PropHandlerFactory().getPropHandler(null): propHandler);
        setDbService(new DBServiceImpl(getPropHandler()));
        setMain(MainController.getInstance());
    }

    /**
     * FXML Function to initialize GUI
     * Links controller to main controller (mediator)
     */
    @FXML
    public void initialize() {
        getMain().registerController(this);
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page == null? Page.NONE : page;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    /**
     * Function to show the controller's scene
     */
    public abstract void show();
    
    /**
     * Function to hide the controller's scene
     */
    public abstract void hide();

    public PropHandler getPropHandler() {
        return propHandler;
    }

    public void setPropHandler(PropHandler propHandler) {
        this.propHandler = propHandler;
    }

    public DBService getDbService() {
        return dbService;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    public MainController getMain() {
        return main;
    }

    public void setMain(MainController main) {
        this.main = main;
    }
}
