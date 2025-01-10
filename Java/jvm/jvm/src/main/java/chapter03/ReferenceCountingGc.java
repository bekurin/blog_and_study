package chapter03;

/**
 * vm option: -Xlog:gc*
 */
public class ReferenceCountingGc {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        testGc();
    }

    private static void testGc() {
        ReferenceCountingGc objectA = new ReferenceCountingGc();
        ReferenceCountingGc objectB = new ReferenceCountingGc();

        objectA.instance = objectB;
        objectB.instance = objectA;

        objectA = null;
        objectB = null;

        System.gc();
    }
}
