package filmprojekt.codec;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalCodec extends Codec<Optional> {
    private static final OptionalCodec INSTANCE = new OptionalCodec();

    public static OptionalCodec getInstance() {
        return INSTANCE;
    }

    private OptionalCodec() {
        super(Optional.class);
    }

    @Override
    public Optional<?> decode(String string, Type type, CodecRegistry registry) {
        if (string.isEmpty()) {
            return Optional.empty();
        } else {
            Class<?> typeArgument = (Class<?>)((ParameterizedType) type).getActualTypeArguments()[0];
            return Optional.of(registry.getCodec(typeArgument).decode(string, type, registry));
        }
    }
}
