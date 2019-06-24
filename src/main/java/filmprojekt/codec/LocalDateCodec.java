package filmprojekt.codec;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class LocalDateCodec extends Codec<LocalDate> {
    private static final LocalDateCodec INSTANCE = new LocalDateCodec();

    public static LocalDateCodec getInstance() {
        return INSTANCE;
    }

    private LocalDateCodec() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate decode(String string, Type type, CodecRegistry registry) {
        return LocalDate.parse(string);
    }
}
