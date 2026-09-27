package satellite;

import java.io.*;

public class Satellite {
    static int[][] oldImage, newImage;
    static int rows, cols;

    public static void main(String[] args) throws IOException {
        InputStream is = Satellite.class.getClassLoader().getResourceAsStream("input.txt");

        if (is == null) {
            // Fallback for nested project structures
            File file = new File("satellite-master/input.txt");
            if (!file.exists()) file = new File("input.txt");
            if (!file.exists()) file = new File("src/input.txt");
            is = new FileInputStream(file);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String firstLine = reader.readLine();
        if (firstLine == null) return;

        String[] dims = firstLine.trim().split("\\s+");
        rows = Integer.parseInt(dims[0]);
        cols = dims.length > 1 ? Integer.parseInt(dims[1]) : Integer.parseInt(reader.readLine().trim());

        oldImage = readImage(reader);
        newImage = readImage(reader);
        reader.close();

        int x1 = getBound(0, 1, true);
        int y1 = getBound(0, 1, false);
        int x2 = getBound(rows - 1, -1, true);
        int y2 = getBound(cols - 1, -1, false);

        printResult(x1, y1, x2, y2);
    }

    public static int[][] readImage(BufferedReader br) throws IOException {
        int[][] img = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            String line = br.readLine();
            while (line != null && line.trim().isEmpty()) {
                line = br.readLine();
            }
            if (line == null) break;
            String[] parts = line.trim().split("\\s+");
            for (int c = 0; c < cols; c++) img[r][c] = Integer.parseInt(parts[c]);
        }
        return img;
    }

    public static int getBound(int start, int step, boolean isRow) {
        int limit = isRow ? rows : cols;
        for (int i = start; i >= 0 && i < limit; i += step) {
            if (!isEqual(i, isRow)) return i + 1;
        }
        return -1;
    }

    public static boolean isEqual(int idx, boolean isRow) {
        int count = isRow ? cols : rows;
        for (int k = 0; k < count; k++) {
            if (isRow && oldImage[idx][k] != newImage[idx][k]) return false;
            if (!isRow && oldImage[k][idx] != newImage[k][idx]) return false;
        }
        return true;
    }

    public static void printResult(int x1, int y1, int x2, int y2) {
        if (x1 == -1 || y1 == -1 || x2 == -1 || y2 == -1) System.out.println("The two images are the same");
        else System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
    }
}