package ga;

import ga.crossover.CrossoverStrategy;

import javafx.util.Pair;
import util.City;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class represents a population with a certain amount of individuals.
 */
public class Population {

    private List<Individual> individuals;
    private final int citySize;

    /**
     * The crossoverRate describes how many of the Individuals in the population are allowed to reproduce.
     * It should be between 0.0 and 1.0 since it represents a %-number.
     */
    private final float crossoverRate;

    /**
     * The mutationRate describes how many of the Individuals in the population are allowed to mutate.
     * It should be between 0.0 and 1.0 since it represents a %-number.
     */
    private final float mutationRate;

    /**
     * The elitismRate describes how many Elite-Individuals are chosen from the population
     * and put (without crossover and mutation) into the next generation.
     */
    private final float elitismRate;

    public Population(int size, float crossoverRate, float mutationRate,
                      float elitismRate, List<City> cities, Random randomGenerator) {

        this(randomIndividuals(size, randomGenerator, cities), crossoverRate, mutationRate, elitismRate, cities.size());
    }

    private Population(List<Individual> individuals, float crossoverRate,
                       float mutationRate, float elitismRate, int amountCities) {

        this.citySize = amountCities;
        this.individuals = new ArrayList<>();
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismRate = elitismRate;
        this.individuals = individuals;
    }

    private static List<Individual> randomIndividuals(int size, Random randomGenerator, List<City> cities) {
        return IntStream.range(0, size).mapToObj(i -> new Individual(randomGenerator, cities)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < individuals.size(); i++) {
            sb.append("I").append(i).append(": ").append(individuals.get(i).toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Generate the offspring of the current population.
     */
    public Population reproduce(CrossoverStrategy crossoverStrategy, Random randomGenerator, int amountCities) {
        List<Individual> elite = this.grabElite();

        PopulationBuilder childrenPop = new PopulationBuilder(individuals.size());

        // insert elite individuals into new childPopulation
        elite.forEach(childrenPop::add);


        while (!childrenPop.isFull()) {

            Individual parent1 = this.selectTournament(randomGenerator);
            Individual parent2 = this.selectTournament(randomGenerator);

            Pair<Individual, Individual> children = crossoverStrategy.doCrossover(parent1, parent2, randomGenerator);

            Individual individualToAdd;

            if (crossoverOk(randomGenerator)) {
                individualToAdd = children.getKey();
            } else {
                individualToAdd = parent1;
            }
            if (mutationOk(randomGenerator)) {
                individualToAdd.mutateBetter(randomGenerator);
            }
            childrenPop.add(individualToAdd);

            if (!childrenPop.isFull()) {

                if (crossoverOk(randomGenerator)) {
                    individualToAdd = children.getValue();
                } else {
                    individualToAdd = parent2;
                }
                if (mutationOk(randomGenerator)) {
                    individualToAdd.mutateBetter(randomGenerator);
                }
                childrenPop.add(individualToAdd);
            }
        }

        return childrenPop.makePopulation(this.crossoverRate, this.mutationRate, this.elitismRate, amountCities);
    }

    public int getAmountOfIndividuals() {
        return individuals.size();
    }

    public Individual getIndividual(int n) {
        return individuals.get(n);
    }

    /**
     * Tournament Selection:
     * Selects a number of random individuals of the population and returns the
     * individual with the best fitness.
     *
     * @return the individual with the best fitness
     * @param randomGenerator
     */
    private Individual selectTournament(Random randomGenerator) {

        int tournamentNumber = 5;
        Individual tournament[] = new Individual[tournamentNumber];

        for (int i = 0; i < tournamentNumber; i++) {
            int k = randomGenerator.nextInt(individuals.size());
            tournament[i] = this.getIndividual(k);
        }

        Individual winner = tournament[0];
        for (int i = 1; i < tournamentNumber; i++) {
            if (tournament[i].getFitness() < winner.getFitness()) {
                winner = tournament[i];
            }
        }
        return winner;
    }

    /**
     * Checks, if the Individual is allowed to reproduce. <br/>
     * (Due to the crossoverRate)
     *
     * @return If the Individual is allowed to reproduce
     * @param randomGenerator
     */
    private boolean crossoverOk(Random randomGenerator) {
        int absolute = (int) (crossoverRate * 100);
        int k = randomGenerator.nextInt(100);

        return k <= absolute;
    }

    /**
     * Checks if an Individual is allowed to mutate (due to the mutationRate).
     *
     * @return If the individual is allowed to mutate.
     * @param randomGenerator
     */
    private boolean mutationOk(Random randomGenerator) {
        int absolute = (int) (mutationRate * 100);
        int k = randomGenerator.nextInt(100);
        return k <= absolute;
    }

    /**
     * Generates a random Bit-Mask with length = citySize
     *
     * @return generated Bit-Mask
     * TODO: better if boolean[], not int[] ?
     * @param randomGenerator
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
     * Grabs the elite (due to the elitismRate) of the current population.
     *
     * @return the elite individuals of the population
     */
    private List<Individual> grabElite() {

        int amountOfIndividualsToBePicked = (int) elitismRate * individuals.size();

        TreeSet<Individual> map = new TreeSet<>();
        map.addAll(individuals);

        List<Individual> elite = new ArrayList<>();

        for (int i = 0; i < amountOfIndividualsToBePicked; i++) {
            elite.add(map.pollFirst());
        }
        return elite;
    }

    public double getAverageFitness() {
        double totalFitness = individuals.stream().mapToDouble(Individual::getFitness).sum();
        return totalFitness / individuals.size();
    }

    public Individual getFittestIndividual() {
        Optional<Individual> min = individuals.stream().min((x, y) -> x.getFitness() - y.getFitness());
        return min.orElseThrow(() -> new IllegalArgumentException("No fittest chromosome found"));
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
                throw new IllegalStateException("Already enough Individuals");
            }
            individuals.add(individual);
        }

        private Population makePopulation(float crossoverRate, float mutationRate,
                                          float elitismRate, int amountCities) {
            return new Population(individuals, crossoverRate, mutationRate, elitismRate, amountCities);
        }

        boolean isFull() {
            return individuals.size() >= size;
        }
    }
}
