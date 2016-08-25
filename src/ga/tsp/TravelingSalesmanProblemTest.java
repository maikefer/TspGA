package ga.tsp;

import ga.Individual;
import ga.crossover.PMX;
import ga.crossover.UOX;
import ga.tsp.initialization.BerlinInitialization;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TravelingSalesmanProblemTest {


    @Test
    public void testTSPWithBerlinDataAndPMX() {

        TravelingSalesmanProblem tsp = new TravelingSalesmanProblem(300, 1000, 0.7f, 0.2f, 0.1f,
                new BerlinInitialization(), new PMX());

        Individual bestIndividual = tsp.findBestIndividual();

        int fitness = bestIndividual.getFitness();
        assertTrue(fitness < 10000);
    }

    @Test
    public void testTSPWithBerlinDataAndUOX() {

        TravelingSalesmanProblem tsp = new TravelingSalesmanProblem(300, 1000, 0.7f, 0.2f, 0.1f,
                new BerlinInitialization(), new UOX());

        Individual bestIndividual = tsp.findBestIndividual();

        int fitness = bestIndividual.getFitness();
        assertTrue(fitness < 10000);
    }

}