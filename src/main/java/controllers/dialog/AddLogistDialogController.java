package controllers.dialog;

import entities.Logist;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.LogistService;

import java.util.UUID;

public class AddLogistDialogController {

    private Logist createdLogist;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;

    private LogistService logistService = new LogistService(); // Service to handle database operations

    private Stage dialogStage; // Stage for the modal dialog

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void handleSave() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String surname = surnameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();

            // Input validation
            if (firstName.isEmpty() || lastName.isEmpty() || surname.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                showErrorDialog("Validation Error", "All fields are required.");
                return;
            }

            // Create a new Logist object
            Logist newLogist = new Logist();
            newLogist.setId(UUID.randomUUID());
            newLogist.setFirstName(firstName);
            newLogist.setLastName(lastName);
            newLogist.setSurname(surname);
            newLogist.setPhoneNumber(phoneNumber);
            newLogist.setEmail(email);

            // Save the new logist using the service
            logistService.addLogist(newLogist);

            createdLogist = newLogist;

            // Close the dialog after saving
            dialogStage.close();
        } catch (Exception e) {
            showErrorDialog("Error", "An error occurred while saving the logist.");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel() {
        dialogStage.close(); // Close the dialog without saving
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Logist getCreatedLogist() {
        return createdLogist;
    }
}
