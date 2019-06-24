package filmprojekt.io;

import filmprojekt.Actor;
import filmprojekt.Movie;
import filmprojekt.Universe;
import filmprojekt.annotation.Property;
import filmprojekt.codec.CodecRegistry;

import java.util.Optional;

/**
 * A visitor that loads a relation between actors and movies that they've participated in.
 */
public class ActorMovieRelationWriter extends DomEntityWriter<ActorMovieRelationEntity> {
    private final Universe universe;

    public ActorMovieRelationWriter(EntityMeta<ActorMovieRelationEntity> meta, String[] fieldNames, CodecRegistry codecRegistry, Universe universe, Optional<EntityVisitor> ev) {
        super(meta, fieldNames, codecRegistry, ev);
        this.universe = universe;
    }

    @Override
    public void visitParsedEntity(ActorMovieRelationEntity entity) {
        Actor actor = universe.getActorById(entity.getActorId());
        Movie movie = universe.getMovieById(entity.getMovieId());

        actor.getMovies().add(movie);
        movie.getActors().add(actor);
    }
}

class ActorMovieRelationEntity {
    @Property("actor_id")
    private int actorId;

    @Property("movie_id")
    private int movieId;

    private ActorMovieRelationEntity() {}

    public int getActorId() {
        return actorId;
    }

    public int getMovieId() {
        return movieId;
    }
}
