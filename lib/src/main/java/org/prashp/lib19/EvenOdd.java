package org.prashp.lib19;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EvenOdd {
    static int evens = 0;
    static int odds = 1;
    static Runnable evenRunnable = new Runnable() {
        @Override
        public void run() {
            while(true)
                log("Even " + evens++);
        }
    };
    static Runnable oddRunnable = new Runnable() {
        @Override
        public void run() {
            while(true)
                log("Odd " + odds++);
        }
    };

    public static void main(String[] args) throws Exception{
        // create 2 threads
        Thread oddThread = new Thread(oddRunnable, "Odd Thread");
        oddThread.setDaemon(true);
        Thread evenThread = new Thread(evenRunnable, "Even Thread");
        evenThread.setDaemon(true);

        // start the 2 threads
        oddThread.start();
        evenThread.start();

        // wait for the 2 threads to complete
        Thread.sleep(500);
    }
    private static void log(String str)
    {
        System.out.println(String.format("[%s] [%s] %s",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()),
                Thread.currentThread().getName(), str));
        //System.out.println(Thread.currentThread().getName() + " " + str);
    }
}
