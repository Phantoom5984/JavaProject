package biblioteca;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        changeScene("primary.fxml", 600, 400);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        changeScene(fxml, stage.getWidth(), stage.getHeight());
    }

    static void changeScene(String fxml, double width, double height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
    }

    static void setWindowSize(double width, double height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public static void main(String[] args) {
        launch();
    }
}