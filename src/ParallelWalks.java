import java.util.Random;

public class ParallelWalks implements Runnable {
    int id;

    @Override
    public void run() {
        try {

            int n = 0;
            int x = Shared.k;
            int u = Shared.u;
            int d = Shared.d;
            Random r = new Random();

            while (x > 0){

                if(r.nextInt(2) == 1){
                    x += u;
                }else{
                    x -= d;
                }
                n++;
            }

            System.out.println ("Thread " +
                    id +
                    " is running, number of steps (n): " + n);
            Shared.mutex.release(); //Changes mutex so main continues.
        }


        catch(Exception e){
            e.printStackTrace();
        }
    }

    public ParallelWalks(int n) {
        id = n;
    }
}
