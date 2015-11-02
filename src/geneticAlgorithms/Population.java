package geneticAlgorithms;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

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
	private float crossoverRate;
	private float mutationRate;
	private boolean crossoverRateArray[];
	private boolean mutationRateArray[];
	private float elitismRate;

	public Population(int size, TravelingSalesmanProblem tsp, float crossoverRate, float mutationRate,
			float elitismRate) {
		this.size = size;
		this.citySize = tsp.getDimension();
		this.individuals = new Individual[size];
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.crossoverRateArray = new boolean[size];
		this.mutationRateArray = new boolean[size];
		this.elitismRate = elitismRate;
	}

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

	public Population reproduce(TravelingSalesmanProblem tsp) {
		// elitism
		Individual elite[] = grabElite();

		Population childrenPop = new Population(size, tsp, crossoverRate, mutationRate, elitismRate);
		for (int i = 0; i < elite.length; i++) {
			childrenPop.individuals[i] = elite[i];
		}

		Individual children[] = new Individual[2];
		int childrenCount = elite.length;
		Individual parent1, parent2;

		while (childrenCount < this.size) {

//			System.out.println("childrenCount: " + childrenCount);

			// grab 2 parents
			parent1 = selectTournament();
			parent2 = selectTournament();

			// get 2 children due to crossover
			int mask[] = getRandomMask();

			// think of crossover rate
			if (crossoverOk()) {
				children[0] = parent1.getChild(0, mask, parent2);
				if (mutationOk()) {
					children[0].mutateReciprocalExchange();
				}
				// put children into childrenPop
				childrenPop.individuals[childrenCount++] = children[0];
			}

//			System.out.println("before 2nd child");
			if (childrenCount < this.size) {
				if (crossoverOk()) {
//					System.out.println("in second child");
					children[1] = parent2.getChild(1, mask, parent1);
					if (mutationOk()) {
						children[1].mutateReciprocalExchange();
					}
					// what if childpop.size is odd? Check that! array overflow!
//					System.out.println("childCount: " + childrenCount + "size: " + size);

					childrenPop.individuals[childrenCount++] = children[1];
				}
			}

			// mutate children
			// think of mutation rate

		}
		return childrenPop;
	}

	public int getSize() {
		return size;
	}

	/**
	 * Gets the individual at position n in individuals[]
	 * 
	 * @param n
	 * @return
	 */
	public Individual getIndividual(int n) {
		// if (n > 49) {
		// System.out.println("ala");
		// }
		return individuals[n];
	}

	/**
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

	private boolean crossoverOk() {
		int absolute = (int) (crossoverRate * 100);
		// System.out.println("absolute: " + absolute);
		// Random number between 0 and 99
		int k = (int) (Math.random() * 100.0);
//		System.out.println("k: " + k);

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

	private boolean mutationOk() {
		// random number
		int k = (int) (Math.random() * 100.0) % size;

		while (mutationRateArray[k]) {
			k = (int) (Math.random() * 100.0) % size;
		}

		mutationRateArray[k] = true;

		// number to be true
		int numTrue = (int) mutationRate * size;

		if (k <= numTrue) {
			return true;
		} else {
			return false;
		}
	}

	private int[] getRandomMask() {
		int mask[] = new int[citySize];

		for (int i = 0; i < citySize; i++) {
			double j = Math.random();
			if (j < 0.5) {
				mask[i] = 0;
			} else {
				mask[i] = 1;
			}
			// System.out.print(mask[i] + " ");
		}

		return mask;
	}

	private Individual[] grabElite() {

		// how many "best" chromosomes are going to be picked
		int amount = (int) elitismRate * size;

		// ascendingly ordered --> grab the first amount
		TreeSet<Individual> map = new TreeSet<Individual>();

		// order Individuals from population in a TreeSet according to their
		// fitness
		for (int i = 0; i < size; i++) {
			map.add(individuals[i]);
		}

		// grab the first amount of Individuals of TreeSet
		Individual elite[] = new Individual[amount];

		for (int i = 0; i < amount; i++) {
			elite[i] = map.pollFirst();
		}

		return elite;
	}

}
