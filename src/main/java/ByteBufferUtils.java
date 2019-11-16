import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class ByteBufferUtils {

    public static void callCleaner(ByteBuffer byteBuffer) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method cleanerMethod = byteBuffer.getClass().getMethod("cleaner");
        cleanerMethod.setAccessible(true);

        Object cleaner = cleanerMethod.invoke(byteBuffer);

        Method cleanMethod = cleaner.getClass().getMethod("clean");
        cleanMethod.setAccessible(true);
        cleanMethod.invoke(cleaner);
    }
}