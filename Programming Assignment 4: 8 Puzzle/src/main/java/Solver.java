import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Daniel on 16/07/15.
 */
public class Solver {
    private SearchNode finalNode;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode pi;
        private int cost;

        private SearchNode(Board board, SearchNode pi, int cost) {
            if (board == null)
                throw new NullPointerException();

            this.board = board;
            this.pi = pi;
            this.cost = cost;
        }

        @Override
        public int compareTo(SearchNode o) {
            return (this.cost+this.board.hamming()) - (o.cost+o.board.hamming());
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
        List<MinPQ<SearchNode>> pqs = new ArrayList<>();
        pqs.add(new MinPQ<SearchNode>());
        pqs.add(new MinPQ<SearchNode>());

        List<Set<Board>> visits = new ArrayList<>();
        visits.add(new HashSet<Board>());
        visits.add(new HashSet<Board>());

        Board[] initials = new Board[] {initial, initial.twin()};
        for (int i=0; i < initials.length; i++) {
            pqs.get(i).insert(new SearchNode(initials[i], null, 0));
            visits.get(i).add(initials[i]);
        }
        while (true) {
            for (MinPQ pq: pqs)
                if (pq.isEmpty())
                    break;

            for (int i=0; i < initials.length; i++) {
                SearchNode cur = pqs.get(i).delMin();
                if (cur.board.isGoal()) {
                    if (i == 0)
                        finalNode = cur;
                    return;
                }
                for (Board nei: cur.board.neighbors()) {
                    if (!visits.get(i).contains(nei)) {
                        visits.get(i).add(nei);
                        pqs.get(i).insert(new SearchNode(nei, cur, cur.cost+1));
                    }
                }
            }
        }
    }

    /**
     * is the initial board solvable?
     * @return
     */
    public boolean isSolvable() {
        return this.finalNode != null;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves() {
        if (this.finalNode == null)
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
            else
                f = new File(Solver.class.getResource("8puzzle/puzzle4x4-unsolvable.txt").toURI());

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
