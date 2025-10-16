package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage; // Store the primary stage reference

        // Load the FXML layout
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));

        // Create a ScrollPane and configure it
        ScrollPane scroll = new ScrollPane();
        scroll.setVvalue(0.05); // Vertical position (0.0 top, 1.0 bottom)
        scroll.setHvalue(0.1); // Horizontal position (0.0 left, 1.0 right)

        // Wrap root in a Group and ScrollPane
        Group group = new Group(root);
        scroll.setContent(group);

        // Set the scene with ScrollPane
        Scene initialScene = new Scene(scroll, 1536, 864); // Adjusted size to match your screen resolution

        primaryStage.setFullScreen(true); // Ensure full-screen
        primaryStage.setResizable(false); // Prevent resizing issues
        primaryStage.setTitle("Simple Layout");
        primaryStage.setScene(initialScene);
        primaryStage.show();
    }

    // Method to switch scenes
    public void switchScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            ScrollPane scroll = new ScrollPane();
            scroll.setVvalue(0.05); // Vertical position (0.0 top, 1.0 bottom)
            scroll.setHvalue(0.1); // Horizontal position (0.0 left, 1.0 right)
            Group group = new Group(root);
            scroll.setContent(group);

            Scene newScene = new Scene(scroll, 1536, 864); // Adjusted size to match your screen resolution
            primaryStage.setScene(newScene);
            primaryStage.setFullScreen(true); // Ensure full-screen when switching
            primaryStage.setResizable(false); // Prevent resizing
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get the primary stage
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
