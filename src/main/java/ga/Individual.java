package ga;

import util.City;

import java.util.List;
import java.util.Random;

/**
 * This class represents an individual. An individual is a solution for the GeneticAlgorithm.
 * <br/> <br/>
 * An individual contains a representation of the order of cities, as well as the specific java.java.tsp that
 * it provides a solution for. 
 * @author Maike Rees
 *
 */
public class Individual implements Comparable<Individual> {

	/**
	 * The representation of the order of the cities
	 */
	private final int[] cities;
	private final List<City> cityCoordinates;

	/**
	 * The Constructor <br/>
	 * The representation of the cities will be initialized randomly. 
	 * @param randomGenerator
	 */
	Individual(Random randomGenerator, List<City> cityCoordinates) {
		this.cities = new int[cityCoordinates.size()];
		this.initializeRandomly(randomGenerator);
		this.cityCoordinates = cityCoordinates;
	}
	
	/**
	 * The Fitness is the total (slow) distance traveled in this individual. We assume
	 * that every city is only one time inside the cities array.
	 * 
	 * @return the fitness value (the smaller the better)
	 */
	public int getFitness() {
		double fitness = 0;
		
		for (int i = 0; i < cities.length - 1; i++){
			fitness += Math.sqrt(calculateDistance(
					cityCoordinates.get(cities[i]).getX(),
					cityCoordinates.get(cities[i + 1]).getX(),
					cityCoordinates.get(cities[i]).getY(),
					cityCoordinates.get(cities[i + 1]).getY()));
		}
		
		fitness += Math.sqrt(calculateDistance(
								cityCoordinates.get(cities.length - 1).getX(),
								cityCoordinates.get(0).getX(),
								cityCoordinates.get(cities.length - 1).getY(),
								cityCoordinates.get(0).getY()));

		return (int)fitness;
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
	 * @param randomGenerator
	 */
	private void initializeRandomly(Random randomGenerator) {
		int city;
		boolean loop;

		for (int i = 0; i < cities.length; i++) {
			loop = true;

			while (loop) {
				city = randomGenerator.nextInt(cities.length);

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
	 * @param randomGenerator
	 */
	public void mutateReciprocalExchange(Random randomGenerator) {

		// take two random numbers within the array, that are not the same
		int k = randomGenerator.nextInt(cities.length);
		int j = randomGenerator.nextInt(cities.length);
		
		while (j == k) {
			j = randomGenerator.nextInt(cities.length);
		}
		
		// swap the cities located at the randomly generated positions
		int save = cities[k];
		cities[k] = cities[j];
		cities[j] = save;
	}
	
	void mutateBetter(Random randomGenerator) {
		int twoPoints[] = getTwoRandomPoints(randomGenerator);
		int temp;
		
		while (twoPoints[0] < twoPoints[1]) {
			temp = cities[twoPoints[0]];
			cities[twoPoints[0]] =  cities[twoPoints[1]];
			cities[twoPoints[1]] = temp;
			twoPoints[0]++;
			twoPoints[1]--;
		}
	}


	/**
	 * Calculates two random points within the length of the cities array.
	 * @return an array with two points whereas the first point is always smaller than the second
	 * @param randomGenerator
	 */
	public int[] getTwoRandomPoints(Random randomGenerator){
	
		int dimension = cities.length;
		int array[] = new int[2];
	
		array[0] = randomGenerator.nextInt(dimension);
		array[1] = randomGenerator.nextInt(dimension);
		
		while (array[0] ==  array[1]) {
			array[1] = randomGenerator.nextInt(dimension);
		}
		
		if (array[0] > array[1]) {
			int save = array[0];
			array[0] = array[1];
			array[1] = save;
		}
		
		return array;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int city : cities) {
			sb.append(city).append(" ");
		}
		return sb.toString();
	}

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
	 * @param randomGenerator
	 * @return The generated Child-Individual.
	 */
	public Individual getChildUOX(int mask[], Individual parent, Random randomGenerator){
		Individual children = new Individual(randomGenerator, this.cityCoordinates); // will get randomly initialized, but doesn't matter
		children.initializeWithMinus1();
		
		for (int i = 0; i < cities.length; i++) {
			if (mask[i] == 1) {
				children.cities[i] = this.cities[i];
			}
		}
		
		for (int i = 0; i < cities.length; i++) {
			if (mask[i] == 0) {
				// get missing numbers in order of second parent
				//  can make faster by remembering the last j 
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

	public Individual getChildPMX(int x, int y, Individual parent2, Random randomGenerator){
		
		Individual child = new Individual(randomGenerator, this.cityCoordinates);
		child.initializeWithMinus1();
		
//		int line1[] = new int[y - x];  (can use parents?)
//		int line2[] = new int[y - x];
		
		// copy randomly decided cities from parent 2 into child
        System.arraycopy(parent2.cities, x, child.cities, x, y + 1 - x);
		
	
		for (int i = 0; i < child.cities.length; i++){
			if(child.cities[i] == -1) {
				
				// fill cities from parent1 into cities, that are not going to be double
				if(!child.contains(this.cities[i])) {
					child.cities[i] = this.cities[i];
				} else { 
					// map the missing cities 
					int matchingNumber = this.cities[parent2.findPositionOf(this.cities[i], x, y)];
					
					while (child.contains(matchingNumber)){
						matchingNumber = this.cities[parent2.findPositionOf(matchingNumber, x, y)];
					} 
					
					child.cities[i] = matchingNumber;
				}
			} 
		}
		return child;
	}
	
	/**
	 * It finds the position of the transfered number within the current cities array.
	 * It only searches between x to y
	 * @param n
	 * @param x 
	 * @param y
	 * @return the position of the transfered number in the current cities-array
	 */
	private int findPositionOf(int n, int x, int y) {
		for (int i = x; i <= y; i++) {
			if (this.cities[i] == n) {
				return i;
			}
		}
		
		return -1;
	
	}
	
	
	/**
	 * Checks if the city represented by n is already contained 
	 * 		in the current Individual's city-representation 
	 * @param n The city represented by n
	 * @return if the city represented by n is already contained in the Individual's city representation
	 */
	private boolean contains(int n){
		for (int city : cities) {
			if (city == n) {
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
	public int compareTo(Individual otherIndividual) {
        return (this.getFitness() - otherIndividual.getFitness());
	}

	public int getCitySize() {
		return cities.length;
	}
}


