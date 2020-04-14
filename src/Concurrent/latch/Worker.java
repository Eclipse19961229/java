package Concurrent.latch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Worker implements Runnable {
    private CountDownLatch countDownLatch;
    private String name;

    public Worker(CountDownLatch countDownLatch, String name) {
        this.countDownLatch = countDownLatch;
        this.name = name;
    }

    @Override
    public void run() {
        doWork();
        try {

            countDownLatch.countDown();
            System.out.println(name+"干完活了，还剩下"+countDownLatch.getCount());
            TimeUnit.SECONDS.sleep(new Random().nextInt(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void doWork(){
        System.out.println(name+"正在干活。。。");
    }
}
