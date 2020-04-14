package Concurrent.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch bossLatch = new CountDownLatch(2);
        Worker worker1 = new Worker(bossLatch,"work1");
        Worker worker2 = new Worker(bossLatch,"work2");
        Boss boss = new Boss(bossLatch);
        executor.execute(worker1);
        executor.execute(worker2);
        executor.execute(boss);
        executor.shutdown();
    }

}
