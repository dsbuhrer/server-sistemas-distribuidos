module com.example.serversd {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires jjwt.api;
    requires jjwt.impl;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires jbcrypt;

    opens com.sd.view.modal to javafx.fxml;
    opens com.sd.view to javafx.fxml;
    opens com.sd.server.Models;
    opens com.sd.server.Exceptions;
    opens com.sd.server;

    exports com.sd.view.modal;
    exports com.sd.view.ConnectionList;
    exports com.sd.server.Packages to com.fasterxml.jackson.databind;
    exports com.sd.server.Base to com.fasterxml.jackson.databind;
    exports com.sd.server.Packages.data.request.login to com.fasterxml.jackson.databind;
    exports com.sd.server.Packages.data.response.login to com.fasterxml.jackson.databind;
    exports com.sd.server.Packages.data.request.user to com.fasterxml.jackson.databind;
    exports com.sd.server.Packages.data.request.logout to com.fasterxml.jackson.databind;
    exports com.sd.server.Packages.data.response.user to com.fasterxml.jackson.databind;

    exports com.sd.server.Models;
    exports com.sd.view;
}
