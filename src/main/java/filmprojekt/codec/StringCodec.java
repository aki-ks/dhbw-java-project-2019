package filmprojekt.codec;

import java.lang.reflect.Type;

public class StringCodec extends Codec<String> {
    private static final StringCodec INSTANCE = new StringCodec();

    public static final StringCodec getInstance() {
        return INSTANCE;
    }

    private StringCodec() {
        super(String.class);
    }

    @Override
    public String decode(String string, Type type, CodecRegistry registry) {
        return string.trim();
    }
}
