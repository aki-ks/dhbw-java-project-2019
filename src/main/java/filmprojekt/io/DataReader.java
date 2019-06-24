package filmprojekt.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Parser for datafiles that calls appropriate events on a {@link DataVisitor}.
 */
public class DataReader {
    static final String NEW_ENTITY_PREFIX = "New_Entity: ";

    private final Iterator<String> lines;
    private BufferedReader reader;

    public DataReader(InputStream in) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.lines = this.reader.lines().iterator();
    }

    /**
     * Read the provided source and play all events onto a {@link DataVisitor}.
     * This method can only be called once since it consumes the underlying {@link InputStream}.
     */
    public void accept(DataVisitor dv) {
        if (this.reader == null) {
            throw new IllegalStateException("Source was already consumed");
        }

        try {
            Optional<EntityVisitor> ev = Optional.empty();

            while (this.lines.hasNext()) {
                String line = this.lines.next();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith(NEW_ENTITY_PREFIX)) {
                    ev.ifPresent(EntityVisitor::visitEnd);

                    String[] fieldNames = separateCsvLine(line, NEW_ENTITY_PREFIX.length());
                    ev = dv.visitEntity(fieldNames);
                } else {
                    ev.ifPresent(e -> e.visit(separateCsvLine(line, 0)));
                }
            }

            ev.ifPresent(EntityVisitor::visitEnd);
            dv.visitEnd();
        } finally {
            try {
                this.reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.reader = null;
        }
    }

    /**
     * Parse a comma separated list of quoted strings
     */
    private String[] separateCsvLine(String line, int offset) {
        List<String> lines = new ArrayList<>();
        StringBuilder currentString = new StringBuilder();

        int cursor = offset;
        if (line.charAt(cursor++) != '"') {
            throw new RuntimeException("Expected opening quote");
        }

        while (true) {
            char c = line.charAt(cursor++);

            if (c == '"') {
                boolean lastStringTerminated = cursor == line.length();

                if (lastStringTerminated || (line.charAt(cursor) == ',' && line.charAt(cursor + 1) == '"')) {
                    lines.add(currentString.toString());
                    currentString.setLength(0);

                    if (lastStringTerminated) {
                        return lines.toArray(new String[0]);
                    } else {
                        cursor += 2;
                        continue;
                    }
                }
            }

            currentString.append(c);
        }
    }
}
