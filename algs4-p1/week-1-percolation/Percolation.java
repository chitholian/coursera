import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private int nOpen = 0;
    private boolean[][] isSiteOpen;
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");
        this.n = n;
        isSiteOpen = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2); // for 2 extra virtual sites.
    }

    private int getN(int r, int c) {
        return r * n + c;
    }

    private void checkRange(int r, int c) {
        if (r > n || c > n || r < 1 || c < 1)
            throw new IllegalArgumentException("Illegal row-col");
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkRange(row, col);
        row -= 1;
        col -= 1;
        if (!isSiteOpen[row][col]) {
            isSiteOpen[row][col] = true;
            nOpen += 1;
            if (row > 0 && isSiteOpen[row - 1][col])
                uf.union(getN(row - 1, col), getN(row, col));
            if (col > 0 && isSiteOpen[row][col - 1])
                uf.union(getN(row, col - 1), getN(row, col));
            if (row < n - 1 && isSiteOpen[row + 1][col])
                uf.union(getN(row + 1, col), getN(row, col));
            if (col < n - 1 && isSiteOpen[row][col + 1])
                uf.union(getN(row, col + 1), getN(row, col));

            if (row == 0) uf.union(col, n * n); // connect to top virtual site.
            if (row == n - 1)
                uf.union(getN(row, col), n * n + 1); // connect to bottom virtual site.
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkRange(row, col);
        return isSiteOpen[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkRange(row, col);
        return uf.find(getN(row - 1, col - 1)) == uf.find(n * n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(n * n) == uf.find(n * n + 1);
    }

    // test client (optional)
    // public static void main(String[] args)
}