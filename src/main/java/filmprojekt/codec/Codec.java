package filmprojekt.codec;

import java.lang.reflect.Type;

/**
 * A deserializer for a certain kind of objects.
 * @param <T>
 */
public abstract class Codec<T> {
    /**
     * Class that this codec is able to decode
     */
    private Class<T> supportedClass;

    public Codec(Class<T> supportedClass) {
        this.supportedClass = supportedClass;
    }

    public Class<T> getSupportedClass() {
        return supportedClass;
    }

    public abstract T decode(String string, Type type, CodecRegistry registry);
}
