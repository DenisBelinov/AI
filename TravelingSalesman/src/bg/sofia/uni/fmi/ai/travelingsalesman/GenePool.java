package bg.sofia.uni.fmi.ai.travelingsalesman;

import bg.sofia.uni.fmi.ai.travelingsalesman.crossover.Crossover;
import bg.sofia.uni.fmi.ai.travelingsalesman.mutator.Mutator;

import java.util.*;

public class GenePool {
    private int poolSize ;

    private Queue<Chromosome> pool;

    private Crossover crossover;
    private Mutator mutator;

    public GenePool(int pointCount, Crossover crossover, Mutator mutator) {
        this.poolSize = pointCount;
        this.crossover = crossover;
        this.mutator = mutator;

        ArrayList<Point> startingPoints = new ArrayList<>();
        for (int i = 0; i < pointCount; i++) {
            startingPoints.add(new Point());
        }

        pool = new PriorityQueue<>(poolSize, Collections.reverseOrder());
        for (int i = 0; i < poolSize; i++) {
            pool.add(new Chromosome(startingPoints));
        }
    }

    public Chromosome getBestChromosome() {
        List<Chromosome> poolList = new ArrayList<>(pool);
        Collections.sort(poolList);

        return poolList.get(0);
    }

    public void evolve() {
        // get the children
        List<Chromosome> currentChromosomesList = new ArrayList<>(pool);
        mutator.mutate(currentChromosomesList);

        List<Chromosome> children = crossover.getNextGeneration(currentChromosomesList);

        for (Chromosome c : children) {
            if (c.getPathLength() < pool.peek().getPathLength()) {
                // we have a better path than the worst path
                // and we add it to the pool
                pool.poll();
                pool.add(c);
            }
        }
    }
}
