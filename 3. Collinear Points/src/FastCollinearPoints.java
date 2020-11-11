import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final int numberOfSegments;
    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points == null");
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

//        System.out.println(Arrays.toString(points));
        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();
        ArrayList<Double> slopeList = new ArrayList<>();
        for (int i = 0; i < points.length; ) {
            Point p = points[i];
            prev = null;
            Arrays.sort(points, p.slopeOrder());

//            System.out.println("-------------------------------------------------------------------");
//            System.out.println("Selected point: " + p);
//            System.out.println(Arrays.toString(points));
//            for (Point point : points) {
//                System.out.print(p.slopeTo(point) + ",   ");
//            }
//            System.out.println("_");

            for (int j = 0; j < points.length - 1; j++) {
                if (prev != null && p.slopeTo(points[j]) == p.slopeTo(prev) && !slopeList.contains(p.slopeTo(points[j]))) {
                    int start = j - 1;
                    while (p.slopeTo(points[j]) == p.slopeTo(prev) && j < points.length) {
                        j++;
                    }
                    j--;
                    int collinearPoints = j - start + 2;
//                    System.out.println("Collinear Points: " + collinearPoints);
                    if (collinearPoints >= 4) {
                        lineSegmentArrayList.add(new LineSegment(p, points[j]));
                        slopeList.add(p.slopeTo(points[j]));
                    }
                }
                prev = points[j];
            }

            points = Arrays.copyOf(points, points.length - 1);
            Arrays.sort(points, Point::compareTo);
//            ArrayList<Point> newPoints = new ArrayList<>();
//            for (int j = 0; j < points.length - 1; j++) {
//                boolean skip = false;
//                for (int k = 0; k < startList.size(); k++) {
//                    if (j >= startList.get(k) && j <= endList.get(k)) {
//                        skip = true;
//                        break;
//                    }
//                }
//                if (!skip) {
//                    newPoints.add(points[j]);
//                }
//            }
//
////            System.out.println(newPoints);
//
//            points = new Point[newPoints.size()];
//            for (int j = 0; j < newPoints.size(); j++) {
//                points[j] = newPoints.get(j);
//            }
        }

        this.numberOfSegments = lineSegmentArrayList.size();
        this.lineSegments = new LineSegment[this.numberOfSegments];
        for (int i = 0; i < lineSegmentArrayList.size(); i++) {
            this.lineSegments[i] = lineSegmentArrayList.get(i);
        }

//        for (Point p : points) {
////        Point p = points[0];
//            //System.out.println(":" + p);
//
//            if (p == null) {
//                continue;
//            }
//
//            Arrays.sort(points2, p.slopeOrder());
//            System.out.println(Arrays.toString(points2));
//
//            prev = null;
//            for (int i = 0; i < points2.length; i++) {
//                if (prev != null && p.slopeTo(points2[i]) == p.slopeTo(prev)) {
//                    int start = i - 1;
//                    while (p.slopeTo(points2[i]) == p.slopeTo(prev) && i < points2.length) {
//                        i++;
//                    }
//                    int end = i;
////                    p1 = points2[i];
//                    System.out.println(new LineSegment(prev, points2[i - 1]));
//
//                    for (int j = start; j < end; j++) {
//                        points2[j] = null;
//                    }
//                }
//                System.out.println(p.slopeTo(points2[i]));
//                prev = points2[i];
//            }
//
//            System.out.println(Arrays.toString(points2));
//        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4),
                new Point(5, 5),
                new Point(6, 6),
                new Point(7, 7),
                new Point(8, 8),
                new Point(9, 9),

                new Point(16, 6),
                new Point(11, 21),
                new Point(40, 50),

                new Point(10, 20),
                new Point(20, 40),
                new Point(30, 60),
                new Point(40, 80),

                new Point(10, 0),
                new Point(0, 10),
                new Point(3, 7),
                new Point(7, 3),
                new Point(20, 21),
                new Point(3, 4),
                new Point(14, 15),
                new Point(6, 7),

                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234, 5678),
                new Point(14000, 10000)
        };

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }
}
