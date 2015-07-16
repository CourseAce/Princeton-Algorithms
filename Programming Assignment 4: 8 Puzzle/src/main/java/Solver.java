/**
 * Created by Daniel on 16/07/15.
 */
public class Solver {
    private BoardPi finalState;

    private class BoardPi implements Comparable<BoardPi> {
        private Board board;
        private BoardPi pi;
        private int curCost;

        private BoardPi(Board board, BoardPi pi, int curCost) {
            if (board == null)
                throw new NullPointerException();

            this.board = board;
            this.pi = pi;
            this.curCost = curCost;
        }

        @Override
        public int compareTo(BoardPi o) {
            return (this.curCost+this.board.hamming()) - (o.curCost+o.board.hamming());
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial
     */
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException();

        // solve the puzzle
        MinPQ<BoardPi> pq = new MinPQ<>();
        MinPQ<BoardPi> pqTwin = new MinPQ<>();
        pq.insert(new BoardPi(initial, null, 0));
        pqTwin.insert(new BoardPi(initial.twin(), null, 0));
        while (!pq.isEmpty() && !pqTwin.isEmpty()) {
            BoardPi cur = pq.delMin();
            BoardPi curTwin = pqTwin.delMin();
            if (cur.board.isGoal()) {
                finalState = cur;
                return;
            }
            if (curTwin.board.isGoal()) {
                return;
            }
            for (Board nei: cur.board.neighbors()) {
                pq.insert(new BoardPi(nei, cur, cur.curCost+1));
            }
            for (Board nei: curTwin.board.neighbors()) {
                pqTwin.insert(new BoardPi(nei, curTwin, curTwin.curCost+1));
            }
        }
    }

    /**
     * is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
        return this.finalState != null;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves() {
        if (this.finalState == null)
            return -1;

        return this.finalState.curCost;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     * @return
     */
    public Iterable<Board> solution() {
        if (this.finalState == null)
            return null;

        Stack<Board> ret = new Stack<>();
        for (BoardPi cur=finalState; cur != null; cur = cur.pi) {
            ret.push(cur.board);
        }

        return ret;
    }

    /**
     * solve a slider puzzle (given below)
     * @param args
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

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
