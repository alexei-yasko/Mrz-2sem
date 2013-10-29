package yaskoam.mrz2.lab2.functions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Q-YAA
 */
public class PeriodicSequence extends Sequence<Long> {

    private static final int PERIOD_LENGTH = 4;

    private static final Map<Integer, Long> NUMBER_AT_PERIOD_POSITION = new HashMap<Integer, Long>() {{
        put(0, 1l);
        put(1, 0l);
        put(2, -1l);
        put(3, 0l);
    }};

    @Override
    public Long apply(int x) {
        int positionInPeriod = x - (x / PERIOD_LENGTH) * PERIOD_LENGTH;
        return NUMBER_AT_PERIOD_POSITION.get(positionInPeriod);
    }
}
