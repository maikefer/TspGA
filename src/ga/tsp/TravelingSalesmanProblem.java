package ga.tsp;

import ga.Individual;
import ga.Population;
import ga.Runner;
import ga.crossover.PMX;
import ga.tsp.initialization.InitializationStrategy;

public class TravelingSalesmanProblem {

    /**
     * The coordinates of the cities.
     */
    private int kmOfCityLinks[][];

    private Population parentPop;
    private int genSize;

    public TravelingSalesmanProblem(int popSize, int genSize, float crossoverRate, float mutationRate,
                                    float elitismRate, InitializationStrategy initializationStrategy) {

        this.kmOfCityLinks = initializationStrategy.createCities();
        this.genSize = genSize;
        this.parentPop = new Population(popSize, this, crossoverRate, mutationRate, elitismRate);
    }

    /**
     * @return the x-value of the desired node
     */
    public int getX(int nodeIdx) {
        return kmOfCityLinks[nodeIdx][0];
    }

    /**
     * @return the y-value of the desired node
     */
    public int getY(int nodeIdx) {
        return kmOfCityLinks[nodeIdx][1];
    }

    /**
     * Finds a solution with a genetic algorithm
     */
    public Individual findBestIndividual() {
        Individual bestIndividual = parentPop.getFittestIndividual();

        int generationCounter = 0;
        Runner.printInFile("averageFitness" + "," + "bestFitness");


        while (generationCounter < genSize + 1) {
            String string = (int) parentPop.getAverageFitness() + "," + bestIndividual.getFitness();
            Runner.printInFile(string);
//			Runner.printInFile((int)parentPop.getAverageFitness());
//			Runner.printInFile(solution.getFitness());

            parentPop = parentPop.reproduce(new PMX());
            // save best child as solution
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
        return kmOfCityLinks.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int amountCities = kmOfCityLinks.length;

        sb.append("Dimension: ").append(amountCities);
        sb.append("\nPopulationSize: ").append(parentPop.getAmountOfIndividuals());
        sb.append("\nNodes:");

        for (int i = 0; i < amountCities; i++) {
            sb.append("\n")
                    .append("no: ")
                    .append(i)
                    .append(" x: ")
                    .append(kmOfCityLinks[i][0])
                    .append(" y: ")
                    .append(kmOfCityLinks[i][1]);
        }
        return sb.toString();
    }
}