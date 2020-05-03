import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double m, s;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Illegal n or trials");
        this.trials = trials;
        double[] thresHolds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int atN = 0;
            while (!percolation.percolates()) {
                int r = StdRandom.uniform(1, n + 1), c = StdRandom.uniform(1, n + 1);
                while (percolation.isOpen(r, c)) {
                    r = StdRandom.uniform(1, n + 1);
                    c = StdRandom.uniform(1, n + 1);
                }
                percolation.open(r, c);
                atN += 1;
            }
            thresHolds[i] = (atN * 1.0) / (n * n);
        }
        m = StdStats.mean(thresHolds);
        s = StdStats.stddev(thresHolds);
    }

    // sample mean of percolation threshold
    public double mean() {
        return m;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return s;
        // StdStats.stddev(thresHolds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return m - (CONFIDENCE_95 * s) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return m + (CONFIDENCE_95 * s) / Math.sqrt(trials);
    }

    // test client
    public static void main(String[] args) {
        int n, trials;
        if (args.length > 1) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        else {
            n = 100;
            trials = 200;
        }
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println(stats.mean());
        StdOut.println(stats.stddev());
        StdOut.println(stats.confidenceLo());
        StdOut.println(stats.confidenceHi());
    }

}