package services.CryptoServices;

public interface PriceListener {
    void onPriceUpdate(String cryptocurrency, double price);
}