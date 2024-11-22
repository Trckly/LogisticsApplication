package controllers;

import controllers.dialog.AddDriverDialogController;
import entities.Driver;
import entities.DriverLicense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.DriverService;

public class DriversController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Driver> driverTable;
    @FXML
    private TableColumn<Driver, String> idColumn;
    @FXML
    private TableColumn<Driver, String> nameColumn;
    @FXML
    private TableColumn<Driver, String> phoneNumberColumn;
    @FXML
    private TableColumn<DriverLicense, String> licenseColumn;

    private DriverService driverService = new DriverService();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        licenseColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        loadDrivers();
    }

    public void loadDrivers() {
        ObservableList<Driver> data = FXCollections.observableArrayList(driverService.getAllDrivers());
        driverTable.setItems(data);
    }

    public void handleSearch() {
        String query = searchField.getText();
        ObservableList<Driver> filteredData = FXCollections.observableArrayList(driverService.searchDrivers(query));
        driverTable.setItems(filteredData);
    }

    public void showAddDriver() {
        try {
            // Load the FXML for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dialogs/AddDriverDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Driver");

            // Create the scene from the FXML
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            // Set the dialog to be modal
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(driverTable.getScene().getWindow());

            // Set the controller for the dialog
            AddDriverDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog
            dialogStage.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load the add order dialog.");
            alert.showAndWait();
        }
    }

    public void handleDelete() {
        Driver selectedDriver = driverTable.getSelectionModel().getSelectedItem();

        if (selectedDriver != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete this driver?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    DriverService driverService = new DriverService();
                    driverService.deleteDriver(selectedDriver);
                    loadDrivers(); // Refresh the table
                }
            });
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("No Selection");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a driver to delete.");
            errorAlert.showAndWait();
        }
    }
}