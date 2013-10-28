package yaskoam.mrz2.lab2.functions;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author Q-YAA
 */
public class FibonacciFunction extends Function<Integer> {

    @Override
    public Integer apply(Integer x) {
        return (int) ((pow((1 + sqrt(5)) / 2, x) - pow((1 - sqrt(5)) / 2, x)) / sqrt(5));
    }

    @Override
    public List<Integer> generateSequence(Integer x, int n) {

        List<Integer> resultSequence = new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {
            resultSequence.add(apply(x + i));
        }

        return resultSequence;
    }
}
