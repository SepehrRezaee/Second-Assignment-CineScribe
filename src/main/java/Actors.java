import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Actors {
    public static final String API_KEY = "I2aWqgEivkyv3STpfvMzgA==VCkG1TiMWUCWH0Nv";

    public Actors(String netWorth, boolean isAlive) {
        this.netWorth = netWorth;
        this.isAlive = isAlive;
    }

    private String netWorth;
    private Boolean isAlive;

    @SuppressWarnings({"deprecation"})
    /**
     * Retrieves data for the specified actor.
     * @param name for which Actor should be retrieved
     * @return a string representation of the Actors info or null if an error occurred
     */
    public String getActorData(String name) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name=" +
                    name.replace(" ", "+") + "&apikey=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", API_KEY);
            System.out.println(connection);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                return response.toString();
            } else {
                return "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getNetWorthViaApi(String actorsInfoJson) {
        JSONObject json = new JSONObject(actorsInfoJson);
        if (json.has("net_worth")) {
            return json.getDouble("net_worth");
        }
        return 0.0;
    }

    public boolean isAlive(String actorsInfoJson) {
        JSONObject json = new JSONObject(actorsInfoJson);
        if (json.has("deathdate")) {
            return false;
        }
        return true;
    }

    public String getDateOfDeathViaApi(String actorsInfoJson) {
        JSONObject json = new JSONObject(actorsInfoJson);
        if (json.has("deathdate")) {
            return json.getString("deathdate");
        }
        return "";
    }
}
