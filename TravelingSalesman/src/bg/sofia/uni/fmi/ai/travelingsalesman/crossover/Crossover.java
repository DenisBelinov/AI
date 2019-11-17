package bg.sofia.uni.fmi.ai.travelingsalesman.crossover;

import bg.sofia.uni.fmi.ai.travelingsalesman.Chromosome;

import java.util.List;

public interface Crossover {
    /**
     * Generates a new generation of parentChromosomes. Shuffles the chromosomes before crossing over.
     *
     * @param parentChromosomes a list of the parent chromosomes
     * @return list of the children of the parent chromosomes
     */
    List<Chromosome> getNextGeneration (List<Chromosome> parentChromosomes);
}
