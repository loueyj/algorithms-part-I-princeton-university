/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] grid;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        grid = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                grid[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder gridBuilder = new StringBuilder();
        gridBuilder.append(grid.length);
        gridBuilder.append("\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                gridBuilder.append(grid[i][j]);
                gridBuilder.append(" ");
            }
            gridBuilder.append("\n");
        }
        return gridBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return grid.length;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        int num = 1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == grid.length - 1 && j == grid[0].length - 1) {
                    if (grid[i][j] != 0) {
                        count++;
                    }
                }
                else if (grid[i][j] != num && grid[i][j] != 0) {
                    count++;
                }
                num++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() { // fix
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0) {
                    int num = grid[i][j];
                    int row = 0;
                    int col = 0;
                    if (num % grid.length == 0) {
                        row = (num / grid.length) - 1;
                        col = grid.length - 1;
                    }
                    else {
                        row = (num - (num % grid.length)) / grid.length;
                        col = (num % grid.length) - 1;
                    }
                    count += (Math.abs(i - row) + Math.abs(j - col));
                }
                /**
                 else {
                 int col = grid.length - 1;
                 int row = grid.length - 1;
                 count += Math.abs(i - row) + Math.abs(j - col);
                 }
                 **/
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (hamming() == 0) {
            return true;
        }
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) {
            return false;
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != that.grid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> list = new ArrayList<Board>();
        int zeroRow = 0;
        int zeroCol = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break;
                }
            }
        }
        if (zeroRow > 0) {
            list.add(copySwap(zeroRow, zeroCol, zeroRow - 1, zeroCol));
        }
        if (zeroRow < grid.length - 1) {
            list.add(copySwap(zeroRow, zeroCol, zeroRow + 1, zeroCol));
        }
        if (zeroCol > 0) {
            list.add(copySwap(zeroRow, zeroCol, zeroRow, zeroCol - 1));
        }
        if (zeroCol < grid[0].length - 1) {
            list.add(copySwap(zeroRow, zeroCol, zeroRow, zeroCol + 1));
        }
        return list;
    }

    private Board copySwap(int firstRow, int firstCol, int secondRow, int secondCol) {
        int[][] copy = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                copy[i][j] = grid[i][j];
            }
        }
        copy[firstRow][firstCol] = grid[secondRow][secondCol];
        copy[secondRow][secondCol] = grid[firstRow][firstCol];

        return new Board(copy);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int swapRow = 0;
        int swapCol = 0;
        int twinRow = grid.length - 1;
        int twinCol = grid[0].length - 1;

        if (grid[swapRow][swapCol] == 0) {
            swapCol++;
        }
        if (grid[twinRow][twinCol] == 0) {
            twinCol--;
        }

        return copySwap(swapRow, swapCol, twinRow, twinCol);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
