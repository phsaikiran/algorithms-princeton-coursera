import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final int segments;
    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(final Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("p == null");
            }
        }

        Arrays.sort(points, Point::compareTo);

        Point prev = null;
        for (Point p : points) {
            if (prev != null && prev.compareTo(p) == 0) {
                throw new IllegalArgumentException("prev != null && prev.compareTo(p) == 0");
            }
            prev = p;
        }

        ArrayList<Point[]> pointList = new ArrayList<>();
        for (Point p1 : points) {
            for (Point p2 : points) {
                for (Point p3 : points) {
                    for (Point p4 : points) {
                        if (p1 == null || p2 == null || p3 == null || p4 == null) {
                            throw new IllegalArgumentException();
                        }
                        if (p1.slopeTo(p2) == p2.slopeTo(p3)
                                && p2.slopeTo(p3) == p3.slopeTo(p4)
                                && p1.compareTo(p2) < 0
                                && p2.compareTo(p3) < 0
                                && p3.compareTo(p4) < 0) {
                            boolean add = true;
                            for (Point[] pointArray : pointList) {
                                if (pointArray[0].compareTo(p1) == 0 && p4.compareTo(pointArray[1]) == 0) {
                                    add = false;
                                    break;
                                } else if (pointArray[0].compareTo(p1) == 0 && p1.slopeTo(p4) == p4.slopeTo(pointArray[1])) {
                                    add = false;
                                    break;
                                } else if (p4.compareTo(pointArray[1]) == 0 && pointArray[0].slopeTo(p1) == p1.slopeTo(p4)) {
                                    add = false;
                                    break;
                                } else if (pointArray[0].slopeTo(p1) == p1.slopeTo(p4) && p1.slopeTo(p4) == p4.slopeTo(pointArray[1])) {
                                    add = false;
                                    break;
                                }
                            }
                            if (add) {
                                Point[] lineSegmentPoints = new Point[2];
                                lineSegmentPoints[0] = p1;
                                lineSegmentPoints[1] = p4;
                                pointList.add(lineSegmentPoints);
                            }
                        }
                    }
                }
            }
        }

        ArrayList<LineSegment> solution = new ArrayList<>();
        for (Point[] pointArray : pointList) {
            solution.add(new LineSegment(pointArray[0], pointArray[1]));
        }

        this.segments = solution.size();
        this.lineSegments = new LineSegment[this.segments];
        for (int i = 0; i < this.segments; i++) {
            this.lineSegments[i] = solution.get(i);
        }
    }

    private BruteCollinearPoints(LineSegment[] lineSegments) {
        this.segments = lineSegments.length;
        this.lineSegments = new LineSegment[this.segments];
        System.arraycopy(lineSegments, 0, this.lineSegments, 0, this.segments);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments;
    }

    // the line segments
    public LineSegment[] segments() {
        return new BruteCollinearPoints(lineSegments).lineSegments;
    }

    public static void main(String[] args) {
        In in = new In("input.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        System.out.println(bruteCollinearPoints.segments);
        for (LineSegment lineSegment : bruteCollinearPoints.lineSegments) {
            StdOut.println(lineSegment);
            lineSegment.draw();
        }
        StdDraw.show();
    }

}
