package Concurrent;

public class WaitNotify {
    static boolean flag = true;//是否等待
    static Object lock = new Object();

    public static void main(String[] args) {
        Thread waitThread = new Thread(new WaitRunner(),"waitThread");
        waitThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread notifyThread = new Thread(new NotifyRunner(),"notifyThread");
        notifyThread.start();
    }

    static class WaitRunner implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                while (flag){
                    try {
                        System.out.println("WaitRunner start wait...");
                        lock.wait();
                        System.out.println("WaitRunner end wait...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("WaitRunner finish work...");
        }
    }

    static class NotifyRunner implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                System.out.println("NotifyRunner start...");
                lock.notifyAll();
                flag = false;
                try {
                    System.out.println("NotifyRunner start sleep...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("NotifyRunner end sleep and relase lock...");
            }

            synchronized (lock){
                System.out.println("NotifyRunner get lock again");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


