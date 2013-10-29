package yaskoam.mrz2.lab2.functions;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Q-YAA
 */
public class FactorialFunctionTest {

    @Test
    public void testSimple() {
        FactorialSequence factorialFunction = new FactorialSequence();

        Assert.assertThat(factorialFunction.apply(0), is(1l));
        Assert.assertThat(factorialFunction.apply(1), is(1l));
        Assert.assertThat(factorialFunction.apply(2), is(2l));
        Assert.assertThat(factorialFunction.apply(3), is(6l));
        Assert.assertThat(factorialFunction.apply(4), is(24l));
        Assert.assertThat(factorialFunction.apply(5), is(120l));
        Assert.assertThat(factorialFunction.apply(6), is(720l));
        Assert.assertThat(factorialFunction.apply(7), is(5040l));
        Assert.assertThat(factorialFunction.apply(8), is(40320l));
        Assert.assertThat(factorialFunction.apply(9), is(362880l));
    }
}
