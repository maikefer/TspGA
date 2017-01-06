package ga;

import ga.crossover.PMX;
import ga.crossover.UOX;
import ga.initialization.BerlinInitialization;
import org.junit.Test;
import ga.GeneticAlgorithm.*;

import static org.junit.Assert.assertTrue;

public class GeneticAlgorithmTest {

    @Test
    public void testTSPWithBerlinDataAndPMXIsFastAndHasGoodFitness() {

        ConfigBuilder configBuilder = new ConfigBuilder()
                .setPopSize(300)
                .setGenSize(1000)
                .setCrossoverRate(0.7f)
                .setMutationRate(0.2f)
                .setElitismRate(0.1f)
                .setSeed(500);

        GeneticAlgorithm tsp = new GeneticAlgorithm(configBuilder.createConfig(), new BerlinInitialization(),
                new PMX());

        long time = System.currentTimeMillis();
        Individual bestIndividual = tsp.findBestIndividual();
        time = System.currentTimeMillis() - time;

        System.out.println(time);
        assertTrue(time < 5000);

        int fitness = bestIndividual.getFitness();
        assertTrue(fitness < 10000);
    }

    @Test
    public void testTSPWithBerlinDataAndUOXisFastAndHasGoodFitness() {

        ConfigBuilder configBuilder = new ConfigBuilder()
                .setPopSize(300)
                .setGenSize(1000)
                .setCrossoverRate(0.7f)
                .setMutationRate(0.2f)
                .setElitismRate(0.1f)
                .setSeed(500);

        GeneticAlgorithm tsp = new GeneticAlgorithm(configBuilder.createConfig(), new BerlinInitialization(), new UOX());

        long time = System.currentTimeMillis();
        Individual bestIndividual = tsp.findBestIndividual();
        time = System.currentTimeMillis() - time;

        System.out.println(time);
        assertTrue(time < 8000);

        int fitness = bestIndividual.getFitness();
        System.out.println(fitness);
        assertTrue(fitness < 11000);
    }
}