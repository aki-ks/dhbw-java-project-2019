package filmprojekt;

import filmprojekt.annotation.Property;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Actor implements Person {
    @Property("actor_id")
    private int actorId;

    @Property("actor_name")
    private String name;

    private final Set<Movie> movies = new HashSet<>();

    /** Default constructor for reflective instantiation */
    private Actor() {}

    public Actor(int actorId, String name) {
        this.actorId = actorId;
        this.name = name;
    }

    public int getActorId() {
        return actorId;
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
        return "Actor{" +
                "actorId=" + actorId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return actorId == actor.actorId &&
                Objects.equals(name, actor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, name);
    }
}
