package filmprojekt;

import filmprojekt.codec.CodecRegistry;
import filmprojekt.io.DataReader;
import filmprojekt.io.DefaultDomDataWriter;

import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Main main = new Main("movieproject2019.db");

        Map<String, Consumer<String>> actions = new HashMap<>(4);
        actions.put("filmsuche", main::searchMovie);
        actions.put("schauspielersuche", main::searchActor);
        actions.put("filmnetzwerk", arg -> main.createMovieNetwork(Integer.parseInt(arg)));
        actions.put("schauspielernetzwerk", arg -> main.createActorNetwork(Integer.parseInt(arg)));

        List<String> invalidParams = Arrays.stream(args)
                .filter(param -> !executeParameter(actions, param))
                .collect(Collectors.toList());

        for (String invalidParam : invalidParams) {
            System.out.println("Ungültiger parameter: " + invalidParam);
        }
    }

    /**
     * Parse a parameter and execute the corresponding action.
     *
     * @param actions a map of all available actions
     * @param arg a parameter in format `--[action]=[query]`
     * @return whether the parameter was in a legal format
     */
    private static boolean executeParameter(Map<String, Consumer<String>> actions, String arg) {
        int equalsIndex = arg.indexOf('=');
        if (!arg.startsWith("--") || equalsIndex < 0) {
            return false;
        }

        String command = arg.substring(2, equalsIndex).toLowerCase();
        String query = arg.substring(equalsIndex + 1);

        Consumer<String> action = actions.get(command);
        if (action == null) {
            return false;
        } else {
            action.accept(query);
            return true;
        }
    }

    private final Universe universe = new Universe();

    public Main(String resourceName) {
        InputStream source = DataReader.class.getClassLoader().getResourceAsStream(resourceName);
        new DataReader(source).accept(new DefaultDomDataWriter(this.universe, CodecRegistry.DEFAULT));
    }

    private void searchMovie(String query) {
        Set<Movie> results = universe.searchMovie(query);

        System.out.println(results.size() + " Treffer für suche nach Film '" + query + "'");
        System.out.println();

        for (Movie movie : results) {
            System.out.println("Titel: " + movie.getTitle());
            System.out.println("Id: " + movie.getMovieId());
            System.out.println("Beschreibung: " + movie.getPlot());
            System.out.println();
        }
    }

    private void searchActor(String query) {
        Set<Actor> results = universe.searchActor(query);

        System.out.println(results.size() + " Treffer für suche nach Schauspieler '" + query + "'");
        System.out.println();

        for (Actor actor : results) {
            System.out.println("Name: " + actor.getName());
            System.out.println("Id: " + actor.getActorId());
            System.out.println();
        }
    }

    private void createMovieNetwork(int movieId) {
        Movie movie = universe.getMovieById(movieId);
        if (movie == null) {
            System.out.println("Kein Film mit id '" + movieId + "' gefunden.");
        } else {
            System.out.println("Erstelle Netzwerk für Film '" + movie.getTitle() + "'");

            Set<String> actorNames = movie.getActors().stream()
                    .map(Actor::getName)
                    .collect(Collectors.toSet());

            Set<String> movieNames = movie.getActors().stream()
                    .flatMap(actor -> actor.getMovies().stream())
                    .filter(m -> m.getMovieId() != movieId)
                    .map(Movie::getTitle)
                    .collect(Collectors.toSet());

            System.out.println("Schauspieler: " + String.join(", ", actorNames));
            System.out.println("Filme: " + String.join(", ", movieNames));
        }
    }

    private void createActorNetwork(int actorId) {
        Actor actor = universe.getActorById(actorId);
        if (actor == null) {
            System.out.println("Kein Schauspieler mit id '" + actorId + "' gefunden.");
        } else {
            System.out.println("Erstelle Netzwerk für Schauspieler '" + actor.getName() + "'");

            Set<String> movieNames = actor.getMovies().stream()
                    .map(Movie::getTitle)
                    .collect(Collectors.toSet());

            Set<String> actorNames = actor.getMovies().stream()
                    .flatMap(movie -> movie.getActors().stream())
                    .filter(a -> a.getActorId() != actorId)
                    .map(Actor::getName)
                    .collect(Collectors.toSet());

            System.out.println("Filme: " + String.join(", ", movieNames));
            System.out.println("Schauspieler: " + String.join(", ", actorNames));
        }
    }
}
