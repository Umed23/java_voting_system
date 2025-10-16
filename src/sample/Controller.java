package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {

    private final Main mainApp = new Main();

    // Buttons
    public Button Candidate;
    public Button Status;
    public Button peoplevote;
    public Button Admin;
    public VBox body;
    public VBox form;

    // TextFields for Candidate registration
    @FXML
    private TextField fullnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField phoneField;
    @FXML
    private Label statusLabel;

    // TextFields for People Vote registration
    @FXML
    private TextField voteFullnameField;
    @FXML
    private TextField voteEmailField;
    @FXML
    private TextField voteRollNoField;
    @FXML
    private TextField votePasswordField;
    @FXML
    private TextField voteConfirmPasswordField;

    // TextFields for People Vote login
    @FXML
    private TextField loginFullnameField;
    @FXML
    private TextField loginPasswordField;

    @FXML
    private Label peopleLabel1; // To show feedback messages

    // admin login textfields
    @FXML
    private TextField adminUsernameField; // Admin username TextField from admin.fxml
    @FXML
    private TextField adminPasswordField; // Admin password TextField from admin.fxml
    @FXML
    private Label adminLoginStatusLabel; // Label to show login feedback

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/peerelect";
    private static final String USER = "root";
    private static final String PASSWORD = "UMed12345";

    // Navigation methods
    @FXML
    public void openAdminPage() {
        mainApp.switchScene("admin.fxml");
    }

    @FXML
    public void openPeopleVotePage() {
        mainApp.switchScene("people_vote.fxml");
    }

    @FXML
    public void openStatusPage() {
        mainApp.switchScene("status.fxml");
    }

    @FXML
    public void openCandidatePage() {
        mainApp.switchScene("candidate.fxml");
    }

    @FXML
    public void openHomePage() {
        mainApp.switchScene("sample.fxml");
    }

    @FXML
    public void openVotingPage() {
        mainApp.switchScene("voting.fxml");
    }

    // Method to navigate to the admin dashboard
    public void openAdminDashboard() {
        mainApp.switchScene("admin_dashboard.fxml");
    }

    // Method to insert candidate data into 'candidateregister' table
    @FXML
    public void submitCandidate() {
        String fullname = fullnameField.getText();
        String email = emailField.getText();
        String position = positionField.getText();
        String phone = phoneField.getText();

        // Ensure all fields are filled
        if (fullname.isEmpty() || email.isEmpty() || position.isEmpty() || phone.isEmpty()) {
            statusLabel.setText("Please fill in all fields.");
            return;
        }

        // SQL insert query for candidateregister table
        String insertQuery = "INSERT INTO candidateregister (fullname, email, position, phone_number) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, fullname);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, position);
            preparedStatement.setString(4, phone);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                statusLabel.setText("Candidate successfully registered.");
            } else {
                statusLabel.setText("Failed to register candidate.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database error: " + e.getMessage());
        }
    }

    // Method to register a person in the 'people' table
    @FXML
    public void submitPeopleVote() {
        String fullname = voteFullnameField.getText();
        String email = voteEmailField.getText();
        String rollNo = voteRollNoField.getText();
        String password = votePasswordField.getText();
        String confirmPassword = voteConfirmPasswordField.getText();

        // Basic validation (check if all fields are filled and if passwords match)
        if (fullname.isEmpty() || email.isEmpty() || rollNo.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty()) {
            peopleLabel1.setText("Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            peopleLabel1.setText("Passwords do not match.");
            return;
        }

        // SQL insert query for people table
        String insertQuery = "INSERT INTO people (fullname, email, roll_no, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, fullname);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, rollNo);
            preparedStatement.setString(4, password);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                peopleLabel1.setText("People vote registration successful.");
            } else {
                peopleLabel1.setText("Failed to register.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            peopleLabel1.setText("Database error: " + e.getMessage());
        }
    }

    // Method to handle login
    @FXML
    public void loginPeople() {
        String fullname = loginFullnameField.getText();
        String password = loginPasswordField.getText();

        if (fullname.isEmpty() || password.isEmpty()) {
            peopleLabel1.setText("Please fill in both fields.");
            return;
        }

        // SQL query to check if the entered fullname and password exist in the database
        String query = "SELECT * FROM people WHERE fullname = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, fullname);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                peopleLabel1.setText("Login successful.");
                openVotingPage(); // Redirect to voting page
            } else {
                peopleLabel1.setText("Invalid fullname or password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            peopleLabel1.setText("Database error: " + e.getMessage());
        }
    }

    // Method to handle admin login
    @FXML
    public void adminLogin() throws IOException {
        String username = adminUsernameField.getText(); // This corresponds to 'fullname' in the database
        String password = adminPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            adminLoginStatusLabel.setText("Please enter both username and password.");
            return;
        }

        // Correct SQL query (using 'fullname' instead of 'username')
        String query = "SELECT * FROM admin WHERE fullname = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username); // Pass 'username' (which is fullname in DB)
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                adminLoginStatusLabel.setText("Login successful.");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) adminUsernameField.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                adminLoginStatusLabel.setText("Invalid username or password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            adminLoginStatusLabel.setText("Database error: " + e.getMessage());
        }
    }

}
