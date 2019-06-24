package filmprojekt.codec;

import java.lang.reflect.Type;

public class IntCodec extends Codec<Integer> {
    private static final IntCodec INSTANCE = new IntCodec();

    public static IntCodec getInstance() {
        return INSTANCE;
    }

    private IntCodec() {
        super(int.class);
    }

    @Override
    public Integer decode(String string, Type type, CodecRegistry registry) {
        return Integer.parseInt(string);
    }
}
