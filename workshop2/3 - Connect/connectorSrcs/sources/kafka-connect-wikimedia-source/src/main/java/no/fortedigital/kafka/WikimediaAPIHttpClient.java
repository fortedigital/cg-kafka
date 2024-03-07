package no.fortedigital.kafka;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class WikimediaAPIHttpClient {
    private static final Logger log = LoggerFactory.getLogger(WikimediaAPIHttpClient.class);

    WikimediaSourceConnectorConfig config;

    public WikimediaAPIHttpClient(WikimediaSourceConnectorConfig config){
        this.config = config;
    }

    protected JSONArray getEvents() throws InterruptedException {
        log.info("Fetching events from Wikimedia API");
        String endpointUrl = "https://stream.wikimedia.org/v2/stream/recentchange";
        JSONArray jsonArray = new JSONArray();

        try {
            URL url = new URL(endpointUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Set timeout to 1 second
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            // Read from the stream until timeout
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        reader.close();
                        connection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    timer.cancel();
                }
            }, 1000); // 1000 milliseconds = 1 second

//            while ((line = reader.readLine()) != null) {
//                if (line.isEmpty()) continue;
//                JSONObject jsonObject = new JSONObject(line);
//                jsonArray.put(jsonObject);
//            }
//            while (!Thread.currentThread().isInterrupted()) {
//                if ((line = reader.readLine()) != null) {
//                    if (line.isEmpty()) continue;
//                    JSONObject jsonObject = new JSONObject(line);
//                    jsonArray.put(jsonObject);
//                } else {
//                    break;
//                }
//            }

            // Read from the stream
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.isEmpty()) continue;
                    JSONObject jsonObject = new JSONObject(line);
                    jsonArray.put(jsonObject);
                }
            } catch (IOException e) {
                // Ignore IOException caused by timeout
                if (!e.getMessage().equals("Stream closed")) {
                    e.printStackTrace();
                }
            }

            // Close resources
            reader.close();
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error fetching events from Wikimedia API", e);
            Thread.sleep(5000);
            return new JSONArray();
        }

        return jsonArray;
    }
}
