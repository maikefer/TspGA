package ga.crossover;

import ga.Individual;
import javafx.util.Pair;

import java.util.Random;


public class UOX implements CrossoverStrategy {

    private final Random random = new Random(100);

    @Override
    public Pair<Individual, Individual> doCrossover(Individual parent1, Individual parent2, Random random) {

        int[] mask = getRandomMask(parent1);

        Individual child1 = parent1.getChildUOX(mask, parent2, random);
        Individual child2 = parent2.getChildUOX(mask, parent1, random);

        return new Pair<>(child1, child2);
    }

    private int[] getRandomMask(Individual parent) {
        int citySize = parent.getCitySize();
        int mask[] = new int[citySize];

        for (int i = 0; i < citySize; i++) {
            double j = random.nextInt(100);
            if (j < 50) {
                mask[i] = 0;
            } else {
                mask[i] = 1;
            }
        }
        return mask;
    }
}
