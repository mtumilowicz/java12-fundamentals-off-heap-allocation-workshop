package workshop

import answer.ShortArrayOffHeapAnswer
import spock.lang.Specification

class ShortArrayOffHeapWorkshopTest extends Specification {

    def shortArray = {long size -> new ShortArrayOffHeapWorkshop(size)}

    def 'allocate'() {
        given:
        long maxIntPlus1 = Integer.MAX_VALUE + 1L
        def longArray = shortArray(maxIntPlus1)

        when:
        longArray.putShortAt(0L, (short) 0)
        longArray.putShortAt(1L, (short) 1)

        then:
        longArray.getShortAt(0) == 0
        longArray.getShortAt(1) == 1
        longArray.destroy()
    }
}
