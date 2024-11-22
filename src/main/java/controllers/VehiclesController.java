package controllers;

import controllers.dialog.AddVehicleDialogController;
import entities.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.VehicleService;

public class VehiclesController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Vehicle> vehicleTable;
    @FXML
    private TableColumn<Vehicle, String> licensePlateColumn;
    @FXML
    private TableColumn<Vehicle, String> modelColumn;
    @FXML
    private TableColumn<Vehicle, Integer> productionYearColumn;
    @FXML
    private TableColumn<Vehicle, String> carryingCapacityColumn;

    private VehicleService vehicleService = new VehicleService();

    @FXML
    public void initialize() {
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        productionYearColumn.setCellValueFactory(new PropertyValueFactory<>("productionYear"));
        carryingCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("carryingCapacity"));

        loadVehicles();
    }

    public void loadVehicles() {
        ObservableList<Vehicle> data = FXCollections.observableArrayList(vehicleService.getAllVehicles());
        vehicleTable.setItems(data);
    }

    public void handleSearch() {
        String query = searchField.getText();
        ObservableList<Vehicle> filteredData = FXCollections.observableArrayList(vehicleService.searchVehicles(query));
        vehicleTable.setItems(filteredData);
    }

    public void showAddVehicle() {
        try {
            // Load the FXML for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dialogs/AddVehicleDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Vehicle");

            // Create the scene from the FXML
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            // Set the dialog to be modal
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(vehicleTable.getScene().getWindow());

            // Set the controller for the dialog
            AddVehicleDialogController controller = loader.getController();
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
}