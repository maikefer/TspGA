package ga.crossover;

import javafx.util.Pair;

import ga.Individual;

import java.util.Random;

public interface CrossoverStrategy {

    Pair<Individual, Individual> doCrossover(Pair<Individual, Individual> parents, Random random);

}
