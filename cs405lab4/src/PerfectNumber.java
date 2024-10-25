
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PerfectNumber implements Callable<List<Integer>> {

    private final int lowerBound, upperBound;

    public PerfectNumber(int l, int u) {
        this.lowerBound = l;
        this.upperBound = u;
    }

    @Override
    public List<Integer> call() throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i = lowerBound; i < upperBound; i++) {
            int sum = 1;
            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    sum += j * j != i ? j + i / j : j;
                }
            }
            if (sum == i && i != 1) {
                list.add(sum);
            }
        }
        return list;
    }
}
