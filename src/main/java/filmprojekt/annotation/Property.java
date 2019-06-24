package filmprojekt.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
    /**
     * Name of a field in the database file
     */
    String value();
}
