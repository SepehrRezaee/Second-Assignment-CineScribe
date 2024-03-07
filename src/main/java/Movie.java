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

    public Movie(String movieName) {
        try {
            String moviesInfoJson = getMovieData(movieName);
            this.ImdbVotes = getImdbVotesViaApi(moviesInfoJson);
            this.rating = getRatingViaApi(moviesInfoJson);
            getActorListViaApi(moviesInfoJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    /**
     * Retrieves data for the specified movie.
     *
     * @param title the name of the title for which MovieData should be retrieved
     * @return a string representation of the MovieData, or null if an error occurred
     */

    public String getMovieData(String title) throws IOException {
        URL url = new URL("https://www.omdbapi.com/?t="+title+"&apikey="+API_KEY);
        URLConnection Url = url.openConnection();
        Url.setRequestProperty("Authorization", "Key" + API_KEY);
        BufferedReader reader = new BufferedReader(new InputStreamReader(Url.getInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine())!=null) {
            stringBuilder.append(line);
        }
        reader.close();
        //handle an error if the chosen movie is not found
        return stringBuilder.toString();
    }
    public int getImdbVotesViaApi(String moviesInfoJson){
        JSONObject json = new JSONObject(moviesInfoJson);
        if (json.has("imdbVotes")) {
            return json.getInt("imdbVotes");
        }
        return 0;
    }

    public String getRatingViaApi(String moviesInfoJson){
        JSONObject json = new JSONObject(moviesInfoJson);
        if (json.has("Ratings")) {
            JSONArray ratings = json.getJSONArray("Ratings");
            for (int i = 0; i < ratings.length(); i++) {
                JSONObject rating = ratings.getJSONObject(i);
                if (rating.getString("Source").equals("Internet Movie Database")) {
                    return rating.getString("Value");
                }
            }
        }
        return "";
    }

    public void getActorListViaApi(String movieInfoJson){
       JSONObject json = new JSONObject(movieInfoJson);
        if (json.has("Actors")) {
            String actors = json.getString("Actors");
            actorsList = new ArrayList<>();
            String[] actorsArray = actors.split(", ");
            for (String actor : actorsArray) {
                actorsList.add(actor);
            }
        }
    }
}
