package bg.sofia.uni.fmi.ai.travelingsalesman.crossover;

import bg.sofia.uni.fmi.ai.travelingsalesman.Chromosome;
import bg.sofia.uni.fmi.ai.travelingsalesman.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OnePointCrossover implements Crossover {
    @Override
    public List<Chromosome> getNextGeneration(List<Chromosome> parentChromosomes) {
        Collections.shuffle(parentChromosomes);

        List<Chromosome> children = new ArrayList<>();
        for (int i = 0; i < parentChromosomes.size(); i += 2) {
            children.addAll(cross(parentChromosomes.get(i), parentChromosomes.get(i + 1)));
        }

        return children;
    }

    private List<Chromosome> cross(Chromosome p1, Chromosome p2) {
        Chromosome c1 = new Chromosome(new ArrayList<>());
        Chromosome c2 = new Chromosome(new ArrayList<>());

        List<Point> c1Path = c1.getPath();
        List<Point> c2Path = c2.getPath();

        int i = ThreadLocalRandom.current().nextInt(1, p1.getPath().size());

        // add the first i elements of the parents to the children
        for (int j = 0; j < i; j++) {
            c1Path.add(p1.getPath().get(j));
            c2Path.add(p2.getPath().get(j));
        }

        // add p1's genes to c2
        for (Point p : p1.getPath()) {
            if (!c2Path.contains(p)) {
                c2Path.add(p);
            }
        }

        // add p2's genes to c1
        for (Point p : p2.getPath()) {
            if (!c1Path.contains(p)) {
                c1Path.add(p);
            }
        }

        c1.calculatePathLength();
        c2.calculatePathLength();

        List<Chromosome> result = new ArrayList<>();
        result.add(c1);
        result.add(c2);
        return result;
    }
}
