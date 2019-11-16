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
//        JvmUtils.verifyJvmArgumentsPresent(List.of("-Xmx64m", "-XX:MaxDirectMemorySize=2g"));

        long capacity2GB = (62 * ByteUtil.MB) - 1;
        assertThat(capacity2GB).isLessThanOrEqualTo(Integer.MAX_VALUE);

        //after this line process memory grows up to 2GB+
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) capacity2GB);


        //perform "GC" on created byte buffer (
        ByteBufferUtils.callCleaner(byteBuffer);
    }

    @Test
    public void testAllocateDirectBiggerThanMaxDirectMemorySize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        JvmUtils.verifyJvmArgumentsPresent(List.of("-Xmx64m"));
//        JvmUtils.verifyJvmArgumentsNotPresent(List.of("-XX:MaxDirectMemorySize"));

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) (62 * ByteUtil.MB) + 1); //OutOfMemoryError: Direct buffer memory
    }

    @Test
    public void testAllocateBiggerThanHeap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        JvmUtils.verifyJvmArgumentsPresent(List.of("-Xmx64m", "-XX:MaxDirectMemorySize=2g"));

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) (64 * ByteUtil.MB)); //OutOfMemoryError: Java heap space
    }
}