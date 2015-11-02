package geneticAlgorithms;

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
	 * The amount of Individuals that will be compared in the tournament selection
	 */
	private final int tournamentNumber = 4;
	private final float crossoverRate = 1.0F;
	private final float mutationRate = 0.1F;

	public Population(int size, TravelingSalesmanProblem tsp) {
		this.size = size;
		this.citySize = tsp.getDimension();
		this.individuals = new Individual[size];
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

	public void doCrossover() {

	}

	public Population reproduce(TravelingSalesmanProblem tsp) {

		Population children = new Population(this.citySize, tsp);
		int childrenCount = 0;
		// elitism

		Individual parent1, parent2;
		
		while (childrenCount < this.size) {
			// grab 2 parents
			
			parent1 = selectTournament();
			parent2 = selectTournament();
			
			// think of crossover rate
			
			// get 2 children due to crossover
			Individual childrens[] = parent1.getChildrenUOX(parent2);
			

			// think of mutation rate
			// mutate both children

			// put children into childpop

		}
		return children;
	}

	public int getSize() {
		return size;
	}

	/**
	 * Gets the individual at position n in individuals[]
	 * @param n
	 * @return
	 */
	public Individual getIndividual(int n) {
		return individuals[n];
	}

	/**
	 * Selects a number of random individuals of the population and returns the individual
	 *  with the best fitness.
	 * 
	 * @return the individual with the best fitness
	 */
	public Individual selectTournament(){
		
		Individual tournament[] = new Individual[tournamentNumber];
		
		for (int i = 0; i < tournamentNumber + 1; i++) {
			int k = (int) (Math.random() * 100.0) % citySize; // a number between 0 and citySize-1
			tournament[i] = this.getIndividual(k);
		}
		
		Individual winner = tournament[0];
		for (int i = 1; i < tournamentNumber + 1; i++){
			if (tournament[i].getFitness() < winner.getFitness()){
				winner = tournament[i];
			}
		}
		
		return winner;
	}
}
