package filmprojekt.codec;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodecTests {
    @Test
    public void testDoubleCodec() {
        testCodec(DoubleCodec.getInstance(), "0", 0.0);
        testCodec(DoubleCodec.getInstance(), "0.0", 0.0);
        testCodec(DoubleCodec.getInstance(), "-1", -1.0);
        testCodec(DoubleCodec.getInstance(), "1.43", 1.43);
    }

    @Test
    public void testIntCodec() {
        testCodec(IntCodec.getInstance(), "0", 0);
        testCodec(IntCodec.getInstance(), "13", 13);
        testCodec(IntCodec.getInstance(), "-12", -12);
    }

    @Test
    public void testDateCodec() {
        testCodec(LocalDateCodec.getInstance(), "2014-09-26", LocalDate.of(2014, 9, 26));
    }

    @Test
    public void testOptionalDoubleCodec() {
        testCodec(OptionalDoubleCodec.getInstance(), "", OptionalDouble.empty());
        testCodec(OptionalDoubleCodec.getInstance(), "0.0", OptionalDouble.of(0.0));
        testCodec(OptionalDoubleCodec.getInstance(), "-20.3", OptionalDouble.of(-20.3));
    }

    @Test
    public void testOptionalIntCodec() {
        testCodec(OptionalIntCodec.getInstance(), "", OptionalInt.empty());
        testCodec(OptionalIntCodec.getInstance(), "42", OptionalInt.of(42));
    }

    @Test
    public void testStringCodec() {
        testCodec(StringCodec.getInstance(), "foo", "foo");
    }

    private <T> void testCodec(Codec<T> codec, String string, T expected) {
        testCodec(codec, codec.getSupportedClass(), string, expected);
    }

    private <T> void testCodec(Codec<T> codec, Class<T> clazz, String string, T expected) {
        CodecRegistry codecRegistry = new CodecRegistry().addCodec(codec);
        T value = codec.decode(string, clazz, codecRegistry);
        assertEquals(expected, value);
    }
}
