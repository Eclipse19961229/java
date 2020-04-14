package Concurrent;

public class Reorder {
    int a;
    boolean flag;

    public void writer(){
        a = 1;
        flag = true;
    }

    public void reader(){
        if(flag){
            System.out.println("a:"+a);
        }
        else {
            System.out.println("error");
        }
    }
}
