import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

public class UnsafeUtils {
    public static Unsafe createUnsafe() {
        try {
            Constructor<Unsafe> unsafeConstructor = Unsafe.class.getDeclaredConstructor();
            unsafeConstructor.setAccessible(true);
            return unsafeConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}