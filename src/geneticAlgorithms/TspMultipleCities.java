package geneticAlgorithms;

public class TspMultipleCities extends TravelingSalesmanProblem {

	public TspMultipleCities(int dimension, int popSize, int genSize, float crossoverRate, float mutationRate,
								float elitisimRate) {
				
			super(dimension, popSize, genSize, crossoverRate, mutationRate, elitisimRate);
			TspBerlin arrayCopy = new TspBerlin(popSize, genSize, crossoverRate, mutationRate, elitisimRate);
			initialize(arrayCopy.nodes);
			
	}
	
	private void initialize(int[][] arrayCopy){
		
		for(int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < 2; j++) {
				this.nodes[i][j] = arrayCopy[i][j];
			}
		}
	}

	
	
}
