import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Should extends Brute but the OJ won't allow the protected/public/package methods except for the main.
 * Created by Daniel on 14/07/15.
 */
public class Fast {
    private Point[] points;
    public static void main(String[] args) {
        try {
            String path;
            File f;
            Fast c = new Fast();

            if (args.length < 1) {
                path = "collinear/mystery10089.txt";  // debug
                f = new File(c.getClass().getClassLoader().getResource(path).toURI());
            }
            else {
                path = args[0];
                f = new File(path);
            }
            c.read(f);
            c.initDraw();
            c.drawPoints();
            c.run();
            c.showDraw();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read(File f) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(f));
        int n = Integer.parseInt(br.readLine());
        Point[] points = new Point[n];
        for (int i=0; i < n; i++) {
            String[] s;
            do {
                s = br.readLine().trim().split("\\s+");
            } while (s.length != 2);
            points[i] = new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        }
        this.points = points;
    }

    private void drawPoints() {
        for (Point point: this.points)
            point.draw();
    }

    private void initDraw() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
    }

    private void showDraw() {
        // display to screen all at once
        StdDraw.show(0);
        // reset the pen radius
        StdDraw.setPenRadius();
    }

    private void drawLine(Point[] points) {
        Arrays.sort(points);
        points[0].drawTo(points[points.length-1]);
    }

    private String toString(Point[] points) {
        Arrays.sort(points);
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < points.length; i++) {
            sb.append(points[i].toString());
            if (i == points.length - 1)
                break;
            sb.append(" -> ");
        }
        return sb.toString();
    }

    /**
     * Algorithm, only this method is different from Brute.java
     *
     * To prevent printing the same line multiple times:
     *  use the "compareTo" method to compare the y value of the original point you used to compute slope and y value of
     *  the first element after you sort all other points that are on the same line. You print only when the former is
     *  smaller than the latter, in which case your original point is the point that has the smallest y value of all the
     *  points on that specific line.
     */
    private void run() {
        List<Point> points = Arrays.asList(this.points);
        Collections.sort(points);
        for (int i=0; i < points.size(); i++) {
            Point p0 = points.get(i);
            List<Point> cp = new ArrayList<>(points); // clone
            Collections.sort(cp, p0.SLOPE_ORDER);
            for (int s=0, e; s < cp.size(); s=e) {
                for (e=s; e < cp.size() && p0.SLOPE_ORDER.compare(cp.get(s) , cp.get(e)) == 0; e++);
                if (e-s+1>= 4) {
                    List<Point> r = new ArrayList<>(cp.subList(s, e));
                    r.add(p0);
                    Collections.sort(r);
                    if (r.get(0).equals(p0)) {
                        Point[] ret = r.toArray(new Point[e-s+1]);
                        StdOut.println(this.toString(ret));
                        this.drawLine(ret);
                    }
                }
            }
        }
    }
}
