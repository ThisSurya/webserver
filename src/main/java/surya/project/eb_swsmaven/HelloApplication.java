package surya.project.eb_swsmaven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import surya.project.global.Global;
import surya.project.handler.ConfigHandler;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage primarystage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Simple web server");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        this.primarystage = stage;
    }
    public static void main(String[] args) {
        new ConfigHandler().loadConfig();
        launch();
    }
}