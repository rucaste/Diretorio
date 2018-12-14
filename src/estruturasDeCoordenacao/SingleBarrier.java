package estruturasDeCoordenacao;

public class SingleBarrier {

    private int currentPosters = 0;
    private int totalPosters;

    private int passedWaiters = 0;
    private int totalWaiters;

    public SingleBarrier(int i, int j) {
        totalPosters = i;
        totalWaiters = j;
    }

    public int getCurrentPosters() {
        return currentPosters;
    }

    public int getTotalPosters() {
        return totalPosters;
    }

    public int getPassedWaiters() {
        return passedWaiters;
    }

    public int getTotalWaiters() {
        return totalWaiters;
    }

    public synchronized void barrierWait(){
        boolean interrupted = false;
        while(currentPosters < totalPosters){
            try {
                //System.out.println("single barrier wait currentPosters:" + currentPosters + " totalPosters: " + totalPosters + " passedWaiters: " + passedWaiters + " totalWaiters: " + totalWaiters);
                wait();
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        //System.out.println("single barrier wait pass currentPosters:" + currentPosters + " totalPosters: " + totalPosters + " passedWaiters: " + passedWaiters + " totalWaiters: " + totalWaiters);
        passedWaiters++;
        if(passedWaiters == totalWaiters) {
            currentPosters = 0;
            passedWaiters = 0;
            notifyAll();
        }
        if(interrupted)
            Thread.currentThread().interrupt();
    }

    public synchronized void barrierPost(){
        boolean interrupted = false;
        while (currentPosters == totalPosters){
            try {
                //System.out.println("single barrier post currentPosters:" + currentPosters + " totalPosters: " + totalPosters + " passedWaiters: " + passedWaiters + " totalWaiters: " + totalWaiters);
                wait();
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        //System.out.println("single barrier post pass currentPosters:" + currentPosters + " totalPosters: " + totalPosters + " passedWaiters: " + passedWaiters + " totalWaiters: " + totalWaiters);
        currentPosters++;
        if(currentPosters == totalPosters)
            notifyAll();
        if(interrupted)
            Thread.currentThread().interrupt();
    }
}
