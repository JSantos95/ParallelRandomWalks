import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Shared.input();
        createThreads();

        for (int i = 0; i < ParallelThreads.size(); i++) { //starts all Threads
            ParallelThreads.get(i).start();
        }

        //Shared.mutex.acquire(); //main waits for threads to finish.
        System.out.println("Running time: " + (System.currentTimeMillis()/1000) + " seconds");

    }

    static ArrayList<Thread> ParallelThreads = new ArrayList<Thread>(); // creates Threads.
    public static void createThreads() {
        int maxWalks = Shared.M;
        for (int i = 0; i < maxWalks; i++){
            ParallelThreads.add(new Thread(new ParallelWalks(i+1)));
        }
    }

}

class Shared{ //class of shared variables
    static int k, u, d, M;
    static Semaphore mutex = new Semaphore(0); //main waits for threads to finish.

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
        reader.close();
    }
}

