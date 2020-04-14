package Concurrent;

public class RunReorder {
    private static Reorder reorder = new Reorder();
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reorder.writer();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reorder.reader();
            }
        });
        t1.start();
        t2.start();
    }
}
