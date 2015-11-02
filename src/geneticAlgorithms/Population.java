package geneticAlgorithms;

import java.util.TreeSet;

/**
 * This class represents a population with a certain amount of individuals.
 * 
 * @author Maike Rees
 *
 */
public class Population {

	/**
	 * size describes how many individuals there are in one population
	 */
	private int size;
	
	/**
	 * The representation of the individuals
	 */
	protected Individual[] individuals;

	/**
	 * cizySize is the amount of cities that are accused by the TSB
	 */
	private int citySize;

	/**
	 * The amount of Individuals that will be compared in the tournament
	 * selection
	 */
	private final int tournamentNumber = 4;
	
	/**
	 * The crossoverRate describes how many of the Individuals in the population are allowed to reproduce.
	 * It should be between 0.0 and 1.0 since it represents a %-number.
	 */
	private float crossoverRate;
	
	/**
	 * The mutationRate describes how many of the Individuals in the population are allowed to mutate.
	 * It should be between 0.0 and 1.0 since it represents a %-number.
	 */
	private float mutationRate;
//	private boolean crossoverRateArray[];
//	private boolean mutationRateArray[];
	
	/**
	 * The elitismRate describes how many Elite-Individuals are chosen from the population 
	 * 				and put (without crossover and mutation) into the next generation.
	 */
	private float elitismRate;

	/**
	 * The Constructor.
	 * @param size How many Individuals are contained in the Population.
	 * @param tsp The TravelingSalesmanProblem that should be solved.
	 * @param crossoverRate How many Individuals are allowed to reproduce them selves.
	 * @param mutationRate How many Individuals will be mutated.
	 * @param elitismRate How many of the best Individuals will be taken straight to the next generation.
	 */
	public Population(int size, TravelingSalesmanProblem tsp, float crossoverRate, float mutationRate,
			float elitismRate) {
		this.size = size;
		this.citySize = tsp.getDimension();
		this.individuals = new Individual[size];
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
//		this.crossoverRateArray = new boolean[size];
//		this.mutationRateArray = new boolean[size];
		this.elitismRate = elitismRate;
	}

	/**
	 * Initialize the Individuals with a random order of the (unique) city-representations.
	 * @param tsp
	 */
	public void initializeIndividualsRandomly(TravelingSalesmanProblem tsp) {
		for (int i = 0; i < size; i++) {
			individuals[i] = new Individual(tsp);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append("I" + i + ": " + individuals[i].toString() + "\n");
		}
		return sb.toString();
	}

	/**
	 * Generate the offspring of the current population.
	 * @param tsp
	 * @return
	 */
	public Population reproduce(TravelingSalesmanProblem tsp) {
		// grab the elite individuals
		Individual elite[] = grabElite();

		Population childrenPop = new Population(size, tsp, crossoverRate, mutationRate, elitismRate);
		// insert elite individuals into new childPopulation
		for (int i = 0; i < elite.length; i++) {
			childrenPop.individuals[i] = elite[i];
		}

		Individual children[] = new Individual[2];
		int childrenCount = elite.length;
		Individual parent1, parent2;

		// generate as many children as needed until the population size is reached
		while (childrenCount < this.size) {

			// grab 2 parents with tournament-selection
			parent1 = selectTournament();
			parent2 = selectTournament();

			// get 2 children due to uox-crossover
			int mask[] = getRandomMask();

			// think of crossover rate
			if (crossoverOk()) {
				children[0] = parent1.getChild(mask, parent2);
				
				// mutate children
				// think of mutation rate
				if (mutationOk()) {
					children[0].mutateReciprocalExchange();
				}
				// put child into childrenPop
				childrenPop.individuals[childrenCount++] = children[0];
			}

			// if there is still space in the childrenPop for another child, get it
			if (childrenCount < this.size) {
				
				if (crossoverOk()) {
					children[1] = parent2.getChild(mask, parent1);

					// mutate child
					// think of mutation rate
					if (mutationOk()) {
						children[1].mutateReciprocalExchange();
					}
					childrenPop.individuals[childrenCount++] = children[1];
				}
			}
		}
		return childrenPop;
	}

	/**
	 * Gets the Size of the Population. Basically it's the length of the individuals-array.
	 * @return size of the population
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Gets the individual at position n in individuals[]
	 * 
	 * @param n
	 * @return Individual at position n
	 */
	public Individual getIndividual(int n) {
		return individuals[n];
	}

	/**
	 * Tournament Selection: 
	 * Selects a number of random individuals of the population and returns the
	 * individual with the best fitness.
	 * 
	 * @return the individual with the best fitness
	 */
	public Individual selectTournament() {

		Individual tournament[] = new Individual[tournamentNumber];

		for (int i = 0; i < tournamentNumber; i++) {
			// a number between 0 and size-1 if size is smaller than
			// 100
			int k = (int) (Math.random() * 100.0) % size;
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
	 * @return If the Individual is allowed to reproduce
	 */
	private boolean crossoverOk() {
		int absolute = (int) (crossoverRate * 100);
		
		// Random number between 0 and 99
		int k = (int) (Math.random() * 100.0);

		if (k <= absolute) {
			return true;
		} else {
			return false;
		}

		// random number
		// int k = (int) (Math.random() * 100.0) % size;
		// int i = 0;
		// // unique k
		// while (crossoverRateArray[k]) {
		// k = (int) (Math.random() * 100.0) % size;
		// i++;
		// System.out.println("stuck " + i);
		// }
		//
		// crossoverRateArray[k] = true;
		//
		// // number to be true
		// int numTrue = (int) (crossoverRate * size);
		//
		// if (k <= numTrue) {
		// return true;
		// } else {
		// return false;
		// }
	}

	/**
	 * Checks if an Individual is allowed to mutate (due to the mutationRate).
	 * @return If the individual is allowed to mutate.
	 */
	private boolean mutationOk() {
		
		int absolute = (int) (mutationRate * 100);
		
		// Random number between 0 and 99
		int k = (int) (Math.random() * 100.0);

		if (k <= absolute) {
			return true;
		} else {
			return false;
		}
		
		// random number
//		int k = (int) (Math.random() * 100.0) % size;
//
//		while (mutationRateArray[k]) {
//			k = (int) (Math.random() * 100.0) % size;
//		}
//
//		mutationRateArray[k] = true;
//
//		// number to be true
//		int numTrue = (int) mutationRate * size;
//
//		if (k <= numTrue) {
//			return true;
//		} else {
//			return false;
//		}
	}

	/**
	 * Generates a random Bit-Mask with length = citySize
	 * @return generated Bit-Mask
	 * TODO: better if boolean[], not int[] ?
	 */
	private int[] getRandomMask() {
		int mask[] = new int[citySize];

		for (int i = 0; i < citySize; i++) {
			double j = Math.random();
			if (j < 0.5) {
				mask[i] = 0;
			} else {
				mask[i] = 1;
			}
		}
		return mask;
	}

	/**
	 * Grabs the elite (due to the elitismRate) of the current population.
	 * @return the elite individuals of the population
	 */
	private Individual[] grabElite() {

		// calculate the amount of "best" chromosomes that are going to be picked
		int amount = (int) elitismRate * size;

		// ascendingly ordered --> grab the first amount
		TreeSet<Individual> map = new TreeSet<Individual>();

		// order Individuals from population in a TreeSet according to their fitness
		for (int i = 0; i < size; i++) {
			map.add(individuals[i]);
		}

		// grab the amount of Individuals of the TreeSet
		Individual elite[] = new Individual[amount];

		for (int i = 0; i < amount; i++) {
			elite[i] = map.pollFirst();
		}
		return elite;
	}
}
