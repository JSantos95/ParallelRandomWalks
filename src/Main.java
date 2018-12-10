import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedWriter;

class Data{ //class of shared variables
    static int k = 100;
    static int u = 1;
    static int d = 2;
    static int M = 10000000;
    static int T = 1000;
    static int walks = M/T;
    static int[][] n = new int[T][walks];
}

public class Main extends Data {
    public static void main(String[] args) throws InterruptedException {
        double startTime = System.currentTimeMillis(); //runtime start
        ParallelWalks threads[] = new ParallelWalks[T]; // initiize thread array
        int max = 0;
        double one = 0;


        for (int j = 0; j < T; ++j) { //starts T threads
            threads[j] = new ParallelWalks(j + 1, walks);
            threads[j].start();
        }


        for (int i = 0; i < T; ++i) {
            ParallelWalks thread = threads[i];
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        for(int i = 0; i < n.length; i++){ //searches for max n
            for (int j = 0; j < walks; j++) {
                if (n[i][j] > max)
                    max = n[i][j];
            }
        }

        double[] res = new double[max+1]; //array for h(n)
        double Min = 1.0/(double)M;
        for(int i = 0; i < n.length; i++){ // checks results
            for (int j = 0; j < walks; j++) {
                int num = n[i][j];
                res[num] += Min;
                one += Min;
            }
        }


        System.out.println(one); //check if sum of all heights are equal to 1.
        System.out.println(max); //print max n

        try(FileWriter fw = new FileWriter("histogram.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("n  " + "  h(n)");
            out.println("------------");
            for(int i = 0; i < max; i++){ //write to file
                double num = res[i];
                out.println(i + "     " + num);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        double endTime = System.currentTimeMillis();
        System.out.println("Running time: " + (endTime - startTime)/1000 + " seconds"); //runtime is printed out

    }
}

