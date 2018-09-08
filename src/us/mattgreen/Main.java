package us.mattgreen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private final static FileInput cardAccts = new FileInput("movie_cards.csv");
    private final static FileOutput outFile = new FileOutput("output.csv");
    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        String line;
        String[] fields;
        int[] nums = new int[2];
        double[] dubs = new double[3];
        System.out.format("%8s  %-18s %6s %6s %9s\n","Account","Name", "Movies", "Points", "Avg Rating");
        while ((line = cardAccts.fileReadLine()) != null) {
            fields = line.split(",");
            findPurchases(fields[0], nums);
            getAvgRating(fields[0], dubs);
            System.out.format("00%6s  %-18s  %2d   %4d %9.2f\n",fields[0],fields[1], nums[0], nums[1], dubs[2]);
            outFile.fileWrite(fields[0] + "," + fields[1] + "," + nums[0] + "," + nums[1] + "," + dubs[2]);
        }
        outFile.fileClose();
        sortFile("output.csv");

        // average ratings were counted rather than individual ratings because individual ratings were never printed
        // in the previous exercise, and the instructions specified that the output file should include all fields
        // that were printed out in the previous exercise
        System.out.println("\nCount of Average Ratings");
        System.out.println("0   " + ratingCounter(0));
        System.out.println("1   " + ratingCounter(1));
        System.out.println("2   " + ratingCounter(2));
        System.out.println("3   " + ratingCounter(3));
        System.out.println("4   " + ratingCounter(4));
        System.out.println("5   " + ratingCounter(5));
    }

    public static void getAvgRating(String acct, double[] dubs) {
        dubs[0] = 0;
        dubs[1] = 0;
        dubs[2] = 0;
        String line;
        String[] fields;
        boolean done = false;
        FileInput movieRatings = new FileInput("movie_rating.csv");
        while (((line = movieRatings.fileReadLine()) != null) && !(done)) {
            fields = line.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                dubs[0]++;
                dubs[1] += Double.parseDouble(fields[1]);
                dubs[2] = dubs[1] / dubs[0];
            }

        }
    }
    public static void sortFile(String fileName) {
        BufferedReader fileIn = null;
        try {
            fileIn = new BufferedReader(new FileReader(fileName));
        } catch(FileNotFoundException e) {
            System.out.println("File Open Error: " + fileName + " "  + e);
        }

        String line;
        String[] lineIn;
        ArrayList<String[]> lines = new ArrayList<>();

        try {
            while ((line = fileIn.readLine()) != null) {
                lineIn = line.split(",");
                lines.add(lineIn);
            }
        } catch (Exception e) {
            System.out.println("File Write Error: " + fileName + " " + e);
        }

        int n = lines.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (Double.parseDouble(lines.get(j)[4]) > Double.parseDouble(lines.get(j + 1)[4])) {
                    // swap temp and arr[i]
                    String[] temp = lines.get(j);
                    lines.set(j, lines.get(j + 1));
                    lines.set(j + 1, temp);
                }
            }
        }

        try {
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutput sortedFile = new FileOutput("output.csv");

        for (String[] s : lines) {
            sortedFile.fileWrite(s[0] + "," + s[1] + "," + s[2] + "," + s[3] + "," + s[4]);
        }

        sortedFile.fileClose();
    }

    public static void findPurchases(String acct, int[] nums) {
        nums[0] = 0;
        nums[1] = 0;
        String line;
        String lastNumber = "";
        String[] fields;
        boolean done = false;
        FileInput cardPurchases = new FileInput("movie_purchases.csv");
        while (((line = cardPurchases.fileReadLine()) != null) && !(done)) {
            fields = line.split(",");

            if (!lastNumber.equals(acct)) {
                lastNumber = acct;
                nums[0] = 0;
                nums[1] = 0;
            }
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                nums[0]++;
                nums[1] += Integer.parseInt(fields[2]);
            }
        }
    }

    public static int ratingCounter(int i) {
        FileInput sortedIn = new FileInput("output.csv");
        String line;
        int ratingCount = 0;
        while ((line = sortedIn.fileReadLine()) != null) {
            String[] split = line.split(",");
            if (Integer.parseInt(split[4].substring(0, 1)) == i) {
                ratingCount++;
            }
        }
        sortedIn.fileClose();
        return ratingCount;
    }
}