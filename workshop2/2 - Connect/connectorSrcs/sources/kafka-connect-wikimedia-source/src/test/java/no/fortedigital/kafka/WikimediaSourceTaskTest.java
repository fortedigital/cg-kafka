package no.fortedigital.kafka;

import no.fortedigital.kafka.model.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.*;

import static no.fortedigital.kafka.WikimediaSourceConnectorConfig.TOPIC_CONFIG;
import static org.junit.Assert.*;

public class WikimediaSourceTaskTest {
    private WikimediaSourceTask wikimediaSourceTask = new WikimediaSourceTask();

    private Map<String, String> initialConfig() {
        Map<String, String> baseProps = new HashMap<>();
        baseProps.put(TOPIC_CONFIG, "wikimedia-events");
        return baseProps;
    }


    @Test
    public void test() throws InterruptedException {
        wikimediaSourceTask.config = new WikimediaSourceConnectorConfig(initialConfig());
        WikimediaAPIHttpClient wikimediaAPIHttpClient = new WikimediaAPIHttpClient(wikimediaSourceTask.config);
        JSONArray jsonArray = wikimediaAPIHttpClient.getEvents();

        assertTrue(jsonArray.length() > 0);

        for (Object obj : jsonArray) {
            Event event = Event.fromJSON((JSONObject) obj);
            assertNotNull(event);
            assertNotNull(event.getMeta());
        }
    }
}
