package filmprojekt.codec;

import java.lang.reflect.Type;
import java.util.OptionalDouble;

public class OptionalDoubleCodec extends Codec<OptionalDouble> {
    private static final OptionalDoubleCodec INSTANCE = new OptionalDoubleCodec();

    public static OptionalDoubleCodec getInstance() {
        return INSTANCE;
    }

    private OptionalDoubleCodec() {
        super(OptionalDouble.class);
    }

    @Override
    public OptionalDouble decode(String string, Type type, CodecRegistry registry) {
        return string.isEmpty() ? OptionalDouble.empty() :
                OptionalDouble.of(Double.parseDouble(string));
    }
}
