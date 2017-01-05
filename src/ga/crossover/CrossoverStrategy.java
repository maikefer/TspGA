package ga.crossover;

import ga.Individual;
import javafx.util.Pair;

public interface CrossoverStrategy {

    Pair<Individual, Individual> doCrossover(Individual parent1, Individual parent2);

}
