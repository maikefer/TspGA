package ga;

import ga.crossover.CrossoverStrategy;
import ga.selection.SelectionStrategy;
import javafx.util.Pair;
import util.City;
import util.Percent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    private List<Individual> individuals;
    private final int citySize;

    /**
     * Describes how many of the Individuals in the population are allowed to reproduce.
     */
    private final Percent crossoverRate;

    /**
     * Describes how many of the Individuals in the population are allowed to mutate.
     */
    private final Percent mutationRate;

    /**
     * Describes how many Elite-Individuals are chosen from the population
     * and put (without crossover and mutation) into the next generation.
     */
    private final Percent elitismRate;

    public Population(int size, Percent crossoverRate, Percent mutationRate,
                      Percent elitismRate, List<City> cities, Random randomGenerator) {

        this(generateRandomIndividuals(size, randomGenerator, cities), crossoverRate, mutationRate, elitismRate, cities.size());
    }

    private Population(List<Individual> individuals, Percent crossoverRate,
                       Percent mutationRate, Percent elitismRate, int amountCities) {

        this.citySize = amountCities;
        this.individuals = new ArrayList<>();
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismRate = elitismRate;
        this.individuals = individuals;
    }

    private static List<Individual> generateRandomIndividuals(int size, Random randomGenerator, List<City> cities) {
        return IntStream.range(0, size)
                .mapToObj(i -> new Individual(randomGenerator, cities))
                .collect(Collectors.toList());
    }

    /**
     * Generates the offspring of the current population.
     */
    public Population reproduce(SelectionStrategy selectionStrategy, CrossoverStrategy crossoverStrategy,
                                Random randomGenerator, int amountCities) {

        PopulationBuilder childrenPop = new PopulationBuilder(individuals.size());

        List<Individual> elite = this.grabElite();
        elite.forEach(childrenPop::add);


        while (childrenPop.isNotFull()) {

            Pair<Individual, Individual> parents = selectionStrategy.selectParents(individuals, randomGenerator);
            Pair<Individual, Individual> children = crossoverStrategy.doCrossover(parents, randomGenerator);
            Pair<Individual, Individual> nextGeneration = determineWhichIndividualsToAdd(parents, children, randomGenerator);

            childrenPop.add(nextGeneration.getKey());

            if (childrenPop.isNotFull()) {
                childrenPop.add(nextGeneration.getValue());
            }
        }
        return childrenPop.buildPopulation(this.crossoverRate, this.mutationRate, this.elitismRate, amountCities);
    }

    private Pair<Individual, Individual> determineWhichIndividualsToAdd(Pair<Individual, Individual> parents,
                                                                        Pair<Individual, Individual> children,
                                                                        Random randomGenerator) {
        Individual firstWinner = parents.getKey();

        if (crossoverIsAllowed(randomGenerator)) {
            firstWinner = children.getKey();
        }
        if (mutationIsAllowed(randomGenerator)) {
            firstWinner.mutateBetter(randomGenerator); // todo
        }

        Individual secondWinner = parents.getValue();

        if (crossoverIsAllowed(randomGenerator)) {
            secondWinner = children.getValue();
        }
        if (mutationIsAllowed(randomGenerator)) {
            secondWinner.mutateBetter(randomGenerator);
        }

        return new Pair<>(firstWinner, secondWinner);
    }

    /**
     * Checks, if the Individual is allowed to reproduce. <br/>
     * (Due to the crossoverRate)
     *
     * @param randomGenerator
     * @return If the Individual is allowed to reproduce
     */
    private boolean crossoverIsAllowed(Random randomGenerator) {
        int absolute = (int) (crossoverRate.getPercentage() * 100);
        int k = randomGenerator.nextInt(100);

        return k <= absolute;
    }

    /**
     * Checks if an Individual is allowed to mutate (due to the mutationRate).
     *
     * @param randomGenerator
     * @return If the individual is allowed to mutate.
     */
    private boolean mutationIsAllowed(Random randomGenerator) {
        int absolute = (int) (mutationRate.getPercentage() * 100);
        int k = randomGenerator.nextInt(100);
        return k <= absolute;
    }

    /**
     * Generates a random Bit-Mask with length = citySize
     *
     * @param randomGenerator
     * @return generated Bit-Mask
     * TODO: better if boolean[], not int[] ?
     */
    private int[] getRandomMask(Random randomGenerator) {
        int mask[] = new int[citySize];

        for (int i = 0; i < citySize; i++) {
            double j = randomGenerator.nextInt(100);
            if (j < 50) {
                mask[i] = 0;
            } else {
                mask[i] = 1;
            }
        }
        return mask;
    }

    /**
     * Grabs the elite of the current population.
     *
     * @return the elite individuals of the population
     */
    private List<Individual> grabElite() {

        int amountEliteIndividuals = ((int) elitismRate.getPercentage() * 100) * individuals.size();
        if (amountEliteIndividuals == 0) {
            return new ArrayList<>();
        }

        TreeSet<Individual> sortedIndividuals = new TreeSet<>(individuals); // sort in ascending order

        return sortedIndividuals.stream().limit(amountEliteIndividuals).collect(Collectors.toList());
    }

    public double getAverageFitness() {
        double totalFitness = individuals.stream().mapToDouble(Individual::getFitness).sum();
        return totalFitness / individuals.size();
    }

    public Individual getFittestIndividual() {
        Optional<Individual> min = individuals.stream().min((x, y) -> x.getFitness() - y.getFitness());
        return min.orElseThrow(() -> new IllegalArgumentException("No fittest individual found"));
    }

    public int getAmountOfIndividuals() {
        return individuals.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, individuals.size())
                .forEach(i -> sb.append("I").append(i).append(": ")
                        .append(individuals.get(i).toString()).append("\n"));
        return sb.toString();
    }

    private class PopulationBuilder {

        private final int size;
        private final List<Individual> individuals;

        private PopulationBuilder(int size) {
            this.size = size;
            this.individuals = new ArrayList<>();
        }

        private void add(Individual individual) {
            if (individuals.size() >= size) {
                throw new IllegalStateException(
                        "The Population contains the maximum amount of Individuals. You can not add another individual!");
            }
            individuals.add(individual);
        }

        private Population buildPopulation(Percent crossoverRate, Percent mutationRate,
                                           Percent elitismRate, int amountCities) {

            return new Population(individuals, crossoverRate, mutationRate, elitismRate, amountCities);
        }

        boolean isNotFull() {
            return individuals.size() < size;
        }
    }
}
