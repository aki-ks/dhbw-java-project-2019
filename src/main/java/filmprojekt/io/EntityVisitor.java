package filmprojekt.io;

import java.util.Optional;

/**
 * Visitor for all instances of an entity declared within a datafile.
 *
 * @see DataVisitor
 */
public abstract class EntityVisitor {
    private final Optional<EntityVisitor> ev;

    public EntityVisitor() {
        this(Optional.empty());
    }

    public EntityVisitor(Optional<EntityVisitor> ev) {
        this.ev = ev;
    }

    public void visit(String[] data) {
        ev.ifPresent(ev -> ev.visit(data));
    }

    /**
     * All entities have been visited
     */
    public void visitEnd() {
        ev.ifPresent(EntityVisitor::visitEnd);
    }
}
