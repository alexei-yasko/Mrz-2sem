package yaskoam.mrz2.lab3.functions;

import static java.lang.Math.pow;

/**
 * @author Q-YAA
 */
public class PowerSequence extends Sequence<Integer> {

    private int number;

    public PowerSequence(int number) {
        this.number = number;
    }

    @Override
    public Integer apply(int x) {
        return (int) pow(number, x);
    }
}
