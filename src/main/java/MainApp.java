import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Logistics Management");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("fxml/Main.fxml")), 1000,600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}