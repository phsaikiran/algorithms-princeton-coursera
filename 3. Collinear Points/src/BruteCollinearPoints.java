import java.util.ArrayList;

public class BruteCollinearPoints {

    private final int segments;
    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<String> allPoints = new ArrayList<>();
        for (Point p1 : points) {
            if (allPoints.contains(p1.toString())) {
                throw new IllegalArgumentException();
            }
            allPoints.add(p1.toString());
        }

        ArrayList<String> visited = new ArrayList<>();
        ArrayList<LineSegment> solution = new ArrayList<>();
        for (Point p1 : points) {
            for (Point p2 : points) {
                for (Point p3 : points) {
                    for (Point p4 : points) {
                        if (p1 == null || p2 == null || p3 == null || p4 == null) {
                            throw new IllegalArgumentException();
                        }
                        boolean c1 = p1.slopeTo(p2) == p2.slopeTo(p3);
                        boolean c2 = p2.slopeTo(p3) == p3.slopeTo(p4);
                        boolean c3 = p1.compareTo(p2) < 0;
                        boolean c4 = p2.compareTo(p3) < 0;
                        boolean c5 = p3.compareTo(p4) < 0;
                        if (c1 && c2 && c3 && c4 && c5) {
                            LineSegment newLineSegment = new LineSegment(p1, p4);
                            if (!visited.contains(newLineSegment.toString())) {
                                visited.add(newLineSegment.toString());
                                solution.add(newLineSegment);
                            }
                        }
                    }
                }
            }
        }

        this.segments = solution.size();
        this.lineSegments = new LineSegment[this.segments];
        for (int i = 0; i < this.segments; i++) {
            this.lineSegments[i] = solution.get(i);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(10000, 0),
                new Point(0, 10000),
                new Point(3000, 7000),
                new Point(7000, 3000),
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000),
        };
        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);

        System.out.println(bruteCollinearPoints.segments);
        for (LineSegment lineSegment : bruteCollinearPoints.lineSegments) {
            System.out.println(lineSegment);
        }
    }

}
