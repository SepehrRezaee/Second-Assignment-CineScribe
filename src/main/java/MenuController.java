import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MenuController {

    @FXML
    private TextField nameField;

    @FXML
    private TextArea outputArea;

    @FXML
    private Button actorButton;

    @FXML
    private Button movieButton;

    @FXML
    private void initialize() {
        actorButton.setOnAction(event -> handleActorButton());
        movieButton.setOnAction(event -> handleMovieButton());
    }

    private void handleActorButton() {
        String name = nameField.getText();
        if (!name.isEmpty()) {
            Actors actor = new Actors("", false);
            String info = actor.getActorData(name);

            if (info != null) {
                actor.setNetWorthFromApi(info);
                actor.setAliveStatusFromApi(info);

                StringBuilder output = new StringBuilder();
                output.append("Net Worth: ").append(actor.getNetWorth()).append("\n");

                if (actor.isAlive()) {
                    output.append(name).append(" is alive!");
                } else {
                    output.append("Date of Death: ").append(actor.getDateOfDeath(info));
                }

                outputArea.setText(output.toString());
            } else {
                outputArea.setText("Error: Could not retrieve actor information.");
            }
        } else {
            outputArea.setText("Please enter an actor/actress name.");
        }
    }

    private void handleMovieButton() {
        String name = nameField.getText();
        if (!name.isEmpty()) {
            Movie movie = new Movie(new ArrayList<>(), "", 0);
            String info = null;
            try {
                info = movie.getMovieData(name);
            } catch (IOException e) {
                outputArea.setText("Error: " + e.getMessage());
                return;
            }

            if (info != null) {
                movie.getActorListFromApi(info);
                StringBuilder output = new StringBuilder();
                output.append("Characters Are: ").append("\n");
                int count = 0;
                for (String character : movie.getActorsList()) {
                    count++;
                    output.append(count).append(") ").append(character).append("\n");
                }

                output.append("IMDb Votes: ").append(movie.getImdbVotesFromApi(info)).append("\n");
                output.append("Rating: ").append(movie.getRatingFromApi(info));

                outputArea.setText(output.toString());
            } else {
                outputArea.setText("Error: Could not retrieve movie information.");
            }
        } else {
            outputArea.setText("Please enter a movie name.");
        }
    }
}
