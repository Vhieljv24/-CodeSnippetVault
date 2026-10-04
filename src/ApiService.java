import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


 
public class ApiService {

    private static final String API_URL = "https://official-joke-api.appspot.com/jokes/programming/random";

    public static String fetchProgrammingJoke() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status != 200) {
                return "Could not fetch a tip right now (server responded with " + status + ").";
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            return parseJoke(response.toString());
        } catch (Exception e) {
            return "Could not fetch a tip. Please check your internet connection.";
        }
    }

  
    private static String parseJoke(String json) {
        String setup = extractValue(json, "setup");
        String punchline = extractValue(json, "punchline");
        if (setup == null || punchline == null) {
            return "Could not read the tip response.";
        }
        return setup + "\n\n" + punchline;
    }

    private static String extractValue(String json, String key) {
        String marker = "\"" + key + "\":\"";
        int start = json.indexOf(marker);
        if (start == -1) {
            return null;
        }
        start += marker.length();
        int end = json.indexOf("\"", start);
        if (end == -1) {
            return null;
        }
        return json.substring(start, end).replace("\\n", "\n");
    }
}
