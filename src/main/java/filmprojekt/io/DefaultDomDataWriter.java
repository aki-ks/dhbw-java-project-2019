package filmprojekt.io;

import filmprojekt.Actor;
import filmprojekt.Director;
import filmprojekt.Movie;
import filmprojekt.Universe;
import filmprojekt.codec.CodecRegistry;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * A {@link DomDataWriter} supporting all entities of a datafile.
 */
public class DefaultDomDataWriter extends DomDataWriter {
    public DefaultDomDataWriter(Universe universe, CodecRegistry codecRegistry) {
        this(universe, codecRegistry, Optional.empty());
    }

    public DefaultDomDataWriter(Universe universe, CodecRegistry codecRegistry, Optional<DataVisitor> dv) {
        super(dv);

        registerFactory(newEntityFactory(Actor.class, codecRegistry, universe::addActor));
        registerFactory(newEntityFactory(Movie.class, codecRegistry, universe::addMovie));
        registerFactory(newEntityFactory(Director.class, codecRegistry, universe::addDirector));

        registerFactory(new DomEntityWriterFactory<ActorMovieRelationEntity>(ActorMovieRelationEntity.class) {
            @Override
            public DomEntityWriter<ActorMovieRelationEntity> newWriter(String[] fieldNames, Optional<EntityVisitor> ev) {
                return new ActorMovieRelationWriter(getMeta(), fieldNames, codecRegistry, universe, ev);
            }
        });

        registerFactory(new DomEntityWriterFactory<DirectorMovieRelationEntity>(DirectorMovieRelationEntity.class) {
            @Override
            public DomEntityWriter<DirectorMovieRelationEntity> newWriter(String[] fieldNames, Optional<EntityVisitor> ev) {
                return new DirectorMovieRelationWriter(getMeta(), fieldNames, codecRegistry, universe, ev);
            }
        });
    }

    /**
     * Shortcut for creation of a {@link DomEntityWriterFactory} producing {@link DomEntityWriter DomEntityWriters}.
     */
    private <E> DomEntityWriterFactory<E> newEntityFactory(Class<E> clazz, CodecRegistry codecRegistry, Consumer<E> storeEntity) {
        return new DomEntityWriterFactory<E>(clazz) {
            @Override
            public DomEntityWriter<E> newWriter(String[] fieldNames, Optional ev) {
                return new DomEntityWriter<E>(getMeta(), fieldNames, codecRegistry, ev) {
                    @Override
                    public void visitParsedEntity(E entity) {
                        storeEntity.accept(entity);
                    }
                };
            }
        };
    }
}
