package no.fortedigital.kafka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class EventStreamClient {

    public static void main(String[] args) {
        String endpointUrl = "https://stream.wikimedia.org/v2/stream/recentchange";

        try {
            URL url = new URL(endpointUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Set timeout to 1 second
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);

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

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Close resources
            reader.close();
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
