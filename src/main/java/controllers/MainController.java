package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    @FXML
    private TabPane tabPane;

    public void showAddresses() {
        loadTab("Addresses", "/fxml/Addresses.fxml");
    }

    public void showOrders() {
        loadTab("Orders", "/fxml/Orders.fxml");
    }

    public void showCargo() {
        loadTab("Cargo", "/fxml/Cargo.fxml");
    }

    public void showVehicles() {
        loadTab("Vehicles", "/fxml/Vehicles.fxml");
    }

    public void showLogists() {
        loadTab("Logists", "/fxml/Logists.fxml");
    }

    public void showDrivers() {
        loadTab("Drivers", "/fxml/Drivers.fxml");
    }

    public void showDriverLicenses() {
        loadTab("DriverLicenses", "/fxml/DriverLicenses.fxml");
    }

    private void loadTab(String title, String fxmlPath) {
        Tab tab = new Tab(title);
        tab.setClosable(true);

        try {
            VBox box = FXMLLoader.load(getClass().getResource(fxmlPath));
            tab.setContent(box);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void handleExit() {
        System.exit(0);
    }
}