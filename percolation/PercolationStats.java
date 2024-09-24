/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double[] arr;
    private int t;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Outside of Range");
        }
        arr = new double[trials];
        t = trials;
        int j = 0;
        int[] closedSites = new int[n * n];
        for (int i = 0; i < n * n; i++) {
            closedSites[i] = i;
        }
        while (trials > 0) {
            Percolation test = new Percolation(n);
            while (!test.percolates()) {
                int index = StdRandom.uniformInt(closedSites.length);
                int site = closedSites[index]; // redundant
                int col = (site % n) + 1;
                int row = ((site - (site % n)) / n) + 1;
                test.open(row, col);
            }
            arr[j] = (double) test.numberOfOpenSites() / (n * n);
            j++;
            trials--;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(arr);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arr);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(arr) - (CONFIDENCE_95 * (StdStats.stddev(arr)) / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(arr) + (CONFIDENCE_95 * (StdStats.stddev(arr)) / Math.sqrt(t));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats trial = new PercolationStats(n, T);
        StdOut.println(String.format("%-24s= %s", "mean", trial.mean()));
        StdOut.println(String.format("%-24s= %s", "stddev", trial.stddev()));
        StdOut.println(String.format("%-24s= %s", "95% confidence interval",
                                     "[" + trial.confidenceLo() + ", " + trial.confidenceHi()
                                             + "]"));
    }

}
