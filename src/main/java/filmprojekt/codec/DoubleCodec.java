package filmprojekt.codec;

import java.lang.reflect.Type;

public class DoubleCodec extends Codec<Double> {
    private static final DoubleCodec INSTANCE = new DoubleCodec();

    public static DoubleCodec getInstance() {
        return INSTANCE;
    }

    private DoubleCodec() {
        super(double.class);
    }

    @Override
    public Double decode(String string, Type type, CodecRegistry registry) {
        return Double.parseDouble(string);
    }
}
