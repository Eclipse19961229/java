package Concurrent.Lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    public static void main(String[] args) {
        myCondition condition = new myCondition();
        Thread w = new Thread(new Waiter(condition),"Waiter");
        Thread s = new Thread(new Signer(condition),"Signer");
        w.start();
        s.start();

    }
}

class myCondition{
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public myCondition() {
    }

    public void myConditionWait() throws InterruptedException{
        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }
    }

    public void myConditionSignal() throws InterruptedException{
        lock.lock();
        try {
            condition.signal();
        }finally {
            lock.unlock();
        }
    }
}

class Waiter implements Runnable{

    myCondition condition;

    public Waiter(myCondition condition) {
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+" wait");
            condition.myConditionWait();
            System.out.println(Thread.currentThread().getName()+" end wait");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Signer implements Runnable{

    myCondition condition;

    public Signer(myCondition condition) {
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+" signal");
            condition.myConditionSignal();
            System.out.println(Thread.currentThread().getName()+" end signal");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
