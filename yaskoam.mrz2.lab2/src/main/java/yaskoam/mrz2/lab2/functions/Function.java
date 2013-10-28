package yaskoam.mrz2.lab2.functions;

import java.util.List;

/**
 * @author Q-YAA
 */
public abstract class Function<T> {

    public abstract T apply(T x);

    public abstract List<T> generateSequence(T x, int n);
}
