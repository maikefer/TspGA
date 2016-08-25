package ga.crossover;

import ga.Individual;
import javafx.util.Pair;


public class PMX implements CrossoverStrategy {

    @Override
    public Pair<Individual, Individual> doCrossover(Individual parent1, Individual parent2) {

        int[] twoRandomPoints = parent1.getTwoRandomPoints();
        Individual child1 = parent1.getChildPMX(twoRandomPoints[0], twoRandomPoints[1], parent2);
        Individual child2 = parent2.getChildPMX(twoRandomPoints[0], twoRandomPoints[1], parent1);

        return new Pair<>(child1, child2);
    }
}
