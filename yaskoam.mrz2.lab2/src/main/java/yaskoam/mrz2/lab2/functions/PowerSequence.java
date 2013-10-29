package yaskoam.mrz2.lab2.functions;

import static java.lang.Math.pow;

/**
 * @author Q-YAA
 */
public class PowerSequence extends Sequence<Long> {

    private int number;

    public PowerSequence(int number) {
        this.number = number;
    }

    @Override
    public Long apply(int x) {
        return (long) pow(number, x);
    }
}
