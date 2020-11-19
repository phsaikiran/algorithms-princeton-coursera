import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final boolean[] mat;
    private int openSites;
    private final WeightedQuickUnionUF weightedQuickUnionUF, weightedQuickUnionUF2;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0.");
        }
        this.n = n;
        this.mat = new boolean[n * n];
        this.openSites = 0;

        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        weightedQuickUnionUF2 = new WeightedQuickUnionUF(n * n + 2);

        for (int i = 0; i < n * n; i++) {
            mat[i] = false;
        }
    }

    private int rowColToIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("row <= 0 || col <= 0 || row > n || col > n");
        }

        if (mat[rowColToIndex(row, col) - 1]) {
            return;
        }
        mat[rowColToIndex(row, col) - 1] = true;
        openSites += 1;

        if (row - 2 >= 0 && mat[rowColToIndex(row - 1, col) - 1]) {
            weightedQuickUnionUF.union(rowColToIndex(row, col), rowColToIndex(row - 1, col));
            weightedQuickUnionUF2.union(rowColToIndex(row, col), rowColToIndex(row - 1, col));
        }
        if (row < n && mat[rowColToIndex(row + 1, col) - 1]) {
            weightedQuickUnionUF.union(rowColToIndex(row, col), rowColToIndex(row + 1, col));
            weightedQuickUnionUF2.union(rowColToIndex(row, col), rowColToIndex(row + 1, col));
        }
        if (col - 2 >= 0 && mat[rowColToIndex(row, col - 1) - 1]) {
            weightedQuickUnionUF.union(rowColToIndex(row, col), rowColToIndex(row, col - 1));
            weightedQuickUnionUF2.union(rowColToIndex(row, col), rowColToIndex(row, col - 1));
        }
        if (col < n && mat[rowColToIndex(row, col + 1) - 1]) {
            weightedQuickUnionUF.union(rowColToIndex(row, col), rowColToIndex(row, col + 1));
            weightedQuickUnionUF2.union(rowColToIndex(row, col), rowColToIndex(row, col + 1));
        }

        if (row == 1) {
            weightedQuickUnionUF.union(rowColToIndex(row, col), 0);
            weightedQuickUnionUF2.union(rowColToIndex(row, col), 0);
        }
        if (row == n) {
            weightedQuickUnionUF.union(rowColToIndex(row, col), n * n + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("row <= 0 || col <= 0 || row > n || col > n");
        }
        return mat[rowColToIndex(row, col) - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("row <= 0 || col <= 0 || row > n || col > n");
        }
        return weightedQuickUnionUF2.find(0) == weightedQuickUnionUF2.find(rowColToIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(0) == weightedQuickUnionUF.find(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 3);
        System.out.println(percolation.isFull(2, 3));
        System.out.println(percolation.percolates());
        System.out.println(percolation.numberOfOpenSites());
        percolation.open(2, 3);
        System.out.println(percolation.isFull(2, 3));
        System.out.println(percolation.percolates());
        percolation.open(4, 3);
        percolation.open(5, 3);
        System.out.println(percolation.percolates());
        percolation.open(3, 3);
        System.out.println(percolation.percolates());
        System.out.println(percolation.numberOfOpenSites());

        System.out.println("----------------------------");

        Percolation percolation2 = new Percolation(1);
        System.out.println(percolation2.percolates());
        percolation2.open(1, 1);
        System.out.println(percolation2.percolates());
        Percolation percolation3 = new Percolation(2);
        System.out.println(percolation3.percolates());
        percolation3.open(1, 1);
        percolation3.open(2, 2);
        System.out.println(percolation3.percolates());
        percolation3.open(1, 2);
        System.out.println(percolation3.percolates());
    }
}