import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

/**
 * Created by Daniel on 14/07/15.
 */
public class Brute {
    private Point[] points;
    public static void main(String[] args) {
        try {
            String path;
            File f;
            Brute c = new Brute();

            if (args.length < 1) {
                path = "collinear/input400.txt";  // debug
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
            String[] s = br.readLine().trim().split("\\s+");
            points[i] = new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        }
        this.points = points;
    }

    private void drawPoints() {
        for(Point point: this.points)
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

    private void run() {
        int n = this.points.length;
        for (int i=0; i < n; i++)
            for (int j=i+1; j < n; j++)
                for (int k=j+1; k < n; k++)
                    for (int l=k+1; l < n; l++) {
                        Point p = this.points[i];
                        Point q = this.points[j];
                        Point r = this.points[k];
                        Point s = this.points[l];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            Point[] pqrs = new Point[] {p, q, r, s};
                            StdOut.println(this.toString(pqrs));
                            this.drawLine(pqrs);
                        }
                    }
    }
}
