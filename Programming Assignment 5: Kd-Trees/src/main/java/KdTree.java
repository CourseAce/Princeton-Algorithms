/**
 * Created by Daniel on 20/07/15.
 */
public class KdTree {
    /**
     * construct an empty set of points
     */
    public PointSET() {

    }

    /**
     * is the set empty?
     * @return
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * number of points in the set
     * @return
     */
    public int size() {
        return 0;
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {

    }

    /**
     * does the set contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return false;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {

    }

    /**
     * all points that are inside the rectangle
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        return null;
    }

    /**
     * unit testing of the methods (optional)
     * @param args
     */
    public static void main(String[] args) {

    }
}
