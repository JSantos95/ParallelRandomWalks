import java.util.concurrent.ThreadLocalRandom;

public class ParallelWalks extends Thread {
    int id;
    int walks;


    @Override
    public void run() {
        try {
            for(int i= 0; i < walks; i++){
                Data.n[id-1][i] = RandomWalks(); // saves n result to main arraylist.
            }

            //System.out.println ("Thread " + id + " is running " + Data.walks + " walks.");
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
        int x = Data.k;
        int u = Data.u;
        int d = Data.d;

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
