package estruturasDeCoordenacao;


import java.util.LinkedList;

public class ThreadPool {

    private Worker[] threads;
    private LinkedList<Runnable> taskQueue;
    private Semaphore semaphore;

    public ThreadPool(int numberOfThreads){
        this.semaphore = new Semaphore(1);
        taskQueue = new LinkedList<Runnable>();
        threads = new Worker[numberOfThreads];

        for(int i = 0; i < threads.length; i++){
            threads[i] = new Worker();
            threads[i].start();
        }
    }

    public void enqueue(Runnable runnable) {
        synchronized (taskQueue) {
            taskQueue.addLast(runnable);
            taskQueue.notify();
        }
    }

    public class Worker extends Thread {

        @Override
        public void run() {
            Runnable runnable;
            while(true){
                synchronized (taskQueue) {
                    while(taskQueue.isEmpty()){
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    runnable = (Runnable) taskQueue.removeFirst();
                }
                runnable.run();
            }
        }
    }

}