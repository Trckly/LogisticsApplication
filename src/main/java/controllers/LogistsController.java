package controllers;

import controllers.dialog.AddLogistDialogController;
import entities.Logist;
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
import services.LogistService;

public class LogistsController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Logist> logistTable;
    @FXML
    private TableColumn<Logist, String> idColumn;
    @FXML
    private TableColumn<Logist, String> nameColumn;
    @FXML
    private TableColumn<Logist, String> phoneColumn;
    @FXML
    private TableColumn<Logist, String> emailColumn;

    private LogistService logistService = new LogistService();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadLogists();
    }

    public void loadLogists() {
        ObservableList<Logist> data = FXCollections.observableArrayList(logistService.getAllLogists());
        logistTable.setItems(data);
    }

    public void handleSearch() {
        String query = searchField.getText();
        ObservableList<Logist> filteredData = FXCollections.observableArrayList(logistService.searchLogists(query));
        logistTable.setItems(filteredData);
    }

    public AddLogistDialogController showAddLogist() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dialogs/AddLogistDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create Logist");

            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(logistTable.getScene().getWindow());

            AddLogistDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait for it to close
            dialogStage.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load the add order dialog.\n" + e.getMessage());
            alert.showAndWait();
        }
        return null;
    }
}