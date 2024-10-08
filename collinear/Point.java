/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        }
        else if (this.y == that.y && this.x == that.x) {
            return 0;
        }
        return 1;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.y == that.y && this.x != that.x) {
            return 0.0;
        }
        else if (this.x == that.x && this.y != that.y) {
            return Double.POSITIVE_INFINITY;
        }
        else if (this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;
        }
        double slope = (double) (that.y - this.y) / (that.x - this.x);
        return slope;
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double firstSlope = slopeTo(a);
            double secondSlope = slopeTo(b);
            if (firstSlope < secondSlope) {
                return -1;
            }
            else if (firstSlope > secondSlope) {
                return 1;
            }
            return 0;
        }
    }

    // Unit tests the Point data type.
    public static void main(String[] args) {

    }
}
