package bouncing.balls;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Ball {

    private double rx, ry;
    private double vx, vy;
    private final double radius;

    public Ball() {
        this.rx = StdRandom.uniform(0.0, 1.0);
        this.ry = StdRandom.uniform(0.0, 1.0);
        this.vx = StdRandom.uniform(-1.0, 1.0) * 0.1;
        this.vy = StdRandom.uniform(-1.0, 1.0) * 0.1;
        this.radius = 0.005;
    }

    public void move(double dt) {
        if ((rx + vx * dt < radius) || (rx + vx * dt > 1.0 - radius)) {
            vx = -vx;
        }
        if ((ry + vy * dt < radius) || (ry + vy * dt > 1.0 - radius)) {
            vy = -vy;
        }
        rx = rx + vx * dt;
        ry = ry + vy * dt;
    }

    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }

}
