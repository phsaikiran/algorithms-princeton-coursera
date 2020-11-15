import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final int segments;
    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(final Point[] pointsArg) {
        if (pointsArg == null) {
            throw new IllegalArgumentException("points == null");
        }

        for (Point p : pointsArg) {
            if (p == null) {
                throw new IllegalArgumentException("p == null");
            }
        }

        Point[] pointsSorted = new Point[pointsArg.length];
        Point[] points = new Point[pointsArg.length];
        System.arraycopy(pointsArg, 0, pointsSorted, 0, pointsArg.length);
        Arrays.sort(pointsSorted, Point::compareTo);
        System.arraycopy(pointsSorted, 0, points, 0, pointsArg.length);
//        System.out.println(Arrays.toString(points));

        Point prev = null;
        for (Point p : points) {
            if (prev != null && prev.compareTo(p) == 0) {
                throw new IllegalArgumentException("prev != null && prev.compareTo(p) == 0");
            }
            prev = p;
        }

        ArrayList<Point[]> pointList = new ArrayList<>();
//        ArrayList<Double> slopeList = new ArrayList<>();
//        ArrayList<Double> interceptList = new ArrayList<>();
        for (Point p : points) {
            prev = null;

//            System.out.println("-------------------------------------------------------------------");
//            System.out.println("Selected point: " + p);
//            System.out.println(Arrays.toString(points));
            Arrays.sort(points, p.slopeOrder());
//            System.out.println(Arrays.toString(points));
//            for (Point point : points) {
//                System.out.print(p.slopeTo(point) + ",   ");
//            }
//            System.out.println();

            for (int j = 1; j < points.length; j++) {
                if (prev != null && p.slopeTo(points[j]) == p.slopeTo(prev)) {
                    int start = j - 1;
                    while (j < points.length && p.slopeTo(points[j]) == p.slopeTo(prev)) {
                        j++;
                    }
                    j--;
                    int collinearPoints = j - start + 2;
//                    System.out.println("Collinear Points: " + collinearPoints);
                    if (collinearPoints >= 4) {
                        boolean add = true;

//                        System.out.println("-------------");
//                        for (Point[] pointArray : pointList) {
//                            System.out.println(Arrays.toString(pointArray));
//                        }
//                        System.out.println("-------------");

                        for (Point[] pointArray : pointList) {
//                            System.out.println(pointArray[0].slopeTo(p) + " " + p.slopeTo(points[j]) + " " + points[j].slopeTo(pointArray[1]));

                            if (pointArray[0].compareTo(p) == 0 && points[j].compareTo(pointArray[1]) == 0) {
                                add = false;
                                break;
                            } else if (pointArray[0].compareTo(p) == 0 && p.slopeTo(points[j]) == points[j].slopeTo(pointArray[1])) {
                                add = false;
                                break;
                            } else if (points[j].compareTo(pointArray[1]) == 0 && pointArray[0].slopeTo(p) == p.slopeTo(points[j])) {
                                add = false;
                                break;
                            } else if (pointArray[0].slopeTo(p) == p.slopeTo(points[j]) && p.slopeTo(points[j]) == points[j].slopeTo(pointArray[1])) {
//                                System.out.print(pointArray[0]);
//                                System.out.print(p);
//                                System.out.print(points[j]);
//                                System.out.print(pointArray[0].slopeTo(p) + " " + p.slopeTo(points[j]) + " " + points[j].slopeTo(pointArray[1]));
//                                System.out.println();
                                add = false;
                                break;
                            }
                        }
                        if (add) {
                            Point[] lineSegmentPoints = new Point[2];
                            lineSegmentPoints[0] = p;
                            lineSegmentPoints[1] = points[j];
                            pointList.add(lineSegmentPoints);
                        }

//                        lineSegmentArrayList.add(new LineSegment(p, points[j]));
//                        slopeList.add(p.slopeTo(prev));
                    }
                }
                prev = points[j];
            }

//            Arrays.sort(points, Point::compareTo);
            System.arraycopy(pointsSorted, 0, points, 0, pointsArg.length);
        }

        ArrayList<LineSegment> solution = new ArrayList<>();
        for (Point[] pointArray : pointList) {
            solution.add(new LineSegment(pointArray[0], pointArray[1]));
        }

        this.segments = solution.size();
        this.lineSegments = new LineSegment[this.segments];
        for (int i = 0; i < solution.size(); i++) {
            this.lineSegments[i] = solution.get(i);
        }
    }

    private FastCollinearPoints(LineSegment[] lineSegments) {
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
        return new FastCollinearPoints(lineSegments).lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("input.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
//            System.out.print(segment);
//            System.out.print("     ");
//            double m = segment.p.slopeTo(segment.q);
//            System.out.print(m);
//            System.out.print("     ");
//            System.out.print(segment.p.y - segment.p.x * m);
//            System.out.println();
            segment.draw();
        }

        StdDraw.show();
    }
}
