package us.mattgreen.Test;
import us.mattgreen.FileInput;
import us.mattgreen.FileOutput;
import org.junit.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainTest extends junit.framework.TestCase {
    public MainTest() {}

    FileInput inFile;
    FileOutput sorted;
    BufferedReader toSort;
    double[] dubs;
    int[] nums;

    @Before
    public void setUp() throws Exception {
        toSort = null;
        dubs = new double[3];
        nums = new int[2];
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testGetAvgRating() {
        dubs[0] = 0;
        dubs[1] = 0;
        dubs[2] = 0;
        String line;
        String[] fields;
        boolean done = false;
        FileInput movieRatings = new FileInput("movie_rating.csv");
        while (((line = movieRatings.fileReadLine()) != null) && !(done)) {
            fields = line.split(",");
            if (fields[0].compareTo("100200") > 0) {
                done = true;
            }
            else if (fields[0].equals("100200")) {
                dubs[0]++;
                dubs[1] += Double.parseDouble(fields[1]);
                dubs[2] = dubs[1] / dubs[0];
            }
        }
        movieRatings.fileClose();

        assertEquals(dubs[2], 3.25);
        assertNotSame(dubs[2], 5);
    }

    @Test
    public void testSortFile() {
        try {
            toSort = new BufferedReader(new FileReader("output.csv"));
        } catch(FileNotFoundException e) {
            System.out.println("File Open Error: output.csv "  + e);
        }

        String line;
        String[] lineIn;
        ArrayList<String[]> lines = new ArrayList<>();

        try {
            while ((line = toSort.readLine()) != null) {
                lineIn = line.split(",");
                lines.add(lineIn);
            }
        } catch (Exception e) {
            System.out.println("File Write Error: output.csv " + e);
        }

        int n = lines.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (Double.parseDouble(lines.get(j)[2]) > Double.parseDouble(lines.get(j + 1)[2])) {
                    String[] temp = lines.get(j);
                    lines.set(j, lines.get(j + 1));
                    lines.set(j + 1, temp);
                }
            }
        }

        try {
            toSort.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sorted = new FileOutput("sorted.csv");

        for (String[] s : lines) {
            sorted.fileWrite(s[0] + "," + s[1] + "," + s[2] + "," + s[3] + "," + s[4]);
        }

        sorted.fileClose();

        FileInput sortTest = new FileInput("sorted.csv");
        String[] sortedArr = new String[5];

        try {
            String sortedLine = sortTest.fileReadLine();
            sortedArr = sortedLine.split(",");
        } catch (Exception e) {
            System.out.println("File Write Error: output.csv " + e);
        }

        sortTest.fileClose();

        assertEquals(sortedArr[2], "3");
        assertNotSame(sortedArr[2], "6");
    }

    @Test
    public void testFindPurchases() {
        nums[0] = 0;
        nums[1] = 0;
        String line;
        String lastNumber = "";
        String[] fields;
        boolean done = false;
        FileInput cardPurchases = new FileInput("movie_purchases.csv");
        while (((line = cardPurchases.fileReadLine()) != null) && !(done)) {
            fields = line.split(",");

            if (!lastNumber.equals("100200")) {
                lastNumber = "100200";
                nums[0] = 0;
                nums[1] = 0;
            }
            if (fields[0].compareTo("100200") > 0) {
                done = true;
            }
            else if (fields[0].equals("100200")) {
                nums[0]++;
                nums[1] += Integer.parseInt(fields[2]);
            }
        }
        cardPurchases.fileClose();

        assertEquals(nums[0], 5);
        assertNotSame(nums[0], 35);
    }

    @Test
    public void testRatingCounter() {
        inFile = new FileInput("output.csv");
        String line;
        int ratingCount = 0;
        while ((line = inFile.fileReadLine()) != null) {
            String[] split = line.split(",");
            if (Integer.parseInt(split[4].substring(0, 1)) == 0) {
                ratingCount++;
            }
        }
        inFile.fileClose();

        assertEquals(ratingCount, 2);
        assertNotSame(ratingCount, 1);
    }
}
