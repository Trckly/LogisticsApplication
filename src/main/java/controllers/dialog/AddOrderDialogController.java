package controllers.dialog;

import entities.*;
import entities.enums.AddressType;
import entities.enums.OrderStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.*;

import java.math.BigDecimal;
import java.util.UUID;

public class AddOrderDialogController {

    @FXML
    private TextField clientNameField;
    @FXML
    private TextField carrierNameField;
    @FXML
    private TextField clientPriceField;
    @FXML
    private TextField carrierPriceField;
    @FXML
    private ComboBox<Logist> logistComboBox;
    @FXML
    private ComboBox<Address> initialAddressComboBox;
    @FXML
    private ComboBox<Address> destinationAddressComboBox;
    @FXML
    private ComboBox<Cargo> cargoComboBox;
    @FXML
    private ComboBox<Driver> driverComboBox;
    @FXML
    private ComboBox<Vehicle> vehicleComboBox;
    @FXML
    private ComboBox<OrderStatus> statusComboBox;

    private OrderService orderService = new OrderService();
    private LogistService logistService = new LogistService();
    private AddressService addressService = new AddressService();
    private CargoService cargoService = new CargoService();
    private DriverService driverService = new DriverService();
    private VehicleService vehicleService = new VehicleService();
    private OrdersAddressService ordersAddressService = new OrdersAddressService();
    private OrdersCargoService ordersCargoService = new OrdersCargoService();

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void initialize() {
        loadComboBoxData();
    }

    private void loadComboBoxData() {
        // Load the data for each ComboBox from the respective services
        logistComboBox.setItems(FXCollections.observableArrayList(logistService.getAllLogists()));
        initialAddressComboBox.setItems(FXCollections.observableArrayList(addressService.getAllAddresses()));
        destinationAddressComboBox.setItems(FXCollections.observableArrayList(addressService.getAllAddresses()));
        cargoComboBox.setItems(FXCollections.observableArrayList(cargoService.getAllCargo()));
        driverComboBox.setItems(FXCollections.observableArrayList(driverService.getAllDrivers()));
        vehicleComboBox.setItems(FXCollections.observableArrayList(vehicleService.getAllVehicles()));
        statusComboBox.setItems(FXCollections.observableArrayList(OrderStatus.values()));
    }

    @FXML
    public void handleSave() {
        try {
            String clientCompanyName = clientNameField.getText();
            String carrierCompanyName = carrierNameField.getText();
            BigDecimal clientPrice = new BigDecimal(clientPriceField.getText());
            BigDecimal carrierPrice = new BigDecimal(carrierPriceField.getText());

            // Get selected items from ComboBoxes
            Logist selectedLogist = logistComboBox.getSelectionModel().getSelectedItem();
            Address initialAddress = initialAddressComboBox.getSelectionModel().getSelectedItem();
            Address destinationAddress = destinationAddressComboBox.getSelectionModel().getSelectedItem();
            Cargo selectedCargo = cargoComboBox.getSelectionModel().getSelectedItem();
            Driver selectedDriver = driverComboBox.getSelectionModel().getSelectedItem();
            Vehicle selectedVehicle = vehicleComboBox.getSelectionModel().getSelectedItem();
            OrderStatus selectedStatus = statusComboBox.getSelectionModel().getSelectedItem();

            if (selectedLogist == null || initialAddress == null || destinationAddress == null || selectedCargo == null || selectedDriver == null || selectedVehicle == null) {
                showErrorDialog("Missing Selection", "Please select all fields.");
                return;
            }

            // Create new Order
            Order newOrder = new Order();
            newOrder.setId(UUID.randomUUID());
            newOrder.setClientCompanyName(clientCompanyName);
            newOrder.setCarrierCompanyName(carrierCompanyName);
            newOrder.setClientPrice(clientPrice);
            newOrder.setCarrierPrice(carrierPrice);
            newOrder.setLogist(selectedLogist);
            newOrder.setDriver(selectedDriver);
            newOrder.setVehicle(selectedVehicle);
            newOrder.setStatus(selectedStatus);

            // Save the new order using the service
            orderService.addOrder(newOrder);

            OrdersAddress initOrdersAddress = new OrdersAddress();
            initOrdersAddress.setId(UUID.randomUUID());
            initOrdersAddress.setOrder(newOrder);
            initOrdersAddress.setAddress(initialAddress);
            initOrdersAddress.setAddressType(AddressType.initial);

            OrdersAddress destOrdersAddress = new OrdersAddress();
            destOrdersAddress.setId(UUID.randomUUID());
            destOrdersAddress.setOrder(newOrder);
            destOrdersAddress.setAddress(destinationAddress);
            destOrdersAddress.setAddressType(AddressType.destination);

            ordersAddressService.addOrderAddress(initOrdersAddress);
            ordersAddressService.addOrderAddress(destOrdersAddress);

            OrdersCargo ordersCargo = new OrdersCargo();
            ordersCargo.setId(UUID.randomUUID());
            ordersCargo.setOrder(newOrder);
            ordersCargo.setCargo(selectedCargo);

            ordersCargoService.addOrdersCargo(ordersCargo);

            // Close the dialog after saving
            dialogStage.close();
        } catch (Exception e) {
            showErrorDialog("Invalid Input", "Please check the input fields.\n" + e.getMessage());
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

    @FXML
    public void createLogist() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/AddLogistDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Logist");

            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(logistComboBox.getScene().getWindow());

            AddLogistDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait for it to close
            dialogStage.showAndWait();

            Logist createdLogist = controller.getCreatedLogist();
            if (createdLogist != null) {
                // Refresh the ComboBox and select the new Logist
                refreshLogistComboBox();
                logistComboBox.getSelectionModel().select(createdLogist);
            }
        } catch (Exception e) {
            showErrorDialog("Error", "Could not open Create Logist dialog.");
        }
    }

