import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Shared.input();
        createThreads();
        double startTime = System.currentTimeMillis();

        for (int i = 0; i < ParallelThreads.size(); i++) { //starts all Threads
            ParallelThreads.get(i).start();
        }

        for (int i = 0; i < Shared.numThreads; ++i) {
            Thread thread = ParallelThreads.get(i);
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        Shared.mutex.acquire(); //main waits for threads to finish.
        double endTime = System.currentTimeMillis();
        System.out.println("Results: " + Shared.results);
        System.out.println("Running time: " + (endTime - startTime)/1000 + " seconds");

    }

    static ArrayList<Thread> ParallelThreads = new ArrayList<>(); // creates Threads.
    public static void createThreads() {
        double maxWalks = Shared.numThreads;
        for (int i = 0; i < maxWalks; i++){
            ParallelThreads.add(new Thread(new ParallelWalks(i+1)));
        }
    }

}

class Shared{ //class of shared variables
    static int k, u, d, M;
    static double numThreads, walks;
    static ArrayList<Integer> results = new ArrayList<>(); //list of results
    //static int[] n = new int[M];
    static Semaphore mutex = new Semaphore(0); //main waits for threads to finish.
    //static File file = new File("test.txt");



    static public void input() {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number for k: ");
        k = reader.nextInt();
        while(k < 0){
            System.out.println("k must be greater 0, try again");
            k = reader.nextInt();
        }

        System.out.println("Enter a number for u: ");
        u = reader.nextInt();
        while(u < 0){
            System.out.println("u must be greater 0, try again");
            u = reader.nextInt();
        }

        System.out.println("Enter a number for d: ");
        d = reader.nextInt();
        while(d < u){
            System.out.println("d must be greater 0 and u, try again");
            d = reader.nextInt();
        }

        System.out.println("Enter a number for M: ");
        M = reader.nextInt();
        while(M < 0){
            System.out.println("M must be greater 0, try again");
            M = reader.nextInt();
        }

        if(M < 100){ //if the number of walks is less than 100, run in one thread
            numThreads = 1;
            walks = M;
        }
        else {
            double remainder = Math.ceil(M % 100);
            numThreads = M / 100 + remainder; //if M is greater than 100, divide work into number of threads
            if(numThreads > 100){
                System.out.println("Max threads reached. " );
                numThreads = 100;
                walks = Math.ceil(M/100);
                System.out.println("Num of threads: " + numThreads);
            }
            else{
                walks = Math.ceil(M/numThreads);
                System.out.println("Num of threads: " + numThreads);
            }
        }
        reader.close();
    }

    static public void writeFile(int n){
        String r = Integer.toString(n);
        try(FileWriter fw = new FileWriter("test.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(r);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

