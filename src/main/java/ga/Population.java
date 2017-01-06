package ga;

import ga.crossover.CrossoverStrategy;

import tsp.TravelingSalesmanProblem;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class represents a population with a certain amount of individuals.
 */
public class Population {

    /**
     * The representation of the individuals
     */
    private List<Individual> individuals;

    /**
     * cizySize is the amount of cities that are accused by the TSB
     */
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

    private final TravelingSalesmanProblem tsp;

    public Population(int size, TravelingSalesmanProblem tsp, float crossoverRate, float mutationRate,
                      float elitismRate) {

        this(randomIndividuals(size, tsp), tsp, crossoverRate, mutationRate, elitismRate);
    }

    private Population(List<Individual> individuals, TravelingSalesmanProblem tsp, float crossoverRate,
                       float mutationRate, float elitismRate) {

        this.tsp = tsp;
        this.citySize = tsp.getAmountCities();
        this.individuals = new ArrayList<>();
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismRate = elitismRate;
        this.individuals = individuals;
    }

    private static List<Individual> randomIndividuals(int size, TravelingSalesmanProblem tsp) {
        return IntStream.range(0, size).mapToObj(i -> new Individual(tsp)).collect(Collectors.toList());
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
    public Population reproduce(CrossoverStrategy crossoverStrategy) {
        List<Individual> elite = this.grabElite();

        PopulationBuilder childrenPop = new PopulationBuilder(individuals.size());

        // insert elite individuals into new childPopulation
        elite.forEach(childrenPop::add);


        while (!childrenPop.isFull()) {

            Individual parent1 = this.selectTournament();
            Individual parent2 = this.selectTournament();

            Pair<Individual, Individual> children = crossoverStrategy.doCrossover(parent1, parent2);

            Individual individualToAdd;

            if (crossoverOk()) {
                individualToAdd = children.getKey();
            } else {
                individualToAdd = parent1;
            }
            if (mutationOk()) {
                individualToAdd.mutateBetter();
            }
            childrenPop.add(individualToAdd);

            if (!childrenPop.isFull()) {

                if (crossoverOk()) {
                    individualToAdd = children.getValue();
                } else {
                    individualToAdd = parent2;
                }
                if (mutationOk()) {
                    individualToAdd.mutateBetter();
                }
                childrenPop.add(individualToAdd);
            }
        }

        return childrenPop.makePopulation(this.tsp, this.crossoverRate, this.mutationRate, this.elitismRate);

//        Individual parent1, parent2;
//        int childrenCount = elite.size();
//        int childrenLeftToCreate = individuals.size() - childrenCount;
//        List<Individual> children = new ArrayList<>();
//
//        while (childrenLeftToCreate > 0) {
//
//            parent1 = selectTournament();
//            parent2 = selectTournament();
//
//            Pair<Individual, Individual> childrenFromCrossover = crossoverStrategy.doCrossover(parent1, parent2);
//
//            children.add(childrenFromCrossover.getKey());
//            childrenLeftToCreate--;
//            if (childrenLeftToCreate >= 1) {
//                children.add(childrenFromCrossover.getValue());
//                childrenLeftToCreate--;
//            }
//
//        }
//
////        Individual children[] = new Individual[2];
////        Individual parent1, parent2;
//
//        // generate as many children as needed until the population size is reached
//        while (childrenCount < individuals.size()) {
//
//            // grab 2 parents with tournament-selection
//            parent1 = selectTournament();
//            parent2 = selectTournament();
//
//
//            // get 2 children
//
//            // UOX:
////			int mask[] = getRandomMask();
//            // PMX:
//            int pointsForPmx[] = parent1.getTwoRandomPoints();
//
//            // think of crossover rate
//            if (crossoverOk()) {
//                // UOX:
////				children[0] = parent1.getChildUOX(mask, parent2);
//                // PMX:
//                children[0] = parent1.getChildPMX(pointsForPmx[0], pointsForPmx[1], parent2);
//
//                // mutate children
//                // think of mutation rate
//                if (mutationOk()) {
////					children[0].mutateReciprocalExchange();
//                    children[0].mutateBetter();
//                }
//
//            } else {
//                children[0] = parent1;
//            }
//
//            // put child (or parent if no crossover) into childrenPop
//            childrenPop.individuals.set(childrenCount++, children[0]);
//
//            // if there is still space in the childrenPop for another child, get it
//            if (childrenCount < individuals.size()) {
//
//                if (crossoverOk()) {
//                    // UOX:
////					children[1] = parent2.getChildUOX(mask, parent1);
//                    // PMX:
//                    children[1] = parent2.getChildPMX(pointsForPmx[0], pointsForPmx[1], parent1);
//
//                    // mutate child
//                    // think of mutation rate
//                    if (mutationOk()) {
////						children[1].mutateReciprocalExchange();
//                        children[1].mutateBetter();
//                    }
//
//                } else {
//                    children[1] = parent2;
//                }
//
//                childrenPop.individuals.set(childrenCount++, children[1]);
//            }
//        }
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
     */
    private Individual selectTournament() {

        int tournamentNumber = 5;
        Individual tournament[] = new Individual[tournamentNumber];

        for (int i = 0; i < tournamentNumber; i++) {
            int k = Runner.randomGenerator.nextInt(individuals.size());
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
     */
    private boolean crossoverOk() {
        int absolute = (int) (crossoverRate * 100);
        int k = Runner.randomGenerator.nextInt(100);

        return k <= absolute;
    }

    /**
     * Checks if an Individual is allowed to mutate (due to the mutationRate).
     *
     * @return If the individual is allowed to mutate.
     */
    private boolean mutationOk() {
        int absolute = (int) (mutationRate * 100);
        int k = Runner.randomGenerator.nextInt(100);
        return k <= absolute;
    }

    /**
     * Generates a random Bit-Mask with length = citySize
     *
     * @return generated Bit-Mask
     * TODO: better if boolean[], not int[] ?
     */
    private int[] getRandomMask() {
        int mask[] = new int[citySize];

        for (int i = 0; i < citySize; i++) {
            double j = Runner.randomGenerator.nextInt(100);
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

        private Population makePopulation(TravelingSalesmanProblem tsp, float crossoverRate, float mutationRate,
                                          float elitismRate) {
            return new Population(individuals, tsp, crossoverRate, mutationRate, elitismRate);
        }

        boolean isFull() {
            return individuals.size() >= size;
        }
    }
}
