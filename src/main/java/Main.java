public class Main {
    public static void main(String[] args) {
        // TODO --> complete main function
        runMenu();
    }
    public static void runMenu() {
        while (true) {
            System.out.println("Enter 1 to search for a movie, 2 to search for an actor, or 3 to exit:");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice == 1) {
                System.out.print("Enter the movie name: ");
                String movieName = scanner.nextLine();
                Movie movie = new Movie(movieName);
                System.out.println(movie.getMovieData(movieName));
            } else if (choice == 2) {
                System.out.print("Enter the actor's name: ");
                String actorName = scanner.nextLine();
                Actors actor = new Actors(new Actors().getActorData(actorName));
                System.out.println("Net Worth: " + actor.getNetWorthViaApi(actor.getActorData(actorName)));
                System.out.println("Is Alive: " + actor.isAlive(actor.getActorData(actorName)));
                if (!actor.isAlive(actor.getActorData(actorName))) {
                    System.out.println("Date of Death: " + actor.getDateOfDeathViaApi(actor.getActorData(actorName)));
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
