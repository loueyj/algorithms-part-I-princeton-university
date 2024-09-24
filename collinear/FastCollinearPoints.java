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
import java.util.Objects;

public class FastCollinearPoints {
    private List<Double> slopes;
    private List<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
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
        slopes = new ArrayList<Double>();
        for (int p = 0; p < pointsArr.length - 3; p++) {
            // HashMap<Double, List<Point>> slopeAndPoints = new HashMap<Double, List<Point>>();
            List<Double> currSlopes = new ArrayList<Double>();
            List<List<Point>> currPoints = new ArrayList<>();
            Point origin = pointsArr[p];
            for (int q = p + 1; q < pointsArr.length; q++) {
                double slope = origin.slopeTo(pointsArr[q]);
                boolean inSlopes = false;
                int index = 0;
                for (int i = 0; i < currSlopes.size(); i++) {
                    if (currSlopes.get(i).compareTo(slope) == 0) {
                        inSlopes = true;
                        index = i;
                        break;
                    }
                }
                if (!inSlopes) {
                    currSlopes.add(slope);
                    currPoints.add(new ArrayList<Point>());
                    currPoints.get(currPoints.size() - 1).add(pointsArr[q]);
                }
                else {
                    currPoints.get(index).add(pointsArr[q]);
                }
                /**
                 if (slopeAndPoints.containsKey(slope)) {
                 slopeAndPoints.get(slope).add(pointsArr[q]);
                 }
                 else {
                 slopeAndPoints.put(slope, new ArrayList<Point>());
                 slopeAndPoints.get(slope).add(pointsArr[q]);
                 }
                 **/
            }
            int currIndex = 0;
            for (List<Point> arr : currPoints) {
                boolean existingSlope = false;
                if (arr.size() >= 3) {
                    for (double val : slopes) {
                        if (Objects.equals(currSlopes.get(currIndex), val)) {
                            existingSlope = true;
                            break;
                        }
                    }
                    if (!existingSlope) {
                        slopes.add(currSlopes.get(currIndex));
                        segments.add(new LineSegment(origin, currPoints.get(currIndex)
                                                                       .get(currPoints.get(
                                                                               currIndex).size()
                                                                                    - 1)));
                    }
                }
                currIndex++;
            }
            /**
             for (Map.Entry<Double, List<Point>> entry : slopeAndPoints.entrySet()) {
             double key = entry.getKey();
             List<Point> value = entry.getValue();
             if (value.size() >= 3 && !slopes.containsKey(key)) {
             segments.add(new LineSegment(origin, value.get(value.size() - 1)));
             slopes.put(key, 1);
             }
             }
             **/
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
