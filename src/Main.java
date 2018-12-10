import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedWriter;

class Shared{ //class of shared variables
    static int k = 1;
    static int u = 1;
    static int d = 2;
    static int M = 10000000;
    static int T = 1000;
    static int walks = M/T;
    static int[][] n = new int[T][walks];
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int max = 0;

        createThreads();
        double startTime = System.currentTimeMillis();

        //for (int i = 0; i < Shared.M; i++){
        for (int j = 0; j < ParallelThreads.size(); j++) { //starts all Threads
            ParallelThreads.get(j).start();
        }
        //}

        for (int i = 0; i < Shared.T; ++i) {
            Thread thread = ParallelThreads.get(i);
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        for(int i = 0; i < Shared.n.length; i++){ //searches for max n
            for (int j = 0; j < Shared.walks; j++) {
                if (Shared.n[i][j] > max)
                    max = Shared.n[i][j];
            }
        }

        double one = 0;
        double[] res = new double[max+1]; //array for h(n)
        double Min = 1.0/(double)Shared.M;
        for(int i = 0; i < Shared.n.length; i++){ // checks results
            for (int j = 0; j < Shared.walks; j++) {
                int num = Shared.n[i][j];
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
            for(int i = 0; i <= max; i++){ //write to file
                double num = res[i];
                out.println(i + "     " + num);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        double endTime = System.currentTimeMillis();
        //System.out.println("Results saved: M:" + Shared.M + " Walks: " + Shared.n.length * Shared.n[0].length);
        System.out.println("Running time: " + (endTime - startTime)/1000 + " seconds"); //runtime is printed out

    }

    static ArrayList<Thread> ParallelThreads = new ArrayList<>(); // creates Threads.
    public static void createThreads() {
        double maxWalks = Shared.T;
        for (int i = 0; i < maxWalks; i++){
            ParallelThreads.add(new Thread(new ParallelWalks(i+1)));
        }
    }
}

