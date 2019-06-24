package filmprojekt;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniverseTest {
    @Test
    public void testActorLookup() {
        Universe universe = new Universe();
        Actor willSmith = new Actor(20, "Will Smith");
        Actor willVinton = new Actor(30, "Will Vinton");
        Actor richMurray = new Actor(40, "Rich Murray");
        universe.addActor(willSmith);
        universe.addActor(willVinton);
        universe.addActor(richMurray);

        // Search by ID
        assertEquals(willSmith, universe.getActorById(20));
        assertEquals(willVinton, universe.getActorById(30));
        assertEquals(richMurray, universe.getActorById(40));

        // Search by Name
        assertEquals(Collections.singleton(willSmith), universe.searchActor("smith"));
        assertEquals(new HashSet<>(Arrays.asList(willSmith, willVinton)), universe.searchActor("will"));
        assertEquals(Collections.emptySet(), universe.searchActor("john"));
    }

    @Test
    public void testMovieLookup() {
        Universe universe = new Universe();
        Movie movie1 = new Movie(10, "Made in Abyss", "some random plot", "Adventure", Optional.empty(), OptionalInt.empty(), OptionalDouble.empty());
        Movie movie2 = new Movie(20, "foo", "example movie 123", "Adventure", Optional.empty(), OptionalInt.empty(), OptionalDouble.empty());
        universe.addMovie(movie1);
        universe.addMovie(movie2);

        // Search by ID
        assertEquals(movie1, universe.getMovieById(10));
        assertEquals(movie2, universe.getMovieById(20));

        // Search by Name
        assertEquals(Collections.singleton(movie1), universe.searchMovie("Made in "));
        assertEquals(Collections.singleton(movie2), universe.searchMovie("foo"));
        assertEquals(Collections.singleton(movie2), universe.searchMovie("fo"));
        assertEquals(Collections.emptySet(), universe.searchMovie("bar"));
    }
}
