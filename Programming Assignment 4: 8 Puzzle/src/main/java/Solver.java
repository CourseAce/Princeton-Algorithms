import java.io.File;
import java.util.*;

/**
 * Created by Daniel on 16/07/15.
 */
public class Solver {
    private SearchNode finalNode;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode pi;
        private boolean isTwin;
        private int cost;

        private SearchNode(Board board, SearchNode pi, boolean isTwin, int cost) {
            if (board == null)
                throw new NullPointerException();

            this.board = board;
            this.pi = pi;
            this.isTwin = isTwin;
            this.cost = cost;
        }

        @Override
        public int compareTo(SearchNode o) {
            return (this.cost+this.board.manhattan()) - (o.cost+o.board.manhattan());
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
        // rather than create two queues, put the twin in the same queue to save memory from time.
        MinPQ<SearchNode> pq = new MinPQ<>();
        Set<Board> visited = new HashSet<>();
        pq.insert(new SearchNode(initial, null, false, 0));
        visited.add(initial);
        pq.insert(new SearchNode(initial.twin(), null, true, 0));
        visited.add(initial.twin());

        while (!pq.isEmpty()) {
            SearchNode cur = pq.delMin();
            if (cur.board.isGoal()) {
                finalNode = cur;
                return;
            }
            for (Board nei: cur.board.neighbors()) {
                if (!visited.contains(nei)) {
                    visited.add(nei);
                    pq.insert(new SearchNode(nei, cur, cur.isTwin, cur.cost+1));
                }
            }
        }
    }

    /**
     * is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
        return this.finalNode != null && !this.finalNode.isTwin;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves() {
        if (!this.isSolvable())
            return -1;

        return this.finalNode.cost;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     * @return
     */
    public Iterable<Board> solution() {
        if (this.finalNode == null)
            return null;

        Stack<Board> ret = new Stack<>();
        for (SearchNode cur= finalNode; cur != null; cur = cur.pi) {
            ret.push(cur.board);
        }

        return ret;
    }

    /**
     * solve a slider puzzle (given below)
     * @param args
     */
    public static void main(String[] args){
        try {
            // create initial board from file
            File f;
            if (args.length > 0)
                f = new File(args[0]);
            else {
                String path = "8puzzle/puzzle30.txt";
                // path = "8puzzle/puzzle4x4-unsolvable.txt";
                f = new File(Solver.class.getResource(path).toURI());
            }


            In in = new In(f);
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
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
