package filmprojekt.io;

import filmprojekt.Director;
import filmprojekt.Movie;
import filmprojekt.Universe;
import filmprojekt.annotation.Property;
import filmprojekt.codec.CodecRegistry;

import java.util.Optional;

/**
 * A visitor that loads a relation between directors and movies that they've created.
 */
public class DirectorMovieRelationWriter extends DomEntityWriter<DirectorMovieRelationEntity> {
    private final Universe universe;

    public DirectorMovieRelationWriter(EntityMeta<DirectorMovieRelationEntity> meta, String[] fieldNames, CodecRegistry codecRegistry, Universe universe, Optional<EntityVisitor> ev) {
        super(meta, fieldNames, codecRegistry, ev);
        this.universe = universe;
    }

    @Override
    public void visitParsedEntity(DirectorMovieRelationEntity entity) {
        Director director = universe.getDirectorById(entity.getDirectorId());
        Movie movie = universe.getMovieById(entity.getMovieId());

        director.getMovies().add(movie);
        movie.getDirectors().add(director);
    }
}

class DirectorMovieRelationEntity {
    @Property("director_id")
    private int directorId;

    @Property("movie_id")
    private int movieId;

    private DirectorMovieRelationEntity() {}

    public int getDirectorId() {
        return directorId;
    }

    public int getMovieId() {
        return movieId;
    }
}
