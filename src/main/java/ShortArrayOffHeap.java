import sun.misc.Unsafe;

public class ShortArrayOffHeap {
    private final long size;
    private final long startAddress;
    private final short SHORT_LENGTH_IN_BYTES = 2;

    private final Unsafe unsafe = UnsafeUtils.createUnsafe();

    public ShortArrayOffHeap(long size) {
        this.size = size;
        startAddress = unsafe.allocateMemory(allocationSize());
        initWithZeros();
    }

    public void putShortAt(long index, short value) {
        verifyIndexNotExceedsSize(index);
        unsafe.putShort(addressOf(index), value);
    }

    public short getShortAt(long index) {
        verifyIndexNotExceedsSize(index);
        return unsafe.getShort(addressOf(index));
    }

    public void destroy() {
        unsafe.freeMemory(startAddress);
    }

    private long addressOf(long index) {
        return startAddress + (index * SHORT_LENGTH_IN_BYTES);
    }

    private void initWithZeros() {
        unsafe.setMemory(startAddress, allocationSize(), (byte) 0);
    }

    private long allocationSize() {
        return size * SHORT_LENGTH_IN_BYTES;
    }

    private void verifyIndexNotExceedsSize(long index) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}