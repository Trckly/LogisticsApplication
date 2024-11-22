package controllers;

import controllers.dialog.AddCargoDialogController;
import entities.Cargo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.CargoService;

public class CargoController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Cargo> cargoTable;
    @FXML
    private TableColumn<Cargo, String> idColumn;
    @FXML
    private TableColumn<Cargo, String> nameColumn;
    @FXML
    private TableColumn<Cargo, Double> weightColumn;
    @FXML
    private TableColumn<Cargo, Double> volumeColumn;

    private CargoService cargoService = new CargoService();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));

        loadCargo();
    }

    public void loadCargo() {
        ObservableList<Cargo> data = FXCollections.observableArrayList(cargoService.getAllCargo());
        cargoTable.setItems(data);
    }

    public void handleSearch() {
        String query = searchField.getText();
        ObservableList<Cargo> filteredData = FXCollections.observableArrayList(cargoService.searchCargo(query));
        cargoTable.setItems(filteredData);
    }

    public void showAddCargo() {
        try {
            // Load the FXML for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dialogs/AddCargoDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Cargo");

            // Create the scene from the FXML
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            // Set the dialog to be modal
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(cargoTable.getScene().getWindow());

            // Set the controller for the dialog
            AddCargoDialogController controller = loader.getController();
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
        Cargo selectedCargo = cargoTable.getSelectionModel().getSelectedItem();

        if (selectedCargo != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete this cargo?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    CargoService cargoService = new CargoService();
                    cargoService.deleteCargo(selectedCargo);
                    loadCargo(); // Refresh the table
                }
            });
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("No Selection");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select a cargo to delete.");
            errorAlert.showAndWait();
        }
    }
}