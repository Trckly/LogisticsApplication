package controllers;

import entities.DriverLicense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.DriverLicenseService;

import java.time.LocalDate;

public class DriverLicensesController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<DriverLicense> licenseTable;
    @FXML
    private TableColumn<DriverLicense, String> idColumn;
    @FXML
    private TableColumn<DriverLicense, String> issuingAuthorityColumn;
    @FXML
    private TableColumn<DriverLicense, LocalDate> dateOfIssueColumn;

    private DriverLicenseService licenseService = new DriverLicenseService();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        issuingAuthorityColumn.setCellValueFactory(new PropertyValueFactory<>("issuer"));
        dateOfIssueColumn.setCellValueFactory(new PropertyValueFactory<>("dateIssued"));

        loadDriverLicenses();
    }

    public void loadDriverLicenses() {
        ObservableList<DriverLicense> data = FXCollections.observableArrayList(licenseService.getAllDriverLicenses());
        licenseTable.setItems(data);
    }

    public void handleSearch() {
        String query = searchField.getText();
        ObservableList<DriverLicense> filteredData = FXCollections.observableArrayList(licenseService.searchDriverLicenses(query));
        licenseTable.setItems(filteredData);
    }

    public void handleDelete() {
        DriverLicense selectedLicense = licenseTable.getSelectionModel().getSelectedItem();

        if (selectedLicense != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete this license?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    DriverLicenseService licenseService = new DriverLicenseService();
                    licenseService.deleteDriverLicense(selectedLicense);
                    loadDriverLicenses(); // Refresh the table
                }
            });
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("No Selection");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a license to delete.");
            errorAlert.showAndWait();
        }
    }
}