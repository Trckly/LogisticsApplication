package controllers;

import controllers.dialog.AddAddressDialogController;
import entities.Address;
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
import services.AddressService;

public class AddressesController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Address> addressTable;
    @FXML
    private TableColumn<Address, String> idColumn;
    @FXML
    private TableColumn<Address, String> provinceColumn;
    @FXML
    private TableColumn<Address, String> settlementColumn;
    @FXML
    private TableColumn<Address, String> streetColumn;
    @FXML
    private TableColumn<Address, String> streetNumberColumn;
    @FXML
    private TableColumn<Address, String> companyColumn;

    private final AddressService addressService = new AddressService();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        settlementColumn.setCellValueFactory(new PropertyValueFactory<>("settlement"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        streetNumberColumn.setCellValueFactory(new PropertyValueFactory<>("streetNumber"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        loadAddresses();
    }

    public void loadAddresses() {
        ObservableList<Address> data = FXCollections.observableArrayList(addressService.getAllAddresses());
        addressTable.setItems(data);
    }

    public void handleSearch() {
        String query = searchField.getText();
        ObservableList<Address> filteredData = FXCollections.observableArrayList(addressService.searchAddresses(query));
        addressTable.setItems(filteredData);
    }

    public void showAddAddress() {
        try {
            // Load the FXML for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dialogs/AddAddressDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Address");

            // Create the scene from the FXML
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            // Set the dialog to be modal
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(addressTable.getScene().getWindow());

            // Set the controller for the dialog
            AddAddressDialogController controller = loader.getController();
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