package filmprojekt.codec;

import java.lang.reflect.Type;
import java.util.OptionalInt;

public class OptionalIntCodec extends Codec<OptionalInt> {
    private static final OptionalIntCodec INSTANCE = new OptionalIntCodec();

    public static OptionalIntCodec getInstance() {
        return INSTANCE;
    }

    private OptionalIntCodec() {
        super(OptionalInt.class);
    }

    @Override
    public OptionalInt decode(String string, Type type, CodecRegistry registry) {
        return string.isEmpty() ? OptionalInt.empty() :
                OptionalInt.of(Integer.parseInt(string));
    }
}
