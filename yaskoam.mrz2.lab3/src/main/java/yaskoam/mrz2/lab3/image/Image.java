package yaskoam.mrz2.lab3.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

/**
 * @author Q-YAA
 */
public class Image {

    private int width;

    private int height;

    private int[][] values;

    private static final Map<Character, Integer> SYMBOLS_TO_VALUES_MAP = new HashMap<Character, Integer>() {{
       put('@', 1);
       put('-', -1);
    }};

    private static final Map<Integer, Character> VALUES_TO_SYMBOLS_MAP = new HashMap<Integer, Character>() {{
       put(1, '@');
       put(-1, '-');
    }};

    public Image(char[][] symbols, int width, int height) {
        this(toValues(symbols, width, height), width, height);
    }

    public Image(int[][] values, int width, int height) {
        this.width = width;
        this.height = height;
        this.values = values;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getSymbols() {
        return toSymbols(values, width, height);
    }

    public int getValue(int row, int column) {
        return values[row][column];
    }

    public static Image fromFile(File file) {
        List<String> lines = readFile(file);
        return fromLines(lines);
    }

    public static Image fromLines(List<String> lines) {
        char[][] symbols = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            symbols[i] = lines.get(i).toCharArray();
        }

        return new Image(symbols, symbols[0].length, symbols.length);
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

    private static int[][] toValues(char[][] symbols, int width, int height) {
        int[][] values = new int[height][width];

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                values[i][j] = SYMBOLS_TO_VALUES_MAP.get(symbols[i][j]);
            }
        }

        return values;
    }

    private static char[][] toSymbols(int[][] values, int width, int height) {
        char[][] symbols = new char[height][width];

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                symbols[i][j] = toSymbol(values[i][j]);
            }
        }

        return symbols;
    }

    private static char toSymbol(int value) {
        return VALUES_TO_SYMBOLS_MAP.get(value);
    }
}
