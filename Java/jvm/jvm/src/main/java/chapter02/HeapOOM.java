package chapter02;

import java.util.ArrayList;

/**
 * OutOfMemoryError
 * vm options: -Xms20m -Xmx20m -XX:+HeapDumpOutOfMemoryError
 */
public class HeapOOM {
    static class OOMObject {}

    public static void main(String[] args) {
        ArrayList<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }
    }
}
