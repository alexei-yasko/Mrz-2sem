package yaskoam.mrz2.lab2.functions;

/**
 * @author Q-YAA
 */
public class FactorialSequence extends Sequence<Long> {

    @Override
    public Long apply(int x) {
        return x <= 0 ? 1 : x * apply(x - 1);
    }
}
