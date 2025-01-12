package chapter02;

/**
 * StackOverFlowError
 * vm options: -Xss180K
 */
public class JavaVmStackSOF_1 {
    private int stackLength = 1;

    private void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVmStackSOF_1 oom = new JavaVmStackSOF_1();
        try {
            oom.stackLeak();
        } catch (Throwable throwable) {
            // Stack Length: 37187
            System.out.println("Stack Length: " + oom.stackLength);
            throw throwable;
        }
    }
}
