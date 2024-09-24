/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int count;
    private int length;
    private WeightedQuickUnionUF id;
    private int top;
    private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Outside of Range");
        }
        grid = new boolean[n][n];
        id = new WeightedQuickUnionUF((n * n) + 2);
        top = (n * n);
        bottom = (n * n) + 1;
        count = 0;
        length = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > length || col <= 0 || col > length) {
            throw new IllegalArgumentException("Outside of Range");
        }
        if (!grid[row - 1][col - 1]) {
            if (row == 1) {
                id.union(col - 1, top);
                if (grid[row][col - 1]) {
                    int down = ((row) * length) + (col - 1);
                    id.union(down, col - 1);
                }
            }
            else if (row == length) {
                int index = ((row - 1) * length) + (col - 1);
                id.union(index, bottom);
                if (grid[row - 2][col - 1]) {
                    int up = ((row - 2) * length) + (col - 1);
                    id.union(up, index);
                }
            }
            else {
                int index = ((row - 1) * length) + (col - 1);
                int up = ((row - 2) * length) + (col - 1);
                int down = ((row) * length) + (col - 1);
                int right = ((row - 1) * length) + (col);
                int left = ((row - 1) * length) + (col - 2);
                if (grid[row - 2][col - 1]) {
                    id.union(index, up);
                }
                if (grid[row][col - 1]) {
                    id.union(index, down);
                }
                if (col == 1) {
                    if (grid[row - 1][col]) {
                        id.union(index, right);
                    }
                }
                else if (col == length) {
                    if (grid[row - 1][col - 2]) {
                        id.union(index, left);
                    }
                }
                else {
                    if (grid[row - 1][col]) {
                        id.union(index, right);
                    }
                    if (grid[row - 1][col - 2]) {
                        id.union(index, left);
                    }
                }
            }
            grid[row - 1][col - 1] = true;
            count++;
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > length || col <= 0 || col > length) {
            throw new IllegalArgumentException("Outside of Range");
        }
        if (grid[row - 1][col - 1]) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > length || col <= 0 || col > length) {
            throw new IllegalArgumentException("Outside of Range");
        }
        int index = ((row - 1) * length) + (col - 1);
        if (id.find(index) == id.find(top)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        if (id.find(bottom) == id.find(top)) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
