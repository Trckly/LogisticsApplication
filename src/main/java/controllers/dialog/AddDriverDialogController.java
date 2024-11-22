package controllers.dialog;

import entities.Driver;
import entities.DriverLicense;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.DriverLicenseService;
import services.DriverService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class AddDriverDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField licenseNumberField;
    @FXML
    private TextField issuingAuthorityField;
    @FXML
    private TextField issueDateField;

    private final DriverService driverService = new DriverService();
    private final DriverLicenseService driverLicenseService = new DriverLicenseService();
    private Stage dialogStage;
    private Driver createdDriver;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Driver getCreatedDriver() {
        return createdDriver;
    }

    @FXML
    public void handleSave() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String surname = surnameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String licenseNumber = licenseNumberField.getText();
            String issuingAuthority = issuingAuthorityField.getText();
            LocalDate issueDate = LocalDate.parse(issueDateField.getText());

            if (firstName.isEmpty() || lastName.isEmpty() || surname.isEmpty() || phoneNumber.isEmpty()
                    || licenseNumber.isEmpty() || issuingAuthority.isEmpty()) {
                showErrorDialog("Validation Error", "All fields are required.");
                return;
            }

            Driver driver = new Driver();
            driver.setId(UUID.randomUUID());
            driver.setFirstName(firstName);
            driver.setLastName(lastName);
            driver.setSurname(surname);
            driver.setPhoneNumber(phoneNumber);

            DriverLicense driverLicense = new DriverLicense();
            driverLicense.setId(licenseNumber);
            driverLicense.setIssuer(issuingAuthority);
            driverLicense.setDateIssued(issueDate);

            driverLicense.setDriver(driver);
            driver.setDrivingLicense(driverLicense);

            driverLicenseService.addDriverLicense(driverLicense);
            driverService.addDriver(driver);
            createdDriver = driver;

            dialogStage.close();
        } catch (DateTimeParseException e) {
            showErrorDialog("Validation Error", "Date of Issue must be in YYYY-MM-DD format.");
        } catch (Exception e) {
            showErrorDialog("Error", "An error occurred while saving the driver.");
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