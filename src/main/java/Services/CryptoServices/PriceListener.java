package Services.CryptoServices;

public interface PriceListener {
    void onPriceUpdate(String cryptocurrency, double price);
}