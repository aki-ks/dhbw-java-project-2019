package filmprojekt.codec;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows to register codecs and find a codec for a certain class.
 */
public class CodecRegistry {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_BOXED_TYPE = new HashMap<>();
    private static final Map<Class<?>, Class<?>> BOXED_TO_PRIMITIVE_TYPE = new HashMap<>();

    private static void registerBoxType(Class<?> primitive, Class<?> box) {
        PRIMITIVE_TO_BOXED_TYPE.put(primitive, box);
        BOXED_TO_PRIMITIVE_TYPE.put(box, primitive);
    }

    static {
        registerBoxType(boolean.class, Boolean.class);
        registerBoxType(byte.class, Byte.class);
        registerBoxType(char.class, Character.class);
        registerBoxType(short.class, Short.class);
        registerBoxType(int.class, Integer.class);
        registerBoxType(long.class, Long.class);
        registerBoxType(float.class, Float.class);
        registerBoxType(double.class, Double.class);
        registerBoxType(void.class, Void.class);
    }

    public static final CodecRegistry DEFAULT = new CodecRegistry()
            .addCodec(DoubleCodec.getInstance())
            .addCodec(IntCodec.getInstance())
            .addCodec(LocalDateCodec.getInstance())
            .addCodec(OptionalCodec.getInstance())
            .addCodec(OptionalIntCodec.getInstance())
            .addCodec(OptionalDoubleCodec.getInstance())
            .addCodec(StringCodec.getInstance());

    private final Map<Class<?>, Codec<?>> codecMap = new HashMap<>();

    public <T> CodecRegistry addCodec(Codec<T> codec) {
        codecMap.put(boxed(codec.getSupportedClass()), codec);
        return this;
    }

    /**
     * Find a codec for a class.
     */
    public <T> Codec<T> getCodec(Class<T> type) {
        Codec<T> codec = (Codec<T>) codecMap.get(boxed(type));
        if (codec == null) {
            throw new RuntimeException("No Codec for type " + type);
        } else {
            return codec;
        }
    }

    /**
     * If the passed in class is of primitive type, the corresponding box type is returned.
     * In all other cases this method behaves like an identity method.
     */
    private Class<?> boxed(Class<?> clazz) {
        return PRIMITIVE_TO_BOXED_TYPE.getOrDefault(clazz, clazz);
    }
}
