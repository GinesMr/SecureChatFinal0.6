package ControladoresUi;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import Services.CryptoServices.CryptoGraphs;
import Services.CryptoServices.CryptoHistoric.data;
import Services.CryptoServices.Extractor;
import Services.CryptoServices.PriceListener;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Dashboard implements PriceListener {
    private CryptoGraphs cryptoGraphs;
    private data data = new data();
    private Extractor extractor = new Extractor();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private String username;
    private Login login;
    Double hisbit = extractor.extract(data.getHistoricData("BTC","USD"));
    Double hiseth = extractor.extract(data.getHistoricData("ETH","USD"));
    Double hissol = extractor.extract(data.getHistoricData("SOL","USD"));

    @FXML private Button homeButton;
    @FXML private Button chatButton;
    @FXML private Button marketButton;
    @FXML private Button createReportButton;
    @FXML private Label marketGraphLabel;
    @FXML private Pane bitcoinPane;
    @FXML private Pane ethereumPane;
    @FXML private Pane xrpPane;
    @FXML private Label welcomeuser;
    @FXML private Label PrecioBitcoin;
    @FXML private Label PrecioEth;
    @FXML private Label PrecioSolana;
    @FXML private Label porsol;
    @FXML private Label poreth;
    @FXML private Label porbit;
    @FXML private WebView overalldata;
    private WebEngine webEngine;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            try {
                initializeWebView();
            } catch (Exception e) {
                System.err.println("Error initializing WebView: " + e.getMessage());
                e.printStackTrace();
            }
        });

        cryptoGraphs = new CryptoGraphs(this);
        cryptoGraphs.connect();

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

    private void initializeWebView() {
        if (overalldata != null) {
            webEngine = overalldata.getEngine();
            String url = "https://cryptotreemap.com/";

            webEngine.documentProperty().addListener((observable, oldDoc, newDoc) -> {
                if (newDoc != null) {
                    Platform.runLater(() -> {
                        try {
                            webEngine.executeScript(
                                    "document.querySelector('header').style.display = 'none';" +
                                            "document.querySelector('footer').style.display = 'none';" +
                                            "document.body.style.overflow = 'hidden';"
                            );
                            webEngine.executeScript(
                                    "let canvas = document.getElementById('coins_chart_area');" +
                                            "canvas.style.display = 'block';" +
                                            "canvas.width = 1350;" +
                                            "canvas.height = 903;"
                            );
                        } catch (Exception e) {
                            System.err.println("Error executing JavaScript: " + e.getMessage());
                            e.printStackTrace();
                        }
                    });
                }
            });

            webEngine.load(url);
        }
    }

    @Override
    public void onPriceUpdate(String cryptocurrency, double price) {
        Platform.runLater(() -> {
            try {
                updatePrice(cryptocurrency, price);
            } catch (Exception e) {
                System.err.println("Error updating price: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void updatePrice(String cryptocurrency, double price) {
        switch (cryptocurrency) {
            case "BTC" -> updateBitcoinPrice(price);
            case "ETH" -> updateEthereumPrice(price);
            case "SOL" -> updateSolanaPrice(price);
        }
    }

    private void updateBitcoinPrice(double price) {
        if (PrecioBitcoin != null) {
            PrecioBitcoin.setText(String.format("$%.2f", price));
            porbit.setText(String.format("%.2f", calculatepr(price, hisbit)));
            updatePriceStyle(porbit, price, hisbit);
        }
    }

    private void updateEthereumPrice(double price) {
        if (PrecioEth != null) {
            PrecioEth.setText(String.format("$%.2f", price));
            poreth.setText(String.format("%.2f", calculatepr(price, hiseth)));
            updatePriceStyle(poreth, price, hiseth);
        }
    }

    private void updateSolanaPrice(double price) {
        if (PrecioSolana != null) {
            PrecioSolana.setText(String.format("$%.2f", price));
            porsol.setText(String.format("%.2f", calculatepr(price, hissol)));
            updatePriceStyle(porsol, price, hissol);
        }
    }

    private void updatePriceStyle(Label label, double price, double historicPrice) {
        if (price > historicPrice) {
            label.setStyle("-fx-text-fill: green;");
            label.setText("Last day: Price increased by up to " + label.getText() + " %  ↑");
        } else {
            label.setStyle("-fx-text-fill: red;");
            label.setText("Last day: Price decreased by up to " + label.getText() + " %  ↓");
        }
    }

    public void setUsername(String username) {
        this.username = username;
        updateWelcomeMessage();
    }

    private void updateWelcomeMessage() {
        if (welcomeuser != null && username != null) {
            welcomeuser.setText("Bienvenido, " + username + "!");
        }
    }

    private void navigateTo(String section) {
        System.out.println("Navigating to: " + section);
    }

    private void navigateToReport() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SolanaMemeCoinMarket/sol.fxml"));
        Pane reppane = fxmlLoader.load();
        SolanaMemeCoinMarket rep = fxmlLoader.getController();
        Scene reps = new Scene(reppane);
        Stage currentStage = (Stage) createReportButton.getScene().getWindow();
        currentStage.setScene(reps);
        currentStage.show();
        cleanup();
    }


    private void navigateToDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashBoradml/dashboard.fxml"));
        Pane dashboard = fxmlLoader.load();
        Dashboard controller = fxmlLoader.getController();
        Scene reps = new Scene(dashboard);
        Stage currentStage = (Stage) homeButton.getScene().getWindow();
        currentStage.setScene(reps);
        currentStage.show();
        cleanup();
    }

    private void navigateToChat() {

    }

    private void navigateToMarket() {

    }
    private double calculatepr(double precio, double preciohis) {
        if (preciohis == 0) {
            return 0;
        }
        return ((precio - preciohis) / preciohis) * 100;
    }

    private boolean showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public void cleanup() {
        if (cryptoGraphs != null) {
            cryptoGraphs.disconnect();
        }
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}