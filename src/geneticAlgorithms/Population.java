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
	
	public Population(int size,TravelingSalesmanProblem tsp){
		this.size = size;
		this.citySize = tsp.getDimension();
		this.individuals = new Individual[size];
		initializeIndividualsRandomly(tsp);
	}
	
	public void initializeIndividualsRandomly(TravelingSalesmanProblem tsp){
		for (int i = 0; i < size; i++) {
			individuals[i] = new Individual(tsp);
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++){
			sb.append("I"+ i + ": " + individuals[i].toString() + "\n");
		}
		return sb.toString();
	}
	
	public void doCrossover(){
		
	}
	
	public void reproduce(){
		
	}
	
	public int getSize(){
		return size;
	}
}
