package Concurrent.dbpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if(initialSize>0){
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection){
        if (connection != null) {
            synchronized (pool){
                pool.addLast(connection);
                pool.notifyAll();
                System.out.println(Thread.currentThread().getName()+"将连接放回pool。。。");

            }
        }
    }

    public Connection fetchConnection(long mils) throws InterruptedException{
        synchronized (pool){
            //完全超时
            if(mils <= 0){
                while (pool.isEmpty()){
                    pool.wait();
                }
                System.out.println(Thread.currentThread().getName()+"在超时情况下返回一个连接");
                return pool.removeFirst();
            }else {
                long future = System.currentTimeMillis()+mils;
                long remaining = mils;

                while (pool.isEmpty()&&remaining>0){
                    System.out.println(Thread.currentThread().getName()+"发现pool中没有连接了，开始等待。。。");
                    pool.wait(remaining);
                    System.out.println(Thread.currentThread().getName()+"结束wait(long)方法");
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;

                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                if(remaining>0)
                    System.out.println(Thread.currentThread().getName()+"在没有超时情况下返回一个连接");
                else
                    System.out.println(Thread.currentThread().getName()+"在超时情况下返回一个空连接");
                return result;
            }
        }
    }
}
