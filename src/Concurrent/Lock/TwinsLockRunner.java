package Concurrent.Lock;


import java.util.concurrent.locks.Lock;

public class TwinsLockRunner {

    public static void main(String[] args) {
        final Lock lock = new TwinsLock();

        class Work extends Thread{
            public void run(){
                while (true){
                    lock.lock();
                    try {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            Work w = new Work();
            w.setDaemon(true);
            w.start();
        }
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
