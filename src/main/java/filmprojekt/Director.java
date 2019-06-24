package filmprojekt;

import filmprojekt.annotation.Property;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Director implements Person {
    @Property("director_id")
    private int directorId;

    @Property("director_name")
    private String name;

    private final Set<Movie> movies = new HashSet<>();

    /** Default constructor for reflective instantiation */
    private Director() {}

    public Director(int directorId, String name) {
        this.directorId = directorId;
        this.name = name;
    }

    public int getDirectorId() {
        return directorId;
    }

    @Override
    public String getName() {
        return name;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "Director{" +
                "directorId=" + directorId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return directorId == director.directorId &&
                Objects.equals(name, director.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(directorId, name);
    }
}
