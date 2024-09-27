module radShipmentCalc {
    requires javafx.fxml;
    requires javafx.controls;

    requires org.apache.logging.log4j;
    requires java.desktop;
    requires plexus.utils;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

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