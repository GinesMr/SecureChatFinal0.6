package ControladoresUi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class dashboard {
    private login login;

    @FXML
    private Button homeButton;
    @FXML
    private Button chatButton;
    @FXML
    private Button marketButton;
    @FXML
    private Button createReportButton;
    @FXML
    private Label marketGraphLabel;
    @FXML
    private Pane bitcoinPane;
    @FXML
    private Pane ethereumPane;
    @FXML
    private Pane xrpPane;
    @FXML
    private Label welcomeuser;
    @FXML
    private Button exit;

    @FXML
    public void initialize() {
        homeButton.setOnAction(event -> navigateTo("Home"));
        chatButton.setOnAction(event -> navigateTo("Chat"));
        marketButton.setOnAction(event -> navigateTo("Market"));
        createReportButton.setOnAction(event -> createReport());
    }


    private void navigateTo(String section) {

        System.out.println("Navigating to: " + section);
    }

    private void createReport() {
        // Implement report creation logic here
        System.out.println("Creating a report...");
    }
}
