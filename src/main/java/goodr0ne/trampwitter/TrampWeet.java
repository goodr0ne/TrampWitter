package goodr0ne.trampwitter;

import com.google.gson.JsonObject;

/**
 * TrampWeet entity class, representing one single tweet of Donald Trump
 * In fact serialized and deserialized from json object
 */
class TrampWeet {
    private Long id;
    private String body;
    private long timestamp;

    TrampWeet() {
        id = (long)1337;
        body = "default constructed";
        timestamp = 1337;
    }

    TrampWeet(JsonObject jsonObj) {
        try {
            id = jsonObj.get("id").getAsLong();
        } catch (Exception e) {
            id = (long)7331;
        }
        try {
            timestamp = jsonObj.get("timestamp").getAsLong();
        } catch (Exception e) {
            timestamp = 7331;
        }
        try {
            body = jsonObj.get("body").getAsString();
        } catch (Exception e) {
            body = "Error while constructed from JsonObject.get(body)";
        }
    }

    JsonObject getAsJson() {
        JsonObject results = new JsonObject();
        results.addProperty("id", id);
        results.addProperty("timestamp", timestamp);
        results.addProperty("body", body);
        return results;
    }
}