/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        // this.points = new Point[points.length];
        Point[] pointsArr = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            pointsArr[i] = points[i];
        }
        Arrays.sort(pointsArr);
        for (int j = 0; j < pointsArr.length - 1; j++) {
            if (pointsArr[j].compareTo(pointsArr[j + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
        segments = new ArrayList<LineSegment>();
        for (int p = 0; p < pointsArr.length - 3; p++) {
            for (int q = p + 1; q < pointsArr.length - 2; q++) {
                for (int r = q + 1; r < pointsArr.length - 1; r++) {
                    for (int s = r + 1; s < pointsArr.length; s++) {
                        if (pointsArr[p].slopeTo(pointsArr[q]) == pointsArr[p].slopeTo(pointsArr[r])
                                && pointsArr[p].slopeTo(pointsArr[r]) == pointsArr[p].slopeTo(
                                pointsArr[s])) {
                            segments.add(new LineSegment(pointsArr[p], pointsArr[s]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
