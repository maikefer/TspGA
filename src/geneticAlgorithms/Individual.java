package geneticAlgorithms;

import java.util.Random;

/**
 * This class represents an individual. An individual is a solution for the TravelingSalesmanProblem. 
 * <br/> <br/>
 * An individual contains a representation of the order of cities, as well as the specific tsp that 
 * it provides a solution for. 
 * @author Maike Rees
 *
 */
public class Individual implements Comparable<Individual> {

	/**
	 * The representation of the order of the cities
	 */
	private int[] cities;

	/**
	 * The specific TravelingSalesmanProblem
	 */
	private TravelingSalesmanProblem tsp;

	/**
	 * The Constructor <br/>
	 * The representation of the cities will be initialized randomly. 
	 * @param tsp The specific tsp for which this Individual represents a solution
	 */
	public Individual(TravelingSalesmanProblem tsp) {
		this.cities = new int[tsp.getDimension()];
		this.tsp = tsp;
		this.initializeRandomly();
	}

	/**
	 * The Fitness is the total (fast) distance traveled in this individual. We assume
	 * that every city is only one time inside the cities array
	 * 
	 * @return the fitness value (the smaller the better)
	 */
	public int getFitness() {
		
		return (int)getRealFitness();
//		int fitness = 0;
//		
//		for (int i = 0; i < cities.length - 1; i++){
//			fitness += calculateDistance(tsp.getX(cities[i]), tsp.getX(cities[i + 1]), tsp.getY(cities[i]), tsp.getY(cities[i + 1]));
//		}
//		
//		fitness += calculateDistance(tsp.getX(cities.length - 1), tsp.getX(0), 
//						tsp.getY(cities.length - 1), tsp.getY(0));
//
//		return fitness;
	}
	
	/**
	 * The Fitness is the total (slow) distance traveled in this individual. We assume
	 * that every city is only one time inside the cities array.
	 * 
	 * @return the fitness value (the smaller the better)
	 */
	public double getRealFitness() {
		double fitness = 0;
		
		for (int i = 0; i < cities.length - 1; i++){
			fitness += Math.sqrt(calculateDistance(tsp.getX(cities[i]), tsp.getX(cities[i + 1]), 
						tsp.getY(cities[i]), tsp.getY(cities[i + 1])));
		}
		
		fitness += Math.sqrt(calculateDistance(tsp.getX(cities.length - 1), tsp.getX(0), 
						tsp.getY(cities.length - 1), tsp.getY(0)));
		return fitness;
	}

	/**
	 * This method calculates the "fast" euclidean distance between two points
	 * (it's called fast because we leave out the sqr-calculation
	 * 
	 * @param x1
	 *            x-value of 1st point
	 * @param x2
	 *            x-value of 2nd point
	 * @param y1
	 *            y-value of 1st point
	 * @param y2
	 *            y-value of 2nd point
	 * @return (fast) distance between two points
	 */
	private int calculateDistance(int x1, int x2, int y1, int y2) {
		return (((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
	}

	/**
	 * Initialize the city-representation with a random order. <br/> 
	 * But every city is only once represented.
	 */
	public void initializeRandomly() {
		int city = 0;
		Random generator = new Random();
		boolean loop;

		for (int i = 0; i < cities.length; i++) {
			loop = true;

			while (loop) {
				city = Math.abs(generator.nextInt() % (cities.length));

				// '\u0000' is the default value for chars --> char hasn't been 
				// initialized
				if (cities[city] == '\u0000') { 
					cities[city] = i;
					loop = false;
				}	
			}
		}
	}

	/**
	 * This is a mutation method.
	 * It swaps two numbers at random unique positions of the cities-array. 
	 */
	public void mutateReciprocalExchange() {

		// take two random numbers within the array, that are not the same
		int k = (int) (Math.random() * 100.0) % cities.length;
		int j = (int) (Math.random() * 100.0) % cities.length;
		
		while (j == k) {
			j = (int) (Math.random() * 100.0) % cities.length;
		}
		
		// swap the cities located at the randomly generated positions
		int save = cities[k];
		cities[k] = cities[j];
		cities[j] = save;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < cities.length; i++){
			sb.append(cities[i] + " ");
		}
		
//		sb.append("\nlenght: " + cities.length );
		
		return sb.toString();

	}

	/**
	 * checks if every number occurs only once in the array
	 * @return
	 */
	public boolean isValid(){
		boolean alreadyIn[] = new boolean[cities.length];
		
		for (int i = 0; i < cities.length; i++){
			if (alreadyIn[cities[i]]){
				System.out.println(i + " and value " + cities[i]);
				return false;
			} else {
				alreadyIn[cities[i]] = true; 
			}
		}
		return true;	
	}
	
	/**
	 * Gets a child using the uniform order crossover algorithm. <br/>
	 * Parent1 is the current Individual.
	 * 
	 * @param mask The generated Bit-mask.
	 * @param parent The second parent.
	 * @return The generated Child-Individual.
	 */
	public Individual getChild(int mask[], Individual parent){
		Individual children = new Individual(tsp); // will get randomly initialized, but doesn't matter
		children.initializeWithMinus1();
		
		for (int i = 0; i < cities.length; i++) {
			if (mask[i] == 1) {
				children.cities[i] = this.cities[i];
			}
		}
		
		for (int i = 0; i < cities.length; i++) {
			if (mask[i] == 0) {
				// get missing numbers in order of second parent
				// TODO can make faster by remembering the last j 
						// (don't have to start looking from the beginning)
			
				for (int j = 0; j < cities.length; j++) {
					if (!children.contains(parent.cities[j])){
						children.cities[i] = parent.cities[j];
						break;
					}
				}
			}
		}
		
		return children;
	}
	
	/**
	 * Checks if the city represented by n is already contained 
	 * 		in the current Individual's city-representation 
	 * @param n The city represented by n
	 * @return if the city represented by n is already contained in the Individual's city representation
	 */
	private boolean contains(int n){
		for (int i = 0; i < cities.length; i++) {
			if (cities[i] == n) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Initialize a city-representation with only -1.
	 */
	private void initializeWithMinus1 () {
		for (int i = 0; i < cities.length; i++) {
			cities[i] = -1;
		}
	}

	@Override
	public int compareTo(Individual arg0) {
		/*
		 * returns:
		 * neg int - arg0 < arg1
		 * 0 - arg0 = arg1
		 * pos int - arg0 > arg1
		 */
	
		
		int fitness0 = this.getFitness();
		int fitness1 = arg0.getFitness();
		
		if (fitness0 < fitness1){
			return -1;
		} else if (fitness0 == fitness1) {
			return 0;
		} else {
			return 1;
		}
	}
}


