import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.util.ArrayList;
public class Movie {
    public static final String API_KEY = "1c39920b";
    private int ImdbVotes;
    private ArrayList<String> actorsList;
    private String rating;

    public Movie(ArrayList<String> actorsList, String rating, int imdbVotes) {
        this.actorsList = actorsList;
        this.rating = rating;
        this.imdbVotes = imdbVotes;
    }

    public ArrayList<String> getActorsList() {
        return actorsList;
    }

    @SuppressWarnings("deprecation")
    public String getMovieData(String title) throws IOException {
        URL url = new URL("https://www.omdbapi.com/?t=" + title + "&apikey=" + API_KEY);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Key" + API_KEY);
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        reader.close();
        String responseJson = stringBuilder.toString();
        JSONObject info = new JSONObject(responseJson);
        boolean isValid = info.getBoolean("Response");

        if (isValid) {
            return responseJson;
        } else {
            throw new IOException("Not Found!!");
        }
    }

    public int getImdbVotesFromApi(String moviesInfoJson) {
        JSONObject info = new JSONObject(moviesInfoJson);
        String votes = info.getString("imdbVotes");
        String output = votes.replaceAll("[,]", "");
        return Integer.parseInt(output);
    }

    public String getRatingFromApi(String moviesInfoJson) {
        JSONObject info = new JSONObject(moviesInfoJson);
        JSONArray ratings = info.getJSONArray("Ratings");
        JSONObject rate = ratings.getJSONObject(0);
        return rate.getString("Value");
    }

    public void getActorListFromApi(String movieInfoJson) {
        JSONObject info = new JSONObject(movieInfoJson);
        String actors = info.getString("Actors");

        for (String actor : actors.split(", ")) {
            actorsList.add(actor);
        }
    }
}
