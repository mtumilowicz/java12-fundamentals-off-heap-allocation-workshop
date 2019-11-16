import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteBufferTest {


    @Test
    public void testAllocateDirect2GB() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JvmUtils.verifyJvmArgumentsPresent(List.of("-Xmx64m", "-XX:MaxDirectMemorySize=2g"));

        long capacity2GB = (2 * ByteUtil.GB) - 1;
        assertThat(capacity2GB).isLessThanOrEqualTo(Integer.MAX_VALUE);

        //after this line process memory grows up to 2GB+
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) capacity2GB);


        //perform "GC" on created byte buffer (
        ByteBufferUtils.callCleaner(byteBuffer);
    }

    @Test
    public void testAllocateDirectBiggerThanMaxDirectMemorySize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JvmUtils.verifyJvmArgumentsPresent(List.of("-Xmx64m"));
        JvmUtils.verifyJvmArgumentsNotPresent(List.of("-XX:MaxDirectMemorySize"));

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) (ByteUtil.GB) + 1); //OutOfMemoryError: Direct buffer memory
    }

    @Test
    public void testAllocateBiggerThanHeap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JvmUtils.verifyJvmArgumentsPresent(List.of("-Xmx64m", "-XX:MaxDirectMemorySize=2g"));

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) (64 * ByteUtil.MB)); //OutOfMemoryError: Java heap space
    }

    @Test
    public void testEndianess() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ByteBuffer bigEndianByteBuffer = ByteBuffer.allocate(4);
        bigEndianByteBuffer.order(ByteOrder.BIG_ENDIAN);

        ByteBuffer littleEndianByteBuffer = ByteBuffer.allocate(4);
        littleEndianByteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        littleEndianByteBuffer.putInt(0xCAFEBABE);
        bigEndianByteBuffer.putInt(0xCAFEBABE);

        String bigEndianHexString = Hex.encodeHexString(bigEndianByteBuffer.array());
        String littleEndianHexString = Hex.encodeHexString(littleEndianByteBuffer.array());

        assertThat(bigEndianHexString).isEqualToIgnoringCase("CAFEBABE");
        assertThat(littleEndianHexString).isEqualToIgnoringCase("BEBAFECA");
    }
}