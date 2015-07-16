import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 16/07/15.
 */
public class Board {
    // private final int[][] board;  // memory issue
    private final int[] board;
    private final int N;
    private int p0;
    private static final int[][] DIRS = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     *
     * @param blocks
     */
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new NullPointerException();

        if (blocks.length == 0 || blocks.length != blocks[0].length)
            throw new UnsupportedOperationException();

        this.N = blocks.length;
        this.board = new int[this.N*this.N];

        for (int i=0; i < this.N; i++)
            for (int j=0; j < this.N; j++) {
                this.board[this.project1d(i, j)] = blocks[i][j];
                if (blocks[i][j] == 0)
                    this.p0 = this.project1d(i, j);
            }
    }

    /**
     * board dimension N
     * @return
     */
    public int dimension() {
        return this.N;
    }

    /**
     * number of blocks out of place
     * Don't count 0 otherwise not admissible
     * @return
     */
    public int hamming() {
        int cnt = 0;
        for (int i=0; i < this.N*this.N; i++)
            if (this.board[i] != 0 && this.board[i]-1 != i)
                cnt += 1;

        return cnt;
    }

    private int project1d(int i, int j) {
        return i*this.N+j;
    }

    private int[] reconstruct2d(int idx) {
        int i = idx/this.N;
        int j = idx%this.N;
        return new int[] {i, j};
    }

    /**
     * sum of Manhattan distances between blocks and goal
     * Don't count 0 otherwise not admissible
     * @return
     */
    public int manhattan() {
        int cnt = 0;
        for (int i=0; i < this.N*this.N; i++)
            if (this.board[i] != 0) {
                int[] cur = this.reconstruct2d(i);
                int[] goal = this.reconstruct2d(this.board[i]-1);
                cnt += Math.abs(cur[0]-goal[0]);
                cnt += Math.abs(cur[1]-goal[1]);
            }

        return cnt;
    }

    /**
     * is this board the goal board?
     * Goal state is that they are in order.
     * @return
     */
    public boolean isGoal() {
        for (int i=0; i < this.N*this.N; i++)
            if (this.board[i] != 0 && this.board[i]-1 != i)
                return false;

        return true;
    }

    /**
     * a boadr that is obtained by exchanging two adjacent blocks in the same row
     * @return
     */
    public Board twin() {
        for (int i=0; i < this.N; i++)
            for (int j=1; j < this.N; j++)
                if (this.board[this.project1d(i, j)] != 0 && this.board[this.project1d(i, j-1)] !=0) {
                    int[][] twin = this.restoreBoard();
                    int t = twin[i][j]; twin[i][j] = twin[i][j-1]; twin[i][j-1] = t;
                    return new Board(twin);
                }

        return null;
    }

    /**
     * does this board equal y?
     * @param y
     * @return
     */
    public boolean equals(Object y) {
        if (this == y)
            return true;

        if (y instanceof Board) {
            Board o = (Board) y;
            if (this.dimension() == o.dimension()) {
                for (int i=0; i < this.N*this.N; i++)
                    if (this.board[i] != o.board[i])
                        return false;

                return true;
            }
        }
        return false;
    }

    /**
     * all neighboring boards
     * @return
     */
    public Iterable<Board> neighbors() {
        List<Board> ret = new ArrayList<>();
        int[] p = this.reconstruct2d(this.p0);
        int i = p[0];
        int j = p[1];
        for (int[] dir: DIRS) {
            int i1 = i+dir[0];
            int j1 = j+dir[1];
            if (0 <= i1 && i1 < this.N && 0 <= j1 && j1 < this.N) {
                int[][] board1 = this.restoreBoard();
                int t = board1[i1][j1]; board1[i1][j1] = board1[i][j]; board1[i][j] = t;
                ret.add(new Board(board1));
            }
        }
        return ret;
    }

    /**
     * string representation of this board (in the output format specified below)
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.dimension());
        sb.append("\n");
        for (int i=0; i < this.N; i++) {
            for (int j=0; j < this.N; j++) {
                sb.append(this.board[this.project1d(i, j)]);
                if (j != this.N-1) sb.append(" ");
            }
            if (i != this.N-1) sb.append("\n");
        }
        return sb.toString();
    }

    private int[][] restoreBoard() {
        int[][] ret = new int[this.N][this.N];
        for (int i=0; i < this.N; i++)
            for (int j=0; j < this.N; j++)
                ret[i][j] = this.board[this.project1d(i, j)];

        return ret;
    }

    /**
     * unit tests (not graded)
     * @param args
     */
    public static void main(String[] args) {

    }
}
