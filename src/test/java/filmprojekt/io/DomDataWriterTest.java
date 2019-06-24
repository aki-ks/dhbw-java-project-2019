package filmprojekt.io;

import filmprojekt.Actor;
import filmprojekt.Director;
import filmprojekt.Movie;
import filmprojekt.Universe;
import filmprojekt.codec.CodecRegistry;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomDataWriterTest {
    @Test
    public void testUniverseLoader() {
        List<Actor> actors = Arrays.asList(
                new Actor(12,"François Lallement"),
                new Actor(42,"Jules-Eugène Legris")
        );

        List<Movie> movies = Arrays.asList(
                new Movie(10, "Matrix", "Some random movie", "Some genre", Optional.of(LocalDate.of(2015, 3, 12)), OptionalInt.of(14424), OptionalDouble.of(9.5)),
                new Movie(97, "Matrix Reloadad", "Some reloaded  movie", "Another genre", Optional.of(LocalDate.of(2016, 12, 24)), OptionalInt.of(523), OptionalDouble.of(9.3))
        );

        List<Director> directors = Arrays.asList(
                new Director(27977,"Stuart Paton"),
                new Director(27978,"Charles Chaplin")
        );


        Universe universe = new Universe();
        DataVisitor dv = new DefaultDomDataWriter(universe, CodecRegistry.DEFAULT);

        dv.visitEntity(new String[] { "actor_id", "actor_name" }).ifPresent(ev -> {
            actors.stream()
                    .map(actor -> new String[] { Integer.toString(actor.getActorId()), actor.getName() })
                    .forEach(ev::visit);
            ev.visitEnd();
        });

        dv.visitEntity(new String[] { "movie_id", "movie_title", "movie_plot", "genre_name", "movie_released", "movie_imdbVotes", "movie_imdbRating" }).ifPresent(ev -> {
            movies.stream()
                    .map(movie -> new String[] { Integer.toString(movie.getMovieId()), movie.getTitle(), movie.getPlot(), movie.getGenre(),
                            movie.getDate().map(LocalDate::toString).orElse(""),
                            movie.getImbdVotes().isPresent() ? Integer.toString(movie.getImbdVotes().getAsInt()) : "",
                            movie.getImbdRating().isPresent() ? Double.toString(movie.getImbdRating().getAsDouble()) : "" })
                    .forEach(ev::visit);
            ev.visitEnd();
        });

        dv.visitEntity(new String[] { "director_id", "director_name" }).ifPresent(ev -> {
            directors.stream()
                    .map(director -> new String[] { Integer.toString(director.getDirectorId()), director.getName() })
                    .forEach(ev::visit);
            ev.visitEnd();
        });

        dv.visitEntity(new String[] { "actor_id", "movie_id" }).ifPresent(ev -> {
            ev.visit(new String[] { "12", "10" });
            ev.visitEnd();
        });

        dv.visitEntity(new String[] { "director_id", "movie_id" }).ifPresent(ev -> {
            ev.visit(new String[] { "27978", "97" });
            ev.visitEnd();
        });

        dv.visitEnd();


        assertEquals(new HashSet<>(actors), new HashSet<>(universe.getActors()));
        assertEquals(new HashSet<>(movies), new HashSet<>(universe.getMovies()));
        assertEquals(new HashSet<>(directors), new HashSet<>(universe.getDirectors()));

        assertEquals(universe.getActorById(12).getMovies(), Collections.singleton(universe.getMovieById(10)));
        assertEquals(universe.getMovieById(10).getActors(), Collections.singleton(universe.getActorById(12)));

        assertEquals(universe.getDirectorById(27978).getMovies(), Collections.singleton(universe.getMovieById(97)));
        assertEquals(universe.getMovieById(97).getDirectors(), Collections.singleton(universe.getDirectorById(27978)));
    }
}
