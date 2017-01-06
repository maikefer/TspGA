package ga.crossover;

import javafx.util.Pair;

import ga.Individual;

public interface CrossoverStrategy {

    Pair<Individual, Individual> doCrossover(Individual parent1, Individual parent2);

}
