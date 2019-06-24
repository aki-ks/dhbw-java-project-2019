package filmprojekt;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A global application context aggregating actors, movies and directors.
 */
public class Universe {
    private final Map<Integer, Actor> actorById = new HashMap<>();
    private final Map<Integer, Movie> movieById = new HashMap<>();
    private final Map<Integer, Director> directorById = new HashMap<>();

    public void addActor(Actor actor) {
        this.actorById.put(actor.getActorId(), actor);
    }

    public void addMovie(Movie movie) {
        this.movieById.put(movie.getMovieId(), movie);
    }

    public void addDirector(Director director) {
        this.directorById.put(director.getDirectorId(), director);
    }

    public Collection<Actor> getActors() {
        return this.actorById.values();
    }

    public Collection<Movie> getMovies() {
        return this.movieById.values();
    }

    public Collection<Director> getDirectors() {
        return this.directorById.values();
    }

    public Actor getActorById(int id) {
        return this.actorById.get(id);
    }

    public Movie getMovieById(int id) {
        return this.movieById.get(id);
    }

    public Director getDirectorById(int id) {
        return this.directorById.get(id);
    }

    public Set<Actor> searchActor(String query) {
        String lowerQuery = query.toLowerCase();
        return getActors().stream()
                .filter(actor -> actor.getName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toSet());
    }

    public Set<Movie> searchMovie(String query) {
        String lowerQuery = query.toLowerCase();
        return getMovies().stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toSet());
    }
}
