package bg.sofia.uni.fmi.ai.travelingsalesman;

import java.util.ArrayList;
import java.util.List;

public class Chromosome implements Comparable<Chromosome> {
    private List<Point> path;
    private double pathLength;

    public Chromosome(List<Point> points) {
        path = new ArrayList<>(points);
        java.util.Collections.shuffle(path);

        calculatePathLength();
    }

    public List<Point> getPath() {
        return path;
    }

    public double getPathLength() {
        return pathLength;
    }

    public void calculatePathLength() {
        this.pathLength = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            pathLength += calculateDistance(path.get(i), path.get(i + 1));
        }
    }
    private double calculateDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
    }

    @Override
    public int compareTo(Chromosome o) {
        return (int)(this.pathLength - o.getPathLength());
    }
}
