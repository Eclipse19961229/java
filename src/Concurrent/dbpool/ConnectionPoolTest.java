package Concurrent.dbpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {

    static ConnectionPool pool = new ConnectionPool(2);
    //CountDownLatch 个同步辅助类，它允许一个或多个线程一直等待直到其他线程执行完毕才开始执行
    //用给定的计数初始化CountDownLatch，其含义是要被等待执行完的线程个数
    //每次调用CountDown()，计数减1
    //主程序执行到await()函数会阻塞等待线程的执行，直到计数为0
    static CountDownLatch start = new CountDownLatch(1);//保证所有的线程同时开始
    static CountDownLatch end ;//保证main线程在其他线程都结束后才继续执行

    public static void main(String[] args) throws Exception{
        int threadCount = 3;
        end = new CountDownLatch(threadCount);
        int count = 2;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            new Thread(new ConnectionRunner(count,got,notGot),"ConnectionRunnerThread-"+i).start();
        }
        Thread.sleep(1000);
        start.countDown();
        end.await();
        System.out.println("total invoke "+(threadCount*count));
        System.out.println("got :"+got);
        System.out.println("not got :"+notGot);
    }

    static class ConnectionRunner implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+"执行start.await()");
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (count > 0){
                try {
                    System.out.println(Thread.currentThread().getName()+"开始获取连接");
                    Connection connection = pool.fetchConnection(1000);
                    if(connection != null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else {
                        notGot.incrementAndGet();
                    }
                }catch (Exception e){

                }finally {
                    count--;
                }
            }

            end.countDown();
        }
    }
}
