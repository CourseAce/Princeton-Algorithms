/*************************************************************************
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(Point o1, Point o2) {
            double slope1 = Point.this.slopeTo(o1);
            double slope2 = Point.this.slopeTo(o2);
            // return Double.compare(slope1+0.0, slope2+0.0);  // avoid -0.0
            if (slope1 < slope2) return -1;
            if (slope1 == slope2) return 0;
            return 1;

        }
    };

    private final int x;
    private final int y;

    private int normalizeCompareTo(double val) {
        double delta = 1e-6;
        if (Math.abs(val) < delta)
            return 0;
        else if (val < 0)
            return -1;
        else
            return 1;
    }

    /**
     * create the point (x, y)
     * @param x
     * @param y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * plot this point to standard drawing
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * draw line between this point and that point to standard drawing
      * @param that
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * slope between this point and that point
     * @param that
     * @return
     */
    public double slopeTo(Point that) {
        int deltaY = this.y - that.y;
        int deltaX = this.x - that.x;
        if (deltaX == 0)
            if (deltaY == 0) return Double.NEGATIVE_INFINITY;  // rather than using Integer.MIN_VALUE;
            else return Double.POSITIVE_INFINITY;

        double ret = deltaY/(double) (deltaX);
        return ret + 0.0;
    }

    /**
     * is this point lexicographically smaller than that one?
     * comparing y-coordinates and breaking ties by x-coordinates
     * @param that
     * @return
     */
    public int compareTo(Point that) {
        int deltaY = this.y - that.y;
        if (deltaY < 0)
            return -1;
        else if (deltaY > 0)
            return 1;
        else
            return Integer.compare(this.x, that.x);
    }

    /**
     * return string representation of this point
     * @return
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * unit test
     * @param args
     */
    public static void main(String[] args) {
        Point p = new Point(0, 9);
        Point q = new Point(0, 3);
        Point r = new Point(0, 3);
        System.out.println(p.SLOPE_ORDER.compare(q, r));
        System.out.println(Double.compare(0.0, -0.0));
        System.out.println(0.0 == -0.0);
        System.out.println(Double.POSITIVE_INFINITY+0.0);
    }
}
