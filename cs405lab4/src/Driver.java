
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
        System.out.print("ENTER UPPER BOUND (N) [1000000 <= N <= 1000000000] >> ");
        int N = scanner.nextInt();
        if ((N < 1000000) || (N > 1000000000)) {
            System.err.println("INVALID INPUT OF N >> DEFAULTING TO 1,000,000");
            N = 1000000;
        }
        System.out.print("ENTER NUMBER OF THREADS (nThreads) [1 <= nThreads <= 10] >> ");
        int nThreads = scanner.nextInt();
        if ((nThreads < 1) || (nThreads > 10)) {
            System.err.println("INVALID INPUT OF nThreads >> DEFAULTING TO 2");
            nThreads = 2;
        }
        scanner.close();
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<Future<List<Integer>>> futureArr = new ArrayList<>();
        int lowerBound = 1;
        for (int i = 1; i <= nThreads; i++) {
            Future<List<Integer>> result = executor.submit(new PerfectNumber(lowerBound, i == nThreads ? N : (N / nThreads) * i));
            System.out.println("STARTED THREAD " + i + " FROM " + lowerBound + " TO " + (N / nThreads) * i);
            futureArr.add(result);
            lowerBound = ((N / nThreads) * i) + 1;
        }
        System.out.print("PERFECT NUMBERS >> ");
        for (Future<List<Integer>> future : futureArr) {
            try {
                List<Integer> result = future.get();
                for (int i : result) {
                    System.out.print(i + " ");
                }
            } catch (InterruptedException | ExecutionException e) {
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.print("\nELAPSED TIME >> " + (endTime - startTime) + " ms");
        System.exit(-1);
    }
}
