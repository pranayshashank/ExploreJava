package in.pks.journal.java.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * A sample to show, how we can use countdown latch.
 * Countdown latch is good to use when we want a single time barrier.
 * The barrier signifies completion of an event or a set of events, post which another dependent task(s) must be executed.
 *
 * In this sample, if enabledCountDownLatchBasedExecution = false, then our Daemon threads dies before completing their intended tasks
 * as main ends before them.
 *
 * Since, we wanted main to terminate only after daemon threads are done, we used CountDownLatch.
 *
 * PS: Please note, CountDownLatch is not the only way to achieve this, but a convenient way indeed.
 */
public class LatchBasedTaskExecutor {

    private CountDownLatch latch = new CountDownLatch(3);
    private boolean enabledCountDownLatchBasedExecution=true;

    public static void main(String[] args) {
        LatchBasedTaskExecutor app = new LatchBasedTaskExecutor();

        Thread t1 = new Thread(()->app.executeTask(app.latch), "DaemonThread-1");
        Thread t2 = new Thread(()->app.executeTask(app.latch), "DaemonThread-2");
        Thread t3 = new Thread(()->app.executeTask(app.latch), "DaemonThread-3");

        t1.setDaemon(true);
        t2.setDaemon(true);
        t3.setDaemon(true);
        t1.start(); t2.start(); t3.start();

        while(app.enabledCountDownLatchBasedExecution)
            try {
                app.latch.await();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        System.out.println(Thread.currentThread().getName() + " finished.");
    }

    private void executeTask(CountDownLatch latch){
        try{
            System.out.println(Thread.currentThread().getName() + " going to execute.");
            Thread.sleep(30000);
            System.out.println(Thread.currentThread().getName() + " executed task.");
            latch.countDown();
        } catch(InterruptedException ignore){}
    }
}
