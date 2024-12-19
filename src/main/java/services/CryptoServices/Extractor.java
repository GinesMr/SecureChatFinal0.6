package services.CryptoServices;
import org.json.JSONObject;

public class Extractor {

    public double extract(String json,String Type) {
        JSONObject Json= new JSONObject(json);

        double precio=Json.getDouble(Type);

        return precio;
    }
}
