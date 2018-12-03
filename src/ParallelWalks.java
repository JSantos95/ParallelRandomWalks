import java.util.Random;

public class ParallelWalks implements Runnable {
    int id;

    @Override
    public void run() {
        try {

            for(int i= 0; i < Shared.walks; i++){
                int n = RandomWalks();
                Shared.saveArr.acquire();
                Shared.results.add(n); // saves n result to arraylist.
                //Shared.writeFile(n);  // saves n result to txt
                Shared.saveArr.release();

            }

            System.out.println ("Thread " +
                    id +
                    " is running " + Shared.walks + " walks.");
            Shared.mutex.release(); //Changes mutex so main continues.
        }


        catch(Exception e){
            e.printStackTrace();
        }
    }

    public ParallelWalks(int n) {
        id = n;
    }

     int  RandomWalks(){ //randomWalks calculation, returns n when stopped
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

        return n;
    }
}
