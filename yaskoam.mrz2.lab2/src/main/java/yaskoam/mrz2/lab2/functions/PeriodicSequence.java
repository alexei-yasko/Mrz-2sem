package yaskoam.mrz2.lab2.functions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Q-YAA
 */
public class PeriodicSequence extends Sequence<Integer> {

    private static final int PERIOD_LENGTH = 4;

    private static final Map<Integer, Integer> NUMBER_AT_PERIOD_POSITION = new HashMap<Integer, Integer>() {{
        put(0, 1);
        put(1, 0);
        put(2, -1);
        put(3, 0);
    }};

    @Override
    public Integer apply(int x) {
        int positionInPeriod = x - (x / PERIOD_LENGTH) * PERIOD_LENGTH;
        return NUMBER_AT_PERIOD_POSITION.get(positionInPeriod);
    }
}
