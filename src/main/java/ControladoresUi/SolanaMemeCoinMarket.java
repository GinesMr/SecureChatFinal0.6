package ControladoresUi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SolanaMemeCoinMarket {
    @FXML
    private Button homeButton;
    @FXML private Button chatButton;
    @FXML private Button marketButton;
    @FXML private Button createReportButton;
    @FXML private ScrollPane ScrollPane;
    @FXML private GridPane GridPane;

    @FXML
    public void initialize() {
        homeButton.setOnAction(event -> {
            try {
                navigateToDashboard();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        chatButton.setOnAction(event -> {
            try {
                navigateToChat();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        marketButton.setOnAction(event -> {
            try {
                navigateToMarket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        createReportButton.setOnAction(event -> {
            try {
                navigateToReport();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Call showMeme to display memes when the controller is initialized
        showMeme();
    }

    private void navigateToReport() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SolanaMemeCoinMarket/sol.fxml"));
        Pane reppane = fxmlLoader.load();
        SolanaMemeCoinMarket rep = fxmlLoader.getController();
        Scene reps = new Scene(reppane);
        Stage currentStage = (Stage) createReportButton.getScene().getWindow();
        currentStage.setScene(reps);
        currentStage.show();
    }

    private void navigateToDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashBoradml/dashboard.fxml"));
        Pane dashboard = fxmlLoader.load();
        Dashboard controller = fxmlLoader.getController();
        Scene reps = new Scene(dashboard);
        Stage currentStage = (Stage) homeButton.getScene().getWindow();
        currentStage.setScene(reps);
        currentStage.show();
    }

    private void navigateToChat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Chat/chat.fxml"));
        Pane chatPane = fxmlLoader.load();
        Scene chatScene = new Scene(chatPane);
        Stage currentStage = (Stage) chatButton.getScene().getWindow();
        currentStage.setScene(chatScene);
        currentStage.show();
    }

    private void navigateToMarket() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Market/market.fxml"));
        Pane marketPane = fxmlLoader.load();
        Scene marketScene = new Scene(marketPane);
        Stage currentStage = (Stage) marketButton.getScene().getWindow();
        currentStage.setScene(marketScene);
        currentStage.show();
    }

    private void showMeme() {
        // Example data for memes
        String[] memeUrls = {
                "https://www.geckoterminal.com/_next/image?url=https%3A%2F%2Fcoin-images.coingecko.com%2Fcoins%2Fimages%2F50264%2Flarge%2FMOODENG.jpg%3F1726726975&w=32&q=75",
                // Add more meme URLs here
        };

        // Clear existing content
        GridPane.getChildren().clear();

        // Add memes to the GridPane
        for (int i = 0; i < memeUrls.length; i++) {
            ImageView memeView = new ImageView(new Image(memeUrls[i]));
            memeView.setFitHeight(100);
            memeView.setFitWidth(100);
            GridPane.add(memeView, i % 3, i / 3); // Adjust columns and rows as needed
        }
    }
}