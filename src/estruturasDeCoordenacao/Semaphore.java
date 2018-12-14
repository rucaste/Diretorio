package estruturasDeCoordenacao;

public class Semaphore {

    private int count;

    public Semaphore(int i){
        this.count = i;
    }

    public Semaphore(){
        this.count = 0;
    }

    public synchronized void init(int i){
        this.count = i;
    }

    public synchronized void acquire() {
        boolean interruped = false;
        while(count == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                interruped = true;
            }
        }
        count--;
        if(interruped){
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void release(){
        count++;
        notifyAll();
    }

}



