package Concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoin extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;
    int start;
    int end;

    public ForkJoin(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if(canCompute){
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        }else {
            int mid = (start+end)/2;
            ForkJoin leftTask = new ForkJoin(start,mid);
            ForkJoin rightTask = new ForkJoin(mid+1,end);
            leftTask.fork();
            rightTask.fork();
            sum = leftTask.join() + rightTask.join();
        }

        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoin task = new ForkJoin(1,4);
        Future<Integer> result = pool.submit(task);

        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
        }
    }
}
