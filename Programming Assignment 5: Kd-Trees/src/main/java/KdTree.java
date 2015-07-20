import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 20/07/15.
 */
public class KdTree {
    private Node root;

    private static class Node {
        private Point2D p;
        private Node left, right;
        private boolean vertical;
        private RectHV rect;
        private int sz;

        public Node(Point2D p, boolean vertical, RectHV rect) {
            this.p = p;
            this.vertical = vertical;
            this.rect = rect;
        }
    }
    /**
     * construct an empty set of points
     */
    public KdTree() {

    }

    /**
     * is the set empty?
     * @return
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * number of points in the set
     * @return
     */
    public int size() {
        if (this.isEmpty())
            return 0;

        return this.root.sz;
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        this.root = this.insert(this.root, p, true, 0, 0, 1, 1);
    }

    private Node insert(Node cur, Point2D p, boolean vertical, double l, double b, double r, double t) {
        if (cur == null)
            cur = new Node(p, vertical, new RectHV(l, b, r, t));

        if (cur.p.equals(p))
            return cur;

        if (vertical) {
            if (p.x() < cur.p.x()) {
                cur.left = this.insert(cur.left, p, !vertical,
                        cur.rect.xmin(), cur.rect.ymin(), cur.p.x(), cur.rect.ymax());
            }
            else
                cur.right = this.insert(cur.right, p, !vertical,
                        cur.p.x(), cur.rect.ymin(), cur.rect.xmax(), cur.rect.ymax());
        }
        else {
            if (p.y() < cur.p.y())
                cur.left = this.insert(cur.left, p, !vertical,
                        cur.rect.xmin(), cur.rect.ymin(), cur.rect.xmax(), cur.p.y());
            else
                cur.right = this.insert(cur.right, p, !vertical,
                        cur.rect.xmin(), cur.p.y(), cur.rect.xmax(), cur.rect.ymax());

        }
        cur.sz = 1+this.sz(cur.left)+this.sz(cur.right);

        return cur;
    }

    private int sz(Node cur) {
        if (cur == null)
            return 0;
        else
            return cur.sz;
    }

    /**
     * does the set contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        Node ret = this.search(p);
        return ret != null;
    }

    private Node search(Point2D p) {
        Node cur = this.root;
        while (true) {
            if (cur == null || cur.p.equals(p))
                return cur;

            if (cur.vertical) {
                if (p.x() < cur.p.x())
                    cur = cur.left;
                else
                    cur = cur.right;
            }
            else {
                if (p.y() < cur.p.y())
                    cur = cur.left;
                else
                    cur = cur.right;
            }
        }
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        this.draw(this.root);
    }

    private void draw(Node cur) {
        if (cur == null)
            return;

        StdDraw.point(cur.p.x(), cur.p.y());
        if (cur.vertical)
            StdDraw.line(cur.p.x(), cur.rect.ymin(), cur.p.x(), cur.rect.ymax());
        else
            StdDraw.line(cur.rect.xmin(), cur.p.y(), cur.rect.xmax(), cur.p.y());

        this.draw(cur.left);
        this.draw(cur.right);
    }

    /**
     * all points that are inside the rectangle
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();

        List<Point2D> ret = new ArrayList<>();
        this.range(this.root, rect, ret);
        return ret;
    }

    private void range(Node cur, RectHV rect, List<Point2D> ret) {
        // post check
        if (cur == null)
            return;

        if (!cur.rect.intersects(rect))
            return;

        if (rect.contains(cur.p))
            ret.add(cur.p);

        this.range(cur.left, rect, ret);
        this.range(cur.right, rect, ret);
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        Point2D[] ret = new Point2D[]{null};
        this.nearest(this.root, p, ret);
        return ret[0];
    }

    private void nearest(Node cur, Point2D p, Point2D[] ret) {
        // post check;
        if (cur == null)
            return;

        if (cur.p.distanceSquaredTo(p) < ret[0].distanceSquaredTo(p))
            ret[0] = cur.p;

        Node[] children = new Node[]{cur.left, cur.right};
        if (cur.vertical && p.x() >= cur.p.x() ||
                !cur.vertical && p.y() >= cur.p.y()) {
            children[0] = cur.right;
            children[1] = cur.left;
        }
        for (Node c: children)
            if (c != null && c.rect.distanceSquaredTo(p) < ret[0].distanceSquaredTo(p))
                this.nearest(c, p, ret);

    }

    /**
     * unit testing of the methods (optional)
     * @param args
     */
    public static void main(String[] args) {

    }
}
