/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private final Point2D point;
        private final boolean horizontal;
        private Node left;
        private Node right;

        public Node(Point2D p, boolean horizontal) {
            this.point = p;
            this.horizontal = horizontal;
            this.left = null;
            this.right = null;
        }

        public int compareTo(Node curr) {
            if (horizontal) {
                return Double.compare(curr.point.x(), this.point.x());
            }
            else {
                return Double.compare(curr.point.y(), this.point.y());
            }
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null || !contains(p)) {
            root = insertNode(root, p, true);
            size++;
        }
    }

    private Node insertNode(Node node, Point2D p, boolean horizontal) {
        Node curr = new Node(p, horizontal);
        if (node == null) {
            return curr;
        }
        if (node.horizontal) {
            // if (curr.compareTo(node) < 0) {
            if (p.x() < node.point.x()) {
                node.left = insertNode(node.left, p, !horizontal);
            }
            else {
                node.right = insertNode(node.right, p, !horizontal);
            }
        }
        else {
            // if (curr.compareTo(node) < 0) {
            if (p.y() < node.point.y()) {
                node.left = insertNode(node.left, p, !horizontal);
            }
            else {
                node.right = insertNode(node.right, p, !horizontal);
            }
        }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) { // Not working
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return containsNode(root, p);
    }

    private boolean containsNode(Node node, Point2D p) {
        if (node == null) {
            return false;
        }
        if (p.equals(node.point)) {
            return true;
        }
        if (node.horizontal && p.x() < node.point.x()
                || !node.horizontal && p.y() < node.point.y()) {
            return containsNode(node.left, p);
        }
        else {
            return containsNode(node.right, p);
        }
    }

    // draw all points to standard draw
    public void draw() {
        drawNode(root, 0, 1, 0, 1);
    }

    private void drawNode(Node node, double minX, double maxX, double minY, double maxY) {
        if (node == null) {
            return;
        }

        drawPoint(node.point);
        if (node.horizontal) {
            drawHorizontal(node, minX, maxX);
            drawNode(node.left, minX, node.point.x(), minY, maxY);
            drawNode(node.right, node.point.x(), maxX, minY, maxY);
        }
        else {
            drawVertical(node, minY, maxY);
            drawNode(node.left, minX, maxX, minY, node.point.y());
            drawNode(node.right, minX, maxX, node.point.y(), maxY);
        }
    }

    private void drawPoint(Point2D p) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        p.draw();
    }

    private void drawHorizontal(Node node, double minX, double maxX) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(minX, node.point.y(), maxX, node.point.y());
    }

    private void drawVertical(Node node, double minY, double maxY) {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(node.point.x(), minY, node.point.x(), maxY);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) { // Not working
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> list = new ArrayList<Point2D>();
        if (!isEmpty()) {
            findRange(root, rect, list);
        }
        return list;
    }

    private void findRange(Node node, RectHV rect, List<Point2D> list) {
        if (node == null) {
            return;
        }
        boolean bothChildrenIntersect;
        Point2D p = node.point;

        if (rect.contains(p)) {
            list.add(p);
            bothChildrenIntersect = true;
        }
        else if (node.horizontal && rect.intersects(new RectHV(p.x(), 0.0, p.x(), 1.0))
                || !node.horizontal && rect.intersects(new RectHV(0.0, p.y(), 1.0, p.y()))) {
            bothChildrenIntersect = true;
        }
        else {
            bothChildrenIntersect = false;
        }

        if (bothChildrenIntersect) {
            findRange(node.left, rect, list);
            findRange(node.right, rect, list);
        }
        else {
            if (node.horizontal && p.x() > rect.xmin() || !node.horizontal && p.y() > rect.ymin()) {
                findRange(node.left, rect, list);
            }
            else {
                findRange(node.right, rect, list);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }
        Node node = findNearest(root, p, root);
        return node.point;
    }

    private Node findNearest(Node curr, Point2D p, Node node) {
        if (curr == null) {
            return node;
        }
        if (curr.point.distanceSquaredTo(p) < node.point.distanceSquaredTo(p)) {
            node = curr;
        }

        Node left = findNearest(curr.left, p, node);
        Node right = findNearest(curr.right, p, node);

        if (left.point.distanceSquaredTo(p) < right.point.distanceSquaredTo(p)) {
            if (left.point.distanceSquaredTo(p) < node.point.distanceSquaredTo(p)) {
                node = left;
            }
        }
        else {
            if (right.point.distanceSquaredTo(p) < node.point.distanceSquaredTo(p)) {
                node = right;
            }
        }
        return node;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
