package ga;

import ga.GeneticAlgorithm.ConfigBuilder;
import ga.crossover.PMX;
import ga.crossover.UOX;
import ga.initialization.BerlinInitialization;
import ga.selection.TournamentSelection;
import org.junit.Test;
import util.Percent;

import static org.junit.Assert.assertTrue;

public class GeneticAlgorithmTest {

    @Test
    public void testTSPWithBerlinDataAndPMXIsFastAndHasGoodFitness() {

        ConfigBuilder configBuilder = new ConfigBuilder()
                .setPopSize(300)
                .setGenSize(1000)
                .setCrossoverRate(new Percent(0.7))
                .setMutationRate(new Percent(0.2))
                .setElitismRate(new Percent(0.1))
                .setSeed(500);

        GeneticAlgorithm tsp = new GeneticAlgorithm(configBuilder.createConfig(), new BerlinInitialization(),
                new TournamentSelection(5), new PMX());

        long time = System.currentTimeMillis();
        Individual bestIndividual = tsp.findBestIndividual();
        time = System.currentTimeMillis() - time;

        System.out.println(time);
        assertTrue(time < 5000);

        int fitness = bestIndividual.getFitness();
        System.out.println(fitness);
        assertTrue(fitness < 10000);
    }

    @Test
    public void testTSPWithBerlinDataAndUOXisFastAndHasGoodFitness() {

        ConfigBuilder configBuilder = new ConfigBuilder()
                .setPopSize(300)
                .setGenSize(1000)
                .setCrossoverRate(new Percent(0.7))
                .setMutationRate(new Percent(0.2))
                .setElitismRate(new Percent(0.1))
                .setSeed(500);

        GeneticAlgorithm tsp = new GeneticAlgorithm(configBuilder.createConfig(), new BerlinInitialization(),
                new TournamentSelection(5), new UOX());

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