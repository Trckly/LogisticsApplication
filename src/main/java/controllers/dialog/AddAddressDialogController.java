package controllers.dialog;

import entities.Address;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.AddressService;

public class AddAddressDialogController {

    @FXML
    private TextField provinceField;
    @FXML
    private TextField settlementField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField streetNumberField;
    @FXML
    private TextField companyNameField;

    private AddressService addressService = new AddressService();
    private Stage dialogStage;
    private Address createdAddress;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Address getCreatedAddress() {
        return createdAddress;
    }

    @FXML
    public void handleSave() {
        try {
            String province = provinceField.getText();
            String settlement = settlementField.getText();
            String street = streetField.getText();
            String streetNumber = streetNumberField.getText();
            String companyName = companyNameField.getText();

            if (province.isEmpty() || settlement.isEmpty() || street.isEmpty() || streetNumber.isEmpty() || companyName.isEmpty()) {
                showErrorDialog("Validation Error", "All fields are required.");
                return;
            }

            Address address = new Address();
            address.setProvince(province);
            address.setSettlement(settlement);
            address.setStreet(street);
            address.setStreetNumber(streetNumber);
            address.setCompanyName(companyName);

            addressService.addAddress(address);
            createdAddress = address;

            dialogStage.close();
        } catch (Exception e) {
            showErrorDialog("Error", "An error occurred while saving the address.");
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