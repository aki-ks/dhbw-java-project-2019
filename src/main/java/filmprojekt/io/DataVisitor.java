package filmprojekt.io;

import java.util.Optional;

/**
 * Visitor for all entity declarations within a datafile
 *
 * @see DataReader
 */
public abstract class DataVisitor {
    private final Optional<DataVisitor> dv;

    public DataVisitor() {
        this(Optional.empty());
    }

    public DataVisitor(Optional<DataVisitor> dv) {
        this.dv = dv;
    }

    /**
     * Visit an entity declaration within a datafile.
     *
     * @return an optional object to visit all instances of the entity if interested in those events
     */
    public Optional<EntityVisitor> visitEntity(String[] fieldNames) {
        return dv.flatMap(dv -> dv.visitEntity(fieldNames));
    }

    /**
     * Visit that the end of the file has been reached
     */
    public void visitEnd() {
        dv.ifPresent(DataVisitor::visitEnd);
    }
}
