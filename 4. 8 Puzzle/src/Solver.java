import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {

    private static class SearchNode implements Iterable<Board> {
        private final Board board;
        private final int moves;
        private SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        public SearchNode(SearchNode searchNode) {
            this.board = searchNode.board;
            this.moves = searchNode.moves;
            this.previous = null;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPrevious() {
            return previous;
        }

        public void setPrevious(SearchNode previous) {
            this.previous = previous;
        }

        @Override
        public String toString() {
            return moves + ":" + board.manhattan() + ":" + board;
        }

        @Override
        public Iterator<Board> iterator() {

            final SearchNode[] rear = {this};

            return new Iterator<Board>() {
                @Override
                public boolean hasNext() {
                    return rear[0] != null;
                }

                @Override
                public Board next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    SearchNode current = rear[0];
                    rear[0] = rear[0].getPrevious();
                    return current.getBoard();
                }
            };
        }
    }

    private boolean solvable;
    private final int moves;
    private final SearchNode solution;

    private SearchNode findSolution(Board initial, Board twinBoard) {
        ArrayList<Board> visitedBoardList = new ArrayList<>();
        MinPQ<SearchNode> minPQ = new MinPQ<>(Comparator.comparingInt(o -> o.getBoard().manhattan() + o.getMoves()));

        ArrayList<Board> visitedTwinBoardList = new ArrayList<>();
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>(Comparator.comparingInt(o -> o.getBoard().manhattan() + o.getMoves()));

        SearchNode firstNode = new SearchNode(initial, 0, null);
        minPQ.insert(firstNode);
        SearchNode firstTwinNode = new SearchNode(twinBoard, 0, null);
        twinMinPQ.insert(firstTwinNode);
        while (true) {
            SearchNode searchNode = minPQ.delMin();
            visitedBoardList.add(searchNode.getBoard());

            SearchNode searchTwinNode = twinMinPQ.delMin();
            visitedTwinBoardList.add(searchTwinNode.getBoard());

            if (searchNode.getBoard().isGoal() || searchTwinNode.getBoard().isGoal()) {
                return searchNode;
            }

            for (Board b : searchNode.getBoard().neighbors()) {
                if (!visitedBoardList.contains(b)) {
                    SearchNode s = new SearchNode(b, searchNode.getMoves() + 1, searchNode);
                    minPQ.insert(s);
                }
            }

            for (Board b : searchTwinNode.getBoard().neighbors()) {
                if (!visitedTwinBoardList.contains(b)) {
                    SearchNode s = new SearchNode(b, searchTwinNode.getMoves() + 1, searchTwinNode);
                    twinMinPQ.insert(s);
                }
            }
        }
    }

    private SearchNode reverse(SearchNode node) {
        SearchNode rev = null;
        while (node != null) {
            SearchNode searchNode = new SearchNode(node);
            if (rev != null) {
                searchNode.setPrevious(rev);
            }
            rev = searchNode;
            node = node.getPrevious();
        }
        return rev;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        Board twinBoard = initial.twin();

        this.solvable = false;
        SearchNode reversed = findSolution(initial, twinBoard);
        solution = reverse(reversed);
        this.moves = reversed.getMoves();
//        System.out.println("Moves: " + this.moves);
//        System.out.println(searchNode);
        if (reversed.getBoard().isGoal()) {
            this.solvable = true;
        }

//        System.out.println(Arrays.toString(this.solutionList));

//        System.out.println(searchNode.getMoves());
//        System.out.println(minPQ.size());
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.solvable ? this.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.solvable ? solution : null;
    }

    // test client (see below)
    public static void main(String[] args) {
//        int[][] tiles = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
//        int[][] tiles2 = {{1, 2, 3}, {4, 0, 6}, {7, 8, 5}};
//        Board board = new Board(tiles2);
//
//
//        Solver solver = new Solver(board);
        In in = new In("input1.txt");
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