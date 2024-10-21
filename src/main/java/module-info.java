module radShipmentCalc {
    // javafx dependencies
    requires javafx.controls;
    requires javafx.fxml;

    // logging dependencies
    requires org.apache.logging.log4j;

    // database dependencies
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires com.h2database;

    // other dependencies
    requires java.desktop; // javax.swing.filechooser.FileSystemView
    requires org.apache.commons.lang3; // org.apache.commons.lang3.StringUtils

    opens com.marcsllite;
    opens com.marcsllite.controller;
    opens com.marcsllite.dao;
    opens com.marcsllite.model;
    opens com.marcsllite.model.db;
    opens com.marcsllite.service;
    opens com.marcsllite.util;
    opens com.marcsllite.util.controller;
    opens com.marcsllite.util.factory;
    opens com.marcsllite.util.handler;

    exports com.marcsllite;
    exports com.marcsllite.controller;
    exports com.marcsllite.dao;
    exports com.marcsllite.model;
    exports com.marcsllite.model.db;
    exports com.marcsllite.service;
    exports com.marcsllite.util;
    exports com.marcsllite.util.controller;
    exports com.marcsllite.util.factory;
    exports com.marcsllite.util.handler;
}