package Concurrent.Lock;

public class ExtendDemo {
    public static void main(String[] args) {
        sub s = new sub();
        s.song();
    }
}

class parent {

    void song(){
        useMouth();
        System.out.println("song");
    }
    void useMouth(){
        System.out.println("use mouth");
    }
}

class sub extends parent{

    @Override
    void useMouth() {
        System.out.println("sub use mouth");
    }
}