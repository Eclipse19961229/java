package Concurrent.latch;

import java.util.concurrent.CountDownLatch;

public class Boss implements Runnable {

    private CountDownLatch countDownLatch;

    public Boss(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("老板等所有人干完活。。。");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("所有人干完活,老板开始检查。。。");
    }
}
