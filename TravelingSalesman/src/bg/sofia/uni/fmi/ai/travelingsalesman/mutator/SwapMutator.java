package bg.sofia.uni.fmi.ai.travelingsalesman.mutator;

import bg.sofia.uni.fmi.ai.travelingsalesman.Chromosome;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SwapMutator implements Mutator {
    @Override
    public void mutate(List<Chromosome> chromosomes) {
        // sort the chromosomes in reverse order, so we don't mutate the best 1/3 of the chromosomes
        Collections.sort(chromosomes, Collections.reverseOrder());

        int chromosomeSize = chromosomes.get(0).getPath().size();

        //mutate the first 2/3 of the chromosomes
        for (int i = 0; i < 2 * chromosomes.size() / 3; i++) {

            int k = ThreadLocalRandom.current().nextInt(0, chromosomeSize);
            int l = ThreadLocalRandom.current().nextInt(0, chromosomeSize);

            Collections.swap(chromosomes.get(i).getPath(), k, l);
        }
    }
}
