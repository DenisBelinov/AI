package bg.sofia.uni.fmi.ai.travelingsalesman.mutator;

import bg.sofia.uni.fmi.ai.travelingsalesman.Chromosome;

import java.util.List;

public interface Mutator {
    void mutate(List<Chromosome> chromosomes);
}
