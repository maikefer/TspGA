package ga;

import ga.crossover.CrossoverStrategy;
import ga.initialization.InitializationStrategy;
import ga.selection.SelectionStrategy;
import util.City;
import util.Percent;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

class GeneticAlgorithm {

    private final List<City> cities;
    private Population parentPop;
    private final int genSize;
    private final CrossoverStrategy crossoverStrategy;
    private final Random random;
    private final SelectionStrategy selectionStrategy;

    GeneticAlgorithm(Config config, InitializationStrategy initializationStrategy, SelectionStrategy selectionStrategy,
                     CrossoverStrategy crossoverStrategy) {

        this.selectionStrategy = selectionStrategy;
        this.random = new Random(config.seed);
        this.cities = initializationStrategy.createCities();
        this.genSize = config.genSize;
        this.parentPop = new Population(config.popSize, config.crossoverRate, config.mutationRate,
                                        config.elitismRate, cities, this.random);
        this.crossoverStrategy = crossoverStrategy;
    }

    Individual findBestIndividual() {

        for (int generationCounter = 0; generationCounter <= genSize; generationCounter++){
            parentPop = parentPop.reproduce(selectionStrategy, crossoverStrategy, random, this.cities.size());
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
        private final Percent crossoverRate;
        private final Percent mutationRate;
        private final Percent elitismRate;
        private final int seed;

        Config(int popSize, int genSize, Percent crossoverRate, Percent mutationRate, Percent elitismRate, int seed) {
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
        private Percent crossoverRate;
        private Percent mutationRate;
        private Percent elitismRate;
        private int seed;

        ConfigBuilder setPopSize(int popSize) {
            this.popSize = popSize;
            return this;
        }

        ConfigBuilder setGenSize(int genSize) {
            this.genSize = genSize;
            return this;
        }

        ConfigBuilder setCrossoverRate(Percent crossoverRate) {
            this.crossoverRate = crossoverRate;
            return this;
        }

        ConfigBuilder setMutationRate(Percent mutationRate) {
            this.mutationRate = mutationRate;
            return this;
        }

        ConfigBuilder setElitismRate(Percent elitismRate) {
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