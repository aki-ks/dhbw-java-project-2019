package filmprojekt.io;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DataReaderTest {
    @Test
    public void testSingleEntityDeclarationReading() {
        DataReader reader = readerFor("New_Entity: \"foo\",\"bar\",\"baz\"");

        reader.accept(new DeclarationTestingDataVisitor(Optional.empty(),
                new String[] { "foo", "bar", "baz" }
        ));
    }

    @Test
    public void testMultipleEntityDeclarationReading() {
        DataReader reader = readerFor(
                "New_Entity: \"foo\",\"bar\",\"baz\"\n" +
                "New_Entity: \"abc\"\n" +
                "New_Entity: \"actor_id\",\"movie_id\""
        );

        reader.accept(new DeclarationTestingDataVisitor(Optional.empty(),
                new String[] { "foo", "bar", "baz" },
                new String[] { "abc" },
                new String[] { "actor_id", "movie_id" }
        ));
    }

    @Test
    public void testEntityReading() {
        DataReader reader = readerFor(
                "New_Entity: \"name\",\"age\",\"gender\"\n" +
                "\"Max\",\"20\",\"male\"\n"+
                "\"Lia\",\"17\",\"female\"\n" +
                "\"John\",\"57\",\"male\""
        );

        reader.accept(new DataVisitor() {
            @Override
            public Optional<EntityVisitor> visitEntity(String[] fieldNames) {
                return Optional.of(new TestEntityVisitor(super.visitEntity(fieldNames),
                        new String[] { "Max", "20", "male" },
                        new String[] { "Lia", "17", "female" },
                        new String[] { "John", "57", "male" }
                ));
            }
        });
    }

    private DataReader readerFor(String data) {
        return new DataReader(new ByteArrayInputStream(data.getBytes()));
    }

    private static class DeclarationTestingDataVisitor extends DataVisitor {
        private final String[][] expectedDeclarations;
        private int visitedDeclaration = 0;

        public DeclarationTestingDataVisitor(Optional<DataVisitor> dv, String[]... expectedDeclarations) {
            super(dv);
            this.expectedDeclarations = expectedDeclarations;
        }

        @Override
        public Optional<EntityVisitor> visitEntity(String[] fieldNames) {
            assertArrayEquals(expectedDeclarations[visitedDeclaration++], fieldNames);
            return super.visitEntity(fieldNames);
        }

        @Override
        public void visitEnd() {
            assertEquals(expectedDeclarations.length, visitedDeclaration);
            super.visitEnd();
        }
    }

    private static class TestEntityVisitor extends EntityVisitor {
        private final String[][] expectedEntities;
        private int visitedEntities = 0;

        private TestEntityVisitor(Optional<EntityVisitor> ev, String[]... expectedEntities) {
            super(ev);
            this.expectedEntities = expectedEntities;
        }

        @Override
        public void visit(String[] fieldNames) {
            assertArrayEquals(expectedEntities[visitedEntities++], fieldNames);
            super.visit(fieldNames);
        }

        @Override
        public void visitEnd() {
            assertEquals(expectedEntities.length, visitedEntities);
            super.visitEnd();
        }
    }
}
