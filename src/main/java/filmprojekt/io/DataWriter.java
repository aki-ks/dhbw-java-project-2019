package filmprojekt.io;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A DataVisitor that writes all events in a format supported by the {@link DataReader}.
 */
public class DataWriter extends DataVisitor {
    private final PrintWriter writer;

    public DataWriter(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public Optional<EntityVisitor> visitEntity(String[] fieldNames) {
        writer.print(DataReader.NEW_ENTITY_PREFIX);
        writer.println(joinStrings(fieldNames));

        return Optional.of(new EntityVisitor() {
            @Override
            public void visit(String[] data) {
                writer.println(joinStrings(data));
            }
        });
    }

    private String joinStrings(String[] strings) {
        return Arrays.stream(strings)
                .map(string -> '"' + string + '"')
                .collect(Collectors.joining(","));
    }
}
