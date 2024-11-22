package controllers;

import controllers.dialog.AddOrderDialogController;
import entities.Driver;
import entities.Logist;
import entities.Order;
import entities.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.OrderService;

public class OrdersController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, String> idColumn;
    @FXML
    private TableColumn<Order, String> clientColumn;
    @FXML
    private TableColumn<Order, String> carrierColumn;
    @FXML
    private TableColumn<Logist, String> logistColumn;
    @FXML
    private TableColumn<Driver, String> driverColumn;
    @FXML
    private TableColumn<Vehicle, String> vehicleColumn;
    @FXML
    private TableColumn<Order, Double> clientPriceColumn;
    @FXML
    private TableColumn<Order, Double> carrierPriceColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;

    private OrderService orderService = new OrderService();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("clientCompanyName"));
        carrierColumn.setCellValueFactory(new PropertyValueFactory<>("carrierCompanyName"));
        logistColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        driverColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        vehicleColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleLicensePlate"));
        clientPriceColumn.setCellValueFactory(new PropertyValueFactory<>("clientPrice"));
        carrierPriceColumn.setCellValueFactory(new PropertyValueFactory<>("carrierPrice"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadOrders();
    }

    public void loadOrders() {
        ObservableList<Order> data = FXCollections.observableArrayList(orderService.getAllOrders());
        orderTable.setItems(data);
    }

    public void handleSearch() {
        String query = searchField.getText();
        ObservableList<Order> filteredData = FXCollections.observableArrayList(orderService.searchOrders(query));
        orderTable.setItems(filteredData);
    }

    public void showAddOrder() {
        try {
            // Load the FXML for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dialogs/AddOrderDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Order");

            // Create the scene from the FXML
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            // Set the dialog to be modal
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(orderTable.getScene().getWindow());

            // Set the controller for the dialog
            AddOrderDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog
            dialogStage.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load the add order dialog.\n" + e.getMessage());
            alert.showAndWait();
        }
    }
    public void handleDelete() {
        // Get the selected order
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            // Confirm deletion
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete this order?");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Delete the order using the service
                    orderService.deleteOrder(selectedOrder);

                    // Refresh the table
                    loadOrders();
                }
            });
        } else {
            // Show an error if no row is selected
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("No Selection");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please select an order to delete.");
            errorAlert.showAndWait();
        }
    }
}