package yaskoam.mrz2.lab3.functions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Q-YAA
 */
public abstract class Sequence<T> {

    public abstract T apply(int x);

    public List<T> generateSequence(int from, int to) {

        List<T> resultSequence = new ArrayList<T>();

        for (int i = from; i < to; i++) {
            resultSequence.add(apply(i));
        }

        return resultSequence;
    }
}
