package filmprojekt.io;

import filmprojekt.annotation.Property;
import filmprojekt.codec.CodecRegistry;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomEntityWriterTest {
    private static final CodecRegistry CODEC_REGISTRY = CodecRegistry.DEFAULT;

    @Test
    public void testEntityCreation() {
        String[] fieldNames = { "foo", "bar", "baz" };
        EntityVisitor ev = new EntityCreatingTestWriter<>(ExampleEntity.class, fieldNames, CODEC_REGISTRY, Optional.empty(),
                new ExampleEntity("ABC", 20, 15.4),
                new ExampleEntity("Hello World", 42, 3.342)
        );

        ev.visit(new String[] { "ABC", "20", "15.4" });
        ev.visit(new String[] { "Hello World", "42", "3.342"});
        ev.visitEnd();
    }

    class EntityCreatingTestWriter<E> extends DomEntityWriter<E> {
        private final E[] expectedEntites;
        private int entityIndex = 0;

        public EntityCreatingTestWriter(Class<E> entityClass, String[] fieldNames, CodecRegistry codecRegistry, Optional<EntityVisitor> ev, E... expectedEntites) {
            super(new EntityMeta<>(entityClass), fieldNames, codecRegistry, ev);
            this.expectedEntites = expectedEntites;
        }

        @Override
        public void visitParsedEntity(E entity) {
            assertEquals(expectedEntites[entityIndex++], entity);
        }

        @Override
        public void visitEnd() {
            assertEquals(expectedEntites.length, entityIndex);
            super.visitEnd();
        }
    }

    public static class ExampleEntity {
        @Property("foo")
        private String foo;

        @Property("bar")
        private int bar;

        @Property("baz")
        private double baz;

        // Default constructor for reflective invocation
        private ExampleEntity() {}

        public ExampleEntity(String foo, int bar, double baz) {
            this.foo = foo;
            this.bar = bar;
            this.baz = baz;
        }

        public String getFoo() {
            return foo;
        }

        public int getBar() {
            return bar;
        }

        public double getBaz() {
            return baz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ExampleEntity that = (ExampleEntity) o;
            return bar == that.bar &&
                    Double.compare(that.baz, baz) == 0 &&
                    Objects.equals(foo, that.foo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(foo, bar, baz);
        }

        @Override
        public String toString() {
            return "ExampleEntity{" +
                    "foo='" + foo + '\'' +
                    ", bar=" + bar +
                    ", baz=" + baz +
                    '}';
        }
    }
}
