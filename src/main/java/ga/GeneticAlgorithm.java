package ga;

import ga.crossover.CrossoverStrategy;
import ga.initialization.InitializationStrategy;
import util.City;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

class GeneticAlgorithm {

    private final List<City> cities;
    private Population parentPop;
    private final int genSize;
    private final CrossoverStrategy crossoverStrategy;
    private final Random random;

    GeneticAlgorithm(Config config, InitializationStrategy initializationStrategy,
                     CrossoverStrategy crossoverStrategy) {

        this.random = new Random(config.seed);
        this.cities = initializationStrategy.createCities();
        this.genSize = config.genSize;
        this.parentPop = new Population(config.popSize, config.crossoverRate, config.mutationRate,
                                        config.elitismRate, cities, this.random);
        this.crossoverStrategy = crossoverStrategy;
    }

    Individual findBestIndividual() {

        for (int generationCounter = 0; generationCounter <= genSize; generationCounter++){
            parentPop = parentPop.reproduce(crossoverStrategy, random, this.cities.size());
        }

        return parentPop.getFittestIndividual();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int amountCities = this.cities.size();

        sb.append("AmountCities: ").append(amountCities);
        sb.append("\nPopulationSize: ").append(parentPop.getAmountOfIndividuals());
        sb.append("\nNodes:");

        IntStream.range(0, amountCities).forEach( i ->
                sb.append("\n")
                .append("no: ")
                .append(i)
                .append(" x: ")
                .append(cities.get(i).getX())
                .append(" y: ")
                .append(cities.get(i).getY()));

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

    static class ConfigBuilder {

        private int popSize;
        private int genSize;
        private float crossoverRate;
        private float mutationRate;
        private float elitismRate;
        private int seed;

        ConfigBuilder setPopSize(int popSize) {
            this.popSize = popSize;
            return this;
        }

        ConfigBuilder setGenSize(int genSize) {
            this.genSize = genSize;
            return this;
        }

        ConfigBuilder setCrossoverRate(float crossoverRate) {
            this.crossoverRate = crossoverRate;
            return this;
        }

        ConfigBuilder setMutationRate(float mutationRate) {
            this.mutationRate = mutationRate;
            return this;
        }

        ConfigBuilder setElitismRate(float elitismRate) {
            this.elitismRate = elitismRate;
            return this;
        }

        ConfigBuilder setSeed(int seed) {
            this.seed = seed;
            return this;
        }

        Config createConfig(){
            return new Config(popSize, genSize, crossoverRate, mutationRate, elitismRate, seed);
        }
    }
}