package ga.crossover;

import ga.Individual;
import javafx.util.Pair;

import java.util.Random;


public class PMX implements CrossoverStrategy {

    @Override
    public Pair<Individual, Individual> doCrossover(Pair<Individual, Individual> parents, Random random) {

        Individual parent1 = parents.getKey();
        Individual parent2 = parents.getValue();

        int[] twoRandomPoints = parent1.getTwoRandomPoints(random);
        Individual child1 = parent1.getChildPMX(twoRandomPoints[0], twoRandomPoints[1], parent2, random);
        Individual child2 = parent2.getChildPMX(twoRandomPoints[0], twoRandomPoints[1], parent1, random);

        return new Pair<>(child1, child2);
    }
}
