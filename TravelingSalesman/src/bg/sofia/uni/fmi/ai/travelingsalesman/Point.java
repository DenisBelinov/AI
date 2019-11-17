package bg.sofia.uni.fmi.ai.travelingsalesman;

import java.awt.print.Printable;
import java.util.Objects;
import java.util.Random;

public class Point {
    private static final int MIN = -10000;
    private static final int MAX = 10000;

    private int x;
    private int y;
    Random randomGenerator = new Random();

    public Point() {
        x = randomGenerator.nextInt((MAX - MIN) + 1) + MIN;
        y = randomGenerator.nextInt((MAX - MIN) + 1) + MIN;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
