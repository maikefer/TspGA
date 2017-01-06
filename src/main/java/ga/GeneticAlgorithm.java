package ga;

import ga.crossover.CrossoverStrategy;
import ga.initialization.InitializationStrategy;
import util.City;

import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    private final List<City> cities;
    private Population parentPop;
    private final int genSize;
    private final CrossoverStrategy crossoverStrategy;
    private final Random random;

    public GeneticAlgorithm(Config config, InitializationStrategy initializationStrategy,
                            CrossoverStrategy crossoverStrategy) {

        this.random = new Random(config.seed);
        this.cities = initializationStrategy.createCities();
        this.genSize = config.genSize;
        this.parentPop = new Population(config.popSize, config.crossoverRate, config.mutationRate,
                config.elitismRate, this.getCities(), this.random);
        this.crossoverStrategy = crossoverStrategy;
    }

    Individual findBestIndividual() {
        Individual bestIndividual = parentPop.getFittestIndividual();

        int generationCounter = 0;

        while (generationCounter < genSize + 1) {

            parentPop = parentPop.reproduce(crossoverStrategy, random, this.cities.size());

            for (int i = 0; i < parentPop.getAmountOfIndividuals(); i++) {
                if (parentPop.getIndividual(i).getFitness() < bestIndividual.getFitness()) {
                    bestIndividual = parentPop.getIndividual(i);
                }
            }
//            System.out.println("Generation Counter: " + generationCounter
//                    + " best Fitness: " + bestIndividual.getFitness()
//                    + " average Fitness: " + parentPop.getAverageFitness());
            generationCounter++;
        }
        return bestIndividual;
    }

    public int getAmountCities() {
        return cities.size();
    }

    public List<City> getCities() {
        return cities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int amountCities = this.getAmountCities();

        sb.append("Dimension: ").append(amountCities);
        sb.append("\nPopulationSize: ").append(parentPop.getAmountOfIndividuals());
        sb.append("\nNodes:");

        for (int i = 0; i < amountCities; i++) {
            sb.append("\n")
                    .append("no: ")
                    .append(i)
                    .append(" x: ")
                    .append(cities.get(i).getX())
                    .append(" y: ")
                    .append(cities.get(i).getY());
        }
        return sb.toString();
    }

    private static class Config {
        private final int popSize;
        private final int genSize;
        private final float crossoverRate;
        private final float mutationRate;
        private final float elitismRate;
        private final int seed;

        Config(int popSize, int genSize, float crossoverRate, float mutationRate, float elitismRate, int seed) {
            this.popSize = popSize;
            this.genSize = genSize;
            this.crossoverRate = crossoverRate;
            this.mutationRate = mutationRate;
            this.elitismRate = elitismRate;
            this.seed = seed;
        }
    }

    public static class ConfigBuilder {

        private int popSize;
        private int genSize;
        private float crossoverRate;
        private float mutationRate;
        private float elitismRate;
        private int seed;

        public ConfigBuilder setPopSize(int popSize) {
            this.popSize = popSize;
            return this;
        }

        public ConfigBuilder setGenSize(int genSize) {
            this.genSize = genSize;
            return this;
        }

        public ConfigBuilder setCrossoverRate(float crossoverRate) {
            this.crossoverRate = crossoverRate;
            return this;
        }

        public ConfigBuilder setMutationRate(float mutationRate) {
            this.mutationRate = mutationRate;
            return this;
        }

        public ConfigBuilder setElitismRate(float elitismRate) {
            this.elitismRate = elitismRate;
            return this;
        }

        public ConfigBuilder setSeed(int seed) {
            this.seed = seed;
            return this;
        }

        public Config createConfig(){
            return new Config(popSize, genSize, crossoverRate, mutationRate, elitismRate, seed);
        }
    }
}