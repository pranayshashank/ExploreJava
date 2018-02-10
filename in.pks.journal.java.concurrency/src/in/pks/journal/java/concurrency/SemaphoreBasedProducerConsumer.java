package in.pks.journal.java.concurrency;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreBasedProducerConsumer {

    Semaphore pub = new Semaphore(1);
    Semaphore con = new Semaphore(0);

    ExecutorService prodPool = Executors.newFixedThreadPool(3);
    ExecutorService consPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        SemaphoreBasedProducerConsumer app = new SemaphoreBasedProducerConsumer();

        Queue<String> dataQueue = new ArrayBlockingQueue<>(1);
        Consumer<String> consumer = new Consumer<>(dataQueue);
        Producer<String> producer = new Producer<>(dataQueue);

        for(int i=0; i<10; i++) {
            int finalI = i;
            app.prodPool.execute(()->{
                Thread.currentThread().setName("Producer-" + Thread.currentThread().getId());
                boolean produced=false;
                try{
                    app.pub.acquireUninterruptibly();
                    producer.produce("Text-"+ finalI);
                    produced=true;
                } finally {
                    if(produced)
                        app.con.release();
                }
            });
        }

        for(int i=0; i<10; i++)
        app.consPool.execute(()->{
            Thread.currentThread().setName("Consumer-" + Thread.currentThread().getId());
            boolean consumed=false;
            try{
                app.con.acquireUninterruptibly();
                consumer.consume();
                consumed=true;
            } finally {
                if(consumed)
                    app.pub.release();
            }
        });

        app.prodPool.shutdown();
        app.consPool.shutdown();

        while (!app.prodPool.isTerminated());
        while (!app.consPool.isTerminated());
    }

    public static class Consumer <T>{
        Queue<T> queue;
        Consumer(Queue<T> queue){
            this.queue = queue;
        }

        public void consume(){
            T element = this.queue.poll();
            System.out.println(Thread.currentThread().getName() + "-Consumed-" + element);
        }

    }

    public static class Producer <T>{
        Queue<T> queue;
        Producer(Queue<T> queue){
            this.queue = queue;
        }

        public void produce(T element){
            this.queue.add(element);
            System.out.println(Thread.currentThread().getName() + "-Published-" + element);
        }
    }

}
