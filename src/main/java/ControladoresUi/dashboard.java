package ControladoresUi;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import services.CryptoServices.CryptoGraphs;
import services.CryptoServices.CryptoHistoric.data;
import services.CryptoServices.Extractor;
import services.CryptoServices.CryptoGraphs;
import services.CryptoServices.PriceListener;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class dashboard implements PriceListener{
    private CryptoGraphs cryptoGraphs;
    private data data= new data();
    private Extractor extractor = new Extractor();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private String username;
    private login login;
     Double hisbit= extractor.extract(data.getHistoricData("BTC","USD"));
     Double hiseth= extractor.extract(data.getHistoricData("ETH","USD"));
     Double hissol= extractor.extract(data.getHistoricData("SOL","USD"));

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
    private Label PrecioBitcoin;
    @FXML
    private Label PrecioEth;
    @FXML
    private Label PrecioSolana;
    @FXML
    private Label porsol;
    @FXML
    private Label poreth;
    @FXML
    private Label porbit;
    @FXML
    private WebView overalldata;



    @FXML
    public void initialize() {
        cryptoGraphs = new CryptoGraphs(this);
        cryptoGraphs.connect();
        WebView();
        homeButton.setOnAction(event -> navigateTo("Home"));
        chatButton.setOnAction(event -> navigateTo("Chat"));
        marketButton.setOnAction(event -> navigateTo("Market"));
        createReportButton.setOnAction(event -> createReport());
    }
    @Override
    public void onPriceUpdate(String cryptocurrency, double price) {
        // Actualizar los Labels en el hilo de JavaFX
        Platform.runLater(() -> {
            switch (cryptocurrency) {
                case "BTC":
                    if (PrecioBitcoin != null) {
                        PrecioBitcoin.setText(String.format("$%.2f", price));

                        porbit.setText(String.format("%.2f", calculatepr(price, hisbit)));

                        if (price > hisbit) {
                            porbit.setStyle("-fx-text-fill: green;");
                            porbit.setText("Last day up to: "+porbit.getText() + " %  "+" ↑");
                        } else {
                            porbit.setStyle("-fx-text-fill: red;");
                            porbit.setText("Last day down to: "+porbit.getText() + " %  "+" ↓");
                        }
                    }
                    break;
                case "ETH":
                        if (PrecioEth != null) {
                            PrecioEth.setText(String.format("$%.2f", price));

                            poreth.setText(String.format("%.2f", calculatepr(price, hiseth)));

                            if (price > hiseth) {
                                poreth.setStyle("-fx-text-fill: green;");
                                poreth.setText("Last day up to: "+poreth.getText() + " %  "+" ↑");
                            } else {
                                poreth.setStyle("-fx-text-fill: red;");
                                poreth.setText("Last day down to: "+poreth.getText() + " %  "+" ↓");
                            }
                        }
                        break;
                case "SOL":
                    if (PrecioEth != null) {
                        PrecioSolana.setText(String.format("$%.2f", price));

                        porsol.setText(String.format("%.2f", calculatepr(price,hissol)));

                        if (price > hissol) {
                            porsol.setStyle("-fx-text-fill: green;");
                            porsol.setText("Last day up to: "+porsol.getText() + " %  "+" ↑");
                        } else {
                            porsol.setStyle("-fx-text-fill: red;");
                            porsol.setText("Last day down to: "+porsol.getText() + " %  "+" ↓");
                        }
                    }
            }
        });
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

    private void updateBitcoinPrice() throws Exception {

    }

    private void navigateTo(String section) {
        System.out.println("Navigating to: " + section);
    }

    private void createReport() {

        System.out.println("Creating a report...");
    }
    private void WebView() {
        WebEngine webEngine = overalldata.getEngine();

        String url = "https://cryptotreemap.com/";
        webEngine.load(url);


        webEngine.documentProperty().addListener((observable, oldDoc, newDoc) -> {
            if (newDoc != null) {

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
            }
        });
    }

    private double calculatepr(double precio, double preciohis){
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
        return result.isPresent() && result.get() == ButtonType.OK; // Devuelve true si se seleccionó OK
    }

    public void cleanup() {
        if (cryptoGraphs != null) {
            cryptoGraphs.disconnect();
        }
    }
}
