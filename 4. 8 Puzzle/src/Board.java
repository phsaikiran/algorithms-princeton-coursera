import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {

    private final int[][] tiles;
    private final int hamming;
    private final int manhattan;
    private int blankPosX, blankPosY;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }
        int length = tiles.length;
        this.tiles = new int[length][length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, length);
        }

        int pos = 1;
        int hamming1 = 0;
        int manhattan1 = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (this.tiles[i][j] == 0) {
                    this.blankPosX = i;
                    this.blankPosY = j;
                } else {
                    if (this.tiles[i][j] != 0 && this.tiles[i][j] != pos) {
                        hamming1 += 1;
                    }

                    int requiredX = this.tiles[i][j] % length == 0 ? this.tiles[i][j] / length - 1 : this.tiles[i][j] / length;
                    int requiredY = this.tiles[i][j] % length == 0 ? length - 1 : this.tiles[i][j] % length - 1;
                    manhattan1 += (Math.abs(requiredX - i) + Math.abs(requiredY - j));
                }

                pos += 1;
            }
        }
        this.manhattan = manhattan1;
        this.hamming = hamming1;
    }

    // string representation of this board
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(tiles.length).append("\n");
        for (int[] row : tiles) {
            for (int tile : row) {
                output.append(tile).append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return this.hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.manhattan == 0 && this.hamming == 0 && blankPosX == tiles.length - 1 && blankPosY == tiles.length - 1;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.tiles.length != that.tiles.length) {
            return false;
        }

        int length = tiles.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private class BoardIterable implements Iterable<Board> {

        private final ArrayList<Integer> possible;

        public BoardIterable(ArrayList<Integer> possible) {
            this.possible = possible;
        }

        private Board nextImpl() {
            int size = possible.size();
            int operation = possible.get(size - 1);
            possible.remove(size - 1);

            if (operation == 1) {
                tiles[blankPosX][blankPosY] = tiles[blankPosX - 1][blankPosY];
                tiles[blankPosX - 1][blankPosY] = 0;
                Board neighbourBoard = new Board(tiles);
                tiles[blankPosX - 1][blankPosY] = tiles[blankPosX][blankPosY];
                tiles[blankPosX][blankPosY] = 0;
                return neighbourBoard;
            } else if (operation == 2) {
                tiles[blankPosX][blankPosY] = tiles[blankPosX][blankPosY + 1];
                tiles[blankPosX][blankPosY + 1] = 0;
                Board neighbourBoard = new Board(tiles);
                tiles[blankPosX][blankPosY + 1] = tiles[blankPosX][blankPosY];
                tiles[blankPosX][blankPosY] = 0;
                return neighbourBoard;
            } else if (operation == 3) {
                tiles[blankPosX][blankPosY] = tiles[blankPosX + 1][blankPosY];
                tiles[blankPosX + 1][blankPosY] = 0;
                Board neighbourBoard = new Board(tiles);
                tiles[blankPosX + 1][blankPosY] = tiles[blankPosX][blankPosY];
                tiles[blankPosX][blankPosY] = 0;
                return neighbourBoard;
            } else {
                tiles[blankPosX][blankPosY] = tiles[blankPosX][blankPosY - 1];
                tiles[blankPosX][blankPosY - 1] = 0;
                Board neighbourBoard = new Board(tiles);
                tiles[blankPosX][blankPosY - 1] = tiles[blankPosX][blankPosY];
                tiles[blankPosX][blankPosY] = 0;
                return neighbourBoard;
            }
        }

        @Override
        public Iterator<Board> iterator() {
            return new Iterator<Board>() {
                @Override
                public boolean hasNext() {
                    return !possible.isEmpty();
                }

                @Override
                public Board next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return nextImpl();
                }
            };
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Integer> possible = new ArrayList<>();
        if (this.blankPosX - 1 >= 0) {
            possible.add(1);
        }
        if (this.blankPosY + 1 < tiles.length) {
            possible.add(2);
        }
        if (this.blankPosX + 1 < tiles.length) {
            possible.add(3);
        }
        if (this.blankPosY - 1 >= 0) {
            possible.add(4);
        }

        return new BoardIterable(possible);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        boolean filled = false, breakLoop = false;
        int length = tiles.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (tiles[i][j] != 0) {
                    if (!filled) {
                        x1 = i;
                        y1 = j;
                        filled = true;
                    } else {
                        x2 = i;
                        y2 = j;
                        breakLoop = true;
                        break;
                    }
                }
            }
            if (breakLoop) {
                break;
            }
        }

        int t = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = t;

        Board twinBoard = new Board(tiles);

        t = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = t;

        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {{3, 0, 7, 14}, {12, 11, 5, 2}, {15, 4, 10, 6}, {1, 9, 8, 13}};
        int[][] tiles2 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board board = new Board(tiles);
        Board board2 = new Board(tiles2);
        System.out.println(board);
        System.out.println(board.isGoal());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());

        for (Board b : board.neighbors()) {
            System.out.println(b);
            System.out.println(b.isGoal());
        }

        System.out.println(board.equals(board2));

        System.out.println(board);
        System.out.println(board.twin());
        System.out.println(board.twin());
        System.out.println(board.twin());
    }

}