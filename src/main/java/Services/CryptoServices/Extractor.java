package Services.CryptoServices;

import org.json.JSONObject;

public class Extractor {

    public double extract(String json) {
        JSONObject jsonObject = new JSONObject(json);

        // Acceder directamente a la clave "Data" y luego a "Data" dentro de "Data"
        JSONObject dataObject = jsonObject.getJSONObject("Data");
        JSONObject dataArray = dataObject.getJSONArray("Data").getJSONObject(0); // Suponiendo primer dato

        double precio = dataArray.getDouble("close");

        return precio;
    }
}
