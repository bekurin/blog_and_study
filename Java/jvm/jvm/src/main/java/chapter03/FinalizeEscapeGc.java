package chapter03;

public class FinalizeEscapeGc {
    public static FinalizeEscapeGc SAVE_HOOK = null;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("starting...");
        SAVE_HOOK = new FinalizeEscapeGc();

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.alive();
        } else {
            System.out.println("I'm dead...");
        }

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.alive();
        } else {
            System.out.println("I'm dead...");
        }

    }

    private void alive() {
        System.out.println("still alive...");
    }

    @Override
    protected void finalize() throws Throwable {
        /**
         * finalize 를 실행할 때 참조 체인에 아무런 객체와 연결 시키면 GC로 청소되지 않고, 다시 살아날 수 있다.
         */
        super.finalize();
        System.out.println("execute finalize()");
        FinalizeEscapeGc.SAVE_HOOK = this;
    }
}
