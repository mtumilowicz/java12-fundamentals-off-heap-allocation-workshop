package common;

public interface ShortArrayOffHeap {
    void putShortAt(long index, short value);

    short getShortAt(long index);

    void destroy();
}