    private void refreshLogistComboBox() {
        logistComboBox.setItems(FXCollections.observableArrayList(logistService.getAllLogists()));
    }

    @FXML
    public void createDriver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/AddDriverDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Driver");

            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(driverComboBox.getScene().getWindow());

            AddDriverDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait for it to close
            dialogStage.showAndWait();

            Driver createdDriver = controller.getCreatedDriver();
            if (createdDriver != null) {
                // Refresh the ComboBox and select the new Logist
                refreshDriverComboBox();
                driverComboBox.getSelectionModel().select(createdDriver);
            }
        } catch (Exception e) {
            showErrorDialog("Error", "Could not open Create Driver dialog.");
        }
    }

    private void refreshDriverComboBox() {
        driverComboBox.setItems(FXCollections.observableArrayList(driverService.getAllDrivers()));
    }

    @FXML
    public void createAddress() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/AddAddressDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Address");

            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(initialAddressComboBox.getScene().getWindow());

            AddAddressDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait for it to close
            dialogStage.showAndWait();

            Address createdAddress = controller.getCreatedAddress();
            if (createdAddress != null) {
                // Refresh the ComboBox and select the new Logist
                refreshAddressComboBox();
                initialAddressComboBox.getSelectionModel().select(createdAddress);
            }
        } catch (Exception e) {
            showErrorDialog("Error", "Could not open Create Address dialog.\n" + e.getMessage());
        }
    }

    private void refreshAddressComboBox() {
        initialAddressComboBox.setItems(FXCollections.observableArrayList(addressService.getAllAddresses()));
    }

    @FXML
    public void createCargo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/AddCargoDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Cargo");

            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(cargoComboBox.getScene().getWindow());

            AddCargoDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait for it to close
            dialogStage.showAndWait();

            Cargo createdCargo = controller.getCreatedCargo();
            if (createdCargo != null) {
                // Refresh the ComboBox and select the new Logist
                refreshCargoComboBox();
                cargoComboBox.getSelectionModel().select(createdCargo);
            }
        } catch (Exception e) {
            showErrorDialog("Error", "Could not open Create Cargo dialog.");
        }
    }

    private void refreshCargoComboBox() {
        cargoComboBox.setItems(FXCollections.observableArrayList(cargoService.getAllCargo()));
    }

    @FXML
    public void createVehicle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/AddVehicleDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Vehicle");

            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(vehicleComboBox.getScene().getWindow());

            AddVehicleDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait for it to close
            dialogStage.showAndWait();

            Vehicle createdVehicle = controller.getCreatedVehicle();
            if (createdVehicle != null) {
                // Refresh the ComboBox and select the new Logist
                refreshVehicleComboBox();
                vehicleComboBox.getSelectionModel().select(createdVehicle);
            }
        } catch (Exception e) {
            showErrorDialog("Error", "Could not open Create Vehicle dialog.");
        }
    }

    private void refreshVehicleComboBox() {
        vehicleComboBox.setItems(FXCollections.observableArrayList(vehicleService.getAllVehicles()));
    }
}