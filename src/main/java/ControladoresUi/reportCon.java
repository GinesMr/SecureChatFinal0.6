package ControladoresUi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import services.CryptoServices.CryptoGraphs;
import services.CryptoServices.CryptoHistoric.data;
import services.CryptoServices.Extractor;
import services.CryptoServices.PriceListener;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class reportCon {
    @FXML
    private Button homeButton;
    @FXML private Button chatButton;
    @FXML private Button marketButton;
    @FXML private Button createReportButton;
    @FXML private ScrollPane excel;

    @FXML
    public void initialize() {

        homeButton.setOnAction(event -> {
            try {
                navigateToDashboard();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        chatButton.setOnAction(event -> navigateToChat());
        marketButton.setOnAction(event -> navigateToMarket());

        createReportButton.setOnAction(event -> {
            try {
                navigateToReport();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void navigateToReport() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Report/report.fxml"));
        Pane reppane = fxmlLoader.load();
        reportCon rep = fxmlLoader.getController();
        Scene reps = new Scene(reppane);
        Stage currentStage = (Stage) createReportButton.getScene().getWindow();
        currentStage.setScene(reps);
        currentStage.show();
    }


    private void navigateToDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashBoradml/dashboard.fxml"));
        Pane dashboard = fxmlLoader.load();
        dashboard controller = fxmlLoader.getController();
        Scene reps = new Scene(dashboard);
        Stage currentStage = (Stage) homeButton.getScene().getWindow();
        currentStage.setScene(reps);
        currentStage.show();
    }

    private void navigateToChat() {

    }

    private void navigateToMarket() {

    }
}
