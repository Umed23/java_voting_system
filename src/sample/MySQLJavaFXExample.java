package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLJavaFXExample extends Application {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3307/peerelect"; // Replace with your database name
    private static final String USER = "root"; //
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Database Data:");
        Button button = new Button("Fetch Data");
        VBox vbox = new VBox(10, label, button);
        Scene scene = new Scene(vbox, 300, 200);

        button.setOnAction(e -> fetchData(label));

        primaryStage.setScene(scene);
        primaryStage.setTitle("MySQL JavaFX Example");
        primaryStage.show();
    }

    private void fetchData(Label label) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = connection.createStatement()) {

            String query = "SELECT * FROM admin"; // Replace with your table name
            ResultSet rs = stmt.executeQuery(query);
            StringBuilder data = new StringBuilder();

            while (rs.next()) {
                data.append("Data: ").append(rs.getString("fullname")).append("\n"); // Replace with your column
                // name
            }

            label.setText(data.toString());
        } catch (SQLException e) {
            label.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
