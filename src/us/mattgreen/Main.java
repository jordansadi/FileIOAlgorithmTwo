package us.mattgreen;

import java.util.Scanner;

public class Main {

    private final static FileInput cardAccts = new FileInput("movie_cards.csv");
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
        }
    }
// fix the while loop readLine issues using control break logic
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
}