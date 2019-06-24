package filmprojekt.io;

import filmprojekt.annotation.Property;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Metadata for an entity objects whose fields have {@link Property} annotations.
 */
public class EntityMeta<E> {
    private final Class<E> entityClass;
    private final Constructor<E> constructor;
    private final Map<String, Field> fields;

    public EntityMeta(Class<E> entityClass) {
        this.entityClass = entityClass;
        this.constructor = getDefaultConstructor(entityClass);
        this.fields = getPropertyFieldMap();

        this.constructor.setAccessible(true);
        this.fields.values().forEach(f -> f.setAccessible(true));
    }

    private Constructor<E> getDefaultConstructor(Class<E> entityClass) {
        try {
            return entityClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Could not find parameterless constructor in " + entityClass);
        }
    }

    private Map<String, Field> getPropertyFieldMap() {
        Map<String, Field> map = new HashMap<>();
        for (Field field : entityClass.getDeclaredFields()) {
            Property annotation = field.getAnnotation(Property.class);
            if (annotation != null) {
                map.put(annotation.value(), field);
            }
        }
        return map;
    }

    public Set<String> getPropertyNames() {
        return fields.keySet();
    }

    public Field getPropertyField(String name) {
        return fields.get(name);
    }

    public E newInstance(Map<String, Object> properties) {
        try {
            E entity = constructor.newInstance();

            for (Map.Entry<String, Field> entry : fields.entrySet()) {
                if (fields.containsKey(entry.getKey())) {
                    Object value = properties.get(entry.getKey());
                    entry.getValue().set(entity, value);
                } else {
                    throw new RuntimeException("No value for property " + entry.getKey());
                }
            }

            return entity;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not create entity instance", e);
        }
    }
}
