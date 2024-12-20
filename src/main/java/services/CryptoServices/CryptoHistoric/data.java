    package services.CryptoServices.CryptoHistoric;

    import services.CryptoServices.Extractor;

    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;

    public class data {
        private static final String API_KEY = "7ce137e1ffbc7b64fe06081576013c62d6fbff887983c825f3cf8584935b8ffc";
        private static final String BASE_URL = "https://min-api.cryptocompare.com/data/v2/histoday";
        int limit = 1; // Último día
        long toTs = System.currentTimeMillis() / 1000 - 86400;

        public String getHistoricData(String fsym, String tsym) {
            try {
                String urlString = String.format(
                        "%s?fsym=%s&tsym=%s&limit=%d&toTs=%d&api_key=%s",
                        BASE_URL, fsym, tsym, limit, toTs, API_KEY
                );
                URL url = new URL(urlString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();

                return response.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
