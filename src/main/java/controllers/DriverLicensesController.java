package controllers;

import entities.DriverLicense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
}