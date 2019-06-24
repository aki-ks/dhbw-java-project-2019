package filmprojekt.io;

import java.util.*;

public abstract class DomDataWriter extends DataVisitor {
    private final Map<Set<String>, DomEntityWriterFactory<?>> factories = new HashMap<>();

    protected DomDataWriter() {
        this(Optional.empty());
    }

    protected DomDataWriter(Optional<DataVisitor> dv) {
        super(dv);
    }

    protected <E> void registerFactory(DomEntityWriterFactory<E> factory) {
        Set<String> propertyNames = factory.getMeta().getPropertyNames();
        propertyNames = Collections.unmodifiableSet(new HashSet<>(propertyNames));

        this.factories.put(propertyNames, factory);
    }

    @Override
    public Optional<EntityVisitor> visitEntity(String[] fieldNames) {
        Optional<EntityVisitor> ev = super.visitEntity(fieldNames);
        DomEntityWriterFactory<?> factory = factories.get(new HashSet<>(Arrays.asList(fieldNames)));

        if (factory != null) {
            ev = Optional.of(factory.newWriter(fieldNames, ev));
        }

        return ev;
    }

    protected abstract class DomEntityWriterFactory<E> {
        private final EntityMeta<E> meta;

        protected DomEntityWriterFactory(Class<E> clazz) {
            this.meta = new EntityMeta<>(clazz);
        }

        protected EntityMeta<E> getMeta() {
            return meta;
        }

        public abstract DomEntityWriter<E> newWriter(String[] fieldNames, Optional<EntityVisitor> ev);
    }
}
