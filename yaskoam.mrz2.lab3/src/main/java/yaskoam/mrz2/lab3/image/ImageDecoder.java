package yaskoam.mrz2.lab3.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

/**
 * @author Q-YAA
 */
public class ImageDecoder {

    private static final Map<Character, Integer> SYMBOLS_TO_VALUES_MAP = new HashMap<Character, Integer>() {{
       put('@', 1);
       put('-', -1);
    }};

    private static final Map<Integer, Character> VALUES_TO_SYMBOLS_MAP = new HashMap<Integer, Character>() {{
       put(1, '@');
       put(-1, '-');
    }};

    public static Image fromFile(File file) {
        List<String> lines = readFile(file);
        return fromLines(lines);
    }

    public static Image image(int[][] values, int width, int height) {
        return new Image(values, width, height);
    }

    public static Image fromSymbols(char[][] symbols, int width, int height) {
        int[][] values = new int[height][width];

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                values[i][j] = SYMBOLS_TO_VALUES_MAP.get(symbols[i][j]);
            }
        }

        return image(values, width, height);
    }

    public static char[][] toSymbols(Image image) {
        char[][] symbols = new char[image.getHeight()][image.getWidth()];

        for(int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                symbols[i][j] = toSymbol(image.getValue(i, j));
            }
        }

        return symbols;
    }

    public static Image fromLines(String[] lines) {
        return fromLines(Arrays.asList(lines));
    }

    public static Image fromLines(List<String> lines) {
        char[][] symbols = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            symbols[i] = lines.get(i).toCharArray();
        }

        return fromSymbols(symbols, symbols[0].length, symbols.length);
    }

    private static List<String> readFile(File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            return IOUtils.readLines(inputStream);
        }
        catch (IOException e) {
            throw new IllegalStateException("can't read image file", e);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    private static char toSymbol(int value) {
        return VALUES_TO_SYMBOLS_MAP.get(value);
    }
}
