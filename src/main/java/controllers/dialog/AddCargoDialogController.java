package controllers.dialog;

import entities.Cargo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CargoService;

import java.util.UUID;

public class AddCargoDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField volumeField;

    private CargoService cargoService = new CargoService();
    private Stage dialogStage;
    private Cargo createdCargo;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Cargo getCreatedCargo() {
        return createdCargo;
    }

    @FXML
    public void handleSave() {
        try {
            String name = nameField.getText();
            double weight = Double.parseDouble(weightField.getText());
            double volume = Double.parseDouble(volumeField.getText());

            if (name.isEmpty()) {
                showErrorDialog("Validation Error", "Name is required.");
                return;
            }

            Cargo cargo = new Cargo();
            cargo.setId(UUID.randomUUID());
            cargo.setDenomination(name);
            cargo.setWeight(weight);
            cargo.setVolume(volume);

            cargoService.addCargo(cargo);
            createdCargo = cargo;

            dialogStage.close();
        } catch (NumberFormatException e) {
            showErrorDialog("Validation Error", "Weight and Volume must be numeric values.");
        } catch (Exception e) {
            showErrorDialog("Error", "An error occurred while saving the cargo.\n" + e.getMessage());
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