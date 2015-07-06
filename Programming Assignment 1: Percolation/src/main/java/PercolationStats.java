/**
 * Created by Daniel on 04/07/15.
 */
public class PercolationStats {
    private double[] xs;
    private int T;

    /**
     * perform T independent experiments on an N-by-N grid
     *
     * @param N
     * @param T
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();

        this.xs = new double[T];
        this.T = T;
        for (int t = 0; t < T; t++) {
            Percolation percolation = new Percolation(N);
            int cnt = 0;
            while (!percolation.percolates()) {
                cnt += 1;
                int i, j;
                do {
                    i = StdRandom.uniform(N) + 1;
                    j = StdRandom.uniform(N) + 1;
                } while (percolation.isOpen(i, j));
                percolation.open(i, j);
            }
            this.xs[t] = cnt / (double) (N * N);
        }
    }

    /**
     * sample mean of percolation threshold
     *
     * @return
     */
    public double mean() {
        double ret = 0;
        for (double xi : this.xs) {
            ret += xi;
        }
        ret /= this.T;
        return ret;
    }

    /**
     * sample standard deviation of percolation threshold
     *
     * @return
     */
    public double stddev() {
        double ret = 0;
        double m = this.mean();
        for (double xi : this.xs) {
            ret += (xi - m) * (xi - m);
        }
        ret /= this.T - 1;
        return Math.sqrt(ret);
    }

    /**
     * low  endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceLo() {
        double mu = this.mean();
        double sigma = this.stddev();
        return mu - 1.96 * sigma / Math.sqrt(this.T);
    }

    /**
     * high endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceHi() {
        double mu = this.mean();
        double sigma = this.stddev();
        return mu + 1.96 * sigma / Math.sqrt(this.T);
    }

/*    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("mean = "+this.mean()+"\n");
        sb.append("stddev = "+this.stddev()+"\n");
        sb.append("95% confidence interval = "+this.confidenceLo()+", "+this.confidenceHi());
        return sb.toString();
    }*/

    /**
     * test client (described below)
     *
     * @param args
     */
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.println(stats);
    }
}
