/**
 * Created by Daniel on 16/07/15.
 */
public class Board {
    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     * @param blocks
     */
    public Board(int[][] blocks) {

    }

    /**
     * board dimension N
     * @return
     */
    public int dimension() {
        return 0;
    }

    /**
     * number of blocks out of place
     * @return
     */
    public int hamming() {
        return 0;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     * @return
     */
    public int manhattan() {
        return 0;
    }

    /**
     * is this board the goal board?
     * @return
     */
    public boolean isGoal() {
        return false;
    }

    /**
     * a boadr that is obtained by exchanging two adjacent blocks in the same row
     * @return
     */
    public Board twin() {
        return null;
    }

    /**
     * does this board equal y?
     * @param y
     * @return
     */
    public boolean equals(Object y) {
        return false;
    }

    /**
     * all neighboring boards
     * @return
     */
    public Iterable<Board> neighbors() {
        return null;
    }

    /**
     * string representation of this board (in the output format specified below)
     * @return
     */
    public String toString() {
        return "";
    }

    /**
     * unit tests (not graded)
     * @param args
     */
    public static void main(String[] args) {

    }
}
