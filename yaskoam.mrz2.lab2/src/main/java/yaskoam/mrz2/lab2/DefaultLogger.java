package yaskoam.mrz2.lab2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;

/**
 * @author Q-YAA
 */
public class DefaultLogger implements Logger {

    private static final String LOG_TEMPLATE = "(%s) - Total error: '%s'; Number of iterations: '%s'";

    private OutputStream outputStream;

    public DefaultLogger(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public DefaultLogger() {
        this(System.out);
    }

    @Override
    public void log(double totalError, int iterations) {
        try {
            String currentTime = Calendar.getInstance().getTime().toString();
            IOUtils.write(String.format(LOG_TEMPLATE, currentTime, totalError, iterations) + "\n", outputStream);
        }
        catch (IOException e) {
            throw new IllegalStateException("can't write in log stream", e);
        }
    }
}
