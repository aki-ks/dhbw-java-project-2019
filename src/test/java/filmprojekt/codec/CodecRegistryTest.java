package filmprojekt.codec;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodecRegistryTest {
    private static final CodecRegistry TEST_REGISTRY = new CodecRegistry()
            .addCodec(IntCodec.getInstance())
            .addCodec(StringCodec.getInstance())
            .addCodec(LocalDateCodec.getInstance());

    @Test
    public void testBasicCodecRetrival() {
        assertEquals(LocalDateCodec.getInstance(), TEST_REGISTRY.getCodec(LocalDate.class));
        assertEquals(StringCodec.getInstance(), TEST_REGISTRY.getCodec(String.class));
    }

    /**
     * Test whether codes can be retrieved for primitive types and their wrapper class
     */
    @Test
    public void testCodecRetrivalForPrimitiveTypes() {
        assertEquals(IntCodec.getInstance(), TEST_REGISTRY.getCodec(int.class));
        assertEquals(IntCodec.getInstance(), TEST_REGISTRY.getCodec(Integer.class));
    }
}
