package yaskoam.mrz2.lab2.functions;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Q-YAA
 */
public class FibonacciFunctionTest {

    @Test
    public void testSimple() {
        FibonacciSequence fibonacciFunction = new FibonacciSequence();

        Assert.assertThat(fibonacciFunction.apply(0), is(0l));
        Assert.assertThat(fibonacciFunction.apply(1), is(1l));
        Assert.assertThat(fibonacciFunction.apply(2), is(1l));
        Assert.assertThat(fibonacciFunction.apply(3), is(2l));
        Assert.assertThat(fibonacciFunction.apply(4), is(3l));
        Assert.assertThat(fibonacciFunction.apply(5), is(5l));
        Assert.assertThat(fibonacciFunction.apply(6), is(8l));
        Assert.assertThat(fibonacciFunction.apply(7), is(13l));
        Assert.assertThat(fibonacciFunction.apply(8), is(21l));
        Assert.assertThat(fibonacciFunction.apply(9), is(34l));
    }
}
