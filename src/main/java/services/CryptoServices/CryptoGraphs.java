package services.CryptoServices;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class CryptoGraphs {
    private static Extractor extractor = new Extractor();
    private static final String API_KEY = "7ce137e1ffbc7b64fe06081576013c62d6fbff887983c825f3cf8584935b8ffc";
    private static final String SOCKET_URL = "wss://streamer.cryptocompare.com/v2?api_key=" + API_KEY;

    private PriceListener priceListener;
    private WebSocketClient webSocketClient;

    public CryptoGraphs(PriceListener listener) {
        this.priceListener = listener;
    }

    public void connect() {
        try {
            webSocketClient = new WebSocketClient(new URI(SOCKET_URL)) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Conexión abierta con CryptoCompare WebSocket");
                    subscribeToPrices();
                }

                @Override
                public void onMessage(String message) {
                    try {
                        // Parsear el mensaje JSON
                        JSONObject jsonMessage = new JSONObject(message);

                        // Verificar si el mensaje contiene información de precio
                        if (jsonMessage.has("PRICE") && jsonMessage.has("FROMSYMBOL")) {
                            String cryptocurrency = jsonMessage.getString("FROMSYMBOL");
                            double price = jsonMessage.getDouble("PRICE");

                            // Notificar al listener si está configurado
                            if (priceListener != null) {
                                priceListener.onPriceUpdate(cryptocurrency, price);
                            }


                        }
                    } catch (Exception e) {
                        System.err.println("Error procesando mensaje: " + e.getMessage());
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Conexión cerrada: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

                private void subscribeToPrices() {
                    String subscriptionMessage = "{\n" +
                            "  \"action\": \"SubAdd\",\n" +
                            "  \"subs\": [\n" +
                            "    \"5~CCCAGG~BTC~USD\",\n" +
                            "    \"5~CCCAGG~ETH~USD\",\n" +
                            "    \"5~CCCAGG~SOL~USD\"\n" +
                            "  ]\n" +
                            "}";
                    send(subscriptionMessage);
                    System.out.println("Suscripción enviada: " + subscriptionMessage);
                }
            };

            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Método para cerrar la conexión si es necesario
    public void disconnect() {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}