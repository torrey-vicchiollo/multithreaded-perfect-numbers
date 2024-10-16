
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Driver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the upper bound (N): ");
        int N = scanner.nextInt();
        if ((N < 1000000) || (N > 1000000000)) {
            System.err.println("Invalid input of N! Defaulting to 1,000,000");
            N = 1000000;
        }
        System.out.println("Enter the number of threads (nThreads): ");
        int nThreads = scanner.nextInt();
        if ((nThreads < 1) || (nThreads > 10)) {
            System.err.println("Invalid input of nThreads! Defaulting to 2");
            nThreads = 2;
        }
        scanner.close();
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<Future<List<Integer>>> futureArr = new ArrayList<>();
        int lowerBound = 1;
        for (int i = 1; i <= nThreads; i++) {
            Future<List<Integer>> result = executor.submit(new PerfectNumber(lowerBound, i == nThreads ? N : (N / nThreads) * i));
            System.out.println("Started thread " + i + ", working from " + lowerBound + " to " + (N / nThreads) * i);
            futureArr.add(result);
            lowerBound = ((N / nThreads) * i) + 1;
        }
        for (Future<List<Integer>> future : futureArr) {
            try {
                List<Integer> result = future.get();
                for (int i : result) {
                    System.out.print(i + ", ");
                }
            } catch (InterruptedException | ExecutionException e) {
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.print("Elapsed time: " + (endTime - startTime) + " ms");
        System.exit(-1);
    }
}
