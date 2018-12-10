import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelWalks implements Runnable {
    int id;
    int walks;


    @Override
    public void run() {
        try {
            int [] arr = new int[walks];

            for(int i= 0; i < walks; i++){
                arr[i] = RandomWalks(); // saves n result to local arraylist.

            }

            Shared.n[id-1] = arr; // saves to arraylist in Main
            //System.out.println ("Thread " + id + " is running " + Shared.walks + " walks.");
        }


        catch(Exception e){
            e.printStackTrace();
        }
    }

    public ParallelWalks(int n, int w) {
        id = n;
        walks = w;
    }

     int  RandomWalks(){ //randomWalks calculation, returns n when stopped
        int n = 0;
        int x = Shared.k;
        int u = Shared.u;
        int d = Shared.d;

        while (x > 0){

            if(ThreadLocalRandom.current().nextInt(2) == 1){
                x += u;
            }else{
                x -= d;
            }
            n++;
        }

        return n;

    }
}
