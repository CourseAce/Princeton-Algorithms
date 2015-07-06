/**
 * Created by Daniel on 04/07/15.
 */


public class Percolation {
    private WeightedQuickUnionUF ufDualEnd;
    private WeightedQuickUnionUF ufSingleEnd;
    private boolean[][] mat;
    private int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    private int N;
    private int HEAD;
    private int TAIL;

    /**
     * create N-by-N grid, with all sites blocked
     *
     * @param N
     */
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();

        this.N = N;
        this.ufDualEnd = new WeightedQuickUnionUF(N*N+2);  // the last two is for dummies
        this.ufSingleEnd = new WeightedQuickUnionUF(N*N+1);
        this.mat = new boolean[N][N];
        this.HEAD = N*N;
        this.TAIL = N*N+1;
    }

    private void unionUF(int a, int b) {
        this.ufDualEnd.union(a, b);
        this.ufSingleEnd.union(a, b);
    }

    private int flat(int i, int j) {
        return i*this.N+j;
    }

    /**
     * open site (row i, column j) if it is not open already
     *
     * @param i [1, N]
     * @param j [1, N]
     */
    public void open(int i, int j) {
        try {
            i -= 1;
            j -= 1;
            this.mat[i][j] = true;
            for (int[] dir : this.dirs) {
                int i1 = i + dir[0];
                int j1 = j + dir[1];
                if (i1 >= 0 && i1 < this.N && j1 >= 0 && j1 < this.N && this.mat[i1][j1]) {
                    this.unionUF(this.flat(i, j), this.flat(i1, j1));
                }
            }
            if (i == 0) {
                this.unionUF(this.flat(i, j), this.HEAD);
            }
            if (i == this.N - 1) {
                this.ufDualEnd.union(this.flat(i, j), this.TAIL);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * is site (row i, column j) open?
     *
     * @param i [1, N]
     * @param j [1, N]
     * @return
     */
    public boolean isOpen(int i, int j) {
        try {
            i -= 1;
            j -= 1;
            return this.mat[i][j];
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * is site (row i, column j) full?
     *
     * @param i
     * @param j
     * @return
     */
    public boolean isFull(int i, int j) {
        try {
            i -= 1;
            j -= 1;
            return this.mat[i][j] && this.ufSingleEnd.connected(this.flat(i, j), this.HEAD);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }

    }

    /**
     * does the system percolate?
     *
     * @return
     */
    public boolean percolates() {
        return this.ufDualEnd.connected(this.HEAD, this.TAIL);
    }

    /**
     * test client (optional)
     *
     * @param args
     */
    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        for (int i=1; i<=4; i++) {
            percolation.open(i, 1);
        }
        percolation.open(1, 4);
        percolation.open(2, 4);
        percolation.open(4, 4);
        System.out.println(percolation.isFull(4, 4));
    }
}
