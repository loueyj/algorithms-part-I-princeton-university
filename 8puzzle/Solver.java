/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<SearchNode> pq;
    private int totalMoves;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();

        SearchNode initialNode = new SearchNode(initial, 0, null);
        SearchNode initialTwinNode = new SearchNode(initial.twin(), 0, null);

        pq.insert(initialNode);
        twinPQ.insert(initialTwinNode);

        while (!pq.min().board.isGoal() && !twinPQ.min().board.isGoal()) {
            SearchNode curr = pq.delMin();
            SearchNode currTwin = twinPQ.delMin();

            for (Board neighbor : curr.board.neighbors()) {
                if (curr.prev != null && curr.prev.board.equals(neighbor)) {
                    continue;
                }
                pq.insert(new SearchNode(neighbor, curr.moves + 1, curr));
            }

            for (Board neighbor : currTwin.board.neighbors()) {
                if (currTwin.prev != null && currTwin.prev.board.equals(neighbor)) {
                    continue;
                }
                twinPQ.insert(new SearchNode(neighbor, currTwin.moves + 1, currTwin));
            }
        }
        if (pq.min().board.isGoal()) {
            totalMoves = pq.min().moves;
            solvable = true;
        }
        if (twinPQ.min().board.isGoal()) {
            totalMoves = -1;
            solvable = false;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return totalMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> stack = new Stack<>();
        SearchNode curr = pq.min();
        while (curr != null) {
            stack.push(curr.board);
            curr = curr.prev;
        }
        return stack;
    }

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        int manhattan;
        SearchNode prev;

        private SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.manhattan = board.manhattan();
            this.prev = prev;
        }

        public int compareTo(SearchNode that) {
            int firstManhattan = this.manhattan;
            int secondManhattan = that.manhattan;
            if ((firstManhattan + this.moves) < (secondManhattan + that.moves)) {
                return -1;
            }
            else if ((firstManhattan + this.moves) > (secondManhattan + that.moves)) {
                return 1;
            }
            return 0;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}


