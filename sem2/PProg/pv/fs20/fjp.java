import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class PrevSumFJ extends RecursiveAction {
    private int[] data;
    private int start;
    private int length;
    private int left;

    public static void fjPrevSum(int[] data) {
        ForkJoinPool fjp = new ForkJoinPool();
        PrevSumFJ task = new PrevSumFJ(data, 0, data.length, 0);
        fjp.invoke(task);
    }

    public PrevSumFJ(int[] data, int start, int length, int left) {
        this.data = data;
        this.start = start;
        this.length = length;
        this.left = left;
    }

    protected void compute() {
        if (length == 1) {
            data[start] = data[start]+left;
        }
        else{
            int newLength = length/2;
            PrevSumFJ firstHalf = new PrevSumFJ(data, start, newLength, left);
            PrevSumFJ secondHalf = new PrevSumFJ(data, start + newLength, length - newLength, data[start + newLength -1]);
            firstHalf.fork();
            secondHalf.compute();
            firstHalf.join();
        }
    }   
}
