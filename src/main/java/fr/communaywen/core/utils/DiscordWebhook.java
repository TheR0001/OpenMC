package fr.communaywen.core.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DiscordWebhook {

    private final String webhookUrl;

    public DiscordWebhook(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public void sendMessage(String username, String avatarUrl, String message) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JsonObject jsonPayload = new JsonObject();
            jsonPayload.addProperty("username", username);
            jsonPayload.addProperty("avatar_url", avatarUrl);

            JsonObject embed = new JsonObject();
            embed.addProperty("description", "``" + message + "``");
            JsonArray embeds = new JsonArray();
            embeds.add(embed);

            jsonPayload.add("embeds", embeds);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.toString().getBytes());
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Failed to send message to Discord webhook. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}