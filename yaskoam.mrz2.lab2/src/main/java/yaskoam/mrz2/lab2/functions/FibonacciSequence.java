package yaskoam.mrz2.lab2.functions;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author Q-YAA
 */
public class FibonacciSequence extends Sequence<Long> {

    @Override
    public Long apply(int x) {
        return (long) ((pow((1 + sqrt(5)) / 2, x) - pow((1 - sqrt(5)) / 2, x)) / sqrt(5));
    }
}
