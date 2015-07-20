import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Daniel on 20/07/15.
 */
public class PointSET {
    private Set<Point2D> set;
    /**
     * construct an empty set of points
     */
    public PointSET() {
        this.set = new TreeSet<>();
    }

    /**
     * is the set empty?
     * @return
     */
    public boolean isEmpty() {
        return this.set.isEmpty();
    }

    /**
     * number of points in the set
     * @return
     */
    public int size() {
        return this.set.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        this.set.add(p);

    }

    /**
     * does the set contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return this.set.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p: this.set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    /**
     * all points that are inside the rectangle
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> ret = new ArrayList<>();
        for (Point2D p: this.set)
            if (rect.contains(p))
                ret.add(p);

        return ret;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        Point2D ret = null;
        for (Point2D c: this.set)
            if (ret == null || c.distanceSquaredTo(p) < ret.distanceSquaredTo(p))
                ret = c;

        return ret;
    }

    /**
     * unit testing of the methods (optional)
     * @param args
     */
    public static void main(String[] args) {

    }
}