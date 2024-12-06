module org.eftmarket.eftmarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
    requires java.net.http;
    requires javafx.swing;


    opens org.eftmarket.eftmarket to javafx.fxml;
    exports org.eftmarket.eftmarket;

    opens org.eftmarket.eftmarket.controllers to javafx.fxml;
    exports org.eftmarket.eftmarket.controllers;

    opens org.eftmarket.eftmarket.models to com.google.gson;
    exports org.eftmarket.eftmarket.models;
}