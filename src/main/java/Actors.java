import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Actors {
    public static final String API_KEY = "I2aWqgEivkyv3STpfvMzgA==VCkG1TiMWUCWH0Nv";

    private String netWorth;
    private boolean isAlive;

    public Actors(String netWorth, boolean isAlive) {
        this.netWorth = netWorth;
        this.isAlive = isAlive;
    }

    public double getNetWorth() {
        return Double.parseDouble(netWorth);
    }

    public boolean isAlive() {
        return isAlive;
    }

    @SuppressWarnings("deprecation")
    public String getActorData(String name) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name=" +
                    name.replace(" ", "+") + "&apikey=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", API_KEY);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                return response.toString();
            } else {
                return "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setNetWorthFromApi(String actorsInfoJson) {
        JSONArray jsonArray = new JSONArray(actorsInfoJson);
        JSONObject info = jsonArray.getJSONObject(0);
        netWorth = String.valueOf(info.getDouble("net_worth"));
    }

    public void setAliveStatusFromApi(String actorsInfoJson) {
        JSONArray jsonArray = new JSONArray(actorsInfoJson);
        JSONObject info = jsonArray.getJSONObject(0);
        isAlive = info.getBoolean("is_alive");
    }

    public String getDateOfDeath(String actorsInfoJson) {
        JSONArray jsonArray = new JSONArray(actorsInfoJson);
        JSONObject info = jsonArray.getJSONObject(0);
        return info.getString("death");
    }
}
