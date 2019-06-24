package filmprojekt.io;

import filmprojekt.codec.Codec;
import filmprojekt.codec.CodecRegistry;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A visitor that builds an entity object for each visited entity.
 */
public abstract class DomEntityWriter<E> extends EntityVisitor {
    private final EntityMeta<E> meta;
    private final CodecRegistry codecRegistry;
    private final String[] fieldNames;
    private final Field[] fields;
    private final Codec<?>[] codecs;

    public DomEntityWriter(EntityMeta<E> meta, String[] fieldNames, CodecRegistry codecRegistry, Optional<EntityVisitor> ev) {
        super(ev);
        this.meta = meta;
        this.codecRegistry = codecRegistry;
        this.fieldNames = fieldNames;

        this.fields = Arrays.stream(fieldNames)
                .map(meta::getPropertyField)
                .toArray(Field[]::new);

        this.codecs = Arrays.stream(fields)
                .map(Field::getType)
                .map(codecRegistry::getCodec)
                .toArray(Codec<?>[]::new);
    }

    @Override
    public void visit(String[] data) {
        super.visit(data);

        Map<String, Object> properties = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            Object value = codecs[i].decode(data[i], fields[i].getGenericType(), this.codecRegistry);
            properties.put(fieldNames[i], value);
        }

        visitParsedEntity(meta.newInstance(properties));
    }

    public abstract void visitParsedEntity(E entity);
}
