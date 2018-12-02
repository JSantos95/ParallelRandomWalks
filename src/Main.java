import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        int k, u, d, M;

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
        while(d < 0){
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

        int[] n = new int[M];

        for(int i= 0; i < M; i++){
            n[i] = randomWalk(k, u, d);
        }

        for(int i= 0; i < M; i++){
            System.out.println("Number of steps is " + n[i] + '\n');
        }

    }


    public static int randomWalk(int x, int u, int d){
        int n = 0;
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
