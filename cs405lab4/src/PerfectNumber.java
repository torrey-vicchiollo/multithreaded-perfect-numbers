
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PerfectNumber implements Callable<List<Integer>> {

    private int lowerBound, upperBound;

    public PerfectNumber(int lower, int upper) {
        this.lowerBound = lower;
        this.upperBound = upper;
    }

    @Override
    public List<Integer> call() throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i = lowerBound; i < upperBound; i++) {
            int sum = 1;
            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    if (j * j != i) {
                        sum = sum + j + i / j;
                    } else {
                        sum = sum + j;
                    }
                }
            }
            if (sum == i && i != 1) {
                list.add(sum);
            }
        }
        return list;
    }
}
