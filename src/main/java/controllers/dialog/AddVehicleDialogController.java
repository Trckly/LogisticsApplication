package controllers.dialog;

import entities.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.VehicleService;

public class AddVehicleDialogController {

    @FXML
    private TextField licensePlateField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField productionYearField;
    @FXML
    private TextField carryingCapacityField;

    private VehicleService vehicleService = new VehicleService();
    private Stage dialogStage;
    private Vehicle createdVehicle;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Vehicle getCreatedVehicle() {
        return createdVehicle;
    }

    @FXML
    public void handleSave() {
        try {
            String licensePlate = licensePlateField.getText();
            String model = modelField.getText();
            int productionYear = Integer.parseInt(productionYearField.getText());
            double carryingCapacity = Double.parseDouble(carryingCapacityField.getText());

            if (licensePlate.isEmpty() || model.isEmpty()) {
                showErrorDialog("Validation Error", "License Plate and Model are required.");
                return;
            }

            Vehicle vehicle = new Vehicle();
            vehicle.setLicensePlate(licensePlate);
            vehicle.setModel(model);
            vehicle.setProductionYear(productionYear);
            vehicle.setCarryingCapacity(carryingCapacity);

            vehicleService.addVehicle(vehicle);
            createdVehicle = vehicle;

            dialogStage.close();
        } catch (NumberFormatException e) {
            showErrorDialog("Validation Error", "Production Year and Carrying Capacity must be numeric.");
        } catch (Exception e) {
            showErrorDialog("Error", "An error occurred while saving the vehicle.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}