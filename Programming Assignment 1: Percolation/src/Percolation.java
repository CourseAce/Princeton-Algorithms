/**
 * Created by Daniel on 04/07/15.
 */


public class Percolation {
    WeightedQuickUnionUF uf;
    boolean[][] mat;
    int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    int N;
    int HEAD;
    int TAIL;

    /**
     * create N-by-N grid, with all sites blocked
     * @param N
     */
    public Percolation(int N) {
        if(N<=0)
            throw new IllegalArgumentException();

        this.N = N;
        this.uf = new WeightedQuickUnionUF(N*N+2);  // the last two is for dummies
        this.mat = new boolean[N][N];
        this.HEAD = N*N;
        this.TAIL = N*N+1;
    }

    private int flat(int i, int j) {
        return i*this.N + j;
    }

    /**
     * open site (row i, column j) if it is not open already
     * @param i [1, N]
     * @param j [1, N]
     */
    public void open(int i, int j) {
        try {
            i = i-1;
            j = j-1;
            this.mat[i][j] = true;
            for(int[] dir: this.dirs) {
                int n_i = i + dir[0];
                int n_j = j + dir[1];
                if(n_i >= 0 && n_i < this.N && n_j >=0 && n_j < this.N && this.mat[n_i][n_j]) {
                    this.uf.union(this.flat(i, j), this.flat(n_i, n_j));
                }
            }
            if (i == 0) {
                this.uf.union(this.flat(i, j), this.HEAD);
            } else if (i == N-1) {
                this.uf.union(this.flat(i, j), this.TAIL);
            }
        }
        catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * is site (row i, column j) open?
     * @param i [1, N]
     * @param j [1, N]
     * @return
     */
    public boolean isOpen(int i, int j) {
        try {
            return this.mat[i-1][j-1];
        }
        catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * is site (row i, column j) full?
     * @param i
     * @param j
     * @return
     */
    public boolean isFull(int i, int j) {
        return this.uf.connected(this.flat(i, j), this.HEAD);
    }

    /**
     * does the system percolate?
     * @return
     */
    public boolean percolates() {
        return this.uf.connected(this.HEAD, this.TAIL);
    }

    /**
     * test client (optional)
     * @param args
     */
    public static void main(String[] args) {

    }
}
