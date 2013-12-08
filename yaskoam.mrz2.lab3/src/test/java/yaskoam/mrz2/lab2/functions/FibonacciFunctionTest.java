package yaskoam.mrz2.lab2.functions;

import org.junit.Assert;
import org.junit.Test;

import yaskoam.mrz2.lab3.functions.FibonacciSequence;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Q-YAA
 */
public class FibonacciFunctionTest {

    @Test
    public void testSimple() {
        FibonacciSequence fibonacciFunction = new FibonacciSequence();

        Assert.assertThat(fibonacciFunction.apply(0), is(0));
        Assert.assertThat(fibonacciFunction.apply(1), is(1));
        Assert.assertThat(fibonacciFunction.apply(2), is(1));
        Assert.assertThat(fibonacciFunction.apply(3), is(2));
        Assert.assertThat(fibonacciFunction.apply(4), is(3));
        Assert.assertThat(fibonacciFunction.apply(5), is(5));
        Assert.assertThat(fibonacciFunction.apply(6), is(8));
        Assert.assertThat(fibonacciFunction.apply(7), is(13));
        Assert.assertThat(fibonacciFunction.apply(8), is(21));
        Assert.assertThat(fibonacciFunction.apply(9), is(34));
    }
}
