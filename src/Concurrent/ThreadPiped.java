package Concurrent;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedReader;
import java.io.PipedWriter;

public class ThreadPiped {
    public static void main(String[] args) throws Exception{
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();

        out.connect(in);

        Thread printThread = new Thread(new PrintThread(in),"printThread");
        printThread.start();

        int receive = 0;
        try {
            while ((receive=System.in.read())!=-1){
                out.write(receive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }


    }
    static class PrintThread implements Runnable{
        private PipedReader in ;

        public PrintThread(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive=in.read())!=-1){
                    System.out.print((char)receive);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
