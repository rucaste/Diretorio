package estruturasDeCoordenacao;

public class Barrier {

    private int nWaiters;
    private int currentWaiters = 0;
    private int passedWaiters = 0;

    public Barrier(int i){
        this.nWaiters = i;
    }

    public int getCurrentWaiters(){
        return this.currentWaiters;
    }

    public synchronized void await() throws InterruptedException {
        this.currentWaiters++;
        while (currentWaiters < nWaiters){
            wait();
        }if(passedWaiters == 0){
            notifyAll();
        }
        passedWaiters++;
        if(passedWaiters == nWaiters){
            passedWaiters = 0;
            currentWaiters = 0;
        }
    }



}
