/**
 * A Tsp for testing purposes.
 * It uses real City - Data but only as much as you want it to.
 * @author Maike
 *
 */
public class TspMultipleCities extends TravelingSalesmanProblem {

	/**
	 * The Constructor creates a new Tsp.
	 * @param dimension
	 * @param popSize
	 * @param genSize
	 * @param crossoverRate
	 * @param mutationRate
	 * @param elitisimRate
	 */
	public TspMultipleCities(int dimension, int popSize, int genSize, float crossoverRate, float mutationRate,
								float elitisimRate) {
				
			super(dimension, popSize, genSize, crossoverRate, mutationRate, elitisimRate);
			TspBerlin arrayCopy = new TspBerlin(popSize, genSize, crossoverRate, mutationRate, elitisimRate);
			initialize(arrayCopy.nodes);
			
	}
	
	/**
	 * This method copies as many city-coordinates as determined in the constructor 
	 * 				form the integer array that is transfered.
	 * @param arrayCopy
	 */
	private void initialize(int[][] arrayCopy){
		
		for(int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < 2; j++) {
				this.nodes[i][j] = arrayCopy[i][j];
			}
		}
	}

	
	
}
