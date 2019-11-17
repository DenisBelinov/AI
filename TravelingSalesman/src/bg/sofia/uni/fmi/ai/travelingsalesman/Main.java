package bg.sofia.uni.fmi.ai.travelingsalesman;

import bg.sofia.uni.fmi.ai.travelingsalesman.crossover.OnePointCrossover;
import bg.sofia.uni.fmi.ai.travelingsalesman.mutator.SwapMutator;

import java.util.*;

public class Main {

    private static final int GENERATIONS_COUNT = 1000;
    private static final Integer[] PRINT_INDEXES = {0, 10, GENERATIONS_COUNT/3, 2 * GENERATIONS_COUNT/3, GENERATIONS_COUNT - 1};

    public static void main(String[] args) {
        // get input
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter N: ");
        int n = scan.nextInt();
        //int n = 100;

        GenePool genePoool = new GenePool(n, new OnePointCrossover(), new SwapMutator());

        for (Integer i = 0; i < GENERATIONS_COUNT; i++) {
            if (Arrays.stream(PRINT_INDEXES).anyMatch(i::equals)) {
                System.out.println(i + "th generation's best path:");
                System.out.println(genePoool.getBestChromosome().getPath().toString());
                System.out.println(genePoool.getBestChromosome().getPathLength());
                System.out.println();
            }

            genePoool.evolve();
        }
    }
}
